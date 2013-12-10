package com.hoyotech.ctgames.db.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Tian
 * Date: 13-12-10
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class App {

    public static final String TABLE_NAME = "app";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_NAME = "version_name";
    public static final String URL = "url";
    public static final String SIZE = "size";
    public static final String IMAGE_PATH = "image_path";
    public static final String SUMMARY = "summary";
    public static final String PRIZE_COUNT = "prize_count";
    public static final String LUCKPEAN_COUNT = "luckypean_count";
    public static final String STATE = "state";

    private int id;
    private String name;
    private int version_code;
    private String version_name;
    private String url;
    private int size;
    private String image_path;
    private String summary;
    private int prize_count;
    private int luckypean_count;
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPrize_count() {
        return prize_count;
    }

    public void setPrize_count(int prize_count) {
        this.prize_count = prize_count;
    }

    public int getLuckypean_count() {
        return luckypean_count;
    }

    public void setLuckypean_count(int luckypean_count) {
        this.luckypean_count = luckypean_count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version_code=" + version_code +
                ", version_name='" + version_name + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", image_path='" + image_path + '\'' +
                ", summary='" + summary + '\'' +
                ", prize_count=" + prize_count +
                ", luckypean_count=" + luckypean_count +
                ", state=" + state +
                '}';
    }
}
