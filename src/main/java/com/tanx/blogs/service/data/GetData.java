package com.tanx.blogs.service.data;

import com.tanx.blogs.pojo.model.*;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.daily.DailyService;
import com.tanx.blogs.service.mail.MailSend;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.service.user.UserService;
import com.tanx.blogs.service.visitor.VisitorService;
import com.tanx.blogs.utils.*;
import eu.bitwalker.useragentutils.Browser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class GetData {
    private MailSend mailSend;
    private static final List<Map<String, Object>> LISTS = VariableUtils.LIST;

    @Autowired
    public void setMailSend(MailSend mailSend) {
        this.mailSend = mailSend;
    }

    public void getData(HttpServletRequest request, TypeService typeService, TagService tagService, ArchivesService archivesService, UserService userService, DailyService dailyService, VisitorService visitorService) {
        ServletContext servletContext = request.getSession().getServletContext();

        Browser browser = VisitorUtil.getBrowser(request);
        String ip = VisitorUtil.getIp(request);
        String browserVersion = VisitorUtil.getBrowserVersion(request);
        Visitor visitor = new Visitor(ip,
                VisitorUtil.getOperatingSystem(request),
                browser.getName(), browserVersion,
                IpInterfaceUtil.getCountry(ip),
                IpInterfaceUtil.getPro(ip),
                IpInterfaceUtil.getCity(ip));

        if (!"127.0.0.1".equals(ip)
                && !"0:0:0:0:0:0:0:1".equals(ip)
                && VariableUtils.IP_MAP.get(visitor.getVisitorIp()) == null
                && visitorService.queryVisitorInfo(visitor) == null
                && browserVersion != null) {

            int result = visitorService.addVisitor(visitor);
            MapCacheUtil.remove("VisitorCount");
            VariableUtils.IP_MAP.put(visitor.getVisitorIp(), visitor);
        }

        // 获取最新的文章
        List<Archives> newsArchives = (List<Archives>) MapCacheUtil.get("NewsArchives");

        // 获取文章的总数
        Integer archivesCount = (Integer) MapCacheUtil.get("ArchivesCount");
        int archivesTotal = archivesCount != null ? archivesCount : archivesService.queryArchivesCount();

        // 获取日记总数
        Integer dailyCount = (Integer) MapCacheUtil.get("DailyCount");
        int dailyTotal = archivesCount != null ? dailyCount : dailyService.dailyCount();

        // 获取访问总数
        Integer visitorCount = (Integer) MapCacheUtil.get("VisitorCount");
        int visitorTotal = visitorCount != null ? visitorCount : visitorService.visitorCount();

        List<Map<String, Object>> lists = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        List<Tag> tags = null;
        List<Type> types = null;
        boolean flat = false, flag = false;
        if (lists != null) {
            for (Map<String, Object> map : lists) {
                if (map.get("queryIsArchivesByTags") != null) {
                    tags = (List<Tag>) map.get("queryIsArchivesByTags");
                    flat = true;
                }
                if (map.get("queryIsArchivesTypes") != null) {
                    types = (List<Type>) map.get("queryIsArchivesTypes");
                    flag = true;
                }
                if (flag && flat) {
                    break;
                }
            }
        }

        if (flag == false) {
            Map<String, Object> map = new HashMap<>(16, 0.75f);
            tags = tagService.queryIsArchivesByTags();
            map.put("queryIsArchivesByTags", tags);
            LISTS.add(map);
            MapCacheUtil.set("Archives", LISTS);
        }

        if (flat == false) {
            Map<String, Object> map = new HashMap<>(16, 0.75f);
            types = typeService.queryIsArchivesTypes();
            map.put("queryIsArchivesTypes", types);
            LISTS.add(map);
            MapCacheUtil.set("Archives", LISTS);
        }

        GetDataUtil util = new GetDataUtil(
                newsArchives != null ? newsArchives : archivesService.selectArchivesNews(),
                tags != null ? tags : tagService.queryIsArchivesByTags(),
                types != null ? types : typeService.queryIsArchivesTypes(),
                archivesTotal,
                dailyTotal,
                visitorTotal,
                VariableUtils.MAP.get("notice"));

        TurnPageUtil<Archives> page = new TurnPageUtil<Archives>(1, archivesTotal, VariableUtils.SHWODPAGE);

        List<Map<String, Object>> list = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        List<Archives> archives = null;
        if (list != null) {
            for (Map<String, Object> map : list) {
                if (map.get("archives-1") != null) {
                    archives = (List<Archives>) map.get("archives-1");
                    break;
                }
            }
        }

        if (archives == null) {
            archives = archivesService.selectArchivesLimit(1);
            Map<String, Object> map = new HashMap<>(16, 0.75f);
            map.put("archives-1", archives);
            LISTS.add(map);
            MapCacheUtil.set("Archives", LISTS);
        }

        page.setArchivesData(archives);
        servletContext.setAttribute("archives", page);
        servletContext.setAttribute("datas", util);
        List<User> users = userService.queryRank();
        if (users.size() == 1) {
            servletContext.setAttribute("super", users.get(0));
        } else {
            User user = new User("Tree", "种树的最佳时候是十年之前，其次是现在！", "湖南·衡阳", "", "1571922819@qq.com");
            try {
                this.mailSend.errorSend(user.getEmail());
                servletContext.setAttribute("super", user);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}