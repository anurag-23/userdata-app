package com.anurag23.userdata;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.second_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setSubtitle("(" + getIntent().getStringExtra("username") + ")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView email = (TextView)findViewById(R.id.user_email);
        TextView aStreet = (TextView)findViewById(R.id.user_a_street);
        TextView aSuite = (TextView)findViewById(R.id.user_a_suite);
        TextView aCity = (TextView)findViewById(R.id.user_a_city);
        TextView aZip = (TextView)findViewById(R.id.user_a_zip);
        TextView phone = (TextView)findViewById(R.id.user_phone);
        TextView website = (TextView)findViewById(R.id.user_website);

        email.setText(getIntent().getStringExtra("email"));
        aStreet.setText(getIntent().getStringExtra("aStreet"));
        aSuite.setText(getIntent().getStringExtra("aSuite"));
        aCity.setText(getIntent().getStringExtra("aCity"));
        aZip.setText(getIntent().getStringExtra("aZip"));
        phone.setText(getIntent().getStringExtra("phone"));
        website.setText(getIntent().getStringExtra("website"));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home: {
                this.finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
