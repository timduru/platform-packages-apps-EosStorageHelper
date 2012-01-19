
package org.teameos.apps.storagehelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.teameos.apps.storagehelper.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

public class StorageReciever extends BroadcastReceiver {

    private static final int USB_STORAGE_MOUNTED = 1;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (intent.getDataString().equals("file:///media/usb")) {

            if (intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                long when = System.currentTimeMillis();

                Resources resources = context.getResources();
                Notification notification = new Notification(R.drawable.ic_dialog_usb,
                        resources.getString(R.string.not_applied), when);

                Intent intent1 = new Intent("android.settings.INTERNAL_STORAGE_SETTINGS");
                intent1.addCategory(Intent.CATEGORY_DEFAULT);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent1, 0);
                notification.flags = Notification.FLAG_ONGOING_EVENT;
                notification.setLatestEventInfo(context,
                        resources.getString(R.string.notification_title),
                        resources.getString(R.string.notification_text), contentIntent);
                mNotificationManager.notify(USB_STORAGE_MOUNTED, notification);
            } else {
                mNotificationManager.cancel(USB_STORAGE_MOUNTED);
            }
        }
    }

}
