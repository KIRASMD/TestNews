package com.example.testnews.model;

/**
 * Created by Administrator on 2016/7/28.
 */

public class TestNewsBean {
    private String newsIconUrl;
    private String newsTitle;
    private String newsAuthor;

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
