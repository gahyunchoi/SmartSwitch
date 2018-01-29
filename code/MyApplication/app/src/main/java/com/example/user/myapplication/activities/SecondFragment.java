package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Adib on 13-Apr-17.
 */
public class SecondFragment extends Fragment{
    final String ip = "192.168.43.123";

    private OnFragmentInteractionListener listener;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_second, container, false);
        LinearLayout Outlinearlayout = (LinearLayout)rootView.findViewById(R.id.second);


        //Some url endpoint that you may have
        String getdevice_Url = "http://"+ip+":3000/api/getDevices";
        //String to place our getdevice_result in
        String getdevice_result = null;
        //Instantiate new instance of our class
        RestOperation getRequest = new RestOperation();
        //Perform the doInBackground method, passing in our url
        try {
            getdevice_result = getRequest.execute("GET",getdevice_Url).get();
            //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("RESTAPI","Result:"+e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("RESTAPI","Result:"+e);
        }

        try {
            JSONArray json_array = new JSONArray(getdevice_result);

            for (int i=0; i<json_array.length(); i++) {
                JSONObject device = json_array.getJSONObject(i);
                final String deviceName = device.getString("deviceName");
                final String deviceIP = device.getString("deviceIP");
                int status = device.getInt("currentStatus");

                int humidity = device.getInt("Humidity");
                int temp = device.getInt("Temp");
                int motion = device.getInt("Motion");
                int elec = device.getInt("Elec");


                Log.e("SECOND - RESTAPI","Result"+i+":"+deviceName);
                Log.e("SECOND - RESTAPI","Result"+i+":"+humidity);
                Log.e("SECOND - RESTAPI","Result"+i+":"+temp);
                Log.e("SECOND - RESTAPI","Result"+i+":"+motion);
                Log.e("SECOND - RESTAPI","Result"+i+":"+elec);
//                Log.e("RESTAPI","Result"+i+":"+deviceName);
                LinearLayout linearlayout = new LinearLayout(getContext());
                linearlayout.setPadding(20,20,0,10);


                TextView tv = new TextView(getContext());
                tv.setTextSize(50);
                tv.setText(deviceName+"방의 현재 온도:");

                TextView tv2 = new TextView(getContext());
                tv2.setTextSize(50);
                tv2.setText(Integer.toString(temp));

                TextView and = new TextView(getContext());
                and.setTextSize(50);
                and.setText(" & ");

                TextView tv3 = new TextView(getContext());
                tv3.setTextSize(50);
                tv3.setText("습도:");

                TextView tv4 = new TextView(getContext());
                tv4.setTextSize(50);
                tv4.setText(Integer.toString(humidity));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,20,0);

                linearlayout.addView(tv,lp);
                linearlayout.addView(tv2,lp);
                linearlayout.addView(and,lp);
                linearlayout.addView(tv3,lp);
                linearlayout.addView(tv4,lp);


//                ListView listview = new ListView(getContext());
//                String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//                        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//                        "Android", "iPhone", "WindowsMobile" };
//
//
//
//                //Some url endpoint that you may have
//                String getdevice_Url2 = "http://"+ip+":3000/api/getTempFromDeviceName/jai";
//                //String to place our getdevice_result in
//                String getdevice_result2 = null;
//                //Instantiate new instance of our class
//                RestOperation getRequest2 = new RestOperation();
//                //Perform the doInBackground method, passing in our url
//                try {
//                    getdevice_result2 = getRequest.execute("GET",getdevice_Url2).get();
//                    //Log.e("CHECK",getdevice_result);
//                    Log.e("CHART-RESTAPI","Result:"+getdevice_result2.substring(1,getdevice_result2.length()-1));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.e("CHART-RESTAPI","Result:"+e);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                    Log.e("CHART-RESTAPI","Result:"+e);
//                }
//                final ArrayList<String> list = new ArrayList<String>();
//                try {
//                    JSONArray json_array2 = new JSONArray(getdevice_result);
//
//                    for (int j = 0; j < json_array.length(); j++) {
//                        JSONObject device2 = json_array.getJSONObject(i);
//                        Date Date = device2.get("changedTime");
//                        String temp2 = device2.getString("Temp");
//                        Log.e("CHART-RESTAPI", "Result:" + i + "," + temp2);
//                        list.add(temp2);
//                    }
//
//                }
//                final StableArrayAdapter adapter = new StableArrayAdapter(getContext(),android.R.layout.simple_list_item_1, list);
//                listview.setAdapter(adapter);
//
//                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, final View view,
//                                            int position, long id) {
//                        final String item = (String) parent.getItemAtPosition(position);
//                        view.animate().setDuration(2000).alpha(0)
//                                .withEndAction(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        list.remove(item);
//                                        adapter.notifyDataSetChanged();
//                                        view.setAlpha(1);
//                                    }
//                                });
//                    }
//
//                });

                linearlayout.setGravity(Gravity.CENTER_VERTICAL);
                Outlinearlayout.addView(linearlayout);
            }
//                        result_json = (String) json2.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        for(int i = 0; i < ButtonNumber; i++){
//
//            LinearLayout linearlayout = new LinearLayout(this);
//            linearlayout.setPadding(20,20,0,10);
//
//
//            TextView tv = new TextView(this);
//            tv.setText("Switch"+i);
//
//
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0,0,20,0);
//
//            linearlayout.addView(tv,lp);
//
//            final com.mahfa.dnswitch.DayNightSwitch switForDevice = new com.mahfa.dnswitch.DayNightSwitch(this); // or create xml view for button and get it with findViewById
//            switForDevice.setLayoutParams(new RelativeLayout.LayoutParams(152, 80));
//            switForDevice.setId(i);
//            switForDevice.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    if(switForDevice.isNight()){
//                        switForDevice.setIsNight(false);
//
//                    }
//                    else{
//                        switForDevice.setIsNight(true);
//                    }
//                }
//            });
//            linearlayout.addView(switForDevice);
//            linearlayout.setGravity(Gravity.CENTER_VERTICAL);
//            Outlinearlayout.addView(linearlayout);
//        }
        return rootView;
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }

}