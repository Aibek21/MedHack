package kz.kbtu.medhack.login;

import android.content.SharedPreferences;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import kz.kbtu.medhack.api.RequestHandler;
import kz.kbtu.medhack.models.User;
import kz.kbtu.medhack.models.credentials.ClientCredentials;
import kz.kbtu.medhack.utils.helps.Helper;
import kz.kbtu.medhack.utils.rx.RxUtil;
import rx.Subscriber;


/**
 * Created by aibekkuralbaev on 08.11.16.
 */

public class LoginPresenter extends MvpBasePresenter<LoginView> {


    private static final String TAG = LoginPresenter.class.getSimpleName();
    private RequestHandler mRequestHandler;
    private Subscriber<User> subscriber;
    private SharedPreferences mSharedPreferences;

    @Inject
    public LoginPresenter(RequestHandler requestHandler, SharedPreferences sharedPreferences) {
        mRequestHandler = requestHandler;
        mSharedPreferences = sharedPreferences;
    }


    public void doLogin(User user) {

        if (isViewAttached()) {
            getView().showLoading();
        }


        subscriber = new Subscriber<User>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    getView().loginSuccessful();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e.getMessage());
                }
            }

            @Override
            public void onNext(User user) {
                Log.e("NAME", user.getName());
                new Helper().setUser(mSharedPreferences, user);
            }
        };


        ClientCredentials clientCredentials = new ClientCredentials();
        clientCredentials.setClient(user);
        mRequestHandler.doLogin(clientCredentials)
                .compose(RxUtil.applyIOToMainThreadSchedulers())
                .subscribe(subscriber);

    }


}
