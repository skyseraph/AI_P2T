package com.skyseraph.ai_p2t.ui.beam;

/**
 * Created by SkySeraph on 2018/1/2.
 */

public class ColorName {

    private String name;
    private Integer color;
    private String des;

    public ColorName(String name, Integer color, String des) {
        this.name = name;
        this.color = color;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
