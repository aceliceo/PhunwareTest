package adalberto.com.phunwaretest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adalberto.com.phunwaretest.R;
import adalberto.com.phunwaretest.activities.VenuesActivity;
import adalberto.com.phunwaretest.model.Venue;

public class VenuesAdapter extends ArrayAdapter<Venue> {
    private Context mContext;
    private  ArrayList<Venue> mVenuesList;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private String mAddress;
    private boolean mEmptyAddress;
    private boolean mEmptyCity;

    public VenuesAdapter(Context context, int layoutId, ArrayList<Venue> list) {
        super(context, layoutId, list);
        this.mContext = context;
        this.mVenuesList = list;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mVenuesList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Venue venue = mVenuesList.get(position);
        mAddress = new String();
        mEmptyAddress = true;
        mEmptyCity = true;

        if(!venue.getAddress().isEmpty()){
            mEmptyAddress = false;
            mAddress += venue.getAddress();
        }
        if(!venue.getCity().isEmpty()){
            mEmptyCity = false;

            if(!mEmptyAddress){
                mAddress += ", ";
            }
            mAddress += venue.getCity();
        }
        if(!venue.getState().isEmpty()){
            if(!mEmptyCity){
                mAddress += ", ";
            }
            mAddress += venue.getState();
        }

        if(convertView == null){
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(mLayoutId, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.tv_name)).setText(venue.getName());
        ((TextView)convertView.findViewById(R.id.tv_description)).setText(mAddress);

        convertView.findViewById(R.id.ly_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetails(venue);
            }
        });

        return convertView;
    }

    private void goToDetails(Venue venue){
        try {
            ((VenuesActivity) mContext).viewDetails(venue);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Error loading details", Toast.LENGTH_SHORT).show();
        }
    }
}
