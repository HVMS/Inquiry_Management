package com.globalitians.inquiry.activities.others;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.globalitians.inquiry.R;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView tvphone;
    private TextView tvanotherphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(ContactUsActivity.this);
        setContentView(R.layout.activity_contact_us);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Contact Us");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.bv_primaryColor)));

        findViews();

    }

    private void findViews() {
        textView = findViewById(R.id.tv_email);
        tvphone = findViewById(R.id.phone_1);
        tvanotherphone = findViewById(R.id.phone_2);
        tvphone.setOnClickListener(this);
        tvanotherphone.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phone_1:
                String phone1 = tvphone.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone1,null));
                startActivity(intent);
                break;
            case R.id.phone_2:
                String phone2 = tvanotherphone.getText().toString().trim();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone2,null));
                startActivity(intent1);
                break;
        }
    }
}
