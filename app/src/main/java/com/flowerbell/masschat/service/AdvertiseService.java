package com.flowerbell.masschat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.IBinder;

import com.flowerbell.masschat.domain.UserSession;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class AdvertiseService extends Service implements WifiP2pManager.DnsSdTxtRecordListener,
        WifiP2pManager.DnsSdServiceResponseListener{
    public static final String ADD_WIFI_DIRECT_NET_SERVICE_DISCOVERY = "add_local_network_service_by_wifi_direct";
    public static final String START_CONNECT_TO_PEERS_BY_MACADDRESS  = "start_discovery_peers_to_find_service";
    public static final int     SCAN_SERVICE_INTERVALS   =   60 * 1000;

    public static String SERVICE_INSTANCE;
    public static final String SERVICE_REG_TYPE             = "_mashchat._tcp";
    public static final String SERVICE_PARA_KEY             = "user_basic";
    public static final int SERVER_PORT                    = 8080;


    WifiP2pManager              _theWifiManager;
    WifiP2pManager.Channel      _currentChannel;
    WifiStatusReceiver          _wifiStatusReceiver;
    IntentFilter                _wifiReceiverFilter;
    ServiceMonitorThread        _monitorDiscoverThread;

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String action = intent.getAction();
        if (null != action){
            if(ADD_WIFI_DIRECT_NET_SERVICE_DISCOVERY.equals(action)){
                clearAndAddLocalServices();
            }else if (START_CONNECT_TO_PEERS_BY_MACADDRESS.equals(action)){
                String device_address = intent.getStringExtra("device");
                startToConnectPeer(device_address);
            }
        }
        return START_STICKY;
    }


    @Override
    public void onCreate(){
        _theWifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        _currentChannel = _theWifiManager.initialize(this, getMainLooper(), null);
        _theWifiManager.setDnsSdResponseListeners(_currentChannel, this, this);

        WifiManager wifi_manager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        SERVICE_INSTANCE = wifi_manager.getConnectionInfo().getMacAddress();


        _wifiReceiverFilter = new IntentFilter();
        _wifiReceiverFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        _wifiReceiverFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        _wifiReceiverFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        _wifiReceiverFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        _wifiReceiverFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);

        _wifiStatusReceiver = new WifiStatusReceiver();
        registerReceiver(_wifiStatusReceiver, _wifiReceiverFilter);

        _monitorDiscoverThread = new ServiceMonitorThread();
        _monitorDiscoverThread.start();
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(_wifiStatusReceiver);
        super.onDestroy();
    }

    void clearAndAddLocalServices() {

        _theWifiManager.clearLocalServices(_currentChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Logger.d("---clear local service success---");
                addLocalService();
            }

            @Override
            public void onFailure(int code) {
                //TODO::show a button and let user try again.
                Logger.w("---clear local service failed:---" + code);
            }
        });
    }

    void addLocalService(){

        Map<String, String> record = new HashMap<String, String>();
        String basic_user_profile = UserSession.getInstance().loadBasicProfile();
        record.put(SERVICE_PARA_KEY, basic_user_profile);


        WifiP2pDnsSdServiceInfo service = WifiP2pDnsSdServiceInfo.newInstance(
                SERVICE_INSTANCE, SERVICE_REG_TYPE, record);

        _theWifiManager.addLocalService(_currentChannel, service, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess () {
                Logger.d("add local service success");
            }
            @Override
            public void onFailure ( int code){
                Logger.e("add local service failed:" + code);
            }
        });
    }


    public void addServiceDiscoveryRequest(){

        _theWifiManager.clearServiceRequests(_currentChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Logger.d(" clear service request success ");

                WifiP2pDnsSdServiceRequest request = WifiP2pDnsSdServiceRequest.newInstance();

                _theWifiManager.addServiceRequest(_currentChannel, request, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Logger.d(" add service request success ");
                        discoverServices();
                    }

                    @Override
                    public void onFailure(int i) {
                        Logger.e(" add service request failure:" + i);
                    }
                });
            }

            @Override
            public void onFailure(int i) {
                Logger.e(" clear service request failure:" + i);
            }
        });
    }

    void discoverServices() {

        _theWifiManager.discoverServices(_currentChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Logger.d(" discover services success ");
            }

            @Override
            public void onFailure(int i) {
                Logger.e(" discover services failure:" + i);
            }
        });
    }

    @Override
    public void onDnsSdServiceAvailable(String instanceName, String serviceType, WifiP2pDevice wifiP2pDevice) {
        Logger.d("----2----instanceName:" + instanceName+"  serviceType:" + serviceType +" device:"+wifiP2pDevice.toString());
    }

    @Override
    public void onDnsSdTxtRecordAvailable(String domainName, Map<String, String> map, WifiP2pDevice wifiP2pDevice) {
        Logger.d("----1----domainName:" + domainName+"  map:" + map.toString() +" device:"+wifiP2pDevice.toString());
        String user_json_str = map.get(AdvertiseService.SERVICE_PARA_KEY);
        PeerDeviceManager.getInstance().addToRecordCache(wifiP2pDevice, user_json_str);
    }

    class ServiceMonitorThread extends  Thread{
        @Override
        public void run(){
            while (true){
                addServiceDiscoveryRequest();
                try { Thread.sleep(SCAN_SERVICE_INTERVALS); } catch (InterruptedException e) { e.printStackTrace();}
            }
        }
    }

    private void startToConnectPeer(String device_address) {

        WifiP2pDevice device = PeerDeviceManager.getInstance().getDevice(device_address);
        if (null == device){
            //TODO::
            Logger.e("Device is null now:" + device_address);
            return;
        }

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        _theWifiManager.connect(_currentChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Logger.i("---connect to peers success---");
            }

            @Override
            public void onFailure(int reason) {
                Logger.e("---Failed to connect peers("+reason+")---");
            }
        });
    }
}
