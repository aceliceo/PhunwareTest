package adalberto.com.phunwaretest.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import adalberto.com.phunwaretest.R;
import adalberto.com.phunwaretest.model.ScheduleItem;
import adalberto.com.phunwaretest.model.Venue;

public class VenueDetailsFragment extends Fragment {
    private LinearLayout mSchedulesContainer;
    private ImageView mVenueImage;
    private TextView mVenueName;
    private TextView mVenueAddress1;
    private TextView mVenueAddress2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_details_fragment, container, false);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        String jsonVenue;
        Venue venue;

        mSchedulesContainer = (LinearLayout)view.findViewById(R.id.schedulesContainer);
        mVenueImage = (ImageView)view.findViewById(R.id.venueImage);
        mVenueName = (TextView)view.findViewById(R.id.venueName);
        mVenueAddress1 = (TextView)view.findViewById(R.id.venueAddress1);
        mVenueAddress2 = (TextView)view.findViewById(R.id.venueAddress2);

        if(bundle != null){
            jsonVenue = bundle.getString("venue");

            if(!jsonVenue.isEmpty()){
                venue = gson.fromJson(jsonVenue, Venue.class);
                updateDetails(getActivity(), venue);
            }
        }

        return view;
    }

    public void updateDetails(Context context, Venue venue){
        if(context != null && mVenueImage != null) {
            if(venue.getImageUrl().isEmpty()){
                mVenueImage.setImageDrawable(null);
                mVenueImage.setBackgroundResource(R.drawable.no_image_available);
            }else {
                mVenueImage.setBackground(null);
                Picasso.with(context).load(venue.getImageUrl()).into(mVenueImage);
            }
        }

        mVenueName.setText(venue.getName());
        mVenueAddress1.setText(venue.getAddress());
        mVenueAddress2.setText(venue.getCity() + ", " + venue.getState() + " " + venue.getZip());
        mSchedulesContainer.removeAllViews();

        if(venue.getSchedule().size() > 0){
            TextView tv_schedule;
            int counter = 1;
            String FORMAT_START = "EEEE MM/dd h:mm a";
            String FORMAT_END = "hh:mm a";
            SimpleDateFormat sdfStart;
            SimpleDateFormat sdfEnd;
            String textDateStart;
            String textDateEnd;

            for(ScheduleItem scheduleItem: venue.getSchedule()){
                sdfStart = new SimpleDateFormat(FORMAT_START);
                textDateStart = sdfStart.format(scheduleItem.getStartDate());

                sdfEnd = new SimpleDateFormat(FORMAT_END);
                textDateEnd = sdfEnd.format(scheduleItem.getEndDate());

                tv_schedule = new TextView(context);
                tv_schedule.setText(textDateStart + " to " + textDateEnd);
                tv_schedule.setId(counter);
                tv_schedule.setTextSize(12);
                tv_schedule.setTextColor(Color.parseColor("black"));
                tv_schedule.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                counter++;

                mSchedulesContainer.addView(tv_schedule);
            }
        }
    }
}
