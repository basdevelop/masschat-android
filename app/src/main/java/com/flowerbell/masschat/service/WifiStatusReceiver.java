package com.flowerbell.masschat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.Settings;

import com.flowerbell.masschat.domain.UserSession;
import com.flowerbell.masschat.service.connection.P2pConnectionManager;
import com.flowerbell.masschat.ui.dialog.MyDialogHint;
import com.orhanobut.logger.Logger;

public class WifiStatusReceiver extends BroadcastReceiver {
    public WifiStatusReceiver(){
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();

        //TIPS::判断当前设备是否支持wifi p2p协议.
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Intent service_intent = new Intent(context, AdvertiseService.class);
                service_intent.setAction(AdvertiseService.ADD_WIFI_DIRECT_NET_SERVICE_DISCOVERY);
                context.startService(service_intent);
            }else{
                MyDialogHint tips_dialog = new MyDialogHint(context);
                tips_dialog.show();
                MyDialogHint.ClickCallBack ok_call = new MyDialogHint.ClickCallBack(){
                    @Override
                    public void execute() {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                };
                tips_dialog.buildDialog("服务启动失败，请到系统设置中开启wifi直连功能", ok_call, null);
            }

            Logger.d("P2P state changed:" + state);

        }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            WifiP2pDeviceList current_list = intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);

            Logger.d(">>>>>>peers changes 最新的列表:" + current_list.toString());

            PeerDeviceManager.getInstance().freshPeerList(current_list);

        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            WifiP2pInfo p2pInfo         = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
            NetworkInfo networkInfo     = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            WifiP2pGroup grpInfo        = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);

            Logger.i("connection state changed====(p2pInfo)"+p2pInfo.toString()
                    +"---(networkInfo:)"+networkInfo+"(---grpInfo:)"+grpInfo);
            if (networkInfo.isConnected()) {
                if (p2pInfo.groupFormed && p2pInfo.isGroupOwner) {
                    P2pConnectionManager.getInstance().init_server_and_notify_other_clients(p2pInfo, grpInfo);
                } else if (p2pInfo.groupFormed) {
                    P2pConnectionManager.getInstance().connect_to_server_and_register_self(p2pInfo, grpInfo);
                }
            }else{
                //TODO::clean the connection status data;
                Logger.i("todo::断开连接的后续处理，此时socket已超时");
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            WifiP2pDevice this_device = (WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Logger.d(">>>"+this_device.deviceAddress+"<<<this device information changed:"+this_device.toString());
            UserSession.getInstance().setLocalDevice(this_device);

        }else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)){

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED){
            }else if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED){
            }

            Logger.d("P2P discovery state changed:" + state);
        }
    }
}
