package me.jincs.tool.ibatis.service;

import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import me.jincs.tool.ibatis.domain.ColumnDomain;
import me.jincs.tool.ibatis.domain.ConfigDomain;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by jinchengsong on 2018/6/22.
 */
public class GenerateService {
    private Configuration cfg;
    private ConfigDomain configDomain;
    private String tableName;
    private JdbcTemplate jdbcTemplate;
    Map<String, Object> root = new HashMap();;

    private static final String SQLMAP_FILE_NAME="sqlmap_template.ftlh";
    private static final String DOMAIN_FILE_NAME="domain_template.ftlh";
    private static final String SELECT_BASE_COL_INFO_SQL="select\n" +
            "utc.column_name columnName,utc.data_type dataType,utc.data_length dataLength,\n" +
            "utc.nullable nullable,ucc.comments comments\n" +
            "from\n" +
            "user_tab_columns utc,user_col_comments ucc\n" +
            "where\n" +
            "utc.table_name = ucc.table_name\n" +
            "and utc.column_name = ucc.column_name\n" +
            "and utc.table_name = ?\n" +
            "order by\n" +
            "column_id";

    private static final String SELECT_PRIMARY_COL_INFO_SQL="select  col.column_name columnName\n" +
            "from user_constraints con,user_cons_columns col\n" +
            "where\n" +
            "con.constraint_name=col.constraint_name and con.constraint_type='P'\n" +
            "and col.table_name=?";
    public GenerateService(ConfigDomain configDomain) {
        this.configDomain = configDomain;
        initCfg();
    }

    public void initCfg(){
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        try {
            cfg.setDirectoryForTemplateLoading(new File(configDomain.getTemplatePath()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void generateFile(String tableName){
        this.tableName = tableName.toUpperCase();
        root.put("tableName",this.tableName.toLowerCase());
        initJdbcTemplate();
        getColumnList();
        getPkColumn();
        generateJavaDomain();
        generateSqlMapFile();
    }

    private void generateSqlMapFile(){
        Template temp = null;
        cfg.setOutputFormat(XMLOutputFormat.INSTANCE);
        try {
            temp = cfg.getTemplate(SQLMAP_FILE_NAME);
            Writer out = new FileWriter(new File(configDomain.getSourcePath()+tableName+".xml"));
            temp.process(root, out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String url = configDomain.getJdbcUrl();
        String username = configDomain.getUsername();
        String password = configDomain.getPassword();
        DataSource dataSource = new DriverManagerDataSource(url,username,password);
        jdbcTemplate.setDataSource(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }

    private void getColumnList(){
        List<ColumnDomain> list = jdbcTemplate.query(SELECT_BASE_COL_INFO_SQL,
                new BeanPropertyRowMapper(ColumnDomain.class),new Object[]{tableName});
        root.put("columns",list);
    }

    private void getPkColumn(){
        List<ColumnDomain> list = jdbcTemplate.query(SELECT_PRIMARY_COL_INFO_SQL,new BeanPropertyRowMapper(ColumnDomain.class),
                new Object[]{tableName});
        root.put("pkCols",list);

    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private void generateJavaDomain(){
        root.put("domainAlias",tableName.toLowerCase()+"Domain");
        root.put("package",configDomain.getDomainPackagePath());
        String domanName = toUpperCaseFirstOne(tableName.toLowerCase())+"Domain";
        root.put("domainName",domanName);
        Template temp = null;
        cfg.setOutputFormat(XMLOutputFormat.INSTANCE);
        try {
            temp = cfg.getTemplate(DOMAIN_FILE_NAME);
            Writer out = new FileWriter(new File(configDomain.getSourcePath()+domanName+".java"));
            temp.process(root, out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
