package io.github.dunwu.springboot.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-18
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 657052319622238665L;

    public static final String SUCCESS_CODE = "0";

    public static final String DEFAULT_SUCCESS_MSG = "SUCCESS";

    /**
     * 错误码
     */
    protected final String code;

    /**
     * 响应信息
     */
    protected final String message;

    /**
     * 数据
     */
    protected final Object data;

    /**
     * 数据类型
     */
    private final transient Type type;

    /**
     * 普通数据
     */
    private transient T normalData = null;

    /**
     * 列表数据
     */
    private transient Collection<T> listData = Collections.emptyList();

    /**
     * 分页数据
     */
    private transient Pagination<T> pageData = new Pagination<>();

    // ------------------------------------------------------
    // 构造方法
    // ------------------------------------------------------

    private BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.type = Type.NONE;
        this.data = null;
    }

    private BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.type = Type.NormalData;
        this.data = data;
        this.normalData = data;
    }

    private BaseResponse(String code, String message, List<T> data) {
        this.code = code;
        this.message = message;
        this.type = Type.ListData;
        this.data = data;
        this.listData = data;
    }

    private BaseResponse(String code, String message, Pagination<T> data) {
        this.code = code;
        this.message = message;
        this.type = Type.ListData;
        this.data = data;
        this.pageData = data;
    }

    // ------------------------------------------------------
    // 构建器
    // ------------------------------------------------------

    public static <V> BaseResponse<V> success(V data) {
        return new BaseResponse<>(SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public static <V> BaseResponse<V> success(V data, String message) {
        return new BaseResponse<>(SUCCESS_CODE, message, data);
    }

    public static <V> BaseResponse<V> success(List<V> data) {
        return new BaseResponse<>(SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public static <V> BaseResponse<V> success(List<V> data, String message) {
        return new BaseResponse<>(SUCCESS_CODE, message, data);
    }

    public static <V> BaseResponse<V> success(Pagination<V> data) {
        return new BaseResponse<>(SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public static <V> BaseResponse<V> success(Pagination<V> data, String message) {
        return new BaseResponse<>(SUCCESS_CODE, message, data);
    }

    public static BaseResponse<?> fail(String code, String message) {
        return new BaseResponse<>(code, message);
    }

    // ------------------------------------------------------
    // Getter
    // ------------------------------------------------------

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public T getNormalData() {
        if (!this.type.equals(Type.NormalData)) {
            throw new TypeException("尝试获取 data 数据，但类型不正确");
        }
        return normalData;
    }

    public Collection<T> getListData() {
        if (!this.type.equals(Type.ListData)) {
            throw new TypeException("尝试获取 data 数据，但类型不正确");
        }
        return listData;
    }

    public Pagination<T> getPageData() {
        if (!this.type.equals(Type.PageData)) {
            throw new TypeException("尝试获取 data 数据，但类型不正确");
        }
        return pageData;
    }

    // ------------------------------------------------------
    // 内部类
    // ------------------------------------------------------

    enum Type {
        NormalData,
        ListData,
        PageData,
        NONE
    }

    /**
     * 分页信息，主要用于分页查询
     *
     * @param <T> 数据类型
     */
    @Data
    @ToString
    @Accessors(chain = true)
    public static class Pagination<T> implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 当前查询页的数据列表
         */
        protected Collection<T> list = Collections.emptyList();

        /**
         * 当前查询页
         */
        protected long current = 1L;

        /**
         * 每页展示记录数
         */
        protected long size = 10L;

        /**
         * 总记录数
         */
        protected long total = 0L;

        public Pagination() {}

        public Pagination(long current, long size, long total) {
            this.current = current;
            this.size = size;
            this.total = total;
        }

        public Pagination(long current, long size, long total, Collection<T> list) {
            this.current = current;
            this.size = size;
            this.total = total;
            this.list = list;
        }

    }

}
