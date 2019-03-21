package cn.edu.sicau.domain;

/**
 * 用于存储用户收藏新闻资讯
 */
public class Collection {
    private int id;
    //用户账号
    private String account;
    //新闻url
    private String url;
    //时间
    private String newsTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}
