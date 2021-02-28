package org.mesibo.messenger.ui;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by umesh on 20/11/15.
 */
public class UssdService extends AccessibilityService {

    public static String TAG = "USSDReader";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "==> " + event.getText());
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
            return;

        Intent intent;
        if (event.getClassName().toString().contains("com.whatsapp") ||
                event.getClassName().toString().toLowerCase().contains("com.gbwhatsapp")) {
            intent = new Intent("com.nsromapa.say.action.CAPTURE");
//            intent.putExtra("message", "Take");
            Log.d(TAG, "onAccessibilityEvent: ==========> its whatsapp");
        } else {
            intent = new Intent("com.nsromapa.say.action.STOPCAPTURE");
//            intent.putExtra("message", "Cancel");
            Log.d(TAG, "onAccessibilityEvent: ==========> its out");
        }
        this.sendBroadcast(intent);
    }


    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected UssdService");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        setServiceInfo(info);
    }
}