package kz.kbtu.medhack.main;

import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import kz.kbtu.medhack.api.RequestHandler;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {

    @Inject
    public MainPresenter(RequestHandler requestHandler, SharedPreferences sharedPreferences) {

    }

}
