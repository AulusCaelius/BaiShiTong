package model;

public class Movie {
    private String name;
    private String title;
    private String url;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Movie() {
        super();
    }

    public Movie(String name, String title, String url) {
        super();
        this.name = name;
        this.title = title;
        this.url = url;
    }
    @Override
    public String toString(){
        String content = "";
        content += "电影名称：" + name + "\r\n";
        content += "标题：" + title + "\r\n";
        content += "下载地址：" + url + "\r\n";

        return content;
    }
}
