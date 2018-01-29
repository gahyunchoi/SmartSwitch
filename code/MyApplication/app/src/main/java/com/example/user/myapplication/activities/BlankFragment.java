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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Adib on 13-Apr-17.
 */
public class BlankFragment extends Fragment {

    final String ip = "192.168.43.123";
    private TextView startTimePicker , endTimePicker;
    private TextView startTime , endTime;
    private LinearLayout startTimeContainer,endTimeContainer;
    private View startTimeLine,endTimeLine;
    private Button addButton;
    private Button deleteButton;

    private Calendar startTimeCalendar = null;
    private Calendar endTimeCalendar = null;


    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.blank, container, false);

        startTimeCalendar = Calendar.getInstance();
        endTimeCalendar = Calendar.getInstance();
        addButton = (Button)rootView.findViewById(R.id.btn_add);
        deleteButton = (Button)rootView.findViewById(R.id.btn_delete);

        TextView log_start_time_picker=  (TextView) rootView.findViewById(R.id.log_start_time_picker);

        TextView log_end_time_picker=  (TextView) rootView.findViewById(R.id.log_end_time_picker);


        TextView txtStartTime=  (TextView) rootView.findViewById(R.id.txtStartTime);
        txtStartTime.setTextColor(getActivity().getResources().getColor(R.color.color_light_gray));

        startTimePicker = (TextView)rootView.findViewById(R.id.log_start_time_picker);
        endTimePicker = (TextView)rootView.findViewById(R.id.log_end_time_picker);

        startTime = (TextView)rootView.findViewById(R.id.log_start_time);

        endTime = (TextView)rootView.findViewById(R.id.log_end_time);
        startTimeLine = rootView.findViewById(R.id.log_start_time_line);
        startTimePicker = (TextView)rootView.findViewById(R.id.log_start_time_picker);
//        startTimePicker.setTypeface(HOSApplication.getFontAwesomeTypeface());
        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditStartTimePicker();
            }
        });

//        startTime.setText("123");

        endTimeLine = rootView.findViewById(R.id.log_end_time_line);
        endTimePicker = (TextView)rootView.findViewById(R.id.log_end_time_picker);
        endTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditEndTimePicker();
            }
        });
















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




            addButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    Log.e("_____________", String.valueOf(spinner.getSelectedItem()));
                    String start_time_H = startTime.getText().toString().substring(0,startTime.getText().toString().indexOf(":"));
                    String start_time_M = startTime.getText().toString().substring(startTime.getText().toString().indexOf(":")+1,startTime.getText().toString().length());
                    String end_time_H = endTime.getText().toString().substring(0,endTime.getText().toString().indexOf(":"));
                    String end_time_M = endTime.getText().toString().substring(endTime.getText().toString().indexOf(":")+1,endTime.getText().toString().length());

                    //
                    String addAlarm_Url = "http://" + ip + ":3000/api/addAlarm/" + spinner.getSelectedItem();
                    //String to place our getdevice_result in
                    String addAlarm_result = null;
                    //Instantiate new instance of our class
                    RestOperation addAlarmRequest = new RestOperation();
                    //Perform the doInBackground method, passing in our url
                    try {
                        addAlarm_result = addAlarmRequest.execute("POSTALARM", addAlarm_Url, start_time_H, start_time_M, end_time_H,end_time_M).get();
                        Log.e("RESTAPI-POST", "Result3:" + addAlarm_result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            });

























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

                deleteButton.setOnClickListener(new View.OnClickListener() {

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
                Outlinearlayout2.addView(deleteButton, lp);


            } catch (Exception e) {

            }


        }
        catch(Exception e){

        }

        return rootView;
    }

    com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener mEditStartTimeListener
            = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

            if(view.isShown()){
                Log.e("Start Time Set","hourOfDay "+hourOfDay+" minute "+minute);
                int newStartTime = (hourOfDay * 60) + minute;
                startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startTimeCalendar.set(Calendar.MINUTE, minute);
                startTime.setText((hourOfDay == 0 ? "00" : hourOfDay) + ":" + (minute));

            }
        }
    };


    private void showEditStartTimePicker(){

        getFragmentManager().beginTransaction()
                .addToBackStack("EditStartTimeFragment");

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd =
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog
                        .newInstance(
                                mEditStartTimeListener,
                                startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                                startTimeCalendar.get(Calendar.MINUTE),
                                false
                        );
        tpd.setAccentColor(getResources().getColor(R.color.aval_warning_bg));


        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener mEditEndTimeListener
            = new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

            if(view.isShown()){
                Log.e("Start Time Set","hourOfDay "+hourOfDay+" minute "+minute);
                int newStartTime = (hourOfDay * 60) + minute;
                endTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endTimeCalendar.set(Calendar.MINUTE, minute);
                endTime.setText((hourOfDay == 0 ? "00" : hourOfDay) + ":" + (minute));

            }
        }
    };
    private void showEditEndTimePicker(){

        getFragmentManager().beginTransaction()
                .addToBackStack("EditStartTimeFragment");

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd =
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog
                        .newInstance(
                                mEditEndTimeListener,
                                startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                                startTimeCalendar.get(Calendar.MINUTE),
                                false
                        );
        tpd.setAccentColor(getResources().getColor(R.color.aval_warning_bg));


        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }




}