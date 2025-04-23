package com.beiming.domain;

/**
 * ImgDto
 * {"i":"37aa5dc36d88444786b6b543845963ba",
 * "w":4182,
 * "h":2304,
 * "t":2}
 */
public class ImgDto {
    private String i;
    private Integer w;
    private Integer h;
    private Integer t;

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "ImgDto{" +
                "i='" + i + '\'' +
                ", w=" + w +
                ", h=" + h +
                ", t=" + t +
                '}';
    }
}

