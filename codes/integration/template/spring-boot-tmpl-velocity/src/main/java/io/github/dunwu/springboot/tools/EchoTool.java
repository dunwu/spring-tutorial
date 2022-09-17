package io.github.dunwu.springboot.tools;

import io.github.dunwu.springboot.tools2.Echo2Tool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.SafeConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Echo Velocity Tool
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">taogu.mxx</a> (Office)
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see SafeConfig
 * @since 2017.01.13
 */
@DefaultKey("echo")
public class EchoTool extends SafeConfig implements ApplicationContextAware {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private Echo2Tool echo2Tool;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void echo(String message) {

        logger.info("EchoTool : " + message);

        if (echo2Tool == null) {
            echo2Tool = applicationContext.getBean(Echo2Tool.class);
        }

        echo2Tool.echo(message);
    }

}
