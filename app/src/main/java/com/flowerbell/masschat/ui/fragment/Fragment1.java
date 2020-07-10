package com.flowerbell.masschat.ui.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.flowerbell.masschat.R;
import com.flowerbell.masschat.adapter.PeripheralFriendsAdapter;
import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.service.PeerDeviceManager;
import com.flowerbell.masschat.utils.ToastUtil;
import com.flowerbell.masschat.utils.ViewUtils;
import com.liaoinstan.springview.widget.SpringView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment1 extends Fragment implements View.OnClickListener, PeerDeviceManager.DeviceStatusChangedListener {


    private View mRootView;

    @BindView(R.id.login)
    TextView mTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.gray_layout)
    View mGrayLayout; //虚影效果
    @BindView(R.id.tv_tag)
    TextView mTvTag;

//    @BindView(R.id.rl_item)
//    RelativeLayout rlItem;
    private PopupWindow mPopupWindow;

    private boolean isPopWindowShowing = false;

    @BindView(R.id.list_recyclerview_peripheral)
    RecyclerView mRecyclerView;

    @BindView(R.id.list_springview_peripheral)
    SpringView mSpringView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment1_list, container, false);
        ButterKnife.bind(this, mRootView);//绑定到butterknife

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PeripheralFriendsAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                Logger.i("------SpringView onRefresh--------");
            }

            @Override
            public void onLoadmore() {
                Logger.i("------SpringView onLoadmore--------");
            }
        });

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setOnClickListener(this);
        mGrayLayout.setOnClickListener(this);
        mTitle.setText("周围");

//        rlItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ChatMessageActivity.class);
//                startActivity(intent);
//            }
//        });

        PeerDeviceManager.getInstance().regist_device_status_changed_listener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                showPopupWindow();
                break;
            case R.id.gray_layout:
                //设置popupWindow动画
                break;
            default:
                break;
        }
    }

    private void showPopupWindow() {
        mGrayLayout.setVisibility(View.VISIBLE);
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_select, null);
        LinearLayout llSelectone = (LinearLayout) contentView.findViewById(R.id.ll_selectone);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        mPopupWindow.showAsDropDown(mTvTag, 0, 0);
        // fromYDelta=-contentHeight-50;
        // mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(context, fromYDelta));

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopWindowShowing = false;
                mGrayLayout.setVisibility(View.GONE);
            }
        });
        llSelectone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(getActivity(), "个人设置");
            }
        });
        isPopWindowShowing = true;
    }

    @Override
    public void device_did_changed() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<BusinessNameCard> cards_list = PeerDeviceManager.getInstance().getCurrentDeviceList();
                mAdapter.refreshNameCardList(cards_list);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        PeerDeviceManager.getInstance().unregist_device_status_changed_listener(this);
    }

    PeripheralFriendsAdapter mAdapter = null;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
