package com.flowerbell.masschat.service;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;

import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.domain.UserSession;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wsli on 2017/7/25.
 */

public final class PeerDeviceManager {
    static PeerDeviceManager _instance;

    final static HashMap<String, WifiP2pDevice>   _peerDeviceMap      = new HashMap<>(25500);
    final static Map<String, JSONObject>          _textRecordCache    = new HashMap<>(25500);
    final static Map<String, BusinessNameCard>    _nameCardToShow     = new HashMap<>(25500);
    final static Map<String, ConnectionStatusChanedListener> _connectionStatusListener = new HashMap<>(25500);
    static int                              _activeSequence           = 0;
    final static Set<DeviceStatusChangedListener> _deviceStatusListener = new HashSet<>();
    static long                    _freshedPeerDeviceLastTime         = 0;



    public static  synchronized  PeerDeviceManager getInstance(){
        if (null == _instance){
            _instance = new PeerDeviceManager();
        }
        return _instance;
    }

    private  PeerDeviceManager(){
        _activeSequence = 0;
    }

    public boolean isActive(BusinessNameCard name_card) {
        return name_card.getFreshSeq() >= _activeSequence - 2;//TIPS::有可能会存在重复刷新
    }

    public boolean isConnected(BusinessNameCard name_card) {
        return name_card.isConnected() && name_card.getFreshSeq() >= _activeSequence - 2;
    }
    public interface DeviceStatusChangedListener{
        public void device_did_changed();
    }

    public interface ConnectionStatusChanedListener{
        public void connection_status_changed(BusinessNameCard card, boolean online);
    }

    public void regist_device_status_changed_listener(DeviceStatusChangedListener l){
        _deviceStatusListener.add(l);
    }
    public void unregist_device_status_changed_listener(DeviceStatusChangedListener l){
        _deviceStatusListener.remove(l);
    }

    public void freshPeerList(WifiP2pDeviceList dl) {

        if (AdvertiseService.SCAN_SERVICE_INTERVALS > System.currentTimeMillis() - _freshedPeerDeviceLastTime){
            return;
        }

        _freshedPeerDeviceLastTime = System.currentTimeMillis();

        try {

            _peerDeviceMap.clear();

            if (dl.getDeviceList().size() == 0) {
                return;
            }

            ArrayList<WifiP2pDevice> list = new ArrayList<WifiP2pDevice>(dl.getDeviceList());
            _activeSequence++;

            for (WifiP2pDevice device : list) {
                String device_address = device.deviceAddress;

                if (false == isSmartPhone(device.primaryDeviceType)) {
                    continue;
                }

                _peerDeviceMap.put(device_address, device);

                BusinessNameCard name_card = _nameCardToShow.get(device_address);
                if (null == name_card) {
                    name_card = new BusinessNameCard();
                    name_card.setMacAddress(device_address);
                    _nameCardToShow.put(device_address, name_card);
                }

                name_card.setFreshSeq(_activeSequence);
                if (!name_card.isConnected()) {
                    JSONObject user_obj = _textRecordCache.get(device_address);
                    if (null != user_obj) {
                        name_card.setName(user_obj.getString("name"));
                        name_card.setTittle(user_obj.getString("tittle"));
                        name_card.setCompany(user_obj.getString("company"));
                        name_card.setCompanyLink(user_obj.getString("companyLink"));
                        name_card.setServiceOk(true);

                    } else {

                        name_card.setName(device.deviceName);
                        name_card.setTittle("");
                        name_card.setCompany(device.deviceAddress);
                        name_card.setCompanyLink("");
                        name_card.setServiceOk(false);
                    }
                }
            }

            Logger.i(_textRecordCache.toString());

            this.notify_device_status_changed();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void notify_device_status_changed() {
        for (DeviceStatusChangedListener listener : _deviceStatusListener){
            listener.device_did_changed();
        }
    }

    public void addToRecordCache(WifiP2pDevice wifiP2pDevice, String user_json_str) {

        if (null == user_json_str){
            return;
        }

        try {
            JSONObject user_obj = new JSONObject(user_json_str);

            _textRecordCache.put(wifiP2pDevice.deviceAddress, user_obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<BusinessNameCard> getCurrentDeviceList(){
        return new ArrayList<BusinessNameCard>(_nameCardToShow.values());
    }

    public void registerConnectionStatusListener(ConnectionStatusChanedListener l, String key){
        _connectionStatusListener.put(key, l);
    }

    public void startConnectPeer(Context context, String macAdress) {
        WifiP2pDevice device = _peerDeviceMap.get(macAdress);
        if (null == device){
            //TODO::设备又不存在了
            return;
        }

        Logger.i(">>>>>>>>Try to make wifi p2p connection :"+device);

        Intent service_intent = new Intent(context, AdvertiseService.class);
        service_intent.setAction(AdvertiseService.START_CONNECT_TO_PEERS_BY_MACADDRESS);
        service_intent.putExtra("device", macAdress);
        context.startService(service_intent);
    }

    public BusinessNameCard getPeerNameCard(String name_card_address) {
        return _nameCardToShow.get(name_card_address);
    }

    public WifiP2pDevice getDevice(String device_address) {
        return _peerDeviceMap.get(device_address);
    }


    public static final int PRIMARY_DEVICE_CATEGORY_COMPUTER    = 1;
    public static final int PRIMARY_DEVICE_CATEGORY_INPUTDEVICE = 2;
    public static final int PRIMARY_DEVICE_CATEGORY_PINTER      = 3;
    public static final int PRIMARY_DEVICE_CATEGORY_CAMERA      = 4;
    public static final int PRIMARY_DEVICE_CATEGORY_STORAGE     = 5;
    public static final int PRIMARY_DEVICE_CATEGORY_NETWORKINFRASTRUCTURE    = 6;
    public static final int PRIMARY_DEVICE_CATEGORY_DISPLAYS    = 7;
    public static final int PRIMARY_DEVICE_CATEGORY_MULTIMEDIADEVICE    = 8;
    public static final int PRIMARY_DEVICE_CATEGORY_GAMEINGDEVICE    = 9;
    public static final int PRIMARY_DEVICE_CATEGORY_TELEPHONE   = 10;
    public static final int PRIMARY_DEVICE_CATEGORY_AUDIODEVICE = 11;

    public static final int PRIMARY_DEVICE_PHONE_SUBCATEGORY_WINDOWSMOBILE      = 1;
    public static final int PRIMARY_DEVICE_PHONE_SUBCATEGORY_PHONESINGLEMODE    = 2;
    public static final int PRIMARY_DEVICE_PHONE_SUBCATEGORY_PHONEDUALMODE      = 3;
    public static final int PRIMARY_DEVICE_PHONE_SUBCATEGORY_SMARTPHONE_SINGLEMODE      = 4;
    public static final int PRIMARY_DEVICE_PHONE_SUBCATEGORY_SMARTPHONE_DUALMODE = 5;


    boolean isSmartPhone(String deviceType){

            String[] device_type_params = deviceType.split("-");
            int category = Integer.valueOf(device_type_params[0]);
            int sub_category = Integer.valueOf(device_type_params[2]);

            if (category == PRIMARY_DEVICE_CATEGORY_TELEPHONE
                && (sub_category == PRIMARY_DEVICE_PHONE_SUBCATEGORY_SMARTPHONE_SINGLEMODE
                || sub_category == PRIMARY_DEVICE_PHONE_SUBCATEGORY_SMARTPHONE_DUALMODE)){
                return true;
            }
            return false;
    }

    public void onLineConnectedUser(BusinessNameCard name_card) {
        BusinessNameCard old_card = _nameCardToShow.get(name_card.getMacAddress());
        if (null == old_card){

            if (name_card.getMacAddress().equals(
                    UserSession.getInstance().getLocalMacAddress()
            )){
                UserSession.getInstance().setLocalIpAddress(name_card.getIpAddress());
            }

            return;
        }

        name_card.setFreshSeq(old_card.getFreshSeq());
        name_card.setServiceOk(true);

        _nameCardToShow.put(name_card.getMacAddress(), name_card);

        this.notify_device_status_changed();

        ConnectionStatusChanedListener call_back = _connectionStatusListener.get(name_card.getMacAddress());
        if (null != call_back){
            call_back.connection_status_changed(name_card, true);
        }
    }
}
