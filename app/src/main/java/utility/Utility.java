package utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by c49 on 23/02/16.
 */
public class Utility {
    public static boolean isConnected(Context context) {
        /*NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        Log.e("","networkInfo==>"+networkInfo.isConnected());
        return networkInfo != null && networkInfo.isConnected();*/
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            Log.e("", "networkInfo==>" + networkInfo.isConnected());
            return networkInfo != null && networkInfo.isConnected();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


    }
}
