package com.weibo.widget;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;

public class WidgetDataManager {
    private static WidgetDataManager mInstance;
    private Context mContext;
    private SparseArray<WidgetDataSet> mWidgetDataSets = new SparseArray<WidgetDataSet>();
    private RequestListener mRequestListener = new RequestListener() {

        @Override
        public void onWeiboException(WeiboException arg0) {
            Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(String arg0) {
            Toast.makeText(mContext, arg0, Toast.LENGTH_SHORT).show();
        }
    };

    private WidgetDataManager(Context context) {
        mContext = context;

    }

    public static WidgetDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WidgetDataManager(context);
        }
        return mInstance;
    }

    public void getlatestweibo(int widgetId) {
        Oauth2AccessToken token = mWidgetDataSets.get(widgetId).mAccessToken;
        if(widgetId==-1){
            token=mWidgetDataSets.get(mWidgetDataSets.keyAt(0)).mAccessToken;
        }
        StatusesAPI statusesApi = new StatusesAPI(token);
        statusesApi.friendsTimeline(0, System.currentTimeMillis(), 2, 1, false, 0, false, mRequestListener);
    }

    public void addWidget(int key, Oauth2AccessToken token) {
        mWidgetDataSets.put(key, new WidgetDataSet(key, token));
    }
}
