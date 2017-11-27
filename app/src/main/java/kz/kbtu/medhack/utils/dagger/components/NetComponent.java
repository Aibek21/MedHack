package kz.kbtu.medhack.utils.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import kz.kbtu.medhack.login.LoginActivity;
import kz.kbtu.medhack.main.MainActivity;
import kz.kbtu.medhack.login.LoginFragment;
import kz.kbtu.medhack.login.LoginPresenter;
import kz.kbtu.medhack.main.MainPresenter;
import kz.kbtu.medhack.tracking.TrackingActivity;
import kz.kbtu.medhack.tracking.advice.AdvicePresenter;
import kz.kbtu.medhack.tracking.advice.AdviceFragment;
import kz.kbtu.medhack.tracking.map.MapTrackFragment;
import kz.kbtu.medhack.tracking.map.MapTrackPresenter;
import kz.kbtu.medhack.user_info.UserInfoActivity;
import kz.kbtu.medhack.user_info.UserInfoPresenter;
import kz.kbtu.medhack.utils.dagger.modules.AppModule;
import kz.kbtu.medhack.utils.dagger.modules.NetModule;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */


@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(UserInfoActivity activity);
    void inject(TrackingActivity activity);

    void inject(LoginFragment fragment);
    void inject(AdviceFragment fragment);
    void inject(MapTrackFragment fragment);

    LoginPresenter loginPresenter();
    AdvicePresenter advicePresenter();
    MapTrackPresenter mapTrackPresenter();
    UserInfoPresenter userInfoPresenter();
    MainPresenter mainPresenter();


}
