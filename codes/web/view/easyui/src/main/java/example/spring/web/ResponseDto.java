package example.spring.web;

import java.util.List;

/**
 * @author Zhang Peng
 * @since 2019-01-06
 */
public class ResponseDto<T> {

    private Boolean success;

    private Integer total;

    private List<T> rows;

    public ResponseDto(Boolean success, Integer total, List<T> rows) {
        this.success = success;
        this.total = total;
        this.rows = rows;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
