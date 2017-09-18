package io.github.zp1024.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 启动嵌入式的Tomcat并初始化Spring环境 <br/>
 * 无 applicationContext.xml 和 web.xml, 靠下述方式进行配置： <br/>
 * 1. 扫描当前package下的class设置自动注入的Bean <br/>
 * 2. 也支持用@Bean标注的类配置Bean <br/>
 * 3. 根据classpath中的三方包Class及集中的application.properties条件配置三方包，如线程池 <br/>
 * 4. 也支持用@Configuration标注的类配置三方包. <br/>
 * @author Zhang Peng
 * @date 2017/7/13 18:07
 */
@Controller
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = "io.github.zp1024.spring.orm.mapper")
public class TomcatApplication {
	private static final Logger logger = LoggerFactory.getLogger(TomcatApplication.class);

    @RequestMapping("/")
    String home() {
        return "redirect:countries";
    }

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				logger.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed");
			}

		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TomcatApplication.class, args);
	}
}
