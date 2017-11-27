package kz.kbtu.medhack.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.tracking.TrackingActivity;
import kz.kbtu.medhack.user_info.UserInfoActivity;

public class MainActivity extends AppCompatActivity {



    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.profile) {
            startActivity(new Intent(this, UserInfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @OnClick(R.id.call_ambulance)
    public void callAmbulanceClick(Button button){
        startActivity(new Intent(this, TrackingActivity.class));
        finish();
    }
}
