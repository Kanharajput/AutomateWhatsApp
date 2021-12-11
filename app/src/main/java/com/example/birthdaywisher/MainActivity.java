package com.example.birthdaywisher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    }

    public void openWhatsapp(View view) {
        String message = mMessOpenWhatEdit.getText().toString();     // take message from the user

        // create an Intent to send data to the whatsapp
        Intent intent = new Intent(Intent.ACTION_VIEW);    // setting action

        // setting data url
        try {
            String url = "https://api.whatsapp.com/send?phone=+91 9131912040" + "&text=" + URLEncoder.encode(message, "UTF-8");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        catch(UnsupportedEncodingException e){
            Log.d("notSupport", "thrown by encoder");
        }
    }
}