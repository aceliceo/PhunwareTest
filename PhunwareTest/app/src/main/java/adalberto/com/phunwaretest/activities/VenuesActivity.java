package adalberto.com.phunwaretest.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import adalberto.com.phunwaretest.R;
import adalberto.com.phunwaretest.fragments.VenueDetailsFragment;
import adalberto.com.phunwaretest.fragments.VenuesListFragment;
import adalberto.com.phunwaretest.model.Venue;

public class VenuesActivity extends ActionBarActivity {
    private VenueDetailsFragment mVenueDetailsFragment;
    private Venue mSelectedVenue;
    private Intent mShareIntent;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venues_activity);

        prepareActionBar();
        loadVenuesListFragment();

        if (findViewById(R.id.venueDetailsFragment) != null) {
            loadVenueDetailsFragment();
        }
    }

    private void prepareActionBar(){
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.icon_spacer);
        menu.setDisplayUseLogoEnabled(true);
        menu.setTitle(getString(R.string.sample_app));
    }

    private void loadVenuesListFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.venuesListFragment, new VenuesListFragment()).commit();
    }

    private void loadVenueDetailsFragment(){
        mVenueDetailsFragment = new VenueDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.venueDetailsFragment, mVenueDetailsFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mVenueDetailsFragment != null){
            getMenuInflater().inflate(R.menu.menu_details, menu);
            MenuItem item = menu.findItem(R.id.menu_item_share);

            mShareIntent = new Intent();
            mShareIntent.setAction(Intent.ACTION_SEND);
            mShareIntent.setType("text/plain");
            mShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Phunware");
            mShareIntent.putExtra(Intent.EXTRA_TEXT, "Select venue");

            mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
            mShareActionProvider.setShareIntent(mShareIntent);
        }else{
            return false;
        }

        return true;
    }

    public void viewDetails(Venue venue) {
        if (mVenueDetailsFragment == null) {
            Gson gson = new Gson();

            Intent intent = new Intent(this, VenueDetailsActivity.class);
            intent.putExtra("venue",  gson.toJson(venue));
            startActivity(intent);
        } else {
            mVenueDetailsFragment.updateDetails(VenuesActivity.this, venue);

            mShareIntent.putExtra(Intent.EXTRA_SUBJECT, venue.getName());
            mShareIntent.putExtra(Intent.EXTRA_TEXT, venue.getName() + ", " + venue.getAddress());
        }
    }
}
