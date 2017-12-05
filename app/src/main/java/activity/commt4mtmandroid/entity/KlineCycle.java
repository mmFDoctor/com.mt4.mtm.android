package activity.commt4mtmandroid.entity;

import java.io.Serializable;

/**
 * Created by fangzhu
 * 用来存储k线周期 文字信息
 */
public class KlineCycle implements Serializable {

    private String name;
    private String code;
    private int time;
    private int normal;

    public KlineCycle() {
    }

    public KlineCycle(String name, String code, int time) {
        this.name = name;
        this.code = code;
        this.time = time;
    }

    public KlineCycle(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public KlineCycle(String name, int normal, int time) {
        this.name = name;
        this.normal = normal;
        this.time = time;
    }

    public KlineCycle(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }
}
