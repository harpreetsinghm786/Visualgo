package com.official.visualgo.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.official.visualgo.MainActivity;
import com.official.visualgo.R;

import java.util.List;

public class graph_vs_adapter extends RecyclerView.Adapter<graph_vs_adapter.ViewHolder> {

    private Context context;
    private List<Integer> values;
    int max=0;
    int i,j;
    boolean aBoolean;
    public graph_vs_adapter(Context context, List<Integer> uploads,boolean aBoolean,int i,int j) {
        this.values = uploads;
        this.context = context;
        this.aBoolean=aBoolean;
        this.i=i;
        this.j=j;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.graph_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        for(int i=0;i<values.size();i++){
            if(values.get(i)>max){
                max=values.get(i);
            }
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        if(aBoolean==false){
        double a=((double)values.get(position)/max)*100;

        ObjectAnimator.ofInt(holder.progressBar, "progress",(int) a)
                .setDuration(900)
                .start();
        }else{

                final double a=((double)values.get(position)/max)*100;
                holder.progressBar.setProgress((int) a);

            if(position==j){
                holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.iteration_prog_grad));
                ObjectAnimator.ofInt(holder.progressBar,"progress",values.get(j));
            }

            if(position==i){

                holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.shift_prog_grad));
                ObjectAnimator.ofInt(holder.progressBar,"progress",values.get(i));
            }


        }



    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;


        View item;

        public ViewHolder(final View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.array_progress);

            this.item = itemView;

        }


    }


}
