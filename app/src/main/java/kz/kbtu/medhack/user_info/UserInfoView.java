package kz.kbtu.medhack.user_info;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public interface UserInfoView extends MvpView {

    void showErrorToast(String message);

    void showProgressLoading();

    void changeSuccessful();

}
