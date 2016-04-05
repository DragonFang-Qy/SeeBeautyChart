package com.a704084109qq.news.util;

import android.content.Context;

import com.a704084109qq.news.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.system.text.ShortMessage;

/**
 * Created by Administrator on 2016/4/3 0003.
 */
public class UtilsShareSDK {

    private static String titleStr;
    private static String titleUrlStr;
    private static Context mContext;

    public static void showShare(Context context, String title, String titleUrl) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();

        titleStr = title;
        titleUrlStr = titleUrl;
        mContext = context;
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(context.getResources().getString(R.string.share_hint));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(titleUrl);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(titleUrl);

// 启动分享GUI
        oks.show(context);
    }


    static class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

        public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
            // 改写twitter分享内容中的text字段，否则会超长，
            // 因为twitter会将图片地址当作文本的一部分去计算长度
            if (ShortMessage.NAME.equals(platform.getName())) {
                String text = titleStr + " " + titleUrlStr + "\n\t --" + mContext.getResources().getString(R.string.share_hint);
                paramsToShare.setText(text);
            }
        }

    }
}
