package adalberto.com.phunwaretest.tasks;

import android.content.Context;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adalberto.com.phunwaretest.interfaces.TaskListener;
import adalberto.com.phunwaretest.model.ScheduleItem;
import adalberto.com.phunwaretest.model.Venue;
import adalberto.com.phunwaretest.utils.PhunwareConstants;

public class GetVenuesRequest {
    private Context mContext;

    public GetVenuesRequest(Context context){
        mContext = context;
    }

    public void makeJsonArrayRequest(final TaskListener listener) {
        PhunwareRequestQueue requestQueue = new PhunwareRequestQueue();

        JsonArrayRequest request = new JsonArrayRequest(PhunwareConstants.VENUES_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Venue> venues = new ArrayList<Venue>();
                            ArrayList<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();
                            Venue venue;
                            ScheduleItem scheduleItem;
                            JSONObject entry;
                            long id;
                            int pCode;
                            double latitude;
                            double longitude;
                            String name;
                            String address;
                            String city;
                            String state;
                            String zip;
                            String phone;
                            String tollFreePhone;
                            String description;
                            String ticketLink;
                            String imageUrl;
                            Date startDate;
                            Date endDate;
                            JSONArray schedules;

                            for (int i = 0; i < response.length(); ++i)
                            {
                                entry = (JSONObject)response.get(i);
                                id = Long.parseLong(entry.getString("id"));
                                pCode = Integer.parseInt(entry.getString("pcode"));
                                latitude = Double.parseDouble(entry.getString("latitude"));
                                longitude = Double.parseDouble(entry.getString("longitude"));
                                name = entry.getString("name");
                                address = entry.getString("address");
                                city = entry.getString("city");
                                state = entry.getString("state");
                                zip = entry.getString("state");
                                phone = entry.getString("phone");
                                tollFreePhone = entry.getString("tollfreephone");
                                description = entry.getString("description");
                                ticketLink = entry.getString("ticket_link");
                                imageUrl = entry.getString("image_url");

                                scheduleItems = new ArrayList<ScheduleItem>();
                                schedules = entry.getJSONArray("schedule");

                                for(int s = 0; s < schedules.length(); s++){
                                    entry = (JSONObject)schedules.get(s);

                                    startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(entry.getString("start_date"));
                                    endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").parse(entry.getString("end_date"));

                                    scheduleItem = new ScheduleItem();
                                    scheduleItem.setStartDate(startDate);
                                    scheduleItem.setEndDate(endDate);

                                    scheduleItems.add(scheduleItem);
                                }

                                venue = new Venue();
                                venue.setId(id);
                                venue.setPcode(pCode);
                                venue.setLatitude(latitude);
                                venue.setLongitude(longitude);
                                venue.setName(name);
                                venue.setAddress(address);
                                venue.setCity(city);
                                venue.setState(state);
                                venue.setZip(zip);
                                venue.setPhone(phone);
                                venue.setTollFreePhone(tollFreePhone);
                                venue.setDescription(description);
                                venue.setTicketLink(ticketLink);
                                venue.setImageUrl(imageUrl);
                                venue.setSchedule(scheduleItems);

                                venues.add(venue);
                            }

                            listener.onTaskSuccess(venues);
                        }catch(Exception ex){
                            listener.onTaskFailure(ex);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onTaskFailure(error);
            }
        });

        requestQueue.addToRequestQueue(mContext, request);
    }
}
