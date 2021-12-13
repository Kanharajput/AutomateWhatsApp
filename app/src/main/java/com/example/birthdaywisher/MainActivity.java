package com.example.birthdaywisher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private EditText mMessOpenWhatEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessOpenWhatEdit = findViewById(R.id.mess);

        Log.d("log","onCreate");

        // on the accessibility service if not
        if (!isAccessibilityOn (getApplicationContext())) {
            Intent intent = new Intent (Settings.ACTION_ACCESSIBILITY_SETTINGS);
            Log.d("log","startingAccessibility");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void openWhatsapp(View view) {
        String message = mMessOpenWhatEdit.getText().toString();     // take message from the user
        message.endsWith("   ");         // adding a suffix to the message for accessibility checkup

        // create an Intent to send data to the whatsapp
        Intent intent = new Intent(Intent.ACTION_VIEW);    // setting action

        // setting data url
        try {
            String url = "https://api.whatsapp.com/send?phone=+91 9131912040" + "&text=" + URLEncoder.encode(message, "UTF-8");
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           // TO WORK OUR INTENT FROM SERVICE
            startActivity(intent);
        }
        catch(UnsupportedEncodingException e){
            Log.d("notSupport", "thrown by encoder");
        }
    }

    // code to on accessibility service
    private boolean isAccessibilityOn (Context context) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + WhatsappAccessibilityService.class.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }
        Log.d("log","inAccessibilityOn");
        return false;
    }
}