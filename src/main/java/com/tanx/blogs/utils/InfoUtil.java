package com.tanx.blogs.utils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.tanx.blogs.service.archivesdiscuss.ArchivesDiscussService;
import com.tanx.blogs.service.dailydiscuss.DailyDiscussService;
import java.util.Map;
import java.util.HashMap;

/**
 * @author tan
 */
public class InfoUtil {
    private static final Map<String, Object> MAP = new HashMap<>(16, 0.75F);

    public static Map<String, Object> info(Integer state, String message) {
        return info(state, message, null);
    }

    public static Map<String, Object> info(Integer state, String message, String data) {
        MAP.put("state", state);
        MAP.put("message", message);
        MAP.put("data", data);
        return MAP;
    }

    public static Map<String, Object> info(Integer state, String message, String data, Integer pageNumber) {
        MAP.put("state", state);
        MAP.put("message", message);
        MAP.put("data", data);
        MAP.put("pageNumber", pageNumber);
        return MAP;
    }

    public static String isContentNotError(String state,String username,String condition,String email,String content,String headImage) {

        if (!IsEmptyUtil.isNumber(state)) {
            //判断是否为一级或二级评论
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO, "出现异常请重试！"));
        }
        if(IsEmptyUtil.isStringEmpty(username.trim())){
            //判断用户名是否为空
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"用户名不能为空！"));
        }
        if(!IsEmptyUtil.isNumber(condition) && !(Integer.parseInt(condition)==1 || Integer.parseInt(condition)==2)){
            //判断从属关系
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"出现异常请重试！"));
        }
        if(Integer.parseInt(condition) == 1 && "Tree".equals(username.trim())){
            //判断名称是否和管理员名称一致
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"用户名需更换！"));
        }
        if(IsEmptyUtil.isStringEmpty(email.trim())){
            //判断邮箱是否为空
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"邮箱不能为空！"));
        }else if(!email.trim().matches(VariableUtils.REGEXEMAIL)){
            //判断又想是否满足格式
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"邮箱格式错误！"));
        }

        int data = headImage.lastIndexOf(".")==-1?IsEmptyUtil.isStringEmpty(headImage)?-1:0:headImage.lastIndexOf(".");
        String image = data==-1 || data == 0?"image":headImage.substring(data+1);
        boolean flag = !IsEmptyUtil.isStringEmpty(headImage) && !("jpg".equals(image) || "jpeg".equals(image) || "png".equals(image));

        if(flag){
            //判断链接的格式否正确
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"链接格式错误！"));
        }

        if(IsEmptyUtil.isStringEmpty(content.trim())){
            //判断内容是否为空
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"内容不能为空！"));
        }
        if(content.trim().length()>900){
            //判断内容是否过长
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"内容过长！"));
        }
        return null;
    }

    public static String isState(String state, String reply, String attachment, String username, int be, DailyDiscussService dailyDiscussService, ArchivesDiscussService archivesDiscussService, long dailyId, long archivesId){
        //判断属于哪一级评论
        switch (Integer.parseInt(state)){
            case 1:
                //判断回复对象是否为空
                if(!IsEmptyUtil.isStringEmpty(reply)){
                    return InfoUtil.error("出现异常请重试！");
                }

                if(IsEmptyUtil.isNumber(attachment)){
                    return InfoUtil.error("依赖关系异常！");
                }
                break;
            case 2:
                //判断回复对象是否为空
                if(IsEmptyUtil.isStringEmpty(reply)){
                    return InfoUtil.error("出现异常请重试！");
                }
                //判断是否和回复者名称一致
                if(reply.equals(username)){
                    return InfoUtil.error("用户名需更换！");
                }
                // be == 0 表示 日记
                // be == 1 表示 文章
                if(be == 0 && archivesId == 0){
                    //判断是否存在回复者
                    if(!dailyDiscussService.queryDiscussByDailyIdAndReply(String.valueOf(dailyId),reply)){
                        return InfoUtil.error("出现异常请重试！");
                    }
                }else if(be == 1 && dailyId == 0){
                    //判断是否存在回复者
                    if(archivesDiscussService.queryDiscussByArchivesIdAndReply(String.valueOf(archivesId),reply)==0){
                        return InfoUtil.error("出现异常请重试！");
                    }
                }else{
                    return InfoUtil.error("出现异常请重试！");
                }

                if(!IsEmptyUtil.isNumber(attachment)){
                    return InfoUtil.error("依赖关系异常！");
                }
                break;
            default:
                return InfoUtil.error("出现异常请重试！");
        }
        return null;
    }
    public static String success(String message){
        return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ONE,message));
    }

    public static String success(String message, Object list){
        return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ONE,message, JSONArray.toJSONString(list)));
    }

    public static String success(String message, Object list,int pageNumber){
        return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ONE,message, JSONArray.toJSONString(list),pageNumber));
    }

    public static String error(String message){
        return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,message));
    }
}
