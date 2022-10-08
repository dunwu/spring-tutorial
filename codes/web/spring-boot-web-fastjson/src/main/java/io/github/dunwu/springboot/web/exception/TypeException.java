package io.github.dunwu.springboot.web.exception;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-18
 */
public class TypeException extends RuntimeException {

    private static final long serialVersionUID = -2732419195634968950L;

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
