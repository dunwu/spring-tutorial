package io.github.dunwu.springboot.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import io.github.dunwu.springboot.dto.BaseResponse;
import io.github.dunwu.springboot.dto.CodeEn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 本类仅为展示如何使用 Fastjson 替换 Spring Boot 默认使用的 Jackson
 *
 * @author Zhang Peng
 * @since 2018-12-29
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义消息转换器：使用 Fastjson 替换 Spring Boot 默认使用的 Jackson
     *
     * @param converters 消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 清除默认 Json 转换器
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);

        // 配置 FastJson
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);

        // 添加 FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastJsonHttpMessageConverter);

        // 添加 StringHttpMessageConverter，解决中文乱码问题
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringHttpMessageConverter);


    }

    /**
     * 自定义异常拦截处理器
     *
     * @param exceptionResolvers 异常处理器
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers
            .add((HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) -> {
                StringBuilder sb = new StringBuilder();
                CodeEn code;
                if (e instanceof AppException) {
                    code = CodeEn.APP_ERROR;
                    sb.append(CodeEn.APP_ERROR.message());
                } else {
                    code = CodeEn.OTHER_ERROR;
                    sb.append(CodeEn.OTHER_ERROR.message());
                }
                sb.append("，详细错误原因：");
                sb.append(e.getMessage());
                BaseResponse<?> baseResponse = BaseResponse.fail(code.code(), sb.toString());
                responseResponse(response, baseResponse);
                return new ModelAndView();
            });
    }

    private void responseResponse(HttpServletResponse response, BaseResponse<?> baseResponse) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(baseResponse));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
