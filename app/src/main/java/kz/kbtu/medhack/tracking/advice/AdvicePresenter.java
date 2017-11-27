package kz.kbtu.medhack.tracking.advice;

import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;

import kz.kbtu.medhack.api.RequestHandler;
import kz.kbtu.medhack.models.Advice;
import kz.kbtu.medhack.utils.base.presenter.BaseRxLcePresenter;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class AdvicePresenter extends BaseRxLcePresenter<AdviceView, List<Advice>> {

    @Inject
    public AdvicePresenter(RequestHandler requestHandler, SharedPreferences sharedPreferences) {

    }

}
