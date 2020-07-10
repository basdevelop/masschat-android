package com.flowerbell.masschat.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by wsli on 2017/7/20.
 */

public class BusinessNameCard implements Serializable {
    byte[]  avatarData;
    String  name;
    String  phone;
    String  email;
    String  sex;
    int     age;
    String  tittle;
    String  company;
    String  industry;//所属行业
    String  companyAddress;
    String  companyLink;//公司首页
    String  wechatName;//个人微信号
    String  wechatOpenId;//微信唯一标示。
    String  ipAddress;
    String  macAddress;
    String  deviceName;


//    temporary status data.
    boolean isServiceOk;
    boolean isConnected;
    int     freshSeq;

    @Override
    public String toString() {
        return "BusinessNameCard{" +
                "avatarData=" + Arrays.toString(avatarData) +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", tittle='" + tittle + '\'' +
                ", company='" + company + '\'' +
                ", industry='" + industry + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyLink='" + companyLink + '\'' +
                ", wechatName='" + wechatName + '\'' +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                ", isServiceOk=" + isServiceOk +
                ", isConnected=" + isConnected +
                ", freshSeq=" + freshSeq +
                ", ipAddress='" + ipAddress + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getFreshSeq() {
        return freshSeq;
    }

    public void setFreshSeq(int freshSeq) {
        this.freshSeq = freshSeq;
    }

    public byte[] getAvatarData() {
        return avatarData;
    }

    public void setAvatarData(byte[] avatarData) {
        this.avatarData = avatarData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyLink() {
        return companyLink;
    }

    public void setCompanyLink(String companyLink) {
        this.companyLink = companyLink;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public boolean isServiceOk() {
        return isServiceOk;
    }

    public void setServiceOk(boolean serviceOk) {
        isServiceOk = serviceOk;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getBasicNameCardForBroadCast() {
        JSONObject object = new JSONObject();

        try {
            object.put("name", name);
            object.put("company", company);
            object.put("tittle", tittle);
            object.put("industry", industry);
            object.put("companyLink", companyLink);

        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
        return object.toString();
    }

    public String getNameCardForRegister(){
        JSONObject object = new JSONObject();

        try {
            object.put("avatarData",avatarData);
            object.put("name", name);
            object.put("phone",phone);
            object.put("email",email);
            object.put("sex",sex);
            object.put("age",age);
            object.put("tittle", tittle);
            object.put("company", company);
            object.put("industry", industry);
            object.put("companyAddress",companyAddress);
            object.put("companyLink", companyLink);
            object.put("wechatName", wechatName);
            object.put("wechatOpenId",wechatOpenId);
            object.put("ipAddress",ipAddress);
            object.put("macAddress",macAddress);
            object.put("deviceName", deviceName);

        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

        return object.toString();
    }

}
