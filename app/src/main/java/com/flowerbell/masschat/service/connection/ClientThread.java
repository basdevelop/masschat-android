package com.flowerbell.masschat.service.connection;

import android.net.wifi.p2p.WifiP2pDevice;

import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.domain.LoginUser;
import com.flowerbell.masschat.domain.UserSession;
import com.flowerbell.masschat.service.AdvertiseService;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by wsli on 2017/8/1.
 */

public class ClientThread extends Thread {

    Socket          _clientSocket       = null;
    String          _hostName           = null;
    InputStream     _inputStream        = null;
    OutputStream    _outputStream       = null;
    byte[]          _readBuffer         = new byte[4096];
    int             _readLength         = -1;

    public  ClientThread(String hostName) {
        this._hostName = hostName;
    }

    void connectToServer() throws InterruptedException, IOException {
        try {
            _clientSocket = new Socket();
            _clientSocket.bind(null);

            InetSocketAddress socket_address = new InetSocketAddress(_hostName, AdvertiseService.SERVER_PORT);
            _clientSocket.connect(socket_address, 2000);

            _inputStream = _clientSocket.getInputStream();
            _outputStream = _clientSocket.getOutputStream();

        }catch (ConnectException ex){
            Logger.e(">>> The server isn't setup.");
            throw ex;
        }
    }


    @Override
    public void run(){
        try {

            Thread.sleep(500);//TIPS::服务器可能还没有设置好，稍等一下再去链接。

            this.connectToServer();

            this.introduce_self_to_all_clients();

            while ((_readLength = _inputStream.read(_readBuffer)) > 0) {
                String msg = new String(_readBuffer, 0, _readLength);

                if (null != msg && msg.length() > 0){
                    MessageManager.getInstance().cacheMessage(msg);
                }else{
                    Logger.e(">>> Failed to read message from input stream.");
                    break;
                }
            }

            finished();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void introduce_self_to_all_clients() {

        BusinessNameCard name_card_to_register = new BusinessNameCard();

        LoginUser current_user = UserSession.getInstance().getCurrentUser();
        byte[] image_data = new byte[4];//TODO::save and load avatar of this user.
        name_card_to_register.setAvatarData(image_data);
        name_card_to_register.setName(current_user.getName());
        name_card_to_register.setPhone(current_user.getPhone());
        name_card_to_register.setEmail(current_user.getEmail());
        name_card_to_register.setSex(current_user.getSex());
        name_card_to_register.setTittle(current_user.getTittle());
        name_card_to_register.setAge(current_user.getAge());
        name_card_to_register.setIndustry(current_user.getIndustry());
        name_card_to_register.setCompanyAddress(current_user.getCompanyAddress());
        name_card_to_register.setCompany(current_user.getCompany());
        name_card_to_register.setCompanyLink(current_user.getCompanyLink());
        name_card_to_register.setWechatName(current_user.getWechatName());
        name_card_to_register.setWechatOpenId(current_user.getWechatOpenId());

        WifiP2pDevice device = UserSession.getInstance().getLocalDevice();
        name_card_to_register.setMacAddress(device.deviceAddress);
        name_card_to_register.setDeviceName(device.deviceName);

        String msg = name_card_to_register.getNameCardForRegister();

        MessageBean msg_bean = new MessageBean();
        msg_bean.setFrom(name_card_to_register.getMacAddress());
        msg_bean.setTo("-1");
        msg_bean.setType(MessageBean.MSG_TYPE_CTL_REGISTER);
        msg_bean.setData(msg);

        this.sendMessage(msg_bean.toJSONString());
    }

    public void sendMessage(String msg){
        try {
            _outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void finished(){try {
        if (null != _clientSocket && _clientSocket.isConnected()){
            _clientSocket.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        _clientSocket   = null;
        _outputStream   = null;
        _inputStream    = null;
    }
    }
}
