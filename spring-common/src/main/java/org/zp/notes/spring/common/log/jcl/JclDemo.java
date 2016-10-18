package org.zp.notes.spring.common.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JclDemo {
    private static final Log log = LogFactory.getLog(JclDemo.class);

    public static void main(String[] args) {
        String msg = "print log, current level: ";
        log.trace(msg + "trace");
        log.debug(msg + "debug");
        log.info(msg + "info");
        log.warn(msg + "warn");
        log.error(msg + "error");
        log.fatal(msg + "fatal");

        test();
    }

    public static void test() {
        System.setProperty("java.util.logging.config.file", "common-logging.properties");
        Log logger = LogFactory.getLog(JclDemo.class);
        logger.trace("测试common-logging + java.util.logging");
    }
}