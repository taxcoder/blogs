package com.tanx.blogs.controller;

import com.tanx.blogs.pojo.model.Archives;
import com.tanx.blogs.pojo.model.FriendLink;
import com.tanx.blogs.pojo.model.Type;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.archivesandtag.ArchivesAndTagService;
import com.tanx.blogs.service.friendlink.FriendLinkService;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/9/1 下午2:04
 */
@RestController
@RequestMapping({"/super"})
@Api(tags = "登录用户的操作接口")
public class AdminOperationController {
    private TypeService typeService;
    private ArchivesService archivesService;
    private TagService tagService;
    private ArchivesAndTagService archivesAndTagService;
    private FriendLinkService friendLinkService;

    @Autowired
    public void setFriendLinkService(FriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }
    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setArchivesService(ArchivesService archivesService) {
        this.archivesService = archivesService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setArchivesAndTagService(ArchivesAndTagService archivesAndTagService) {
        this.archivesAndTagService = archivesAndTagService;
    }


    @DeleteMapping("/administrator/v1/delete/removeTag/{tagId}")
    @ApiOperation(value = "删除标签",notes = "根据标签ID删除标签信息")
    @ApiImplicitParam(name = "tagId",value="标签ID",required = true,paramType = "path")
    public String removeTag(@PathVariable String tagId){
        if(!IsEmptyUtil.isNumber(tagId)){
            return InfoUtil.error("ID异常！");
        }

        if(tagService.deleteTag(Long.parseLong(tagId)) == 0){
            return InfoUtil.error("删除失败！");
        }
        MapCacheUtil.remove("Archives");
        VariableUtils.LIST.clear();
        return InfoUtil.success("删除成功！");
    }

    @DeleteMapping("/administrator/v1/delete/removeType/{typeId}")
    @ApiOperation(value = "删除分类",notes = "根据分类ID删除分类信息")
    @ApiImplicitParam(name = "typeId",value="分类ID",required = true,paramType = "path")
    public String removeType(@PathVariable String typeId){
        if(!IsEmptyUtil.isNumber(typeId)){
            return InfoUtil.error("ID异常！");
        }

        if(typeService.deleteType(Long.parseLong(typeId)) == 0){
            return InfoUtil.error("删除失败！");
        }
        MapCacheUtil.remove("Archives");
        VariableUtils.LIST.clear();
        return InfoUtil.success("删除成功！");
    }

    @GetMapping("/administrator/v1/get/takeArchivesData/{current}")
    @ApiOperation(value = "获取文章的信息",notes = "根据页数获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query",value="查询的信息", paramType = "query"),
            @ApiImplicitParam(name = "current",value="当前页",required = true,paramType = "path")
    })
    public String takeArchives(String query,@PathVariable String current){

        if(!IsEmptyUtil.isNumber(current) && Integer.parseInt(current) <= 0){
            return InfoUtil.error("当前页错误！");
        }

        if(IsEmptyUtil.isStringEmpty(query)){
            TurnPageUtil<Archives> turn = new TurnPageUtil<>(
                    Integer.parseInt(current),
                    archivesService.queryArchivesCount(),
                    VariableUtils.SHWODPAGE);

            return InfoUtil.success(
                    "查询数据成功！",
                    archivesService.selectArchivesLimit(Math.min(turn.getCurrentPage(), turn.getPageNumber())),
                    turn.getPageNumber());
        }

        String archivesId = null;
        String title = null;
        String uploadTime = null;

        for(String temp: query.toLowerCase().split(" ")){
            if(temp.contains("id")){
                archivesId = temp.split(":")[1];
            }
            if(temp.contains("archivesname")){
                title = temp.split(":")[1];
            }

            if(temp.contains("uploadtime")){
                uploadTime = temp.split(":")[1];
            }
        }

        if(archivesId == null && title == null && uploadTime == null){
            return InfoUtil.error("格式输入错误！");
        }

        if(!IsEmptyUtil.isStringEmpty(archivesId) && !archivesId.matches("\\d+")){
            return InfoUtil.error("文章的ID错误！");
        }

        TurnPageUtil<Archives> turn = new TurnPageUtil<>(
                Integer.parseInt(current),
                archivesService.queryArchivesSearchCount(IsEmptyUtil.isNumber(archivesId)?Long.parseLong(archivesId):null, title, uploadTime),
                VariableUtils.SHWODPAGE);

        List<Archives> archives = archivesService.queryArchivesSearch(
                    IsEmptyUtil.isNumber(archivesId)?Long.parseLong(archivesId):null,
                    IsEmptyUtil.isStringEmpty(title)?null:title.trim(),
                    IsEmptyUtil.isStringEmpty(uploadTime)?null:uploadTime.trim(),
                    Math.min(turn.getCurrentPage() , turn.getPageNumber()));

        return InfoUtil.success("查询数据成功！", archives, turn.getPageNumber());
    }

    @PutMapping("/administrator/v1/put/updateArchivesInfo/{archivesId}")
    @ApiOperation(value = "修改文章信息",notes = "根据文章的ID修改文章的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value="标题", paramType = "query"),
            @ApiImplicitParam(name = "subTitle",value="副标题",paramType = "query"),
            @ApiImplicitParam(name = "archivesId",value="文章ID",required = true,paramType = "path")
    })
    public String updateArchivesInfo(String title,String subTitle,@PathVariable String archivesId){
        if(!IsEmptyUtil.isStringEmpty(title,subTitle)){
            return InfoUtil.error("标题和副标题都为空！");
        }
        if(title.length() > 20){
            return InfoUtil.error("标题过长(>20)！");
        }
        if(subTitle.length() > 70){
            return InfoUtil.error("说明过长(>70)！");
        }
        if(!IsEmptyUtil.isNumber(archivesId)){
            return InfoUtil.error("文章ID错误！");
        }
        if(archivesService.queryArchivesId(archivesId)==null){
            return InfoUtil.error("文章ID未查询到数据！");
        }

        return archivesService.updateArchivesInfo(title,subTitle,Long.parseLong(archivesId)) == 0?InfoUtil.error("文章修改错误！"):InfoUtil.success("文章修改成功！");
    }

    @DeleteMapping("/administrator/v1/delete/deleteArchives/{archivesId}")
    @ApiOperation(value = "删除文章",notes = "根据文章ID删除文章")
    @ApiImplicitParam(name = "archivesId",value="文章ID",required = true, paramType = "path")
    public String deleteArchives(@PathVariable String archivesId){
        if(!IsEmptyUtil.isNumber(archivesId)){
            return InfoUtil.error("文章ID错误！");
        }

        Archives archives = archivesService.queryArchivesId(archivesId);
        if(archives == null){
            return InfoUtil.error("文章查询错误！");
        }

        if(archivesService.deleteArchivesById(archives.getArchivesId()) == 0){
            return InfoUtil.error("文章删除错误！");
        }

        String system = System.getProperty("os.name");
        if(( "Linux".equals(system)?VariableUtils.LINUXPATH:"Windows".equals(system)?VariableUtils.WINDOWSPATH:null)==null){
            return InfoUtil.error("系统类型赞不支持！");
        }
        if("Linux".equals(System.getProperty("os.name"))){
            DataOutputStream os;
            try {
                os = new DataOutputStream(Runtime.getRuntime().exec("su root").getOutputStream());
                os.writeBytes(ReaderPropertiesOrYamlUtil.properties("linux.properties", "linux.root.password")+"\n");
                os.writeBytes("rm "+VariableUtils.LINUXPATH+archives.getAddress().substring(archives.getAddress().lastIndexOf("/"))+" -rf");
                os.flush();
                os.close();
            } catch (IOException e) {
                return  InfoUtil.error("本地文件删除失败！");
            }
        }
        OssUploadUtil.deleteOssFile(archives.getAddress().split("https://api.oss.tanxiangblog.com/")[1]);
        MapCacheUtil.remove("FootMarkData");
        MapCacheUtil.remove("ArchivesCount");
        MapCacheUtil.remove("HotsArchives");
        MapCacheUtil.remove("NewsArchives");
        MapCacheUtil.remove("Archives");
        VariableUtils.LIST.clear();
        return  InfoUtil.success("文章删除成功！");
    }

    @PutMapping({"/administrator/writeArchives/v1/put/changeHtml"})
    @ApiOperation(value = "修改html的格式",notes = "将由markdown格式转换成的html代码进行样式的增加")
    @ApiImplicitParam(name = "content",value="文章信息",required = true, paramType = "query")
    public String changeHtml(String content) {
        if ("".equals(content.trim())) {
            return InfoUtil.error("数据不能为空！");
        }
        String html = "<div>"+ArchivesOperationUtil.MarkDownChangeHtml(content)+"</div>";
        return InfoUtil.success(" 转换成功！", (new ArrayList<>().add(html)));
    }

    @PostMapping({"/administrator/v1/post/uploadHtml"})
    @ApiOperation(value = "上传文章",notes = "上传文章及其他的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "html",value="文章内容",required = true, paramType = "query"),
            @ApiImplicitParam(name = "title",value="标题",required = true, paramType = "query"),
            @ApiImplicitParam(name = "subTitle",value="副标题",required = true, paramType = "query"),
            @ApiImplicitParam(name = "category",value="分类名",required = true, paramType = "query")
    })
    public String uploadHtml(String html, String title, String subTitle, String category, HttpServletRequest request) {
        //判断文本是否为空
        if (IsEmptyUtil.isStringEmpty(html)) {
            return InfoUtil.error("文章内容不能为空！");
        }
        //判断标题是否为空
        if (IsEmptyUtil.isStringEmpty(title)) {
            return InfoUtil.error("标题不能为空！");
        }
        //判断分类是否为空
        if (IsEmptyUtil.isStringEmpty(category)) {
            return InfoUtil.error("分类不能为空！");
        }

        if(!IsEmptyUtil.isStringEmpty(subTitle) && subTitle.trim().length() > 70){
            return InfoUtil.error("说明过长！");
        }
        Type type = this.typeService.queryTypeByName(category);
        //判断分类是否存在
        if (type == null) {
            return InfoUtil.error("分类不能为空！");
        }

        //获取tag的内容
        String[] tags = request.getParameter("tag").split(",");

        if (tags.length == 0) {
            return InfoUtil.error("标签至少要有一个！");
        }

        List<Long> tagList = new ArrayList<>(10);
        for (String tag : tags) {
            tagList.add(tagService.selectTag(tag).getTagId());
        }
        try {
            return ArchivesOperationUtil.createArchivesFile(title, html, subTitle, "files/", type, tagList, archivesService, archivesAndTagService, request);
        } catch (IOException e) {
            return InfoUtil.error(e.getMessage());
        }
    }

    @SuppressWarnings("all")
    @PostMapping({"/administrator/writeArchives/v1/post/uploadImageOss"})
    @ApiOperation(value = "上传文章图片",notes = "上传文章的图片到OSS，并返回地址")
    @ApiImplicitParam(name = "image",value="图片内容(base64)",required = true, paramType = "query")
    public String uploadImage(String image) {
        if (IsEmptyUtil.isStringEmpty(image)) {
            return InfoUtil.error("上传异常！");
        }
        String[] data = image.split(",");
        String type = data[0].split("/")[1].split(";")[0];

        if (!"png".equals(type) && !"jpeg".equals(type) && !"jpg".equals(type)) {
            return InfoUtil.error("上传文件类型必须为png,jpg,jpeg！");
        }

        byte[] bytes = EncryptionUtil.decoderByte(data[1], 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String img = "image-" + format.format(new Date()) + "." + type;
        if("Linux".equals(System.getProperty("os.name"))){
            try {
                File file = new File(File.separator+"blog"+File.separator+"temp"+File.separator);
                if (!file.exists()) {
                    boolean mkdirs = file.mkdirs();
                }
                File index = new File(file,img);
                if(!index.exists()){
                    boolean writable = index.setWritable(true, false);
                    boolean newFile = index.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(index);
                stream.write(bytes);
                stream.close();
                return OssUploadUtil.upload(index, img, "blogs/");
            } catch (IOException e) {
                return InfoUtil.error(e.getMessage());
            }
        }else{
            try {
                File file = new File("C:"+File.separator+"temp");
                if(!file.exists()){
                    boolean mkdirs = file.mkdirs();
                }
                File index = new File(file,img);
                if (!index.exists()) {
                    boolean newFile = file.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(bytes);
                stream.close();
                return OssUploadUtil.upload(index, img, "blogs/");
            } catch (IOException e) {
                return InfoUtil.error(e.getMessage());
            }
        }
    }

    @GetMapping("/administrator/v1/get/takeFriendLinkData/{current}")
    @ApiOperation(value = "获取友链数据",notes = "根据当前页获取友链数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value="当前页",required = true, paramType = "path"),
            @ApiImplicitParam(name = "friendName",value="友链名称", paramType = "query"),
            @ApiImplicitParam(name = "active",value="是否出于激活状态",required = true, paramType = "query")
    })
    public String takeFriendLinkData(@PathVariable String current,String friendName,String active){

        if(!IsEmptyUtil.isNumber(current)){
            return InfoUtil.error("输入的参数错误！");
        }

        if(!IsEmptyUtil.isBoolean(active)){
            return InfoUtil.error("激活参数错误！");
        }

        TurnPageUtil<FriendLink> turn = new TurnPageUtil<>(
                Integer.parseInt(current),
                friendLinkService.queryFriendLinkDataCount(friendName,Boolean.parseBoolean(active.toLowerCase())),
                VariableUtils.SHWODPAGE);
        List<FriendLink> friendLinks;

        friendLinks = friendLinkService.queryFriendLinkData(
                friendName,
                Boolean.parseBoolean(active.toLowerCase()),
                Math.min(turn.getCurrentPage(),turn.getPageNumber()));

        return InfoUtil.success( "查询完成！",friendLinks, turn.getPageNumber());
    }

    @DeleteMapping("/administrator/v1/delete/deleteFriendLink/{friendLinkId}")
    @ApiOperation(value = "删除友链",notes = "根据友ID删除友链")
    @ApiImplicitParam(name = "friendLinkId",value="友链ID",required = true, paramType = "path")
    public String deleteFriendLink(@PathVariable String friendLinkId){

        FriendLink friendLink = friendLinkService.queryFriendLinkById(friendLinkId);

        if(friendLink == null){
            return InfoUtil.error("未查询到数据！");
        }

        return friendLinkService.deleteFriendLinkById(friendLinkId)==0?InfoUtil.error("删除失败！"):InfoUtil.success("删除成功！");
    }

    @ApiOperation(value = "激活友链",notes = "修改友链的激活状态")
    @ApiImplicitParam(name = "friendLinkId",value="友链ID",required = true, paramType = "path")
    @PutMapping("/administrator/v1/put/updateFriendLinkActive/{friendLinkId}")
    public String updateFriendLinkActive(@PathVariable String friendLinkId){

        if(!IsEmptyUtil.isNumber(friendLinkId)){
            return InfoUtil.error("友链ID不为数字！");
        }

        if(friendLinkService.queryFriendLinkById(friendLinkId)==null){
            return InfoUtil.error("未查询到数据！");
        }

        return friendLinkService.updateFriendLinkActive(Long.parseLong(friendLinkId))==0?InfoUtil.error("激活失败！"):InfoUtil.success("激活成功！");
    }

    @PutMapping("/administrator/v1/put/updateFriendLinkActiveFalse/{friendLinkId}")
    @ApiOperation(value = "失活友链",notes = "修改友链的激活状态")
    @ApiImplicitParam(name = "friendLinkId",value="友链ID",required = true, paramType = "path")
    public String updateFriendLinkActiveFalse(@PathVariable String friendLinkId){

        if(!IsEmptyUtil.isNumber(friendLinkId)){
            return InfoUtil.error("友链ID不为数字！");
        }

        if(friendLinkService.queryFriendLinkById(friendLinkId)==null){
            return InfoUtil.error("未查询到数据！");
        }

        return friendLinkService.updateFriendLinkActiveFalse(Long.parseLong(friendLinkId))==0?InfoUtil.error("失活失败！"):InfoUtil.success("失活成功！");
    }
}
