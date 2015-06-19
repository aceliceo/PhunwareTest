package adalberto.com.phunwaretest.tasks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import adalberto.com.phunwaretest.utils.PhunwareConstants;

public class PhunwareRequestQueue {
    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Context context, Request<T> request){
        request.setTag(PhunwareConstants.VENUES_TAG);
        getRequestQueue(context).add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}