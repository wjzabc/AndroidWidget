package com.weibo.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WidgetProvider extends AppWidgetProvider {
    private static final String WIDGET_CLICKED = "widget_clicked";
    private static int count = 0;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if (intent.getAction().equals(WIDGET_CLICKED)) {
            rv.setTextViewText(R.id.text1, "xxx" + count++);
            Intent intentClick = new Intent(WIDGET_CLICKED);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick,
                    PendingIntent.FLAG_ONE_SHOT);
            rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
            AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
            int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
            appWidgetManger.updateAppWidget(appIds, rv);
        }

    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgeManger, int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intentClick = new Intent(WIDGET_CLICKED);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
        rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
        rv.setTextViewText(R.id.text1, "go" + count++);
        // rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
        appWidgeManger.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {
                    RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                    rv.setTextViewText(R.id.text1, "service " + count++);
                    Intent intentClick = new Intent(WIDGET_CLICKED);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick,
                            PendingIntent.FLAG_ONE_SHOT);
                    rv.setOnClickPendingIntent(R.id.text1, pendingIntent);
                    AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
                    int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
                    appWidgetManger.updateAppWidget(appIds, rv);
                    
                }
            }, 0, 1000, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onDisabled(Context context) {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }

        super.onDisabled(context);
    }

}
