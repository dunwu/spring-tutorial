package org.zp.notes.spring.web.controller.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 基础 Controller
 * @author Zhang Peng
 */
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 成功情况下的返回对象
     */
    protected <T> ResponseDTO<T> returnWithSuccess(T t) {
        return new ResponseDTO<T>(t);
    }

    /**
     * 成功情况下的返回对象
     */
    protected <T> ResponseDTO<T> returnWithSuccess(String msg, T t) {
        ResponseDTO<T> dto = new ResponseDTO<T>();
        dto.addMsg(msg);
        dto.setData(t);
        return dto;
    }

    /**
     * 成功情况下的返回 json 对象
     */
    protected <T> String returnWithSuccessInJson(T t) throws JsonProcessingException {
        ResponseDTO responseDTO = returnWithSuccess(t);

        // 序列化为 json 形式返回
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(responseDTO);
    }

    /**
     * 成功情况下的返回 json 对象
     */
    protected <T> String returnWithSuccessInJson(String msg, T t) throws JsonProcessingException {
        ResponseDTO responseDTO = returnWithSuccess(msg, t);

        // 序列化为 json 形式返回
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(responseDTO);
    }

    /**
     * 失败情况下的返回对象
     */
    protected ResponseDTO returnWithFail(Integer code, String msg) {
        ResponseDTO dto = new ResponseDTO();
        dto.setCode(code);
        dto.getMessages().clear();
        dto.addMsg(msg);
        return dto;
    }

    /**
     * 失败情况下的返回对象
     */
    protected ResponseDTO returnWithFail(Integer code, List<String> msgs) {
        ResponseDTO dto = new ResponseDTO();
        dto.setCode(code);
        dto.getMessages().clear();
        dto.addMsgs(msgs);
        return dto;
    }

    /**
     * 失败情况下的返回 json 对象
     */
    protected String returnWithFailInJson(Integer code, String msg) throws JsonProcessingException {
        ResponseDTO responseDTO = returnWithFail(code, msg);

        // 序列化为 json 形式返回
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(responseDTO);
    }

    /**
     * 失败情况下的返回 json 对象
     */
    protected String returnWithFailInJson(Integer code, List<String> msgs) throws JsonProcessingException {
        ResponseDTO responseDTO = returnWithFail(code, msgs);

        // 序列化为 json 形式返回
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(responseDTO);
    }

    /**
     * 参数校验失败情况下, 多错误对象返回
     *
     * @param errorList
     * @return
     */
    protected ResponseDTO returnWithCheckFail(List<? extends ObjectError> errorList) {
        ResponseDTO dto = new ResponseDTO();
        if (CollectionUtils.isNotEmpty(errorList)) {
            dto.setCode(ResponseDTO.ResponseResultEnum.PARAM_CHECK_FAILED.code());
            for (ObjectError error : errorList) {
                dto.addMsg(error.getDefaultMessage());
            }
        }
        return dto;
    }

    /**
     * 参数校验失败情况下, 单错误对象返回
     *
     * @param error
     * @return
     */
    protected ResponseDTO returnWithCheckFail(ObjectError error) {
        ResponseDTO dto = new ResponseDTO();
        dto.setCode(ResponseDTO.ResponseResultEnum.PARAM_CHECK_FAILED.code());
        dto.addMsg(error.getDefaultMessage());
        return dto;
    }

    private String getRequestPostStr(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {
            /* report an error */ }

        return sb.toString();
    }

    protected String getRequestJsonString(HttpServletRequest request) throws IOException {
        String submitMehtod = request.getMethod();
        if (submitMehtod.equals("GET")) {
            return new String(request.getQueryString().getBytes("ISO8859-1"), "UTF-8").replaceAll("%22", "\"");
        } else {
            return getRequestPostStr(request);
        }
    }
}
