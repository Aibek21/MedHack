package kz.kbtu.medhack.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

/**
 * Created by aibekkuralbaev on 08.11.16.
 */

public class LoginViewState implements RestorableViewState<LoginView> {

    private final String KEY_STATE =
            " acelight.kz.cvetochnik.login.LoginViewState.state";


    final int STATE_SHOW_LOGIN_FORM = 0;
    final int STATE_SHOW_LOADING = 1;
    final int STATE_SHOW_ERROR = 2;

    int currentState = STATE_SHOW_LOGIN_FORM;

    @Override
    public void apply(LoginView view, boolean retained) {

        switch (currentState) {
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;

            case STATE_SHOW_ERROR:
                view.showError(null);
                break;

            case STATE_SHOW_LOGIN_FORM:
                view.showLoginForm();
                break;
        }
    }

    public void setShowLoginForm() {
        currentState = STATE_SHOW_LOGIN_FORM;
    }

    public void setShowError() {
        currentState = STATE_SHOW_ERROR;
    }

    public void setShowLoading() {
        currentState = STATE_SHOW_LOADING;
    }

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putInt(KEY_STATE, currentState);
    }

    @Override
    public RestorableViewState<LoginView> restoreInstanceState(Bundle in) {
        currentState = in.getInt(KEY_STATE);
        return this;
    }
}
