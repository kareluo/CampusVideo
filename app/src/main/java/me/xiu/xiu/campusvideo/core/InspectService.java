package me.xiu.xiu.campusvideo.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import me.xiu.xiu.campusvideo.common.xml.Xml;
import me.xiu.xiu.campusvideo.common.xml.XmlObject;
import me.xiu.xiu.campusvideo.common.xml.XmlParser;
import me.xiu.xiu.campusvideo.ui.activity.PlayerActivity;
import me.xiu.xiu.campusvideo.ui.activity.TipsActivity;
import me.xiu.xiu.campusvideo.util.ConnectivityUtils;
import me.xiu.xiu.campusvideo.util.ValueUtil;

/**
 * Created by felix on 16/12/23.
 */
public class InspectService extends IntentService {
    private static final String TAG = "InspectService";

    public InspectService() {
        super(TAG);
    }

    @Override
    protected synchronized void onHandleIntent(Intent intent) {
        if (ConnectivityUtils.isAvailable(getApplicationContext())) {
            if (!isAvailable() && shouldShowTips()) {
                startActivity(new Intent(getApplicationContext(), TipsActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
    }

    private boolean isAvailable() {
        try {
            XmlObject xmlObject = XmlParser.parse(Xml.BARSET, Xml.TAG_ROOT, 1);
            return xmlObject != null && !xmlObject.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean shouldShowTips() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (manager != null) {
            List<RunningTaskInfo> tasks = manager.getRunningTasks(1);
            if (!ValueUtil.isEmpty(tasks)) {
                RunningTaskInfo info = tasks.get(0);
                if (info.topActivity.getClassName().equals(PlayerActivity.class.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void inspect(Context context) {
        context.startService(new Intent(context, InspectService.class));
    }

    public enum STATUS {
        NET_PHONE("4G网络"),
        NET_WIFI("WIFI网络"),
        INACCESSIBLE("服务器无法访问");

        String desc;

        STATUS(String desc) {
            this.desc = desc;
        }
    }
}
