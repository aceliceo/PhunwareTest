package adalberto.com.phunwaretest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adalberto.com.phunwaretest.R;
import adalberto.com.phunwaretest.adapters.VenuesAdapter;
import adalberto.com.phunwaretest.interfaces.TaskListener;
import adalberto.com.phunwaretest.model.Venue;
import adalberto.com.phunwaretest.tasks.GetVenuesRequest;
import adalberto.com.phunwaretest.utils.PhunwareUtils;

public class VenuesListFragment extends Fragment {
    private ListView mVenuesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venues_fragment, container, false);

        mVenuesListView = (ListView)view.findViewById(R.id.venuesList);
        downloadVenues();

        return view;
    }

    private void downloadVenues(){
        if(PhunwareUtils.hasNetworkConnection(getActivity())){
            GetVenuesRequest venuesRequest = new GetVenuesRequest(getActivity());
            venuesRequest.makeJsonArrayRequest(venuesTaskListener);
        } else{
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private TaskListener<ArrayList<Venue>> venuesTaskListener = new TaskListener<ArrayList<Venue>>() {
        @Override
        public void onTaskSuccess(ArrayList<Venue> result) {
            mVenuesListView.setAdapter(new VenuesAdapter(getActivity(), R.layout.item_row_venue, result));
        }

        @Override
        public void onTaskFailure(Exception e) {
            Toast.makeText(getActivity(), "Failed to download content", Toast.LENGTH_LONG).show();
        }
    };
}