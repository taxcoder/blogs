package com.tanx.blogs.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.tanx.blogs.pojo.dto.User;
import com.tanx.blogs.pojo.model.*;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.archivesandvisitor.ArchivesAndVisitorService;
import com.tanx.blogs.service.archivesdiscuss.ArchivesDiscussService;
import com.tanx.blogs.service.boarddiscuss.BoardDiscussService;
import com.tanx.blogs.service.changelog.ChangeLogService;
import com.tanx.blogs.service.daily.DailyService;
import com.tanx.blogs.service.dailyandvisitor.DailyAndVisitorService;
import com.tanx.blogs.service.dailydiscuss.DailyDiscussService;
import com.tanx.blogs.service.friendlink.FriendLinkService;
import com.tanx.blogs.service.mail.MailSend;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.service.user.UserServiceImpl;
import com.tanx.blogs.service.visitor.VisitorService;
import com.tanx.blogs.utils.*;
import eu.bitwalker.useragentutils.Browser;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanxiang
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping({"/api/admin"})
@Api("未登录用户url")
public class UserNotLoginController {
    private static final List<Map<String,Object>> LISTS = VariableUtils.LIST;
    private static final List<Map<String,Object>> DAILY_LISTS = VariableUtils.DAILY_LIST;
    private static final List<Map<String,Object>> LOG_LISTS = VariableUtils.LOG_LIST;
    private static final List<Map<String,Object>> FOOTMARK_LISTS = VariableUtils.FOOTMARK_LIST;
    private final Lock LOCK = new ReentrantLock();
    private ArchivesAndVisitorService archivesAndVisitorService;
    private DailyAndVisitorService dailyAndVisitorService;
    private ArchivesDiscussService archivesDiscussService;
    private BoardDiscussService boardDiscussService;
    private DailyDiscussService dailyDiscussService;
    private FriendLinkService friendLinkService;
    private ChangeLogService changeLogService;
    private ArchivesService archivesService;
    private VisitorService visitorService;
    private MailSend mailSend;
    private UserServiceImpl userService;
    private DailyService dailyService;
    private TypeService typeService;
    private TagService tagService;
    private MailSend send;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setMailSend(MailSend mailSend) {
        this.mailSend = mailSend;
    }

    @Autowired
    public void setFriendLinkService(FriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    @Autowired
    public void setArchivesAndVisitorService(ArchivesAndVisitorService archivesAndVisitorService) {
        this.archivesAndVisitorService = archivesAndVisitorService;
    }

    @Autowired
    public void setDailyAndVisitorService(DailyAndVisitorService dailyAndVisitorService) {
        this.dailyAndVisitorService = dailyAndVisitorService;
    }

    @Autowired
    public void setDailyDiscussService(DailyDiscussService dailyDiscussService) {
        this.dailyDiscussService = dailyDiscussService;
    }

    @Autowired
    public void setArchivesDiscussService(ArchivesDiscussService archivesDiscussService) {
        this.archivesDiscussService = archivesDiscussService;
    }

    @Autowired
    public void setBoardDiscussService(BoardDiscussService boardDiscussService) {
        this.boardDiscussService = boardDiscussService;
    }

    @Autowired
    public void setChangeLogService(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

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
    public void setSend(MailSend send) {
        this.send = send;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 用户注册
     *
     * @param user    用户信息
     * @param request 请求
     * @return 是否注册成功
     */
    @ResponseBody
    @PostMapping({"/v1/post/register"})
    @ApiOperation(value = "用户注册", notes = "用户注册请求")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, paramType = "query")
    public String register(User user, HttpServletRequest request) {
        String regexPassword = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$";
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        String regexEmail = "^\\w+((-\\w)|(\\.\\w))*@[A-Za-z0-9]+((\\.\\|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

        if (FilterDataUtil.isSensitiveTrue(user.getLoginName().trim())) {
            return InfoUtil.error("登录名存在敏感词，请重新填写！");
        }

        if (this.userService.queryLoginName(user.getLoginName().trim()) != null) {
            return InfoUtil.error("登录名已存在！");
        }

        if (!this.userService.queryUserName(user.getUsername().trim())) {
            return InfoUtil.error("此昵称已存在！");
        }

        if (user.getUsername().trim().length() > 10) {
            return InfoUtil.error("昵称长度不能大于10！");
        }

        if (!FilterDataUtil.filterTrimAndFeed(user.getPassword().trim()).matches(regexPassword) || IsEmptyUtil.isStringEmpty(user.getPassword().trim())) {
            return InfoUtil.error("密码格式错误！");
        }

        user.setPassword(EncryptionUtil.simpleMD5(user.getPassword().trim()));
        if (!FilterDataUtil.filterTrimAndFeed(user.getEmail()).matches(regexEmail) || IsEmptyUtil.isStringEmpty(user.getEmail())) {
            return InfoUtil.error("邮箱的格式不正确！");
        }

        if (!code.trim().equals(user.getCode())) {
            return InfoUtil.error("验证码错误！");
        }
        com.tanx.blogs.pojo.model.User userData = new com.tanx.blogs.pojo.model.User(
                user.getLoginName().trim(),
                user.getUsername(),
                user.getPassword().trim(),
                user.getEmail().trim());

        userData.setHeadImg("https://api.oss.tanxiangblog.com/images/users/akari.jpg");

        String content = user.getUsername() + "您好,请点击链接 https://www.tanxiangblog.com/api/admin/v1/get/mailActive?loginName=" + user.getLoginName() + "&email=" + user.getEmail() + " 进行账号激活!";
        try {
            this.send.send(userData, "注册确认！", content);
            return userService.userRegister(userData) > 0 ? InfoUtil.success("注册成功,请进入邮箱进行账号激活，保证邮箱的正确性！") : InfoUtil.error("未知错误，注册失败！");
        } catch (MessagingException e) {
            return InfoUtil.error(e.getMessage());
        }
    }

    /**
     * 查询登录名是否存在
     *
     * @param loginName 登录名
     * @return 存在情况
     */
    @ResponseBody
    @PostMapping({"/v1/post/loginName"})
    @ApiOperation(value = "查询登录名", notes = "判断登录名是否存在")
    @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query")
    public String loginName(String loginName) {
        if (FilterDataUtil.isSensitiveTrue(loginName.trim())) {
            return InfoUtil.error("登录名存在敏感词，请重新填写！");
        }

        return userService.queryLoginName(loginName.trim()) != null ? InfoUtil.error("登录名已存在！") : InfoUtil.success("success");
    }

    /**
     * 通过登录名查询用户信息
     *
     * @param loginName 登录名
     * @return 返回用户信息
     */
    @ResponseBody
    @GetMapping("/v1/get/headImage")
    @ApiOperation(value = "通过登录名查询用户信息", notes = "通过登录名获取用户信息")
    @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query")
    public String headImage(String loginName) {
        return InfoUtil.success("success", userService.queryLoginName(loginName));
    }

    /**
     * 查询用户名是否存在
     *
     * @param username 用户名
     * @return 存在情况
     */
    @ResponseBody
    @PostMapping({"/v1/post/username"})
    @ApiOperation(value = "查询用户名是否存在", notes = "判断用户名是否存在")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query")
    public String username(String username) {
        if (FilterDataUtil.isSensitiveTrue(username.trim())) {
            return InfoUtil.error("昵称存在敏感词，请重新填写！");
        }

        if (!userService.queryUserName(username.trim())) {
            return InfoUtil.error("此昵称已存在！");
        }

        return username.trim().length() > 10 ? InfoUtil.error("昵称长度不能大于10！") : InfoUtil.success("success");
    }

    /**
     * 验证码
     *
     * @param response 转发
     * @param request  请求
     */
    @ResponseBody
    @GetMapping({"/v1/get/code"})
    @ApiOperation(value = "验证码", notes = "图形验证码")
    public void code(HttpServletResponse response, HttpServletRequest request) {
        try {
            String code = VerifyCodeUtil.drawImage(137, 47);
            request.getSession().setAttribute("code", code);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/png");
            response.getOutputStream().write(VerifyCodeUtil.getByteArrayOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测验证码是否正确
     *
     * @param code    验证码
     * @param request 请求
     * @return 检测结果
     */
    @ResponseBody
    @PostMapping({"/v1/post/checkCode"})
    @ApiOperation(value = "检测验证码是否正确", notes = "判断验证码是否和存储的一致")
    @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query")
    public String checkCode(String code, HttpServletRequest request) {
        String verify = (String) request.getSession().getAttribute("code");
        return verify.equals(code) ? InfoUtil.success("success") : InfoUtil.error("验证失败！");
    }

    /**
     * 账号激活
     *
     * @param loginName 登录名
     * @param email     邮箱
     * @return 返回首页或404
     */
    @GetMapping({"/v1/get/mailActive"})
    @ApiOperation(value = "账号激活", notes = "判断用户的登录名和邮箱是否正确，正确则账号激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query")
    })
    public String mailActive(String loginName, String email) {
        return userService.updateUserActive(loginName, email) == 1 ? "redirect:/home" : "error/404";
    }

    /**
     * 查询邮箱是否正确
     *
     * @param email 邮箱
     * @return 返回查询的结果
     */
    @ResponseBody
    @PostMapping({"/v1/post/queryEmail"})
    @ApiOperation(value = "查询邮箱是否正确", notes = "判断输入的邮箱是否正确")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query")
    public String queryEmail(String email) {
        String regexEmail = "^\\w+((-\\w)|(\\.\\w))*@[A-Za-z0-9]+((\\.\\|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

        if (!FilterDataUtil.filterTrimAndFeed(email).matches(regexEmail) ||
                IsEmptyUtil.isStringEmpty(email)) {
            return InfoUtil.error("邮箱的格式不正确！");
        }
        return userService.queryEmail(email) ? InfoUtil.error("邮箱未注册!") : InfoUtil.success("success");
    }

    /**
     * 获取新的验证码
     *
     * @param request   请求
     * @param email     邮箱
     * @param loginName 登录名
     * @return 返回获取的情况
     */
    @ResponseBody
    @PostMapping({"/v1/post/getCode"})
    @ApiOperation(value = "获取新的验证码", notes = "找回密码，获取的新验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query")
    })
    public String getCode(HttpServletRequest request, String email, String loginName) {
        String regexEmail = "^\\w+((-\\w)|(\\.\\w))*@[A-Za-z0-9]+((\\.\\|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        if (IsEmptyUtil.isStringEmpty(loginName)) {
            return InfoUtil.error("登录名不能为空！");
        }
        if (IsEmptyUtil.isStringEmpty(email)) {
            return InfoUtil.error("邮箱不能为空！");
        }
        if (!FilterDataUtil.filterTrimAndFeed(email).matches(regexEmail) ||
                IsEmptyUtil.isStringEmpty(email)) {
            return InfoUtil.error("邮箱的格式不正确！");
        }
        if (this.userService.queryLoginNameEmail(email, loginName) == null) {
            return InfoUtil.error("登录名或邮箱错误！");
        }
        String randomNumber = VerifyCodeUtil.getRandomNumber();
        com.tanx.blogs.pojo.model.User user = new com.tanx.blogs.pojo.model.User();
        user.setEmail(email);
        String content = "此次的验证码为" + randomNumber;
        try {
            send.send(user, "找回密码", content);
            request.getSession().setAttribute("randomNumber", randomNumber);
            return InfoUtil.success("邮件已发送！");
        } catch (MessagingException e) {
            return InfoUtil.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param request     请求
     * @param loginName   登录名
     * @param email       邮箱
     * @param code        验证码
     * @param newPassword 新密码
     * @return 返回修改的结果
     */
    @ResponseBody
    @PostMapping({"/v1/post/updatePassword"})
    @ApiOperation(value = "修改密码", notes = "传入登录名、邮箱、验证码、新密码进行验证，成功修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "newPassword", value = "新的密码", required = true, paramType = "query")
    })
    public String updatePassword(HttpServletRequest request, String loginName, String email, String code, String newPassword) {
        HttpSession session = request.getSession();
        String regexPassword = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$";
        String randomNumber = (String) session.getAttribute("randomNumber");
        if (IsEmptyUtil.isStringEmpty(code)) {
            return InfoUtil.error("验证码为空！");
        }
        if (IsEmptyUtil.isStringEmpty(randomNumber)) {
            return InfoUtil.error("验证码异常，请重新获取！");
        }

        if (!randomNumber.equals(code)) {
            return InfoUtil.error("验证码错误！");
        }
        if (this.userService.queryLoginNameEmail(email, loginName) == null) {
            return InfoUtil.error("此用户不存在！");
        }
        if (IsEmptyUtil.isStringEmpty(newPassword)) {
            return InfoUtil.error("密码不能为空！");
        }
        if (!newPassword.matches(regexPassword)) {
            return InfoUtil.error("密码格式错误！");
        }
        int result = this.userService.updateOldPassword(EncryptionUtil.simpleMD5(newPassword), email, loginName);
        return result == 0 ? InfoUtil.error("修改失败！") : InfoUtil.success("修改成功!");
    }


    /**
     * 发送账号激活码
     *
     * @param loginName 登录名
     * @param password  密码
     * @return 返回邮件发送的情况
     */
    @ResponseBody
    @PostMapping({"/v1/post/againSendMail"})
    @ApiOperation(value = "发送账号激活码", notes = "账户未激活，判断之后发送验证码邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query")
    })
    public String againSendMail(String loginName, String password) {
        if (IsEmptyUtil.isStringEmpty(loginName, password)) {
            return InfoUtil.error("用户名和密码都不能为空！");
        }

        User user = new User(loginName, null, EncryptionUtil.simpleMD5(password), null);
        com.tanx.blogs.pojo.model.User userData = userService.userLogin(user);
        if (userData == null) {
            return InfoUtil.error("用户名或密码错误！");
        }

        if (userData.getActive()) {
            return InfoUtil.error("此用户已激活！");
        }

        try {
            String content = userData.getUsername() + "您好,请点击链接 https://www.tanxiangblog.com/api/admin/v1/get/mailActive?loginName=" + userData.getLoginName() + "&email=" + userData.getEmail() + " 进行账号激活!";
            send.send(userData, "注册确认！", content);
            return InfoUtil.success("邮件发送成功！");
        } catch (MessagingException e) {
            return InfoUtil.error("邮件发送失败！");
        }
    }

    /**
     * 文章点赞
     *
     * @param request    请求
     * @param archivesId 文章ID
     * @return 是否点赞成功
     */
    @ResponseBody
    @PutMapping({"/v1/put/praise/{archivesId}"})
    @ApiOperation(value = "文章点赞", notes = "文章点赞功能的实现")
    @ApiImplicitParam(name = "archivesId", value = "文章ID", required = true, paramType = "path")
    public String praise(HttpServletRequest request, @PathVariable String archivesId) {
        int result = 0;
        Browser browser = VisitorUtil.getBrowser(request);
        Visitor visitor = new Visitor(VisitorUtil.getIp(request), VisitorUtil.getOperatingSystem(request), browser.getName(), VisitorUtil.getBrowserVersion(request));
        if ("127.0.0.1".equals(visitor.getVisitorIp()) || "0:0:0:0:0:0:0:1".equals(visitor.getVisitorIp())) {
            return InfoUtil.error("无法解析您的IP地址！");
        }
        Visitor data = VariableUtils.IP_MAP.get(visitor.getVisitorIp());
        boolean flag = false;
        if (data != null
                && data.getOperationSystem().equals(visitor.getOperationSystem())
                && data.getBrowser().equals(visitor.getBrowser())
                && data.getBrowserVersion().equals(visitor.getBrowserVersion())) {
            flag = true;
        }

        Visitor visitorData = flag ? data : visitorService.queryVisitorInfo(visitor);

        if (visitorData == null) {
            result = visitorService.addVisitor(visitor);
        }
        Archives archives = this.archivesService.queryArchivesId(archivesId);

        if (archives == null || visitorData == null && result == 0) {
            return InfoUtil.error("数据异常，点赞失败！");
        }

        ArchivesAndVisitor archivesAndVisitor = new ArchivesAndVisitor(archives.getArchivesId(), this.visitorService.queryVisitorInfo(visitor).getVisitorId());
        if (this.archivesAndVisitorService.queryArchivesAndVisitor(archivesAndVisitor) == null) {
            if (this.archivesAndVisitorService.addArchivesAndVisitor(archivesAndVisitor) == 0) {
                return InfoUtil.error("数据异常，点赞失败！");
            }

            if (archivesService.updateArchivesGoodNumber(archives.getArchivesId(), archives.getGoodNumber() + 1)) {
                archivesAndVisitorService.removeArchivesAndVisitor(archivesAndVisitorService.queryArchivesAndVisitor(archivesAndVisitor).getArchivesAndVisitorId());
                return InfoUtil.error("数据异常，点赞失败！");
            }
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ONE, "点赞成功，谢谢您的赞许！"));
        }

        return InfoUtil.error("谢谢您的赞许，但您已经点过赞了！");
    }

    /**
     * 获取文章数据
     *
     * @param current 当前页
     * @return 返回当前页数据
     */
    @ResponseBody
    @GetMapping({"/v1/get/archives/pages/{current}"})
    @ApiOperation(value = "获取文章数据", notes = "获取当前页的文章数据")
    @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path")
    public String currentPageData(@PathVariable String current) {

        if (!IsEmptyUtil.isNumber(current.trim()) || Integer.parseInt(current.trim()) <= 0) {
            return InfoUtil.error("数据拉取失败！");
        }

        List<Map<String, Object>> archivesLists = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        if (archivesLists != null) {
            for (Map<String, Object> map : archivesLists) {
                if (map.get("archives-" + current) != null) {
                    return InfoUtil.success("数据拉取成功", map.get("archives-" + current));
                }
            }
        }

        TurnPageUtil<Archives> util = new TurnPageUtil<>(Integer.parseInt(current.trim()), archivesService.queryArchivesCount(), VariableUtils.SHWODPAGE);
        List<Archives> archives = archivesService.selectArchivesLimit(Math.min(util.getCurrentPage(), util.getPageNumber()));
        Map<String, Object> map = new HashMap<>(16, 0.75f);
        map.put("archives-" + current, archives);
        LISTS.add(map);
        MapCacheUtil.set("Archives",LISTS);
        util.setArchivesData(archives);
        return InfoUtil.success("数据拉取成功！", util.getArchivesData());
    }

    /**
     * 获取指定分页名的当前页数据
     *
     * @param current  当前页
     * @param typeName 分类名
     * @return 返回指定页数的数据
     */

    @ResponseBody
    @GetMapping({"/v1/get/archives/types/{typeName}/pages/{current}"})
    @ApiOperation(value = "获取指定分页名的当前页数据", notes = "根据当前页获取分类名下的文章数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path"),
            @ApiImplicitParam(name = "typeName", value = "分类名", required = true, paramType = "path")
    })
    public String archivesTypesPages(@PathVariable String current, @PathVariable String typeName) {
        if (IsEmptyUtil.isStringEmpty(typeName.trim()) || !IsEmptyUtil.isNumber(current.trim())) {
            return VariableUtils.minusOne();
        }

        Type type = this.typeService.queryTypeByName(typeName.trim());
        if (type == null) {
            return VariableUtils.minusOne();
        }

        List<Map<String, Object>> list = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        if (list != null) {
            for (Map<String, Object> map : list) {
                if (map.get(typeName+"-" + current) != null) {
                    return InfoUtil.success("数据拉取成功", map.get(typeName+"-" + current));
                }
            }
        }

        List<Archives> archives = this.archivesService.queryTypeArchives(type.getTypeId(), Integer.parseInt(current));
        Map<String, Object> map = new HashMap<>(16, 0.75f);
        map.put(typeName+"-" + current, archives);
        LISTS.add(map);
        MapCacheUtil.set("Archives",LISTS);
        return InfoUtil.success("数据拉取成功！", archives);
    }

    /**
     * 获取指定标签的当前页数据
     *
     * @param current 当前页
     * @param tagName 标签名
     * @return 返回指定页数的数据
     */
    @ResponseBody
    @GetMapping({"/v1/get/archives/tags/{tagName}/pages/{current}"})
    @ApiOperation(value = "获取指定标签的当前页数据", notes = "根据当前页获取标签名下的文章数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path"),
            @ApiImplicitParam(name = "tagName", value = "标签名", required = true, paramType = "path")
    })
    public String archivesTagsPages(@PathVariable String current, @PathVariable String tagName) {

        if (IsEmptyUtil.isStringEmpty(tagName.trim()) || !IsEmptyUtil.isNumber(current.trim())) {
            return VariableUtils.minusOne();
        }

        Tag tag = this.tagService.selectTag(tagName.trim());
        if (tag == null) {
            return VariableUtils.minusOne();
        }

        List<Map<String, Object>> list = (List<Map<String, Object>>) MapCacheUtil.get("Archives");
        if (list != null) {
            for (Map<String, Object> map : list) {
                if (map.get(tagName+"-" + current) != null) {
                    return InfoUtil.success("数据拉取成功", map.get(tagName+"-" + current));
                }
            }
        }

        List<Archives> archives = archivesService.queryArchivesByTagId(tag.getTagId(), Integer.parseInt(current));
        Map<String, Object> map = new HashMap<>(16, 0.75f);
        map.put(tagName+"-" + current, archives);
        LISTS.add(map);
        MapCacheUtil.set("Archives",LISTS);
        return InfoUtil.success("数据拉取成功！", archives);
    }

    /**
     * 日记点赞
     *
     * @param request 请求
     * @param dailyId 日志ID
     * @return 是否点赞成功
     */
    @ResponseBody
    @PutMapping({"/v1/put/dailyPraise/{dailyId}"})
    @ApiOperation(value = "日记点赞", notes = "根据日记ID点赞")
    @ApiImplicitParam(name = "dailyId", value = "日记ID", required = true, paramType = "path")
    public String dailyPraise(HttpServletRequest request, @PathVariable String dailyId) {
        int result = 0;

        Browser browser = VisitorUtil.getBrowser(request);
        Visitor visitor = new Visitor(VisitorUtil.getIp(request), VisitorUtil.getOperatingSystem(request), browser.getName(), VisitorUtil.getBrowserVersion(request));

        if ("127.0.0.1".equals(visitor.getVisitorIp()) || "0:0:0:0:0:0:0:1".equals(visitor.getVisitorIp())) {
            return InfoUtil.error("无法解析您的IP地址！");
        }

        Visitor data = VariableUtils.IP_MAP.get(visitor.getVisitorIp());
        boolean flag = false;
        if (data != null
                && data.getOperationSystem().equals(visitor.getOperationSystem())
                && data.getBrowser().equals(visitor.getBrowser())
                && data.getBrowserVersion().equals(visitor.getBrowserVersion())) {
            flag = true;
        }

        Visitor visitorData = flag ? data : visitorService.queryVisitorInfo(visitor);

        if (visitorData == null) {
            result = visitorService.addVisitor(visitor);
        }

        Daily daily = this.dailyService.queryDailyById(dailyId);
        if (daily == null || (visitorData == null && result == 0)) {
            return InfoUtil.error("数据异常，点赞失败！");
        }

        DailyAndVisitor dailyAndVisitor = new DailyAndVisitor(daily.getDailyId(), this.visitorService.queryVisitorInfo(visitor).getVisitorId());
        //加锁，只允许一个线程进行点赞
        if (this.dailyAndVisitorService.queryDailyAndVisitor(dailyAndVisitor) == null) {
            LOCK.lock();
            try {
                if (dailyAndVisitorService.addDailyAndVisitor(dailyAndVisitor) == 0) {
                    return InfoUtil.error("数据异常，点赞失败！");
                }
                dailyService.addDailyByGoodNumber(daily.getGoodNumber() + 1, daily.getDailyId());
                return InfoUtil.success("点赞成功，谢谢您的赞许！");
            } catch (Exception e) {
                return InfoUtil.error("数据异常，点赞失败！");
            } finally {
                LOCK.unlock();
            }
        }

        return InfoUtil.error("谢谢您的赞许，但您已经点过赞了！");
    }

    /**
     * 获取足迹的数据
     *
     * @param current 当前页
     * @return 获取指定页数的数据
     */
    @ResponseBody
    @GetMapping({"/v1/get/getFootMarkData/{current}"})
    @ApiOperation(value = "获取足迹的数据", notes = "获取当前页获取足迹的数据")
    @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path")
    public String getFootMarkData(@PathVariable String current) {
        if (!IsEmptyUtil.isNumber(current.trim()) || Integer.parseInt(current.trim()) <= 0) {
            return InfoUtil.error("数据拉取失败！");
        }

        List<Map<String, Object>> footMarkData = (List<Map<String, Object>>) MapCacheUtil.get("FootMarkData");
        if (footMarkData != null) {
            for (Map<String, Object> map : footMarkData) {
                if (map.get("footMarkData-" + current) != null) {
                    return InfoUtil.success("数据拉取成功", map.get("footMarkData-" + current));
                }
            }
        }

        TurnPageUtil<Archives> data = new TurnPageUtil<>(Integer.parseInt(current.trim()), archivesService.queryArchivesCount(), VariableUtils.SHWODPAGE * 2);
        List<Archives> lists = archivesService.queryArchivesTypeName(Math.min(data.getPageNumber(), data.getCurrentPage()), data.getShowPage());
        Map<String, Object> map = new HashMap<>(16, 0.75f);
        map.put("footMarkData-" + current, lists);
        FOOTMARK_LISTS.add(map);
        MapCacheUtil.set("FootMarkData",FOOTMARK_LISTS);
        return InfoUtil.success("数据拉取成功！", lists);
    }


    /**
     * 日志分页
     *
     * @param current 当前页
     * @return 返回指定页数的数据
     */
    @ResponseBody
    @GetMapping({"/v1/get/changeLogPaging/{current}"})
    @ApiOperation(value = "日志分页", notes = "获取指定页数的日记数")
    @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path")
    public String changeLogPaging(@PathVariable String current) {
        if (!IsEmptyUtil.isNumber(current.trim()) || Integer.parseInt(current.trim()) <= 0) {
            return InfoUtil.error("数据拉取失败！");
        }

        List<Map<String, Object>> lists = (List<Map<String, Object>>) MapCacheUtil.get("Log");
        if (lists != null) {
            for (Map<String, Object> map : lists) {
                if (map.get("logPages-" + current) != null) {
                    return InfoUtil.success("数据拉取成功！", map.get("logPages-" + current));
                }
            }
        }

        TurnPageUtil<ChangeLog> page = new TurnPageUtil<>(Integer.parseInt(current.trim()), changeLogService.changeLogCount(), VariableUtils.SHWODPAGE * 2);
        List<ChangeLog> changeLogs = changeLogService.queryChangeLog(Math.min(page.getCurrentPage(), page.getPageNumber()));
        page.setArchivesData(changeLogs);
        Map<String, Object> map = new HashMap<>(16, 0.75f);
        map.put("logPages-" + current, changeLogs);
        LOG_LISTS.add(map);
        MapCacheUtil.set("Archives",LOG_LISTS);
        return InfoUtil.success("数据拉取成功！", page.getArchivesData());
    }

    /**
     * @param data    分类名
     * @param current 当前页
     * @return 返回结果
     */
    @ResponseBody
    @GetMapping({"/v1/get/archives/search/{data}/pages/{current}"})
    @ApiOperation(value = "查询数据获取文章信息", notes = "根据输入的数据查询匹配的文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "分类名", required = true, paramType = "path"),
            @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "path")
    })
    public String searchData(@PathVariable String data, @PathVariable String current) {
        String regex = "\\d+";
        if (IsEmptyUtil.isStringEmpty(data.trim()) ||
                IsEmptyUtil.isStringEmpty(current.trim()) ||
                !current.matches(regex)) {
            return VariableUtils.minusOne();
        }
        Type type = typeService.queryTypeByName(data);
        Tag tag = this.tagService.selectTag(data);
        String dataSearch = data.replaceAll("\u5E74", "-").replaceAll("\u6708", "-").replaceAll("\u65E5", "-").replaceAll("/", "-");
        TurnPageUtil<Archives> page = new TurnPageUtil<>();
        page.setCurrentPage(Integer.parseInt(current));

        if (type != null) {
            page.setAllPage(this.archivesService.queryTypeArchivesCount(type.getTypeId()));
            page.setShowPage(VariableUtils.SHWODPAGE);
            page.setArchivesData(this.archivesService.queryTypeArchives(type.getTypeId(), page.getCurrentPage()));
        } else if (tag != null) {
            page.setAllPage(this.archivesService.queryArchivesByTagIdCount(tag.getTagId()));
            page.setShowPage(VariableUtils.SHWODPAGE);
            page.setArchivesData(this.archivesService.queryArchivesByTagId(tag.getTagId(), page.getCurrentPage()));
        } else if (dataSearch.matches("\\d+")) {
            page.setAllPage(this.archivesService.queryArchivesByIdOrUploadTimeCount(Long.parseLong(dataSearch), "%" + dataSearch + "%"));
            page.setShowPage(VariableUtils.SHWODPAGE);
            page.setArchivesData(this.archivesService.queryArchivesByIdOrUploadTime(Long.parseLong(dataSearch), dataSearch, page.getCurrentPage()));
        } else {
            page.setAllPage(this.archivesService.queryArchivesByTitleAndUploadTimeCount(dataSearch, dataSearch));
            page.setShowPage(VariableUtils.SHWODPAGE);
            page.setArchivesData(this.archivesService.queryArchivesByTitleAndUploadTime(dataSearch, dataSearch, page.getCurrentPage()));
        }

        if (page.getAllPage() == 0) {
            return InfoUtil.error("查询不到数据！");
        }

        return page.getAllPage() == 0 && page.getArchivesData().size() == 0 ? InfoUtil.error("数据获取异常！") : InfoUtil.success("数据拉取成功！", page.getArchivesData());
    }

    /**
     * 日记评论增加
     *
     * @param username  用户名
     * @param email     邮箱
     * @param headImage 头像地址
     * @param content   内容
     * @param dailyId   日记ID
     * @param state     日记还是文章
     * @return 返回增加的结果
     */
    @ResponseBody
    @PostMapping("/v1/post/addComment")
    @ApiOperation(value = "增加评论信息(日记)", notes = "在日记的ID下写评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "headImage", value = "头像图片", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "dailyId", value = "日记ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "reply", value = "回复", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "状态", required = true, paramType = "query"),
            @ApiImplicitParam(name = "attachment", value = "父类ID(可以没有)", paramType = "query"),
            @ApiImplicitParam(name = "condition", value = "判断从属关系(为1condition就不能有数据)", required = true, paramType = "query")
    })
    public String addComment(String username, String email, @RequestParam("headImg") String headImage, String content, String dailyId, String reply, String state, String attachment, String condition) {
        Daily daily = dailyService.queryDailyById(dailyId);
        if (daily == null) {
            //日记ID为空或者错误
            return InfoUtil.error("日记信息异常！");
        }

        String contentNotError = InfoUtil.isContentNotError(state, username, condition, email, content, headImage);
        if (contentNotError != null) {
            return contentNotError;
        }

        String message = InfoUtil.isState(state, reply, attachment, username, 0, dailyDiscussService, archivesDiscussService, daily.getDailyId(), 0);
        if (message != null) {
            return message;
        }
        int status = IsEmptyUtil.isUrlRight(headImage);
        String rep = "".equals(reply.trim()) ? null : reply.trim();
        List<com.tanx.blogs.pojo.model.User> users = userService.queryUserNameList(username);
        if (Integer.parseInt(condition) == 2 && users.size() == 0) {
            return InfoUtil.error("登录名不存在！");
        }
        LOCK.lock();
        try {
            //判断图片链接是否有效
            DailyDiscuss discuss = new DailyDiscuss(Integer.parseInt(condition) == 2 ? users.get(0).getUsername() : username.trim(),
                    Long.parseLong(dailyId),
                    content.trim(),
                    new Date(),
                    Integer.parseInt(condition) == 2 ? users.get(0).getEmail() : email.trim(),
                    Integer.parseInt(condition) == 2 ? users.get(0).getHeadImg() : status == HttpURLConnection.HTTP_OK ? headImage : "https://api.oss.tanxiangblog.com/images/users/akari.jpg",
                    rep,
                    Integer.parseInt(state),
                    IsEmptyUtil.isNumber(attachment) ? Long.parseLong(attachment) : 0);

            if (dailyDiscussService.addDiscuss(discuss) == 1) {
                dailyService.addDailyByDiscussNumber(daily.getDiscussNumber() + 1, daily.getDailyId());
                MapCacheUtil.remove("DailyDiscuss-" + dailyId);
                return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ONE, "发布成功！"));
            }
            return InfoUtil.error("出现异常，发布失败！");
        } catch (Exception e) {
            return InfoUtil.error(e.getMessage());
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 根据日记ID查询数据
     *
     * @param dailyId 日记ID
     * @return 返回信息
     */
    @ResponseBody
    @GetMapping("/v1/get/getDiscuss")
    @ApiOperation(value = "根据日记ID查询评论", notes = "获取指定日记下的评论信息")
    @ApiImplicitParam(name = "dailyId", value = "日记ID", required = true, paramType = "query")
    public String getDiscussByDaily(String dailyId) {

        Daily daily = dailyService.queryDailyById(dailyId);
        if (daily == null) {
            return InfoUtil.error("日记异常！");
        }

        List<DailyDiscuss> dailyDiscuss = (List<DailyDiscuss>) MapCacheUtil.get("DailyDiscuss-" + dailyId);
        if (dailyDiscuss != null) {
            return InfoUtil.success("查询成功", dailyDiscuss);
        }

        List<DailyDiscuss> discusses = dailyDiscussService.queryDiscussByDailyId(dailyId);
        MapCacheUtil.set("DailyDiscuss-" + dailyId, discusses);
        return InfoUtil.success("查询成功！", discusses);
    }

    /**
     * 日记分页
     *
     * @param current 当前页
     * @return 返回指定页数的数据
     */
    @ResponseBody
    @GetMapping({"/v1/get/getDailyData/{current}"})
    @ApiOperation(value = "日记分页", notes = "获取指定页数的日记信息")
    @ApiImplicitParam(name = "current", value = "当前页", required = true, paramType = "query")
    public String getDaily(@PathVariable String current) {
        if (!IsEmptyUtil.isNumber(current)) {
            return InfoUtil.error("数据拉取失败！");
        }

        List<Map<String, Object>> dailyPages = (List<Map<String, Object>>) MapCacheUtil.get("dailyPages");
        if (dailyPages != null) {
            for (Map<String, Object> map : dailyPages) {
                if (map.get("dailyPages-" + current) != null) {
                    return InfoUtil.success("数据拉取成功！", map.get("dailyPages-" + current));
                }
            }
        }

        Map<String, Object> map = new HashMap<>(16, 0.75f);
        TurnPageUtil<Daily> page = new TurnPageUtil<>(Integer.parseInt(current.trim()), dailyService.dailyCount(), VariableUtils.SHWODPAGE * 2);
        List<Daily> dailyList = dailyService.queryDaily(Math.min(page.getPageNumber(), page.getCurrentPage()));
        page.setArchivesData(dailyList);
        map.put("dailyPages-" + current, dailyList);
        DAILY_LISTS.add(map);
        MapCacheUtil.set("dailyPages",DAILY_LISTS);
        return InfoUtil.success("数据拉取成功！", page.getArchivesData());
    }

    @ResponseBody
    @PostMapping("/v1/post/addArchivesComment")
    @ApiOperation(value = "增加文章评论", notes = "增加指定文章ID下的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "headImage", value = "头像图片", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "发布内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "archivesId", value = "文章ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "reply", value = "回复", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "当前等级(处于几级评论)", required = true, paramType = "query"),
            @ApiImplicitParam(name = "attachment", value = "父类ID(可以没有)", paramType = "query"),
            @ApiImplicitParam(name = "condition", value = "判断从属关系(为1condition就不能有数据)", required = true, paramType = "query")
    })
    public String addArchivesComment(String username, String email, @RequestParam("headImg") String headImage, String content, String archivesId, String reply, String state, String attachment, String condition) {
        Archives archives = archivesService.queryArchivesId(archivesId);
        if (archives == null) {
            return InfoUtil.error("评论发布失败！");
        }

        String contentNotError = InfoUtil.isContentNotError(state, username, condition, email, content, headImage);
        if (contentNotError != null) {
            return contentNotError;
        }

        //判断属于哪一级评论
        String message = InfoUtil.isState(state, reply, attachment, username, 1, dailyDiscussService, archivesDiscussService, 0, archives.getArchivesId());
        if (message != null) {
            return message;
        }
        int status = IsEmptyUtil.isUrlRight(headImage);
        String rep = "".equals(reply.trim()) ? null : reply.trim();
        List<com.tanx.blogs.pojo.model.User> users = userService.queryUserNameList(username);
        if (Integer.parseInt(condition) == 2 && users.size() == 0) {
            return InfoUtil.error("登录名不存在！");
        }
        LOCK.lock();
        try {
            //判断图片链接是否有效
            ArchivesDiscuss discuss = new ArchivesDiscuss(
                    Integer.parseInt(condition) == 2 ? users.get(0).getUsername() : username.trim(),
                    Long.parseLong(archivesId),
                    content.trim(),
                    new Date(),
                    Integer.parseInt(condition) == 2 ? users.get(0).getEmail() : email.trim(),
                    Integer.parseInt(condition) == 2 ? users.get(0).getHeadImg() : status == HttpURLConnection.HTTP_OK ? headImage.trim() : "https://api.oss.tanxiangblog.com/images/users/akari.jpg",
                    rep,
                    Integer.parseInt(state),
                    IsEmptyUtil.isNumber(attachment) ? Long.parseLong(attachment) : 0);

            if (archivesDiscussService.addDiscuss(discuss) == 1) {
                archivesService.updateArchivesDiscussNumber(archivesId, archives.getDiscussNumber() + 1);
                MapCacheUtil.remove("ArchivesDiscuss-" + archivesId);
                return InfoUtil.success("发布成功！");
            }

            return InfoUtil.error("出现异常，发布失败！");
        } catch (Exception e) {
            return InfoUtil.error(e.getMessage());
        } finally {
            LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/get/getArchivesDiscuss")
    @ApiOperation(value = "获取文章评论", notes = "获取指定文章ID下面的评论")
    @ApiImplicitParam(name = "archivesId", value = "文章ID", required = true, paramType = "query")
    public String getArchivesDiscuss(String archivesId) {
        List<ArchivesDiscuss> discusses = (List<ArchivesDiscuss>) MapCacheUtil.get("ArchivesDiscuss-" + archivesId);
        if (discusses != null) {
            return InfoUtil.success("查询成功！", discusses);
        }

        if (archivesService.queryArchivesId(archivesId) == null) {
            InfoUtil.error("文章错误！");
        }

        List<ArchivesDiscuss> archivesDiscusses = archivesDiscussService.queryDiscussByArchivesId(archivesId);
        MapCacheUtil.set("ArchivesDiscuss-" + archivesId, archivesDiscusses);
        return InfoUtil.success("查询成功！", archivesDiscusses);
    }

    @ResponseBody
    @PostMapping("/v1/post/addMessageComment")
    @ApiOperation(value = "增加留言板信息", notes = "留言版回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "website", value = "网站网址", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query"),
            @ApiImplicitParam(name = "headImg", value = "头像图片", required = true, paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "query"),
            @ApiImplicitParam(name = "reply", value = "回复者", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "评论等级", required = true, paramType = "query"),
            @ApiImplicitParam(name = "attachment", value = "父类ID", paramType = "query"),
    })
    public String addMessageComment(String website, String username, String email, String headImg, String content, String reply, String state, String attachment) {
        int statusUrl = IsEmptyUtil.isUrlRight(website);
        int statusImg = IsEmptyUtil.isUrlRight(headImg);
        int data = headImg.lastIndexOf(".") == -1 ? IsEmptyUtil.isStringEmpty(headImg) ? -1 : 0 : headImg.lastIndexOf(".");
        String image = data == -1 || data == 0 ? "image" : headImg.substring(data + 1);
        boolean flag = !IsEmptyUtil.isStringEmpty(headImg) && !("jpg".equals(image) || "jpeg".equals(image) || "png".equals(image));
        if (statusUrl == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            //判断网址的格式否正确
            return InfoUtil.error("网址异常！");
        } else if (flag) {
            //判断链接的格式否正确
            return InfoUtil.error("链接格式错误！");
        } else if (statusImg == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            return InfoUtil.error("链接错误！");
        } else if ("Tree".equals(username)) {
            //判断名称是否和管理员名称一致
            return InfoUtil.error("用户名需更换！");
        } else if (IsEmptyUtil.isStringEmpty(email)) {
            //判断邮箱是否为空
            return InfoUtil.error("邮箱不能为空！");
        } else if (!email.matches(VariableUtils.REGEXEMAIL)) {
            //判断又想是否满足格式
            return InfoUtil.error("邮箱格式错误！");
        } else if (IsEmptyUtil.isStringEmpty(content.trim())) {
            return InfoUtil.error("内容不能为空！");
        } else if (content.trim().length() > 900) {
            return InfoUtil.error("内容过长！");
        } else if (!IsEmptyUtil.isNumber(state)) {
            //判断评论等级是否正确
            return InfoUtil.error("无法判断属于几级评论！");
        }
        switch (Integer.parseInt(state)) {
            case 1:
                //判断回复对象是否为空
                if (!IsEmptyUtil.isStringEmpty(reply)) { return InfoUtil.error("错误异常！");
                }
                if (IsEmptyUtil.isNumber(attachment)) { return InfoUtil.error("依赖关系异常！");
                }
                break;
            case 2:
                //判断回复对象是否为空
                if (IsEmptyUtil.isStringEmpty(reply)) { return InfoUtil.error("无法判断回复给谁！");
                }
                if (reply.equals(username)) { return InfoUtil.error("不能与回复对象名称一致！");
                }
                //回复对象ID
                if (!IsEmptyUtil.isNumber(attachment)) { return InfoUtil.error("依赖关系异常！");
                }
                if (!boardDiscussService.queryBoardDiscussByReply(attachment, reply)) { return InfoUtil.error("回复对象为空！");
                }
                break;
            default:
                return InfoUtil.error("错误的评论等级！");
        }
        BoardDiscuss discuss = new BoardDiscuss(
                username,
                statusUrl == HttpURLConnection.HTTP_OK || statusUrl == HttpURLConnection.HTTP_MOVED_PERM ? website : null,
                content, new Date(), email,
                statusImg == HttpURLConnection.HTTP_OK || statusImg == HttpURLConnection.HTTP_MOVED_PERM ? headImg : null,
                reply, Integer.parseInt(state), Integer.parseInt(state) == 1 ? 0 : Long.parseLong(attachment));
        LOCK.lock();
        try {
            if (!boardDiscussService.addBoardDiscuss(discuss)) {
                return InfoUtil.error("发布失败！");
            }
            // 发布成功，删除缓存中的数据
            MapCacheUtil.remove("BoardDiscuss");
            return InfoUtil.success("发布成功！");
        } catch (Exception e) {
            return InfoUtil.error(e.getMessage());
        } finally {
            LOCK.unlock();
        }
    }

    @ResponseBody
    @GetMapping("/v1/get/boardDiscuss")
    @ApiOperation(value = "留言墙评论", notes = "获取留言墙评论")
    public String boardDiscuss() {
        List<BoardDiscuss> boardDiscuss = (List<BoardDiscuss>) MapCacheUtil.get("BoardDiscuss");
        if (boardDiscuss != null) {
            return InfoUtil.success("数据拉取成功！", boardDiscuss);
        }

        List<BoardDiscuss> boardDiscusses = boardDiscussService.queryBoardDiscuss();
        MapCacheUtil.set("BoardDiscuss", boardDiscusses);
        return InfoUtil.success("数据拉取成功！", boardDiscusses);
    }

    @ResponseBody
    @PostMapping("/v1/post/addFriendLink")
    @ApiOperation(value = "上传友链信息", notes = "使用了上传自己网站的功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "web", value = "网站", required = true, paramType = "query"),
            @ApiImplicitParam(name = "image", value = "图片", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "desc", value = "描述", required = true, paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "query")
    })
    public String addFriendLink(String web, String image, String username, String desc, String email) {
        int webState = IsEmptyUtil.isUrlRight(web);
        int imageState = IsEmptyUtil.isUrlRight(image);
        if (webState == 404) {
            return InfoUtil.error("网址为空！");
        } else if (imageState == 404) {
            return InfoUtil.error("头像图片地址为空！");
        } else if (webState == 500) {
            return InfoUtil.error("网址请求失败！");
        } else if (imageState == 500) {
            return InfoUtil.error("头像图片请求失败！");
        } else if (IsEmptyUtil.isStringEmpty(username)) {
            //名称判断是否为空
            return InfoUtil.error("名称为空！");
        } else if (IsEmptyUtil.isStringEmpty(desc)) {
            //描述判断是否为空
            return InfoUtil.error("描述为空！");
        } else if (!IsEmptyUtil.isStringEmpty(email) && !email.matches(VariableUtils.REGEXEMAIL)) {
            //判断又想是否满足格式
            return InfoUtil.error("邮箱格式错误！");
        }

        FriendLink friendLink;
        if ((webState == HttpURLConnection.HTTP_OK || webState == HttpURLConnection.HTTP_MOVED_PERM)
                && (imageState == HttpURLConnection.HTTP_OK || imageState == HttpURLConnection.HTTP_MOVED_PERM)) {
            friendLink = new FriendLink(username, desc, web, image, email);
        } else {
            return InfoUtil.error("发送失败！");
        }

        try {
            mailSend.send(friendLink);
        } catch (MessagingException e) {
            return InfoUtil.error(e.getMessage());
        }

        return friendLinkService.addFriendLink(friendLink) ? InfoUtil.success("上传成功！") : InfoUtil.error("上传失败！");
    }

    @ResponseBody
    @GetMapping("/v1/get/getNewsArchives")
    @ApiOperation(value = "获取最新文章", notes = "获取前五条最新文章")
    public String getNewsArchives() {
        List<Archives> newsArchives = (List<Archives>) MapCacheUtil.get("NewsArchives");

        if (newsArchives != null) {
            return InfoUtil.success("数据获取成功！", newsArchives);
        }

        List<Archives> archives = archivesService.selectArchivesNews();
        MapCacheUtil.set("NewsArchives", archives);
        return InfoUtil.success("数据获取成功！", archives);
    }


    @ResponseBody
    @GetMapping("/v1/get/getHotsArchives")
    @ApiOperation(value = "获取最火文章", notes = "获取前五条最火文章")
    public String getHotsArchives() {
        List<Archives> hotsArchives = (List<Archives>) MapCacheUtil.get("HotsArchives");

        if (hotsArchives != null) {
            return InfoUtil.success("数据获取成功！", hotsArchives);
        }

        List<Archives> archives = archivesService.selectArchivesHots();
        MapCacheUtil.set("HotsArchives", archives);
        return InfoUtil.success("数据获取成功！", archives);
    }

    @ResponseBody
    @GetMapping("/v1/get/getDiscussArchives")
    @ApiOperation(value = "获取最新的文章评论数", notes = "获取前五条文章评论数")
    public String getDiscussArchives() {
        List<Archives> discussArchives = (List<Archives>) MapCacheUtil.get("DiscussArchives");

        if (discussArchives != null) {
            return InfoUtil.success("数据获取成功！", discussArchives);
        }

        List<ArchivesDiscuss> list = archivesDiscussService.queryDiscuss();
        MapCacheUtil.set("DiscussArchives", list);
        return InfoUtil.success("数据获取成功！", list);
    }
}

