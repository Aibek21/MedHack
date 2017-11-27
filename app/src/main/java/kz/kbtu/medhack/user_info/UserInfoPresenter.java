package kz.kbtu.medhack.user_info;

import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import kz.kbtu.medhack.api.RequestHandler;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class UserInfoPresenter extends MvpBasePresenter<UserInfoView> {

    @Inject
    public UserInfoPresenter(RequestHandler requestHandler, SharedPreferences sharedPreferences) {

    }
}
