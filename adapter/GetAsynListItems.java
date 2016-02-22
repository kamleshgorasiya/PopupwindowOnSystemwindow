package com.matt.clicksendenlist.endless.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.matt.clicksendenlist.android_interface.IItemsReadyListener;
import com.matt.clicksendenlist.utils.AppConstance;
import com.matt.clicksendenlist.utils.Utility;
import com.matt.clicksendenlist.ws.helper.WebserviceConnector;
import com.matt.clicksendenlist.ws.helper.model.Contact;
import com.matt.clicksendenlist.ws.helper.model.ListResponseHandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by c49 on 03/02/16.
 */
public class GetAsynListItems extends AsyncTask<Void, Void, Object> {
    IItemsReadyListener listener;

    /*
     * The point from where to start counting. In a real
     * life scenario this could be a pagination number
     */
    String url;
    Context context;
    Exception exception=null;

    protected GetAsynListItems(Context context,IItemsReadyListener listener, String url) {
        this.listener=listener;
        this.url=url;
        this.context=context;
        Log.e("     ===>", "url  =>"+url);
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object responseObject=null;

        try {

            /**
             * this is for reachability check to the server.
             */
            URL checkingUrl = new URL(AppConstance.BASE_URL);
            HttpURLConnection urlc = (HttpURLConnection) checkingUrl.openConnection();
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(2000); // Timeout 2 seconds.
            urlc.setReadTimeout(2000);
            urlc.connect();
           boolean isNetworkConnected = urlc.getResponseCode() == 200; //Successful response.
           // Utility.showToast(context, "Status code" + urlc.getResponseCode());
            if (isNetworkConnected) {
                responseObject = new WebserviceConnector(url, context).execute(ListResponseHandler.class, null, AppConstance.METHOD_GET);
            }
        }catch(Exception e){
            e.printStackTrace();
            exception=e;
        }
        return responseObject;
    }

    @Override
    protected void onPostExecute(Object result) {
        listener.onItemsReady(result,exception);
    }
}