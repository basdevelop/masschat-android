package com.flowerbell.masschat.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseActivity;
import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.service.PeerDeviceManager;

import butterknife.BindView;


public class ChatMessageActivity extends BaseActivity implements View.OnClickListener, PeerDeviceManager.ConnectionStatusChanedListener {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.login_btn_back)
    ImageView mReturn;

    @BindView(R.id.btn_send)
    Button mMsgSendBtn;

    String _nameCardMacAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this._nameCardMacAddress = getIntent().getStringExtra("target");
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_chat_ui);
    }

    @Override
    protected void findViewById() {
        mReturn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        mReturn.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void connection_status_changed(final BusinessNameCard card, final boolean online) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (online){
                    mTitle.setText(card.getName()+"(在线)");
                }else{
                    mTitle.setText(card.getName()+"(离线)");
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        BusinessNameCard name_card = PeerDeviceManager.getInstance().getPeerNameCard(_nameCardMacAddress);

        boolean is_still_active = PeerDeviceManager.getInstance().isActive(name_card);
        if (false == is_still_active){
            mMsgSendBtn.setEnabled(false);
            mTitle.setText(name_card.getName()+"(离线)");

        }else if (false == name_card.isConnected()){
            mMsgSendBtn.setEnabled(false);
            mTitle.setText(name_card.getName()+"(连接中......)");

            PeerDeviceManager.getInstance().registerConnectionStatusListener(this, name_card.getMacAddress());
            PeerDeviceManager.getInstance().startConnectPeer(context, name_card.getMacAddress());
        }else {
            mTitle.setText(name_card.getName()+"(在线)");
        }
    }

}
