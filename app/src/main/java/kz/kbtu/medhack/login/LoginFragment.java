package kz.kbtu.medhack.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.kbtu.medhack.App;
import kz.kbtu.medhack.main.MainActivity;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.utils.base.view.BaseViewStateFragment;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;
import kz.kbtu.medhack.utils.helps.Helper;
import kz.kbtu.medhack.utils.helps.Keys;

public class LoginFragment extends BaseViewStateFragment<LoginView, LoginPresenter> implements LoginView {


    @BindView(R.id.phone_number)
    EditText mPhoneView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.email_sign_in_button)
    Button mSignInButton;
//    @BindView(R.id.forget_pass)
//    TextView mForgetPass;
    @Inject
Context mContext;
    @Inject
    SharedPreferences mSharedPreferences;
    ProgressDialog progressDialog;
    private NetComponent mNetComponent;


    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return mNetComponent.loginPresenter();
    }


    private void showMain() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        mPhoneView.setError(null);
        mPasswordView.setError(null);
        mPasswordView.setOnEditorActionListener((v, actionId, event) -> false);


        String phone = mPhoneView.getText().toString();
        phone = phone.replaceAll(" ", "");
        phone = phone.replaceAll("\\(", "");
        phone = phone.replaceAll("\\)", "");
        phone = phone.replaceAll("\\-", "");
        phone = phone.replaceAll("\\+", "");

        String password = mPasswordView.getText().toString();
        String token = mSharedPreferences.getString(Keys.TOKEN, "");
        boolean cancel = false;
        View focusView = null;

        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_email));
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            presenter.doLogin(user);
        }
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }


    @Override
    public void showLoginForm() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoginForm();
    }

    @Override
    public void showError(String message) {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowError();

        progressDialog.dismiss();
        if (message != null)
            Helper.showAlertDialog(getActivity(), message);
    }

    @Override
    public void showLoading() {
        LoginViewState vs = (LoginViewState) viewState;
        vs.setShowLoading();

        progressDialog = new ProgressDialog(getActivity(), R.style.DialogTheme);
        progressDialog.setCancelable(false);
        if (!progressDialog.isShowing()) progressDialog.show();

    }

    @Override
    public void loginSuccessful() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
        showMain();
    }

    @NonNull
    @Override
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        showLoginForm();
    }

    @Override
    protected void injectDependencies() {
        mNetComponent = ((App) getActivity().getApplication()).getNetComponent();
        mNetComponent.inject(this);

    }

    @OnClick({R.id.email_sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
//            case R.id.forget_pass:
//                EventBus.getDefault().post(new ChangeFragmentEvent(ForgetPasswordFragment.newInstance()));
//                break;
        }
    }
}
