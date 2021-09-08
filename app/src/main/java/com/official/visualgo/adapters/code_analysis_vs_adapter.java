package com.official.visualgo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.official.visualgo.R;

import java.util.List;

public class code_analysis_vs_adapter extends RecyclerView.Adapter<code_analysis_vs_adapter.ViewHolder> {

    private Context context;
    private List<String> values;
    int id;



    public code_analysis_vs_adapter(Context context, List<String> uploads, int id) {
        this.values = uploads;
        this.context = context;
        this.id=id;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.code_analysis_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.textView.setText(values.get(position).toString());


        if(id==1){
            if(position==1 ||position==3){
                holder.textView.setTextColor(context.getResources().getColor(R.color.black));
                holder.textView.setBackgroundColor(context.getResources().getColor(R.color.red));

            }
        }else if(id==2){
            if(position==2){
                holder.textView.setTextColor(context.getResources().getColor(R.color.black));
                holder.textView.setBackgroundColor(context.getResources().getColor(R.color.red));

            }

           }else if(id==-1){
            holder.textView.setTextColor(context.getResources().getColor(R.color.grey));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.black));

        }


    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View item;

        public ViewHolder(final View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.codeline);
            this.item = itemView;

        }


    }

}
