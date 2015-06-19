package adalberto.com.phunwaretest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import adalberto.com.phunwaretest.R;
import adalberto.com.phunwaretest.fragments.VenueDetailsFragment;
import adalberto.com.phunwaretest.model.Venue;

public class VenueDetailsActivity extends ActionBarActivity {
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String jsonVenue = new String();
        Gson gson = new Gson();
        Venue venue;

        if (extras != null) {
            jsonVenue = extras.getString("venue");

            if(!jsonVenue.isEmpty()){
                displayFragment(jsonVenue);
            }
        }

        venue = gson.fromJson(jsonVenue, Venue.class);

        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_SUBJECT, venue.getName());
        mShareIntent.putExtra(Intent.EXTRA_TEXT, venue.getName() + ", " + venue.getAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider share = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
        share.setShareIntent(mShareIntent);

        return true;
    }

    private void displayFragment(String jsonVenue){
        VenueDetailsFragment venueDetailsFragment = new VenueDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("venue", jsonVenue);
        venueDetailsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.venueDetailsFragment, venueDetailsFragment).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
