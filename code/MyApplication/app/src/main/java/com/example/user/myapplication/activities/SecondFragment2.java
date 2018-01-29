package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
/**
 * Created by Adib on 13-Apr-17.
 */
public class SecondFragment2 extends Fragment{
    final String ip = "192.168.43.123";

    private OnFragmentInteractionListener listener;

    public static SecondFragment2 newInstance() {
        return new SecondFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_second2, container, false);
        LinearLayout Outlinearlayout = (LinearLayout)rootView.findViewById(R.id.second);




        ArrayList<ArrayList<String>> DATA_TO_SHOW = new ArrayList<ArrayList<String>>();
        ArrayList<String> DATA_TO_INSERT = new ArrayList<String>();

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
                DATA_TO_INSERT = new ArrayList<String>();
                JSONObject device = json_array.getJSONObject(i);
                final String deviceName = device.getString("deviceName");
                final String deviceIP = device.getString("deviceIP");
                int status = device.getInt("currentStatus");

                int humidity = device.getInt("Humidity");
                int temp = device.getInt("Temp");
                int motion = device.getInt("Motion");
                int elec = device.getInt("Elec");
                DATA_TO_INSERT.add(deviceName);
                DATA_TO_INSERT.add(Integer.toString(status));
                DATA_TO_INSERT.add(Integer.toString(humidity));
                DATA_TO_INSERT.add(Integer.toString(temp));
                DATA_TO_INSERT.add(Integer.toString(motion));
                DATA_TO_INSERT.add(Integer.toString(elec));


                DATA_TO_SHOW.add(DATA_TO_INSERT);
                //Total six column


            }
            Log.e("FIRST",Integer.toString(DATA_TO_SHOW.size()));


            // Iterate over list
            String[][] DATA_TO_SHOW_ARRAY = new String[DATA_TO_SHOW.size()][6];
            String[] DATA_TO_INSERT_ARRAY = new String[6];
            int index_list = 0;
            int index = 0;
            for (ArrayList<String> data_list : DATA_TO_SHOW) {
                Log.e("OUTSIDE",Integer.toString(index_list));
                DATA_TO_INSERT_ARRAY = new String[6];
                index = 0;
                for(String data : data_list){
                    DATA_TO_INSERT_ARRAY[index] = data;
                    Log.e("INSIDE",data_list.get(index));
                    index++;
                }
                DATA_TO_SHOW_ARRAY[index_list] = DATA_TO_INSERT_ARRAY;
                index_list++;
            }


            String[] HEADER_STRING = {"DEV","ON","HUMID","TEMP","MOT","ELEC"};
            TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(getContext(), DATA_TO_SHOW_ARRAY));
            tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(), HEADER_STRING));

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