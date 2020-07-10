package com.flowerbell.masschat.domain;

import android.net.wifi.p2p.WifiP2pDevice;

import com.flowerbell.masschat.service.DatabaseManager;

/**
 * Created by wsli on 2017/7/20.
 */

public class UserSession {
    static UserSession      _instance       = null;
    static WifiP2pDevice    _thisP2pDevice  = null;
    static String           _localIpAddress = null;

    public static synchronized  UserSession getInstance(){
        if (null == _instance){
            _instance = new UserSession();
        }
        return _instance;
    }

    LoginUser   _currentSignedInUser;
    private UserSession(){
        _currentSignedInUser =  DatabaseManager.getInstance().loadSignedInUser();
    }

    public String loadBasicProfile() {
        return _currentSignedInUser.getBasicNameCardForBroadCast();
    }

    public void setLocalDevice(WifiP2pDevice this_device) {
        _thisP2pDevice = this_device;
    }

    public WifiP2pDevice getLocalDevice(){
        return _thisP2pDevice;
    }

    public String getLocalMacAddress(){
        return _thisP2pDevice.deviceAddress;
    }

    public void setLocalIpAddress(String local_ip) {
        _localIpAddress = local_ip;
    }
    public String getLocalIpAddress(){
        return _localIpAddress;
    }

    public LoginUser getCurrentUser() {
        return _currentSignedInUser;
    }
}
