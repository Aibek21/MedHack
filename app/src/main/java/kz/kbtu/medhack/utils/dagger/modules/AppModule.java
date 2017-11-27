package kz.kbtu.medhack.utils.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.kbtu.medhack.models.User;

/**
 * Created by aibekkuralbaev on 05.10.16.
 */

@Module
public class AppModule {

    Application mApplication;
    Context mContext;

    public AppModule(Application application, Context context) {
        mApplication = application;
        mContext = context;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mContext;
    }


    @Provides @Singleton
    public ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    @Singleton @Provides
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }


    @Provides
    @Nullable
    public User providesUser(SharedPreferences sharedPreferences, Gson gson) {
        String savedValue = sharedPreferences.getString(User.class.getName(), "");
        if (savedValue.equals("")) {
            return null;
        } else {
            Type type = new TypeToken<User>() {
            }.getType();
            return gson.fromJson(savedValue, type);
        }
    }


//    @Provides
//    @Singleton
//    public ProgressDialog providesProgressDialog(Context context) {
//        ProgressDialog progressDialog = new ProgressDialog(context, R.style.DialogTheme);
//        progressDialog.setCancelable(false);
//        return  progressDialog;
//    }


}