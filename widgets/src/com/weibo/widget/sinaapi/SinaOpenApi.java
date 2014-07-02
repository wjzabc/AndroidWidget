package com.weibo.widget.sinaapi;

import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.os.Bundle;

public class SinaOpenApi {

    public static void authorize(Activity activity){
        WeiboAuth mWeiboAuth = new WeiboAuth(activity, Constants.APP_KEY, Constants.REDIRECT_URL, null);
        SsoHandler mSsoHandler = new SsoHandler(activity, mWeiboAuth);
        mSsoHandler.authorize(new WeiboAuthListener() {
            
            @Override
            public void onWeiboException(WeiboException paramWeiboException) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onComplete(Bundle paramBundle) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                
            }
        });
    }
}
