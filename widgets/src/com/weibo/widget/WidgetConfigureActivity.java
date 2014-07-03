package com.weibo.widget;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginButton;
import com.weibo.widget.sinaapi.AccessTokenKeeper;
import com.weibo.widget.sinaapi.Constants;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WidgetConfigureActivity extends Activity {
    private int mAppWidgetId;
    private LoginButton mLoginButton;
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {

         private Oauth2AccessToken mAccessToken;

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
             mAccessToken = Oauth2AccessToken.parseAccessToken(values);
             if (mAccessToken.isSessionValid()) {

            // 保存 Token 到 SharedPreferences
             AccessTokenKeeper.writeAccessToken(WidgetConfigureActivity.this,
             mAccessToken);
            Toast.makeText(WidgetConfigureActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            updateRemoteViews();
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
             finish();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                Toast.makeText(WidgetConfigureActivity.this, "授权失败", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_configure_layout);
        mLoginButton = (LoginButton) findViewById(R.id.btn_login);
        mLoginButton.setWeiboAuthInfo(Constants.APP_KEY, Constants.REDIRECT_URL, null, mWeiboAuthListener);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        setResult(RESULT_CANCELED);

    }

    void updateRemoteViews() {
        WidgetProvider.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLoginButton != null) {
            mLoginButton.onActivityResult(requestCode, resultCode, data);
        }
    }
}
