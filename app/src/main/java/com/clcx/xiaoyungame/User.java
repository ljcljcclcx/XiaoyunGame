package com.clcx.xiaoyungame;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ljc123 on 2018/12/12.
 */

public class User {
    private int resid;
    private ImageView iv;
    private TextView tv;
    private int position;
    private int aX, aY;
    private String name;
    private boolean nullposition;

    public User() {
    }

    public User copy(User user) {
        this.resid = user.getResid();
        this.name = user.getName();
        this.iv = user.getIv();
        this.tv = user.getTv();
        this.position = user.getPosition();
        this.aX = user.getaX();
        this.aY = user.getaY();
        nullposition = false;
        return this;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    /**
     * 把某个user的属性传递到空user上面
     *
     * @param user
     */
    public void resetUser(User user) {
        setName(user.getName());
        setIv(user.getIv());
        setTv(user.getTv());
        setResid(user.getResid());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNullposition(boolean nullposition) {
        this.nullposition = nullposition;
    }

    public User(String name, int resid, TextView tv, ImageView iv, int position, int aX, int aY) {
        this.resid = resid;
        this.name = name;
        this.iv = iv;
        this.tv = tv;
        this.position = position;
        this.aX = aX;
        this.aY = aY;
        nullposition = false;
    }

    public boolean isNullposition() {
        return nullposition;
    }

    public int getaX() {
        return aX;
    }

    public void setaX(int aX) {
        this.aX = aX;
    }

    public int getaY() {
        return aY;
    }

    public void setaY(int aY) {
        this.aY = aY;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
