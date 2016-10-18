package org.zp.notes.spring.common.log.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试 slf4j + logback 输出日志
 *
 * @author victor zhangs
 */
public class Slf4jDemo {
    private static final Logger log = LoggerFactory.getLogger(Slf4jDemo.class);

    public static void main(String[] args) {
        String msg = "print log, current level: {}";
        log.trace(msg, "trace");
        log.debug(msg, "debug");
        log.info(msg, "info");
        log.warn(msg, "warn");
        log.error(msg, "error");
    }
}
