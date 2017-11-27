package kz.kbtu.medhack.user_info;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.medhack.App;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.utils.base.view.BaseActivity;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;
import kz.kbtu.medhack.utils.helps.Helper;

public class UserInfoActivity extends BaseActivity<UserInfoView, UserInfoPresenter> implements UserInfoView {


    NetComponent mNetComponent;


    @Inject
    @Nullable
    User mUser;

    @Inject
    SharedPreferences mSharedPreferences;


    @BindView(R.id.name)
    EditText name;


    @BindView(R.id.bday)
    EditText bday;


    @BindView(R.id.address)
    EditText address;


    @BindView(R.id.mobile_phone)
    EditText mobile_phone;


    @BindView(R.id.gender)
    EditText gender;


    @BindView(R.id.home_phone)
    EditText home_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Мои данные");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mUser = new Helper().getUser(mSharedPreferences);
        setContent();

    }


    private void setContent(){
        name.setText(mUser.getName());
        bday.setText(Helper.getDate(mUser.getBday()));
        address.setText(mUser.getAddress());
        mobile_phone.setText(mUser.getPhone());
        home_phone.setText(mUser.getPhone());
        gender.setText(mUser.getGender() == 0 ? "Муж" : "Жен");
    }

    @Override
    protected void injectDependencies() {
        mNetComponent = ((App) getApplication()).getNetComponent();
        mNetComponent.inject(this);
    }


    @NonNull
    @Override
    public UserInfoPresenter createPresenter() {
        return mNetComponent.userInfoPresenter();
    }

    @Override
    public void showErrorToast(String message) {

    }

    @Override
    public void showProgressLoading() {

    }

    @Override
    public void changeSuccessful() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
