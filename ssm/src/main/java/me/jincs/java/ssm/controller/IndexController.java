package me.jincs.java.ssm.controller;

import me.jincs.java.ssm.domain.AccessLog;
import me.jincs.java.ssm.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private AccessLogService accessLogService;

    @RequestMapping("hello")
    public String hello2(HttpServletRequest request){
        String ip = request.getRemoteHost();
        int count = 1;
        AccessLog accessLog = accessLogService.selectByPrimaryKey(ip);
        if (accessLog == null){
            accessLog = new AccessLog();
            accessLog.setIp(ip);
            accessLog.setCount(count);
            accessLogService.insert(accessLog);
        }else{
            count = accessLog.getCount();
            count++;
            accessLog.setCount(count);
            accessLogService.updateByPrimaryKey(accessLog);

        }
        request.setAttribute("ip",ip);
        request.setAttribute("count",count);
        return "hello";
    }
}
