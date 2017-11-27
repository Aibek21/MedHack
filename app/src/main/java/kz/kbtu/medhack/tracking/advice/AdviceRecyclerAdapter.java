package kz.kbtu.medhack.tracking.advice;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kbtu.medhack.R;
import kz.kbtu.medhack.models.Advice;

/**
 * Created by aibekkuralbaev on 28.05.17.
 */

public class AdviceRecyclerAdapter  extends RecyclerView.Adapter<AdviceRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    Typeface mTypeface;
    private List<Advice> mLogList;


    public AdviceRecyclerAdapter(Context context, List<Advice> logList) {
        mContext = context;
        mLogList = logList;
        //mTypeface = Typeface.createFromAsset(context.getAssets(), Keys.MATERIAL_FONT);
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_advice_item, parent, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setPosition(position);


        if (position%2==0) {
            holder.adviceText.setText("Ложитесь и выпейте воды");
            holder.mTime.setText("12:05");
        }else{
            holder.adviceText.setText("Убедиться, что при оказании первой помощи вам ничего не угрожает и вы не подвергаете себя опасности.");
            holder.mTime.setText("12:11");
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        Context mContext;
        int position;

        @BindView(R.id.time)
        TextView mTime;

        @BindView(R.id.advice_text)
        TextView adviceText;


        public ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.bind(this, view);
            mContext = context;
        }


        public void setPosition(int position) {
            this.position = position;
        }

    }
}
