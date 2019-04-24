package com.gyportal.controller;

import com.gyportal.component.TimeUtil;
import com.gyportal.mapper.VisitorMapper;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.model.Visitor;
import com.gyportal.utils.IPUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;

/**
 * create by lihuan at 18/11/6 19:30
 */
@RestController
public class IndexController {

    @Resource
    private VisitorMapper visitorMapper;

    private TimeUtil timeUtil = new TimeUtil();

    //基础访客量
    private static final Integer BASE_VISITOR_COUNT = 0;

    /**
     * 获取总访客量
     * @return
     */
    @GetMapping("/getVisitorCount")
    public AjaxResponseBody getVisitorCount(@RequestParam(value = "group", required = false) String group) {

        Integer count = null;
        if (StringUtils.isBlank(group)) {
            count = visitorMapper.getVisitorCount() + BASE_VISITOR_COUNT;
        } else if (group.equals("day")) {
            count = visitorMapper.getVisitorCountByDate(timeUtil.getFormatDateForThree());
        } else if (group.equals("month")) {
            count = visitorMapper.getVisitorCountByDate(timeUtil.getFormatDateForTwo());
        }


        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(count).build();
    }

    /**
     * 新增访客记录
     * @param request
     * @return
     */
    @GetMapping("/increaseVisitorCount")
    public AjaxResponseBody increaseVisitorCount(HttpServletRequest request) {

        // 获取客户端IP地址
        String clientIp = IPUtils.getClientIp(request);

        Visitor visitor = new Visitor();
        visitor.setIp(clientIp);
        visitor.setCount(1);
        visitor.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        visitor.setLastLoadAddress(IPUtils.getAddressJson(clientIp));
        visitorMapper.save(visitor);

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

    /**
     * 按日期统计访客量
     * @param group
     * @return
     */
    @GetMapping("/countVisitor")
    public AjaxResponseBody countVisitor(String group) {

        List<List<Object>> data = null;

        if (group.equals("day")) {
            data = visitorMapper.findVisitorGroupByDay();

        } else if (group.equals("month")) {
            data = visitorMapper.findVisitorGroupByMonth();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(data).build();
    }

    @GetMapping("/getRequestHead")
    public AjaxResponseBody getRequestHead(HttpServletRequest request) {

        Enumeration<String> e =request.getHeaderNames();
        while (e.hasMoreElements()){
            String s = e.nextElement();
            System.out.println(s+":"+request.getHeader(s));
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData("").build();
    }

}
