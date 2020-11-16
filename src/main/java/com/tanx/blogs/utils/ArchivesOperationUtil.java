package com.tanx.blogs.utils;

import com.alibaba.druid.support.json.JSONUtils;
import com.tanx.blogs.pojo.model.Archives;
import com.tanx.blogs.pojo.model.ArchivesAndTag;
import com.tanx.blogs.pojo.model.Type;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.archivesandtag.ArchivesAndTagService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@SuppressWarnings("all")
public class ArchivesOperationUtil {
    public static String createArchivesFile(String title, String content, String subtitle, String url, Type type, List<Long> tag, ArchivesService archivesService, ArchivesAndTagService archivesAndTagService, HttpServletRequest request) throws IOException {
        String res;
        if("Linux".equals(System.getProperty(""))){
            File file = new File(File.separator+"blog"+File.separator+"temp"+File.separator);
            if (!file.exists()) {
                file.mkdirs();
            }
            res = file.toString();
        }else{
            File file = new File("C:"+File.separator+"blog"+File.separator+"temp");
            res =   res = file.toString();
        }
        File file = new File(res);

        if (!file.exists()) {
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO, "临时存储点创建失败！"));
        }

        File index = new File(file, title + ".txt");

        if (index.exists()) {
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO, "此文件已存在！"));
        }else{
            boolean write = index.setWritable(true,false);
            boolean newFile = index.createNewFile();
            if(!newFile){
                return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO, "临时文件创建失败！"));
            }
        }
        Writer writer = new FileWriter(index);
        writer.write(content);
        writer.close();
        Archives archives = new Archives(title, subtitle, type.getTypeId(), "https://api.oss.tanxiangblog.com/text/files/" + title + ".txt", new Date());
        if (archivesService.addArchives(archives) == 0) {
            index.delete();
            return InfoUtil.error("数据插入失败!");
        }
        Archives archivesData = archivesService.selectArchivesTitle(title);
        ArchivesAndTag archivesAndTag = new ArchivesAndTag(archivesData.getArchivesId(), tag);
        if (!archivesAndTagService.addArchivesAndTag(archivesAndTag)) {
            List<Archives> list = archivesService.queryArchivesByTitleAndTypeId(archives.getTitle(), String.valueOf(archives.getTypeId()));
            if (list.size() == 1) {
                archivesService.deleteArchives(String.valueOf(list.get(0).getArchivesId()));
            } else if (list.size() != 1 && list.size() != 0) {
                return InfoUtil.error("有文章名称相同的文章!");
            }
            return InfoUtil.error("写入失败!");
        }
        MapCacheUtil.remove("Archives");
        MapCacheUtil.remove("FootMarkData");
        MapCacheUtil.remove("ArchivesCount");
        MapCacheUtil.remove("HotsArchives");
        MapCacheUtil.remove("NewsArchives");
        VariableUtils.LIST.clear();
        return OssUploadUtil.uploadText(request.getSession(), index, "files/", index.getName(), archivesService, archives);
    }


    public static String takeArchivesFile(String address, String title) throws IOException {
        File file = null;
        switch (System.getProperty("os.name")) {
            case "Linux":
                file = new File(VariableUtils.LINUXPATH);
                if (!file.exists()) {
                    DataOutputStream os = new DataOutputStream(Runtime.getRuntime().exec("su root").getOutputStream());
                    os.writeBytes(ReaderPropertiesOrYamlUtil.properties("linux.properties", "linux.root.password")+"\n");
                    os.writeBytes("mkdir -p"+"\t"+VariableUtils.LINUXPATH+"\n");
                    os.writeBytes("chmod -R o=rwx"+"\t"+"/"+VariableUtils.LINUXPATH.split("/")[1]+"\n");
                    os.flush();
                    os.close();
                }
                break;
            case "Windows 10":
                file = new File(VariableUtils.WINDOWSPATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                break;
            case "Windows 7":
                file = new File(VariableUtils.WINDOWSPATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                break;
            default:
                return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO, "操作系统赞不支持！"));
        }

        File index = new File(file,address.substring(address.lastIndexOf("/")+1));
        if(!index.exists()){
            return takeContent(OssUploadUtil.downloadOssText(address,file.toString()));
        }
        return takeContent(index.toString());
    }

    public static String takeContent(String address){
        File file = new File(address);
        if(!file.exists()){
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"此文件以不存在！"));
        }
        StringBuilder sb = new StringBuilder();
        String data = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while(true){
                if((data = reader.readLine())!=null){
                    sb.append(data);
                }else {
                    break;
                }
            }
            int i = 0/1;
            return sb.toString();
        } catch (IOException e) {
            return JSONUtils.toJSONString(InfoUtil.info(VariableUtils.ZERO,"文件读取错误！"));
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String MarkDownChangeHtml(String markdown){
        StringBuilder buffer = new StringBuilder();
        StringBuilder builder = new StringBuilder(markdown);
        while(true){
            int count = builder.toString().indexOf("<pre><code>");
            int lastcount = builder.toString().indexOf("</code></pre>");
            if(count==-1){
                break;
            }
            buffer.append(builder.toString().substring(count,lastcount+13));
            String str = buffer.toString().replaceAll("\n","<br/>").replaceAll("<pre><code>","<pre>").replaceAll("</code></pre>","</pre>");
            builder.replace(count,lastcount+13,str);
            buffer.delete(0,buffer.length());
        }
        return builder.toString().replaceAll("<pre>", "<pre style='width:580px;background:black;margin:10px 0;border-radius:5px;padding:10px;white-space: normal;color: white;word-break: break-all;'>")
                .replaceAll("<code>","<span style=\"display:inline-block;background:#fdc8c8;border-radius:3px;padding:1px 1px;color:red;font-weight:700;\">")
                .replaceAll("</code>","</span>").replaceAll("\n","").replaceAll("<ul>","<ul style='padding-left:25px'>")
                .replaceAll("<blockquote><p>","<blockquote style=\"margin: 20px 0px;\"><p style=\"border-left: 5px solid #38bd65;height: 40px;line-height: 40px;padding-left: 5px;background: #e4e2e2;\">")
                .replaceAll("\"","'");
    }
}