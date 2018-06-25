package me.jincs.tool.ibatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import me.jincs.tool.ibatis.domain.ConfigDomain;
import me.jincs.tool.ibatis.service.GenerateService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jinchengsong on 2018/6/22.
 */
public class generate {

    private static String TEMPLATE_PATH = generate.class.getClassLoader().getResource("templates").getPath();
    public static void main(String[] args) {
        String configFileName = System.getProperty("user.dir")+File.separator+"config.json";
        JSONObject configData=null;
        try(FileInputStream fis = new FileInputStream(configFileName);
            InputStreamReader inputStreamReader=new InputStreamReader(fis,"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);)
        {
            String line;
            StringBuilder stringBuilder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            configData= JSON.parseObject(stringBuilder.toString());//得到JSONobject对象
        }catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        JSONArray tables = configData.getJSONArray("tables");
        ConfigDomain configDomain = new ConfigDomain();
        configDomain.setJdbcUrl(configData.getString("jdbcUrl"));
        configDomain.setUsername(configData.getString("username"));
        configDomain.setPassword(configData.getString("password"));
        for(int i=0;i<tables.size();i++){
            JSONObject table = tables.getJSONObject(i);
            String tableName=table.getString("tableName");
            String sourcePath=table.getString("sourcePath");
            if(sourcePath == null){
                sourcePath = configData.getString("sourcePath");
            }
            String domainPackagePath=table.getString("DomainPackagePath");
            if(domainPackagePath == null){
                domainPackagePath = configData.getString("domainPackagePath");
            }
            configDomain.setDomainPackagePath(domainPackagePath);
            configDomain.setSourcePath(sourcePath);
            configDomain.setTemplatePath(TEMPLATE_PATH);
            GenerateService generateService = new GenerateService(configDomain);
            generateService.generateFile(tableName);
        }

    }
}
