package com.weibo.widget.sinaapi;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SsoActivity extends Activity {
    private static final String TAG = "SsoActivity";
    private static WeiboAuth mWeiboAuth;
    private static SsoHandler mSsoHandler;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(SsoActivity.this, mAccessToken);
                Toast.makeText(SsoActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                Toast.makeText(SsoActivity.this, "授权失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
        }
        
        @Override
        public void onWeiboException(WeiboException paramWeiboException) {
            // TODO Auto-generated method stub
        }
    };

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        authorize(this);
    };

    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            finish();
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    };
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	Log.d(TAG, "onBackPressed");
    }

    private void authorize(Activity activity) {
        mWeiboAuth = new WeiboAuth(activity, Constants.APP_KEY, Constants.REDIRECT_URL, null);
        mSsoHandler = new SsoHandler(activity, mWeiboAuth);
        mSsoHandler.authorize(mWeiboAuthListener);
    }
}
