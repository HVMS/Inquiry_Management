package com.globalitians.inquiry.activities.others;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.Utility.Constants;

import java.util.Locale;

import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_ACTION_VOICE;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_LOGGED_IN;

public class SettingActivity extends AppCompatActivity {

    private Switch switchVoiceOff;
    private TextView tvLogout;
    private SharedPreferences mSharedPrefrences;
    public static TextToSpeech textToSpeechAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(SettingActivity.this);
        setContentView(R.layout.activity_setting);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        mSharedPrefrences = getSharedPreferences(Constants.LOGIN_PREFRENCES, Context.MODE_PRIVATE);

        switchVoiceOff = findViewById(R.id.switchVoiceOff);

        switchVoiceOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSharedPrefrences.getString(KEY_IS_ACTION_VOICE, "").equalsIgnoreCase("true")) {
                    switchVoiceOff.setChecked(false);
                    saveVoicePrefrence("false");
                } else {
                    switchVoiceOff.setChecked(true);
                    saveVoicePrefrence("true");
                }
            }
        });

        tvLogout=findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigAteToLoginScreen();
            }
        });

    }

    private void navigAteToLoginScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage("Are you Sure ?");
        builder.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clearLoginPrefrences();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nothing
            }
            // Create the AlertDialog object and return it
        });

        builder.show();
    }

    private void clearLoginPrefrences() {
        if (mSharedPrefrences != null) {
            SharedPreferences.Editor editor = mSharedPrefrences.edit();
            editor.remove(KEY_IS_LOGGED_IN);
            editor.clear();
            editor.commit();
        }
    }

    private void saveVoicePrefrence(String toggleVoice) {
        SharedPreferences.Editor editor = mSharedPrefrences.edit();
        if(toggleVoice.equalsIgnoreCase("false")){
            playSoundForAttendance("Bye Bye Dear, I am silence now.",SettingActivity.this);
        }else{
            playSoundForAttendance("Hello, I can Speak now. Yeppi",SettingActivity.this);
        }
        editor.putString(KEY_IS_ACTION_VOICE, toggleVoice);
        editor.commit();
    }

    public static void setFullScreenMode(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void playSoundForAttendance(final String strToSpeech, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LOGIN_PREFRENCES", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("KEY_IS_ACTION_VOICE", "").equalsIgnoreCase("true")) {
            textToSpeechAttendance = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeechAttendance.setLanguage(Locale.UK);
                        textToSpeechAttendance.setSpeechRate(0.9f);
                        textToSpeechAttendance.speak(strToSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
