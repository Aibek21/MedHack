package kz.kbtu.medhack.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by aibekkuralbaev on 08.11.16.
 */

public interface LoginView extends MvpView {

    void showLoginForm();

    void showError(String message);

    void showLoading();

    void loginSuccessful();
}