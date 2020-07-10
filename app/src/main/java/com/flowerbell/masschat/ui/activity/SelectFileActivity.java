package com.flowerbell.masschat.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.adapter.MyFragmentPagerAdapter;
import com.flowerbell.masschat.base.BaseActivity;
import com.flowerbell.masschat.ui.fragment.FileFragment;
import com.flowerbell.masschat.ui.fragment.ImageFragment;
import com.flowerbell.masschat.ui.fragment.MusicFragment;
import com.flowerbell.masschat.ui.fragment.VideoFragment;

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


public class SelectFileActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.login_btn_back)
    ImageView mReturn;


    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_select_file);
    }

    @Override
    protected void findViewById() {
        mTitle.setText("选择文件");
        mReturn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        mReturn.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(myAdapter);
        initMagicIndicator();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_back:
                finish();
                break;

            default:
                break;
        }
    }

    private void initFragmentList() {
        mTitleList.add("文件");
        mTitleList.add("图片");
        mTitleList.add("音乐");
        mTitleList.add("视频");
        mFragments.add(new FileFragment());
        mFragments.add(new ImageFragment());
        mFragments.add(new MusicFragment());
        mFragments.add(new VideoFragment());
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.gradient_center));
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setTextSize(15);
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
                linePagerIndicator.setColors(getResources().getColor(R.color.gradient_center));
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(context, 15);
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
