package io.github.dunwu.springboot.tools2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.SafeConfig;

/**
 * Echo2 Velocity Tool
 *
 * @author <a href="mailto:taogu.mxx@alibaba-inc.com">taogu.mxx</a> (Office)
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see SafeConfig
 * @since 2017.01.13
 */
@DefaultKey("echo2")
public class Echo2Tool extends SafeConfig {

    private final Log logger = LogFactory.getLog(this.getClass());

    public void echo(String message) {

        logger.info("Echo2Tool : " + message);
    }

}
