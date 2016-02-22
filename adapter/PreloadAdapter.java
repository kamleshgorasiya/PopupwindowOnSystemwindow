package com.matt.clicksendenlist.endless.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;

import com.matt.clicksendenlist.R;
import com.matt.clicksendenlist.android_interface.IItemsReadyListener;
import com.matt.clicksendenlist.utils.AppConstance;
import com.matt.clicksendenlist.ws.helper.model.Contact;
import com.matt.clicksendenlist.ws.helper.model.ListResponseHandler;

import java.util.ArrayList;

/**
 * Created by c49 on 02/02/16.
 */
public class PreloadAdapter extends EndlessAdapter implements
        IItemsReadyListener {

    private Context activity;
    private ArrayList<Contact> contacts;
    private String url="";
    private int totalCount=0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LayoutInflater li ;
    IItemsReadyListener iItemsReadyListener;
    public PreloadAdapter(ArrayList<Contact> contacts,Context activity,String url,SwipeRefreshLayout mSwipeRefreshLayout,IItemsReadyListener iItemsReadyListener) {
        super(new ArrayAdapter<Contact>(activity, R.layout.row,
                android.R.id.text1, contacts));
        this.activity=activity;
        this.contacts=contacts;
        this.url=url;
        this.mSwipeRefreshLayout=mSwipeRefreshLayout;
        this.iItemsReadyListener=iItemsReadyListener;
        li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected View getPendingView(ViewGroup parent) {
        View row=li.inflate(R.layout.row, null);
        //row.setVisibility(View.INVISIBLE);
        /*Animation animation = AnimationUtils.loadAnimation(this.activity, R.anim.up_from_bottom);
        row.startAnimation(animation);*/
        return(row);
    }

    @Override
    protected boolean cacheInBackground() throws Exception {
        Log.e("   cacheInBackground()", "contact size " + contacts.size() + "totalCount " + totalCount + "url " + url);
        Log.e("   cacheInBackground()", "totalCount==0 " + (totalCount == 0) + " contacts.size()<totalCount " + (contacts.size() < totalCount) + "url " + url);

        if(url!=null) {
            mSwipeRefreshLayout.setRefreshing(true);
            new GetAsynListItems(activity, this, url + AppConstance.GET_LIST_DATA_BY_ID_DESC).execute();

        }
        if(totalCount==-1)
            return false;
        if(totalCount==0){
           return true;

        }
        return  (contacts.size()<totalCount);
      /*  if(contacts.size()<totalCount){

            return true;
        }

        return false;*/
    }

    @Override
    public void onItemsReady(Object result,Exception exceptionc) {

        //mSwipeRefreshLayout.setRefreshing(false);
        if(exceptionc!=null) {
            iItemsReadyListener.onItemsReady(result, exceptionc);

            if(contacts.size()>=0)
                mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
        }
        try {
            if(result!=null){
                ListResponseHandler data=(ListResponseHandler)result;

            this.contacts.addAll(data.getListDetailAndList().getContactList());
            this.totalCount=data.getListDetailAndList().getTotal();
                if(totalCount==0)
                totalCount=-1;
                if(contacts.size()==totalCount){
                    //mSwipeRefreshLayout.setRefreshing(false);
                    totalCount=-1;
                }

                this.url=data.getListDetailAndList().getNextPageUrl();
                Log.e("   onReady()","contact size "+contacts.size()+"totalCount "+totalCount+"url " +url);
                super.onDataReady();

                iItemsReadyListener.onItemsReady(result, exceptionc);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void appendCachedData() {

    }


}
