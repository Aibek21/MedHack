package kz.kbtu.medhack.tracking.advice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.medhack.App;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.models.Advice;
import kz.kbtu.medhack.utils.base.view.BaseLceFragment;
import kz.kbtu.medhack.utils.dagger.components.NetComponent;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class AdviceFragment extends BaseLceFragment<SwipeRefreshLayout, List<Advice>, AdviceView, AdvicePresenter> implements AdviceView, SwipeRefreshLayout.OnRefreshListener{

    public static AdviceFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AdviceFragment fragment = new AdviceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Inject
    Context mContext;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private NetComponent mNetComponent;

    private AdviceRecyclerAdapter mAdapter;
    private List<Advice> mOrderList = new ArrayList<>();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdviceRecyclerAdapter(mContext, mOrderList);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void injectDependencies() {
        mNetComponent = ((App) getActivity().getApplication()).getNetComponent();
        mNetComponent.inject(this);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_advice;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public AdvicePresenter createPresenter() {
        return mNetComponent.advicePresenter();
    }

    @Override
    public void setData(List<Advice> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
