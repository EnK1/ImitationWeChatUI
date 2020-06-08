package com.zhuangxueyan.imitationwechatui;

import android.graphics.Bitmap;

public class Chat {
    private String title;

    private String author_name;
    private String thumbnail_pic_s;
    private Bitmap bitmap;

    public Chat(String title, String author_name, String thumbnail_pic_s, Bitmap bitmap) {
        this.title = title;
        this.author_name = author_name;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.bitmap = bitmap;
    }

    public Chat() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "title='" + title + '\'' +
                ", author_name='" + author_name + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
