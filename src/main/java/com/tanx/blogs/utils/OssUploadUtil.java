package com.tanx.blogs.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder; 
import com.aliyun.oss.model.*;
import com.tanx.blogs.pojo.model.Archives;
import com.tanx.blogs.pojo.model.User;
import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.user.UserService;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Properties;
import javax.servlet.http.HttpSession;

@SuppressWarnings("all")
public class OssUploadUtil {
    private static String ENDPOINT;
    private static String ACCESSKEYID;
    private static String ACCESSKEYSECRET;
    private static String BUCKETNAME;
    private static String ObjectName;

    static {
        try {
            InputStream inputStream = new ClassPathResource("oss.properties").getInputStream();
            if(inputStream == null){
                throw new RuntimeException("classpath下的oss.properties文件不存在！");
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            ENDPOINT = properties.getProperty("ENDPOINT");
            ACCESSKEYID = properties.getProperty("ACCESSKEYID");
            ACCESSKEYSECRET = properties.getProperty("ACCESSKEYSECRET");
            BUCKETNAME = properties.getProperty("BUCKETNAME");
            ObjectName = properties.getProperty("ObjectName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String upload(File file, String img, String address) {
        return upload(file, img, address, null, null);
    }

    public static String upload(File file, String img, String address, HttpSession session, UserService userService) {
        String info, data;
        User user = null;
        if (session != null) {
            user = (User)session.getAttribute("user");
            data = ObjectName + address + user.getUsername() + "/" + img;
        } else {
            data = ObjectName + address + img;
        }
        OSS ossClient = (new OSSClientBuilder()).build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        if (!ossClient.doesObjectExist(BUCKETNAME, data)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType("image/" + img.substring(img.lastIndexOf(".") + 1));
            metadata.setHeader("x-oss-storage-class", StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            PutObjectRequest request = new PutObjectRequest(BUCKETNAME, data, file, metadata);
            ossClient.putObject(request);
            String url = "https://api.oss.tanxiangblog.com/" + data;
            file.delete();
            info = InfoUtil.success("上传成功！", url);
            if (user != null && user.getHeadImg() != null && user.getHeadImg().split("https://api.oss.tanxiangblog.com/")[1].contains(user.getUsername())) {
                String index = user.getHeadImg().split("https://api.oss.tanxiangblog.com/")[1];
                deleteOssFile(index);
                userService.updateImage(url, user.getUserId());
                session.invalidate();
            } else if (user != null && user.getHeadImg() != null) {
                userService.updateImage(url, user.getUserId());
                session.invalidate();
            }
        } else {
            info = InfoUtil.error("文件已存在！");
        }
        ossClient.shutdown();
        return info;
    }

    public static String uploadText(HttpSession session, File file, String address, String txt, ArchivesService archivesService,Archives archives){
        String info, data;
        if(session==null){
            return InfoUtil.error("上传异常！");
        }
        User user = (User) session.getAttribute("user");
        if(user==null){
            return InfoUtil.error("用户未登录！");
        }
        if(user.getRank()!=true || !"Tree".equals(user.getUsername())){
            return InfoUtil.error("您不是管理员！");
        }
        data = "text/"+address+txt;
        OSS ossClient = (new OSSClientBuilder()).build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        if (!ossClient.doesObjectExist(BUCKETNAME, data)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType("application/octet-stream");
            metadata.setHeader("x-oss-storage-class", StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            PutObjectRequest request = new PutObjectRequest(BUCKETNAME, data, file, metadata);
            ossClient.putObject(request);
            file.delete();
            info = InfoUtil.success("上传成功！");
        }else{
            info = InfoUtil.error("文件已存在！");
        }
        ossClient.shutdown();
        return info;
    }

    public static String downloadOssText(String address,String path) {
        String replace = address.replace("https://api.oss.tanxiangblog.com/", "");
        String substring = path+address.substring(address.lastIndexOf("/"));
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖d，不存在则新建。
        ossClient.getObject(new GetObjectRequest(BUCKETNAME, replace), new File(substring));
        ossClient.shutdown();
        return substring;
    }

    public static void deleteOssFile(String index){
        OSS ossClient = (new OSSClientBuilder()).build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        ossClient.deleteObject(BUCKETNAME, index);
    }

    public static void uploadTime(String address){
        File file = new File(address);
        File[] files = file.listFiles();
        OSS ossClient = (new OSSClientBuilder()).build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
        StringBuilder count = new StringBuilder();
        for(int i=0;i< files.length;++i){
            count.delete(0,count.length());
            if((i+1) < 10){
                count.append("000"+(i+1));
            }else if((i+1)>=10 && (i+1)<100) {
                count.append("00" + (i + 1));
            }else if((i+1)>=100 && (i+1)<1000){
                count.append("0"+(i+1));
            }else{
                count.append((i+1));
            }
            String data = "rotation"+File.separator+"images"+File.separator+"img"+count+".jpg";
            if (!ossClient.doesObjectExist(BUCKETNAME, data)) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.length());
                metadata.setContentType("image/jpg");
                metadata.setHeader("x-oss-storage-class", StorageClass.Standard.toString());
                metadata.setObjectAcl(CannedAccessControlList.PublicRead);
                PutObjectRequest request = new PutObjectRequest(BUCKETNAME, data, files[i], metadata);
                ossClient.putObject(request);
                System.out.println("上传成功！"+count);
            }
        }

        ossClient.shutdown();
    }
}
