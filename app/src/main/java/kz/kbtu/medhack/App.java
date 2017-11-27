package kz.kbtu.medhack;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import kz.kbtu.medhack.utils.dagger.components.DaggerNetComponent;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;
import kz.kbtu.medhack.utils.dagger.modules.AppModule;
import kz.kbtu.medhack.utils.dagger.modules.NetModule;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this, this))
                .netModule(new NetModule())
                .build();

    }


    public  NetComponent getNetComponent() {
        return mNetComponent;
    }
}
