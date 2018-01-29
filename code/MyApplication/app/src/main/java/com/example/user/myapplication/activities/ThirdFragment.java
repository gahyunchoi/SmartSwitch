package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Adib on 13-Apr-17.
 */
public class ThirdFragment extends Fragment {

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    final String ip = "192.168.43.123";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_third, container, false);


        final TimePicker StartTimePicker = (TimePicker) rootView.findViewById(R.id.timePickerStart);
        final TimePicker EndTimePicker = (TimePicker) rootView.findViewById(R.id.timePickerEnd);


        //Some url endpoint that you may have
        String getdevice_Url = "http://" + ip + ":3000/api/getDevices";
        //String to place our getdevice_result in
        String getdevice_result = null;
        //Instantiate new instance of our class
        RestOperation getRequest = new RestOperation();
        //Perform the doInBackground method, passing in our url
        try {
            getdevice_result = getRequest.execute("GET", getdevice_Url).get();
            //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("RESTAPI", "Result:" + e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("RESTAPI", "Result:" + e);
        }
        try {
            JSONArray json_array = new JSONArray(getdevice_result);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < json_array.length(); i++) {
                final JSONObject device = json_array.getJSONObject(i);
                final String deviceName = device.getString("deviceName");
                list.add(deviceName);
            }

            final Spinner spinner = new Spinner(getContext());

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

            LinearLayout Outlinearlayout = (LinearLayout) rootView.findViewById(R.id.SelectDevice);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 20, 0);


            Outlinearlayout.setPadding(20, 20, 0, 10);
            Outlinearlayout.addView(spinner, lp);
            Outlinearlayout.setGravity(Gravity.CENTER_VERTICAL);

            Button button = new Button(getContext());

            button.setText("ADD");
            button.setOnClickListener(new OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    Log.e("LISTVALUE", String.valueOf(spinner.getSelectedItem()));
                    Log.e("TIMESTART", StartTimePicker.getHour() + "," + StartTimePicker.getMinute());
                    Log.e("TIMESTART", EndTimePicker.getHour() + "," + EndTimePicker.getMinute());
                    //
                    String addAlarm_Url = "http://" + ip + ":3000/api/addAlarm/" + spinner.getSelectedItem();
                    //String to place our getdevice_result in
                    String addAlarm_result = null;
                    //Instantiate new instance of our class
                    RestOperation addAlarmRequest = new RestOperation();
                    //Perform the doInBackground method, passing in our url
                    try {
                        addAlarm_result = addAlarmRequest.execute("POSTALARM", addAlarm_Url, Integer.toString(StartTimePicker.getHour()), Integer.toString(StartTimePicker.getMinute()), Integer.toString(EndTimePicker.getHour()), Integer.toString(EndTimePicker.getMinute())).get();
                        Log.e("RESTAPI-POST", "Result3:" + addAlarm_result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }

            });
            Outlinearlayout.addView(button, lp);


            //Some url endpoint that you may have
            String getdevice_Url2 = "http://" + ip + ":3000/api/getAlarm";
            //String to place our getdevice_result in
            String getdevice_result2 = null;
            //Instantiate new instance of our class
            RestOperation getRequest2 = new RestOperation();
            //Perform the doInBackground method, passing in our url
            try {
                getdevice_result2 = getRequest2.execute("GET", getdevice_Url2).get();
                //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("RESTAPI", "Result:" + e);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.e("RESTAPI", "Result:" + e);
            }
            try {
                JSONArray json_array2 = new JSONArray(getdevice_result2);
                List<String> list2 = new ArrayList<String>();
                for (int i = 0; i < json_array2.length(); i++) {
                    final JSONObject device = json_array2.getJSONObject(i);
                    final String deviceName = device.getString("deviceName");
                    final String startalarmhour = device.getString("StartAlarmHour");
                    final String startalarmminute = device.getString("StartAlarmMinute");
                    final String endalarmhour = device.getString("EndAlarmHour");
                    final String endalarmminute = device.getString("EndAlarmMinute");

                    list2.add(deviceName + " / " + startalarmhour + ":" + startalarmminute + "~" + endalarmhour + ":" + endalarmminute);
                }

                final Spinner spinner2 = new Spinner(getContext());

                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list2);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(dataAdapter2);

                LinearLayout Outlinearlayout2 = (LinearLayout) rootView.findViewById(R.id.DeleteAlarm);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.setMargins(0, 0, 20, 0);


                Outlinearlayout2.setPadding(20, 20, 0, 10);
                Outlinearlayout2.addView(spinner2, lp2);
                Outlinearlayout2.setGravity(Gravity.CENTER_VERTICAL);

                Button button2 = new Button(getContext());

                button2.setText("DELETE");
                button2.setOnClickListener(new OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        //
                        String selected_device_name = spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf("/")).trim();
                        String selected_start_time = spinner2.getSelectedItem().toString().substring(spinner2.getSelectedItem().toString().indexOf("/") + 1, spinner2.getSelectedItem().toString().indexOf("~")).trim();
                        String selected_start_Hour = selected_start_time.substring(0,selected_start_time.indexOf(":"));
                        String selected_start_Min = selected_start_time.substring(selected_start_time.indexOf(":")+1,selected_start_time.length());

                        String selected_end_time = spinner2.getSelectedItem().toString().substring(spinner2.getSelectedItem().toString().indexOf("~") + 1, spinner2.getSelectedItem().toString().length()).trim();
                        String selected_end_Hour = selected_end_time.substring(0,selected_end_time.indexOf(":"));
                        String selected_end_Min = selected_end_time.substring(selected_end_time.indexOf(":")+1,selected_end_time.length());

                        Log.e("RESULT333", "1" + selected_device_name + "," + "2" + selected_start_time + "," + "3" + selected_end_time);
                        Log.e("RESULT444", "1" + selected_device_name + "," + "2:" + selected_start_Hour + "," + "3:" + selected_start_Min);
                        Log.e("RESULT555", "1" + selected_device_name + "," + "2:" + selected_end_Hour + "," + "3:" + selected_end_Min);

                        String Delete_Alarm_Url = "http://" + ip + ":3000/api/DeleteAlarm/" + selected_device_name;
                        String Delete_Alarm_result = null;
                        RestOperation DeleteAlarmRequest = new RestOperation();
                        try {
                            Delete_Alarm_result = DeleteAlarmRequest.execute("DELETEALARM", Delete_Alarm_Url, selected_start_Hour,selected_start_Min,selected_end_Hour,selected_end_Min).get();
                            Log.e("RESTAPI-POST", "Result3:" + Delete_Alarm_result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

//                        ThirdFragment ni = (ThirdFragment) getFragmentManager().findFragmentByTag("thirdfragment");
//                        ni.();

                    }

                });
                Outlinearlayout2.addView(button2, lp);


            } catch (Exception e) {

            }
        } catch (Exception e) {

        }


        return rootView;
    }
}
