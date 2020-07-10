package com.flowerbell.masschat.service.connection;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;

import com.flowerbell.masschat.domain.UserSession;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wsli on 2017/7/31.
 */

public class P2pConnectionManager {
    private static P2pConnectionManager _instance = null;

    public static synchronized  P2pConnectionManager getInstance() {
        if (null == _instance){
            _instance = new P2pConnectionManager();
        }
        return _instance;
    }


    private ServerThread    _serverThread = null;
    private ClientThread    _clientThread = null;
    private boolean         _isGroupOwner = false;

    private P2pConnectionManager() {
    }

    public void connect_to_server_and_register_self(WifiP2pInfo p2pInfo, WifiP2pGroup groupOwner) {

            String group_owner_address = p2pInfo.groupOwnerAddress.getHostAddress();

            _isGroupOwner = false;

            WifiP2pDevice owner_device =  groupOwner.getOwner();

            Logger.i(">>>>>>>>Try to make sockect connection to owner_device :"+owner_device.toString());

            _clientThread = new ClientThread(group_owner_address);

            _clientThread.start();
    }

    public void init_server_and_notify_other_clients(WifiP2pInfo p2pInfo, WifiP2pGroup grpInfo) {

        _isGroupOwner = true;
        String group_owner_address = p2pInfo.groupOwnerAddress.getHostAddress();
        UserSession.getInstance().setLocalIpAddress(group_owner_address);

        _serverThread = new ServerThread();

        _serverThread.start();
    }

    public void broad_msg_to_group_member(JSONObject msg_obj) {
        try {
            _serverThread.broadcast_online_message(msg_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
