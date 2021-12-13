package com.example.birthdaywisher;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsappAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(getRootInActiveWindow() == null) {
            return;
        }
        // getting root node as it is not null
        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        // get the edittext where we write message in whatsapp
        List<AccessibilityNodeInfoCompat> messageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        // check if this editText have text or not
        if(messageNodeList == null || messageNodeList.isEmpty()) {
            return;
        }
        AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);

        // getting the field to check it is filled or not and it have our suffix with the message or not
        if(messageField == null || messageField.getText().length() == 0 || !messageField.getText().toString().endsWith("   ")) {
            return;     // message may by null or not have our suffix
        }

        // message have suffix to we get the button id to command for pressing
        List<AccessibilityNodeInfoCompat> sendMessageNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if(sendMessageNodeList == null || sendMessageNodeList.isEmpty()) {    // same as we did for editText
            return;
        }

        AccessibilityNodeInfoCompat sendMessage = sendMessageNodeList.get(0);      // I think we are getting the first id from the list .... that's not make any sense to me
        if(!sendMessage.isVisibleToUser()) {
            return;            // as that button work for both mic and send message , that's why we are checking this
        }

        // button is visible to the user so just press it
        sendMessage.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);

        // code for returning back to the app
        try {
            Thread.sleep(2000);     // sleep for 5 seconds
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(2000);
        }
        catch(InterruptedException ignored) { }

        performGlobalAction(GLOBAL_ACTION_BACK);

    }

    @Override
    public void onInterrupt() {

    }
}
