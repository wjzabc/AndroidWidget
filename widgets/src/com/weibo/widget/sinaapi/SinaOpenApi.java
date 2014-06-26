package com.weibo.widget.sinaapi;

import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class SinaOpenApi {

    public void authorize(){
        SsoHandler mSsoHandler = new SsoHandler(WBAuthActivity.this, mWeiboAuth);
    }
}
