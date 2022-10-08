package io.github.dunwu.springboot.web.exception;

/**
 * @author Zhang Peng
 * @since 2018-12-29
 */
public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

}
