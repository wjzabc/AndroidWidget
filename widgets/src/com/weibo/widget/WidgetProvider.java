package com.weibo.widget;

import com.sina.weibo.sdk.utils.LogUtil;
import com.weibo.widget.sinaapi.SsoActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {
    private static final String WIDGET_CLICKED = "widget_clicked";
    private static final String TAG = "WidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.d(TAG, "onReceive action="+action);
        
        
          if (action.equals(WIDGET_CLICKED)) {
              Log.d(TAG, "WIDGET_CLICKED");
              Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
          }
/*        if (action.equals(WIDGET_CLICKED)) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            Intent intentClick = new Intent(WIDGET_CLICKED);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick,
                    PendingIntent.FLAG_ONE_SHOT);
            rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
            AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
            int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
            appWidgetManger.updateAppWidget(appIds, rv);
        }*/

    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgeManger, int appWidgetId) {
        Log.d(TAG, "updateAppWidget");
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//        Intent intentClick = new Intent(WIDGET_CLICKED);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
        Intent intent=new Intent(context, SsoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
        appWidgeManger.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onEnabled(final Context context) {
        Log.d(TAG, "onEnabled");
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled");
        super.onDisabled(context);
    }
}
