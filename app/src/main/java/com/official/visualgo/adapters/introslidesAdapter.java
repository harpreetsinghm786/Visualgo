package com.official.visualgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.official.visualgo.R;
import com.official.visualgo.login;


public class introslidesAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private int layouts[];
    private Context context;
    Button start;
    public introslidesAdapter(int[] layouts, Context context) {
        this.layouts = layouts;
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

     @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view=layoutInflater.inflate(layouts[position],container,false);
         container.addView(view);
         if(position==2) {
             start = view.findViewById(R.id.start1);
             start.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(checkinternetconnected()){
                     context.startActivity(new Intent(context, login.class));
                     ((Activity)context).finish();}else{
                         Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                     }

                 }
             });
         }
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view=(View)object;
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    private boolean checkinternetconnected()
    {


        Boolean wificonnected=false;
        Boolean mobiledata=false;
        ConnectivityManager connMgr=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeinfo = connMgr.getActiveNetworkInfo();
        if(activeinfo!=null && activeinfo.isConnected())
        {
            wificonnected=activeinfo.getType()==ConnectivityManager.TYPE_WIFI;
            mobiledata=activeinfo.getType()==ConnectivityManager.TYPE_MOBILE;
            if(wificonnected||mobiledata)
            {return true;}
        }

        return false;


    }
}
