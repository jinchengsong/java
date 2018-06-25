package me.jincs.tool.ibatis.domain;

/**
 * Created by jinchengsong on 2018/6/25.
 */
public class ConfigDomain {
    private String templatePath;
    private String sourcePath;
    private String domainPackagePath;
    private String jdbcUrl;
    private String username;
    private String password;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDomainPackagePath() {
        return domainPackagePath;
    }

    public void setDomainPackagePath(String domainPackagePath) {
        this.domainPackagePath = domainPackagePath;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
