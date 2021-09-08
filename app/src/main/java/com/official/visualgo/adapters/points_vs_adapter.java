package com.official.visualgo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.official.visualgo.R;

import java.util.List;

public class points_vs_adapter extends RecyclerView.Adapter<points_vs_adapter.ViewHolder> {

    private Context context;
    private List<Integer> values;
    int i,j;



    public points_vs_adapter(Context context, List<Integer> uploads,int i,int j) {
        this.values = uploads;
        this.context = context;
        this.i=i;
        this.j=j;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.points_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.textView.setText(values.get(position).toString());

        if(position==i&& i>-1){
            holder.textView.setTextColor(context.getResources().getColor(R.color.yellow));
        }
        if(position==j&& j>-1){
            holder.textView.setTextColor(context.getResources().getColor(R.color.red));

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
            textView=itemView.findViewById(R.id.point);
            this.item = itemView;

        }


    }

}
