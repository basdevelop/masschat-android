package com.flowerbell.masschat.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by 涛 on 2016/6/30.
 */
public class LoginUser implements Serializable {

        long rowId;
        private String uid;
        private String phone;
        String email;
        private String name;
        String  wechatName;
        String  wechatOpenId;//微信唯一标示。
        private int age;
        private String sex;
        private String password;
        private String enterTime;
        private String createTime;
        private String updateTime;
        String  tittle;
        String  company;
        String  industry;//所属行业
        String  companyAddress;
        String  companyLink;//公司首页

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "rowId=" + rowId +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", wechatName='" + wechatName + '\'' +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", password='" + password + '\'' +
                ", enterTime='" + enterTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", tittle='" + tittle + '\'' +
                ", company='" + company + '\'' +
                ", industry='" + industry + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyLink='" + companyLink + '\'' +
                '}';
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEnterTime() {
            return enterTime;
        }

        public void setEnterTime(String enterTime) {
            this.enterTime = enterTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
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
}
