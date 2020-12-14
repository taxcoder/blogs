package com.tanx.blogs.controller;

import com.tanx.blogs.pojo.model.*;
import com.tanx.blogs.service.changelog.ChangeLogService;
import com.tanx.blogs.service.daily.DailyService;
import com.tanx.blogs.service.mail.MailSend;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.service.user.UserService;
import com.tanx.blogs.utils.*;
import com.tanx.blogs.utils.EncryptionUtil;
import com.tanx.blogs.utils.OssUploadUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping({"/super"})
@Api(tags = "登录用户接口",hidden = true)
public class AdminController {
    private MailSend mailSend;
    private TagService tagService;
    private UserService userService;
    private TypeService typeService;
    private DailyService dailyService;
    private ChangeLogService changeLogService;

    @Autowired
    public void setMailSend(MailSend mailSend) {
        this.mailSend = mailSend;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
    public void setChangeLogService(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @Autowired
    public void setDailyService(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    /**
     * 忘记密码
     * @param request 请求
     * @return 返回件的发送情况
     */
    @ResponseBody
    @PostMapping({"/operate/emailSend"})
    @ApiOperation(value = "邮件发送",notes = "登录用户修改密码发送验证码")
    public String changePassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return InfoUtil.error("用户不存在！");
        }

        String randomNumber = VerifyCodeUtil.getRandomNumber();
        try {
            mailSend.send(user, "修改密码验证码", ("您的验证码是：" + randomNumber));
            session.setAttribute("verification", randomNumber);
            return InfoUtil.success("发送成功!");
        } catch (MessagingException e) {
            return InfoUtil.error(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping({"/operate/updatePwd"})
    @ApiOperation(value = "修改密码",notes = "登录用户的修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newPassword",value="新密码",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code",value="验证码",required = true,paramType = "query")
    })
    public String updatePassword(String newPassword, HttpServletRequest request, String code) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return InfoUtil.error("用户不存在！");
        }
        String verification = (String) session.getAttribute("verification");
        String regexPassword = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$";

        if (!verification.equals(code)) {
            return InfoUtil.error("验证码错误!");
        }

        if (!FilterDataUtil.filterTrimAndFeed(newPassword).matches(regexPassword) || IsEmptyUtil.isStringEmpty(newPassword)) {
            return InfoUtil.error("密码长度8到16位,必须包含数字、字母，区分大小写！");
        }

        if (userService.updatePassword(EncryptionUtil.simpleMD5(newPassword), user.getUserId(), user.getLoginName()) == 0) {
            return InfoUtil.error("密码修改失败，请重试！");
        }
        session.invalidate();
        return InfoUtil.success("密码修改成功！");
    }

    @ResponseBody
    @PostMapping({"/administrator/addTypeName"})
    @ApiOperation(value = "增加文章分类",notes = "增加分类,给文章更多的选择")
    @ApiImplicitParam(name = "typeName",value="分类名称",required = true,paramType = "query")
    public String addCategory(String typeName) {

        if (IsEmptyUtil.isStringEmpty(typeName)) {
            return InfoUtil.error("分类名为空！");
        }
        if (typeService.queryTypeByName(typeName) != null) {
            return InfoUtil.error("此分类名已存在！");
        }
        if (tagService.selectTag(typeName) != null) {
            return InfoUtil.error("分类名不能和标签名一致！");
        }

        if(typeService.addType(typeName) != 1){
            return InfoUtil.error("增加失败！");
        }

        MapCacheUtil.remove("Archives");
        VariableUtils.LIST.clear();
        return InfoUtil.success("增加成功！");
    }


    @ResponseBody
    @GetMapping({"/administrator/types"})
    @ApiOperation(value = "获取所以的分类信息",notes = "在文章编写页面时，获取全部的分类信息")
    public String getCategory() {
        List<Type> types = typeService.selectTypes();

        if (typeService.selectTypesCount() == 0) {
            return InfoUtil.error("分类为空，请添加分类!");
        }

        return types == null || types.size() == 0?InfoUtil.error("数据请求失败！"):InfoUtil.success("数据请求成功!", types);
    }


    @ResponseBody
    @PostMapping({"/administrator/addTagName"})
    @ApiOperation(value = "增加标签名",notes = "给文章增加标签")
    @ApiImplicitParam(name = "tagName",value="标签名称",required = true,paramType = "query")
    public String addTagName(String tagName) {
        if (IsEmptyUtil.isStringEmpty(tagName)) {
            return InfoUtil.error("标签名为空！");
        }
        if (tagService.selectTag(tagName) != null) {
            return InfoUtil.error("此标签已存在！");
        }
        if (typeService.queryTypeByName(tagName) != null) {
            return InfoUtil.error("标签名不能和分类一致！");
        }

        if(tagService.addTag(tagName) != 1){
            return InfoUtil.error("增加失败！");
        }

        MapCacheUtil.remove("Archives");
        VariableUtils.LIST.clear();
        return InfoUtil.success("增加成功！");
    }


    @ResponseBody
    @GetMapping({"/administrator/tags"})
    @ApiOperation(value = "获取全部的标签名",notes = "在文章编写页面时，获取全部的标签信息")
    public String getTagData() {
        List<Tag> tags = tagService.selectTags();
        if (tagService.selectTagsCount() == 0) {
            return InfoUtil.error("标签为空，请添加标签！");
        }

        return tags == null || tags.size() == 0?InfoUtil.error("数据请求失败！"):InfoUtil.success("数据请求失败！",tags);
    }


    @ResponseBody
    @PutMapping({"/administrator/updateAdminInfo"})
    @ApiOperation(value = "修改个性签名以及地址",notes = "修改个性签名以及地址(tree)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign",value="个性签名",paramType = "query"),
            @ApiImplicitParam(name = "address",value="当前地址",paramType = "query")
    })
    public String updateInfo(String sign, String address, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        if (!userService.queryUserById(String.valueOf(user.getUserId()))) {
            return InfoUtil.error("您的权限不足！");
        }

        if ("null".equalsIgnoreCase(sign) && "null".equalsIgnoreCase(address)) {
            return InfoUtil.error("个性签名和地址至少填写一项！");
        }

        if (!"null".equalsIgnoreCase(address) && userService.updateAdminInfoAddress(user.getUserId(), address) == 0) {
            return InfoUtil.error("地址修改失败！");
        }

        return (!"null".equalsIgnoreCase(sign)
                && userService.updateAdminInfoSign(user.getUserId(), sign) == 0)
                ?InfoUtil.error("签名修改失败！")
                :InfoUtil.success("更改成功！");
    }

    @ResponseBody
    @PostMapping({"/administrator/addDaily"})
    @ApiOperation(value = "增加日记",notes = "增加日记")
    @ApiImplicitParam(name = "content",value="日记信息",required = true,paramType = "query")
    public String addDaily(String content) {

        if (IsEmptyUtil.isStringEmpty(content)) {
            return InfoUtil.error("发布的信息不能为空！");
        }
        Daily daily = new Daily(content.trim(), new Date());
        int result = dailyService.addDaily(daily);
        if(result == 0){
            return InfoUtil.error("发布失败，请重新尝试！");
        }

        MapCacheUtil.remove("dailyPages");
        MapCacheUtil.remove("DailyCount");
        VariableUtils.DAILY_LIST.clear();
        return InfoUtil.success("发布成功！");
    }

    @ResponseBody
    @PutMapping({"/administrator/addNotice"})
    @ApiOperation(value = "编写公告栏",notes = "编写公告栏给用户看")
    @ApiImplicitParam(name = "bulletinBoardContent",value="编写公告栏信息",required = true,paramType = "query")
    public String bulletinBoard(@RequestParam(value = "bbc", required = false) String bulletinBoardContent) {
        if (IsEmptyUtil.isStringEmpty(bulletinBoardContent.trim())) {
            return InfoUtil.error("公告栏不能为空！");
        }

        VariableUtils.MAP.put("notice", bulletinBoardContent);
        return InfoUtil.success("修改成功！");
    }


    @ResponseBody
    @PostMapping({"/administrator/addLog"})
    @ApiOperation(value = "编写日志信息",notes = "编写系统日志")
    @ApiImplicitParam(name = "logContent",value="日志内容",required = true,paramType = "query")
    public String addLog(@RequestParam("changeLog") String logContent) {
        if (IsEmptyUtil.isStringEmpty(logContent.trim())) {
            return InfoUtil.error("日志内容为空！");
        }

        int result = changeLogService.addChangeLog(new ChangeLog(logContent, new Date()));
        if(result == 0){
           return InfoUtil.error("日志发布失败！");
        }
        MapCacheUtil.remove("Log");
        VariableUtils.LOG_LIST.clear();
        return InfoUtil.success("发布成功！");
    }

    @ResponseBody
    @SuppressWarnings("all")
    @PostMapping({"/userImages/ossUpload"})
    @ApiOperation(value = "修改用户的头像",notes = "用户修改自己的头像图片")
    @ApiImplicitParam(name = "image",value="图片内容(base64)",required = true,paramType = "query")
    public String uploadImageAdministratorOss(String image, HttpServletRequest request) {

        if (IsEmptyUtil.isStringEmpty(image)) {
            return InfoUtil.error("上传异常！");
        }

        String[] data = image.split(",");
        String type = data[0].split("/")[1].split(";")[0];
        if (!"png".equals(type) && !"jpeg".equals(type) && !"jpg".equals(type)) {
            return InfoUtil.error("上传文件类型必须为png,jpg,jpeg！");
        }
        byte[] bytes = EncryptionUtil.decoderByte(data[1], 1);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String img = "image-" + format.format(date) + "t." + type;
        if ("Linux".equals(System.getProperty("os.name"))) {
            try {
                File file = new File(File.separator + "blog" + File.separator + "temp" + File.separator);
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                }
                File index = new File(file, img);
                if (!index.exists()) {
                    boolean writable = index.setWritable(true, false);
                    boolean newFile = index.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(index);
                stream.write(bytes);
                stream.close();
                return OssUploadUtil.upload(index, img, "users/", request.getSession(), userService);
            } catch (IOException e) {
                return InfoUtil.error(e.getMessage());
            }
        } else {
            try {
                File file = new File("C:" + File.separator + "temp");
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                }
                File index = new File(file, img);
                if (!index.exists()) {
                    boolean newFile = file.createNewFile();
                }
                OutputStream stream = new FileOutputStream(file);
                stream.write(bytes);
                stream.close();
                return OssUploadUtil.upload(index, img, "users/", request.getSession(), userService);
            } catch (IOException e) {
                return InfoUtil.error(e.getMessage());
            }
        }
    }
}
