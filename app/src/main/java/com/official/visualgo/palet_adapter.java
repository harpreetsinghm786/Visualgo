package com.official.visualgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class palet_adapter extends RecyclerView.Adapter<palet_adapter.ViewHolder> {

    private Context context;
    private List<inputlist> values;



    public palet_adapter(Context context, List<inputlist> uploads) {
        this.values = uploads;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.input_palet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        inputlist inputlist=values.get(position);

        if(inputlist.getCommand().equals("")){
            holder.command.setVisibility(View.GONE);
        }else{
            holder.command.setText(inputlist.getCommand());
        }
        if(inputlist.getInstruction().equals("")){
            holder.instruction.setVisibility(View.GONE);
        }else{
        holder.instruction.setText(inputlist.getInstruction());
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView instruction,command;
        View item;

        public ViewHolder(final View itemView) {

            super(itemView);
            instruction=itemView.findViewById(R.id.instruction);
            command=itemView.findViewById(R.id.command);
            this.item = itemView;

        }


    }

}
