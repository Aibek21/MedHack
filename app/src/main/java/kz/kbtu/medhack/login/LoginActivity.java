package kz.kbtu.medhack.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.kbtu.medhack.App;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.api.RequestHandler;
import kz.kbtu.medhack.main.MainActivity;
import kz.kbtu.medhack.models.Order;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.tracking.TrackingActivity;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;
import kz.kbtu.medhack.utils.events.ChangeFragmentEvent;
import kz.kbtu.medhack.utils.rx.RxUtil;
import rx.Subscriber;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    Context mContext;

    @Nullable
    @Inject
    User mUser;

    @Inject
    RequestHandler mRequestHandler;

    private FragmentManager fragmentManager;

    private NetComponent mNetComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mNetComponent = ((App) getApplication()).getNetComponent();
        mNetComponent.inject(this);
        if (mUser != null) {
           showMain();
        }else {

            fragmentManager = getSupportFragmentManager();

            LoginFragment fragment = LoginFragment.newInstance();
            showFragment(fragment);
        }


    }


    public void getOrders() {


        Subscriber<List<Order>> subscriber = new Subscriber<List<Order>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Order> orders) {
                if (orders.size() > 0) showTracking();
                else showMain();
            }
        };



        mRequestHandler.getOrders()
                .compose(RxUtil.applyIOToMainThreadSchedulers())
                .subscribe(subscriber);

    }

    public void showTracking() {
        startActivity(new Intent(this, TrackingActivity.class));
        finish();
        return;
    }

    public void showMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return;
    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStack();
        else
            finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragmentEvent(ChangeFragmentEvent event) {
        showFragment(event.getFragment());
    }


}

