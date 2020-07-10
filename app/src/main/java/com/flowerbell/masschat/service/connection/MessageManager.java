package com.flowerbell.masschat.service.connection;

import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.service.PeerDeviceManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by wsli on 2017/8/1.
 */

public class MessageManager {

    static MessageManager _instance = null;
    public synchronized  static  MessageManager getInstance(){
        if (null == _instance){
            _instance = new MessageManager();
        }

        return _instance;
    }

    MessageDispatcher _centralDispather;
    private  MessageManager(){
        _centralDispather = new MessageDispatcher();
        _centralDispather.start();
    }

    static BlockingQueue<MessageBean>   _messageQueue = new ArrayBlockingQueue<MessageBean>(10000);

    public void cacheMessage(String msg) {
        Logger.i(">>>Cached message:" + msg);
        try {
            JSONObject msg_obj = new JSONObject(msg);

            MessageBean msg_bean = new MessageBean();
            msg_bean.setFrom(msg_obj.getString("from"));
            msg_bean.setType(msg_obj.getInt("type"));
            msg_bean.setTo(msg_obj.getString("to"));
            msg_bean.setData(msg_obj.getString("data"));

            _messageQueue.put(msg_bean);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void processUserOnlineMsg(JSONObject msg_obj) throws JSONException{

        BusinessNameCard name_card = new BusinessNameCard();

//        name_card.setAvatarData((byte[]) msg_obj.get("avatarData"));
        name_card.setName(msg_obj.has("name")?msg_obj.getString("name"):"未设置");
        name_card.setPhone(msg_obj.has("phone")? msg_obj.getString("phone"):"保密");
        name_card.setEmail(msg_obj.has("email") ? msg_obj.getString("email"):"保密");
        name_card.setSex(msg_obj.has("sex")?msg_obj.getString("sex"):"女");
        name_card.setTittle(msg_obj.has("tittle") ?msg_obj.getString("tittle"):"保密");
        name_card.setAge(msg_obj.has("age")?msg_obj.getInt("age"):18);
        name_card.setIndustry(msg_obj.has("industry")? msg_obj.getString("industry"):"未设置");
        name_card.setCompanyAddress(msg_obj.has("companyAddress") ?msg_obj.getString("companyAddress"):"未设置");
        name_card.setCompany(msg_obj.has("company")? msg_obj.getString("company"):"未设置");
        name_card.setCompanyLink(msg_obj.has("companyLink")? msg_obj.getString("companyLink"):"未设置");
        name_card.setWechatName(msg_obj.has("wechatName")? msg_obj.getString("wechatName"):"未关联");
        name_card.setWechatOpenId(msg_obj.has("wechatOpenId") ? msg_obj.getString("wechatOpenId"):"0");
        name_card.setIpAddress(msg_obj.has("ipAddress")? msg_obj.getString("ipAddress"):"");
        name_card.setMacAddress(msg_obj.has("macAddress")? msg_obj.getString("macAddress"):"");
        name_card.setDeviceName(msg_obj.has("deviceName")? msg_obj.getString("deviceName"):"");
        name_card.setConnected(true);

        PeerDeviceManager.getInstance().onLineConnectedUser(name_card);
    }

    public class MessageDispatcher extends Thread{

        @Override
        public void run(){
            try {
                MessageBean msg_bean = null;
                while (null != (msg_bean = _messageQueue.take())){
                    JSONObject msg_obj = new JSONObject(msg_bean.getData());

                    switch (msg_bean.getType()){
                        case MessageBean.MSG_TYPE_CTL_REGISTER:{

                            processUserOnlineMsg(msg_obj);

                            P2pConnectionManager.getInstance().broad_msg_to_group_member(msg_obj);

                        }break;
                        case MessageBean.MSG_TYPE_CTL_NEWUSERONLINE:{
                            processUserOnlineMsg(msg_obj);
                        }break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
