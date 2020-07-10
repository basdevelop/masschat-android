package com.flowerbell.masschat.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.adapter.MyFragmentPagerAdapter;
import com.flowerbell.masschat.base.BaseFragment;
import com.flowerbell.masschat.ui.activity.FileHistoryActivity;
import com.flowerbell.masschat.utils.Utils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;


public class Fragment3 extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment3_list, container, false);
    }

    @Override
    protected void initListener() {
        mTvRight.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(myAdapter);
        initMagicIndicator();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(context, FileHistoryActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }

    private void initFragmentList() {
        mTitleList.add("收文件");
        mTitleList.add("发文件");
        mFragments.add(new ReceiveFileFragment());
        mFragments.add(new SendFileFragment());
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.linegrey));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 40));
                linePagerIndicator.setColors(Color.WHITE);
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mActivity, 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(mMagicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }
}
