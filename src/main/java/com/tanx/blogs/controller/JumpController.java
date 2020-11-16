package com.tanx.blogs.controller;

import com.tanx.blogs.pojo.model.*;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.changelog.ChangeLogService;
import com.tanx.blogs.service.daily.DailyService;
import com.tanx.blogs.service.data.GetData;
import com.tanx.blogs.service.friendlink.FriendLinkService;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.service.user.UserService;
import com.tanx.blogs.service.visitor.VisitorService;
import com.tanx.blogs.utils.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 页面跳转
 *
 * @author tanxiang
 */
@Controller
@Api(tags = "跳转页面")
@SuppressWarnings("unchecked")
public class JumpController {
    private GetData getData;
    private TagService tagService;
    private UserService userService;
    private FriendLinkService friendLinkService;
    private TypeService typeService;
    private DailyService dailyService;
    private VisitorService visitorService;
    private ArchivesService archivesService;
    private ChangeLogService changeLogService;
    private static final List<Map<String,Object>> LISTS = VariableUtils.LIST;
    private static final List<Map<String,Object>> DAILY_LISTS = VariableUtils.DAILY_LIST;
    private static final List<Map<String,Object>> LOG_LISTS = VariableUtils.LOG_LIST;
    private static final List<Map<String,Object>> FOOTMARK_LISTS = VariableUtils.FOOTMARK_LIST;

    @Autowired
    public void setVisitorService(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @Autowired
    public void setDailyService(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @Autowired
    public void setArchivesService(ArchivesService archivesService) {
        this.archivesService = archivesService;
    }

    @Autowired
    public void setGetData(GetData getData) {
        this.getData = getData;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setChangeLogService(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @Autowired
    public void setFriendLinkService(FriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    @GetMapping({"/404"})
    @ApiOperation(value = "404", notes = "跳转到404页面")
    public String error404() {
        return "/error/404";
    }


    @GetMapping({"/", "/home", "/home.html"})
    @ApiOperation(value = "首页", notes = "跳转到首页")
    public String home(HttpServletRequest request) {
        getData.getData(request, this.typeService, this.tagService, this.archivesService, this.userService, this.dailyService, this.visitorService);
        return "blog/reception/home";
    }


    @GetMapping({"/types/{name}", "/home/types/{name}", "/home.html/types/{name}"})
    @ApiOperation(value = "查询分类名", notes = "根据传入的分类名，进行查询")
    @ApiImplicitParam(name = "name", value = "分类名", required = true, paramType = "path")
    public String typeName(HttpServletRequest request, Model model, @PathVariable String name) {

        if (IsEmptyUtil.isStringEmpty(name)) {
            return "redirect:/home";
        }

        Type type = typeService.queryTypeByName(name);
        if (type == null) {
            return "redirect:/home";
        }

        TurnPageUtil<Archives> util = new TurnPageUtil<>(1, archivesService.queryTypeArchivesCount(type.getTypeId()), VariableUtils.SHWODPAGE);


        List<Map<String, Object>> archives = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        if (archives != null) {
            for (Map<String, Object> map : archives) {
                if(map.get("type-"+name) != null){
                    util.setArchivesData((List<Archives>)map.get("type-"+name));
                    request.getSession().getServletContext().setAttribute("archives", util);
                    model.addAttribute("typeName", type.getTypeName());
                    return "blog/reception/home";
                }
            }
        }

        List<Archives> archivesTypes = archivesService.queryTypeArchives(type.getTypeId(), Math.min(util.getCurrentPage(), util.getPageNumber()));
        Map<String,Object> map = new HashMap<>(16,0.75f);
        map.put("type-"+name,archivesTypes);
        LISTS.add(map);
        MapCacheUtil.set("Archives",LISTS);
        util.setArchivesData(archivesTypes);
        request.getSession().getServletContext().setAttribute("archives", util);
        model.addAttribute("typeName", type.getTypeName());
        return "blog/reception/home";
    }

    @SuppressWarnings("all")
    @GetMapping({"/superRank", "/superRank/index"})
    @ApiOperation(value = "后台页面", notes = "通过url跳转到指定的后台页面")
    public String backstage(HttpServletRequest request, Authentication authentication) {

        StringBuffer buffer = new StringBuffer();
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !user.getLoginName().equals(authentication.getName())) {
            return "redirect:/home";
        }

        Object rank = authentication.getAuthorities().toArray()[1];
        String randomCharCount = VerifyCodeUtil.getRandomCharCount(50);
        buffer.append(randomCharCount).insert(30, rank.toString().split("_")[1].toLowerCase());

        return "dev".equals(ReaderPropertiesOrYamlUtil.systemEnvironment())
                ?"redirect:http://localhost:8080/admin/administrator/profile/" + user.getLoginName() + "?&r=" + buffer.toString()
                :"redirect:https://www.tanxiangblog.com/admin/administrator/profile/"+user.getLoginName()+"?&r="+buffer.toString();
    }

    @GetMapping({"/tags/{name}", "/home/tags/{name}", "/home.html/tags/{name}"})
    @ApiOperation(value = "查询标签名", notes = "根据传入的标签名，进行查询")
    @ApiImplicitParam(name = "name", value = "标签名", required = true, paramType = "path")
    public String home(HttpServletRequest request, Model model, @PathVariable String name) {

        ServletContext servletContext = request.getServletContext();
        GetDataUtil datas = (GetDataUtil) servletContext.getAttribute("datas");
        if (datas == null) {
            return "redirect:/home";
        }

        if (IsEmptyUtil.isStringEmpty(name.trim())) {
            return "redirect:/home";
        }
        Tag tag = tagService.selectTag(name);
        if (tag == null) {
            return "redirect:/home";
        }

        TurnPageUtil<Archives> util = new TurnPageUtil<>(1, archivesService.queryArchivesByTagIdCount(tag.getTagId()), VariableUtils.SHWODPAGE);

        List<Map<String,Object>> archivesTag = (List<Map<String,Object>>) MapCacheUtil.get("Archives");
        if(archivesTag != null){
            for(Map<String,Object> map:archivesTag){
                if(map.get("tag-"+name) != null){
                    util.setArchivesData((List<Archives>) map.get("tag-"+name));
                    request.getSession().getServletContext().setAttribute("archives", util);
                    model.addAttribute("tagData", tag);
                    return "blog/reception/home";
                }
            }
        }

        List<Archives> archives = archivesService.queryArchivesByTagId(tag.getTagId(), util.getCurrentPage());
        Map<String,Object> map = new HashMap<>(16,0.75f);
        map.put("tag-"+name,archives);
        LISTS.add(map);
        MapCacheUtil.set("Archives",LISTS);
        util.setArchivesData(archives);
        request.getSession().getServletContext().setAttribute("archives", util);
        model.addAttribute("tagData", tag);
        return "blog/reception/home";
    }

    @GetMapping({"/search/", "/home/search/", "/home.html/search/", "/search", "/home/search", "/home.html/search"})
    @ApiOperation(value = "错误查询", notes = "错误查询页面")
    public String searchNotNull() {
        return "redirect:/home.html";
    }

    @GetMapping({"/search/{data}", "/home/search/{data}", "/home.html/search/{data}"})
    @ApiOperation(value = "查询数据", notes = "根据传入的数据查询文章")
    @ApiImplicitParam(name = "data", value = "查询的数据", required = true, paramType = "path")
    public String search(@PathVariable String data, HttpServletRequest request, Model model) {
        if (IsEmptyUtil.isStringEmpty(data.trim())) {
            return "redirect:/home.html";
        }

        ServletContext servletContext = request.getServletContext();
        GetDataUtil datas = (GetDataUtil) servletContext.getAttribute("datas");
        if (datas == null) {
            return "redirect:/home";
        }

        Type type = this.typeService.queryTypeByName(data);
        Tag tag = this.tagService.selectTag(data);

        String dataSearch = data.replaceAll("\u5E74", "-")
                .replaceAll("\u6708", "-")
                .replaceAll("\u65E5", "-")
                .replaceAll("/", "-");

        TurnPageUtil<Archives> page = new TurnPageUtil<>(1, 0, VariableUtils.SHWODPAGE);

        if (type != null) {
            page.setAllPage(this.archivesService.queryTypeArchivesCount(type.getTypeId()));
            page.setArchivesData(this.archivesService.queryTypeArchives(type.getTypeId(), page.getCurrentPage()));
        } else if (tag != null) {
            page.setAllPage(this.archivesService.queryArchivesByTagIdCount(tag.getTagId()));
            page.setArchivesData(this.archivesService.queryArchivesByTagId(tag.getTagId(), page.getCurrentPage()));
        } else if (dataSearch.matches("\\d+")) {
            page.setAllPage(this.archivesService.queryArchivesByIdOrUploadTimeCount(Long.parseLong(dataSearch), "%" + dataSearch + "%"));
            page.setArchivesData(this.archivesService.queryArchivesByIdOrUploadTime(Long.parseLong(dataSearch), dataSearch, page.getCurrentPage()));
        } else {
            page.setAllPage(archivesService.queryArchivesByTitleAndUploadTimeCount(dataSearch, dataSearch));
            page.setArchivesData(this.archivesService.queryArchivesByTitleAndUploadTime(dataSearch, dataSearch, page.getCurrentPage()));
        }
        request.getSession().getServletContext().setAttribute("archives", page);
        model.addAttribute("data", dataSearch);
        return "blog/reception/home";
    }

    @GetMapping({"/footmark"})
    @ApiOperation(value = "足迹", notes = "进入足迹页面")
    public String footmark(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        GetDataUtil datas = (GetDataUtil) servletContext.getAttribute("datas");
        if (datas == null) {
            return "redirect:/home";
        }
        TurnPageUtil<Archives> util = new TurnPageUtil<>(1, archivesService.queryArchivesCount(), VariableUtils.SHWODPAGE * 2);
        List<Map<String, Object>> footMarkData = (List<Map<String, Object>>) MapCacheUtil.get("FootMarkData");
        if(footMarkData != null){
            for(Map<String, Object> map:footMarkData){
                if(map.get("footMarkData-1") != null){
                    util.setArchivesData((List<Archives>) map.get("footMarkData-1"));
                    request.getSession().getServletContext().setAttribute("archives", util);
                    return "blog/reception/footmark";
                }
            }
        }
        List<Archives> archives = archivesService.queryArchivesTypeName(util.getCurrentPage(), util.getShowPage());
        Map<String,Object> map = new HashMap<>(16);
        map.put("footMarkData-1",archives);
        FOOTMARK_LISTS.add(map);
        MapCacheUtil.set("FootMarkData",FOOTMARK_LISTS);
        util.setArchivesData(archives);
        request.getSession().getServletContext().setAttribute("archives", util);
        return "blog/reception/footmark";
    }


    @GetMapping({"/informal"})
    @ApiOperation(value = "日记", notes = "进入日记页面")
    public String informal(HttpServletRequest request, Model model) {
        ServletContext servletContext = request.getServletContext();
        GetDataUtil datas = (GetDataUtil) servletContext.getAttribute("datas");
        if (datas == null) {
            return "redirect:/home";
        }

        TurnPageUtil<Daily> util = new TurnPageUtil<>(1, dailyService.dailyCount(), VariableUtils.SHWODPAGE * 2);
        List<Map<String, Object>> dailyPages = (List<Map<String, Object>>) MapCacheUtil.get("dailyPages");
        if(dailyPages != null){
            for(Map<String, Object> map:dailyPages){
                if(map.get("dailyPages-1") != null){
                    model.addAttribute("dailyList", map.get("dailyPages-1"));
                    model.addAttribute("pageNumber", util.getPageNumber());
                    return "blog/reception/informal";
                }
            }
        }

        List<Daily> dailies = dailyService.queryDaily(util.getCurrentPage());
        Map<String,Object> map = new HashMap<>(16);
        map.put("dailyPages-1",dailies);
        DAILY_LISTS.add(map);
        MapCacheUtil.set("dailyPages",DAILY_LISTS);
        model.addAttribute("dailyList", dailies);
        model.addAttribute("pageNumber", util.getPageNumber());
        return "blog/reception/informal";
    }

    @GetMapping({"/journal"})
    @ApiOperation(value = "日志", notes = "进入日志页面")
    public String journal(Model model) {
        TurnPageUtil<ChangeLog> page = new TurnPageUtil<>(1, this.changeLogService.changeLogCount(), VariableUtils.SHWODPAGE * 2);
        List<Map<String, Object>> lists = (List<Map<String, Object>>) MapCacheUtil.get("Log");
        if (lists != null) {
            for (Map<String, Object> map : lists) {
                if (map.get("logPages-1") != null) {
                    page.setArchivesData((List<ChangeLog>) map.get("logPages-1"));
                    model.addAttribute("changeLogs", page);
                    return "blog/reception/journal";
                }
            }
        }

        List<ChangeLog> log = changeLogService.queryChangeLog(page.getCurrentPage());
        Map<String,Object> map = new HashMap<>(16);
        map.put("logPages-1",log);
        LOG_LISTS.add(map);
        MapCacheUtil.set("Log",LOG_LISTS);
        page.setArchivesData(log);
        model.addAttribute("changeLogs", page);
        return "blog/reception/journal";
    }

    /** 有时间写一个定时 Map */
    @GetMapping({"/archives/{archivesId}"})
    @ApiOperation(value = "进入文章", notes = "根据ID进入指定的文章页面")
    @ApiImplicitParam(name = "archivesId", value = "文章ID", required = true, paramType = "path")
    public String archives(HttpServletRequest request, @PathVariable String archivesId, Model model) {

        Archives archives = archivesService.queryArchivesByIdAndTag(archivesId);
        if (archives == null) {
            return "/error/404";
        }

        ServletContext servletContext = request.getServletContext();
        GetDataUtil datas = (GetDataUtil) servletContext.getAttribute("datas");
        if (datas == null) {
            return "redirect:/home";
        }

        try {
            model.addAttribute("content", ArchivesOperationUtil.takeArchivesFile(archives.getAddress(), archives.getTitle()));
            model.addAttribute("type", typeService.selectTypeId(archives.getTypeId()));

            if (archivesService.updateArchivesReadNumber(archives.getArchivesId(), archives.getReadNumber() + 1) == 1) {
                archives.setReadNumber(archives.getReadNumber() + 1);
            }

            model.addAttribute("archives", archives);
            return "blog/reception/archives";
        } catch (IOException e) {
            return "/error/404";
        }
    }

    @GetMapping({"/friend", "/friend.html"})
    @ApiOperation(value = "友链", notes = "进入友链页面")
    public String friend(Model model) {
        model.addAttribute("links", friendLinkService.queryFriendLink());
        return "blog/reception/friend";
    }

    @GetMapping({"/board", "/board.html"})
    @ApiOperation(value = "留言板", notes = "进入留言板")
    public String messageBoard() {
        return "blog/reception/MessageBoard";
    }
}