package com.tanx.blogs.pojo.dto;

/**
 * @version 1.0
 * @Author tanxiang
 * @Date 2020/9/2 下午2:53
 */
public class TakeImage {
    private String code;
    private String imageUrl;
    private String imageWidth;
    private String imageHeight;

    public TakeImage() {

    }

    public TakeImage(String code, String imageUrl, String imageWidth, String imageHeight) {
        this.code = code;
        this.imageUrl = imageUrl;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public String toString() {
        return "TakeImage{" +
                "code='" + code + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageWidth='" + imageWidth + '\'' +
                ", imageHeight='" + imageHeight + '\'' +
                '}';
    }
}
