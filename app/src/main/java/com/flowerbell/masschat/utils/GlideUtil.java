package com.flowerbell.masschat.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flowerbell.masschat.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class GlideUtil {

    // 是否大屏
    public static void Load(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.head_img_default)
                .placeholder(R.mipmap.head_img_default)
                .crossFade()//淡入显示,
                .into(image);
    }

    // 圆四角
    public static void LoadRoundedCorners(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .error(R.mipmap.head_img_default)
                .placeholder(R.mipmap.head_img_default)
                .crossFade()//淡入显示,
                .bitmapTransform(new RoundedCornersTransformation(image.getContext(), 15, 0))
                .into(image);
    }

    // 圆角
    public static void LoadCircleCorners(ImageView image, String url) {
        Glide.with(image.getContext())

                .load(url)
                .error(R.mipmap.head_img_default)
                .placeholder(R.mipmap.head_img_default)
                .bitmapTransform(new CropCircleTransformation(image.getContext()))
                .into(image);
    }
}