package com.flowerbell.masschat.service.connection;

import com.flowerbell.masschat.service.AdvertiseService;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wsli on 2017/8/1.
 */

public class ServerThread extends Thread {

    static Map<String, WorkerThread> _connectedClientCache = new HashMap<>(255);
    private ServerSocket _serverSocket = null;

    private final ThreadPoolExecutor _threadPooler = new ThreadPoolExecutor(
            100, 255, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Override
    public void run(){

        try {
            _serverSocket = new ServerSocket(AdvertiseService.SERVER_PORT);
            Logger.i(">>>>>>>>waiting client on port:"+AdvertiseService.SERVER_PORT);

            while (true) {
                Socket client = _serverSocket.accept();

                String client_ip_address = client.getInetAddress().getHostAddress();
                Logger.i(">>>>>>>>client ip address:" + client_ip_address);

                WorkerThread one_worker = new WorkerThread(client, client_ip_address);
                _threadPooler.execute(one_worker);

                _connectedClientCache.put(client_ip_address, one_worker);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.finished();

    }

    public  void finished(){

        try {
            if (null != _serverSocket && !_serverSocket.isClosed()){
                _serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            _serverSocket = null;
        }
    }

    public void broadcast_online_message(JSONObject msg_obj) throws JSONException {

        for (WorkerThread client: _connectedClientCache.values()){

            MessageBean name_card = new MessageBean();
            name_card.setType(MessageBean.MSG_TYPE_CTL_NEWUSERONLINE);
            name_card.setFrom("-1");
            name_card.setTo("all");

            msg_obj.put("ipAddress", client.getIpAddress());
            name_card.setData(msg_obj.toString());

            client.sendMessage(name_card.toJSONString());
        }
    }


    public static class WorkerThread implements Runnable {

        Socket          _currentSocket      = null;
        InputStream     _inputStream        = null;
        OutputStream    _outputStream       = null;
        byte[]          _readBuffer         = new byte[4096];
        int             _readLength         = -1;
        String          _thisSocketIp       = null;


        public WorkerThread(Socket s, String ip){
            _currentSocket = s;
            this._thisSocketIp = ip;
        }

        @Override
        public void run() {
            try {

                _inputStream    = _currentSocket.getInputStream();
                _outputStream   = _currentSocket.getOutputStream();

                while ((_readLength = _inputStream.read(_readBuffer)) > 0) {
                    String msg = new String(_readBuffer, 0, _readLength);

                    if (null != msg && msg.length() > 0){
                        MessageManager.getInstance().cacheMessage(msg);
                    }else{
                        Logger.e(">>> Failed to read message from input stream.");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            _inputStream    = null;
            _outputStream   = null;
        }

        public void sendMessage(String msg){
            try {
                _outputStream.write(msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getIpAddress() {
            return this._thisSocketIp;
        }
    }
}
