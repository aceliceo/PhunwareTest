package adalberto.com.phunwaretest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class PhunwareUtils {
    public static boolean hasNetworkConnection(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo;

        if(connectivityManager != null) {
            netInfo = connectivityManager.getAllNetworkInfo();

            if (netInfo != null) {
                for (NetworkInfo ni : netInfo) {
                    if (ni.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
