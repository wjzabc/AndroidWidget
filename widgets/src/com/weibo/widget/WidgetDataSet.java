package com.weibo.widget;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class WidgetDataSet {
    public int mWidgetID = -1;
    public Oauth2AccessToken mAccessToken = null;

    public WidgetDataSet(int widgetId, Oauth2AccessToken token) {
        mWidgetID = widgetId;
        mAccessToken = token;
    }
}
