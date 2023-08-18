package com.globalitians.inquiry.activities.others;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.globalitians.inquiry.R;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;

public class AboutUsActivity extends AppCompatActivity {

    private TextView mTxtmoreinfolink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreenMode(AboutUsActivity.this);
        setContentView(R.layout.activity_about_us);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("About Us");getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();

    }

    private void findViews() {
        mTxtmoreinfolink = findViewById(R.id.tv_link);
        mTxtmoreinfolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://globalitians.com"));
                startActivity(webIntent);
            }
        });
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
