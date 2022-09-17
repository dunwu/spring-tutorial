package io.github.dunwu.springboot.dto;

import java.util.Date;

/**
 * @author Zhang Peng
 * @since 2018-12-29
 */
public class InfoDTO {

    private String appName;

    private String version;

    private Date date;

    public InfoDTO() {
    }

    public InfoDTO(String appName, String version, Date date) {
        this.appName = appName;
        this.version = version;
        this.date = date;
    }

    @Override
    public String toString() {
        return "InfoDTO{" + "appName='" + appName + '\'' + ", version='" + version + '\'' + ", date=" + date + '}';
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
