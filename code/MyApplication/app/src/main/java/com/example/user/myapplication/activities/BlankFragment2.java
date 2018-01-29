package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
/**
 * Created by Adib on 13-Apr-17.
 */
public class BlankFragment2 extends Fragment {

    final String ip = "192.168.43.123";
    private int position2_private = 0;
    private TextView startTimePicker , endTimePicker;
    private int position_private = 0;
    private TextView startTime , endTime;
    private LinearLayout startTimeContainer,endTimeContainer;
    private View startTimeLine,endTimeLine;
    private Button addButton;
    private Button deleteButton;

    private Calendar startTimeCalendar = null;
    private Calendar endTimeCalendar = null;


    public static BlankFragment2 newInstance() {
        return new BlankFragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.blank2, container, false);

        startTimeCalendar = Calendar.getInstance();
        endTimeCalendar = Calendar.getInstance();
        addButton = (Button)rootView.findViewById(R.id.btn_add);
        deleteButton = (Button)rootView.findViewById(R.id.btn_delete);

        TextView log_start_time_picker=  (TextView) rootView.findViewById(R.id.log_start_time_picker);

        TextView log_end_time_picker=  (TextView) rootView.findViewById(R.id.log_end_time_picker);


        final TextView AlarmForWhat=  (TextView) rootView.findViewById(R.id.AlarmForWhat);
        AlarmForWhat.setTextColor(getActivity().getResources().getColor(R.color.color_light_gray));

//        startTimePicker = (TextView)rootView.findViewById(R.id.log_start_time_picker);
//        endTimePicker = (TextView)rootView.findViewById(R.id.log_end_time_picker);

        startTime = (TextView)rootView.findViewById(R.id.log_start_time);

//        endTime = (TextView)rootView.findViewById(R.id.log_end_time);
        startTimeLine = rootView.findViewById(R.id.log_start_time_line);
        startTimePicker = (TextView)rootView.findViewById(R.id.log_start_time_picker);
//        startTimePicker.setTypeface(HOSApplication.getFontAwesomeTypeface());
        startTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditEndTimePicker();
            }
        });


        startTime.setText("1234s");












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

            final Spinner spinner6 = new Spinner(getContext());

            List<String> list6 = new ArrayList<String>();
            list6.add("온도");
            list6.add("습도");

            ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list6);
            dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(dataAdapter6);

            Outlinearlayout.setPadding(20, 20, 0, 10);
            Outlinearlayout.addView(spinner6, lp);
            Outlinearlayout.setGravity(Gravity.CENTER_VERTICAL);

            final Spinner spinner7 = new Spinner(getContext());

            List<String> list7 = new ArrayList<String>();
            list7.add("상한선");
            list7.add("하한선");

            ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list7);
            dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner7.setAdapter(dataAdapter7);

            Outlinearlayout.setPadding(20, 20, 0, 10);
            Outlinearlayout.addView(spinner7, lp);
            Outlinearlayout.setGravity(Gravity.CENTER_VERTICAL);

            spinner7.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Log.e("SELECTED!", String.valueOf(position));
                    //High

                    if(position==0){
                        position2_private = position;
                        Log.e("position_private2", String.valueOf(position2_private));
                    }
                    else{
                        position2_private = position;
                        Log.e("position_private2", String.valueOf(position2_private));
                    }
//                    List<String> list6 = new ArrayList<String>();
//                    list6.add("TEMPERATURE");
//                    list6.add("HUMIDITY");
//
//                    ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list6);
//                    dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner6.setAdapter(dataAdapter6);
//
//                    Outlinearlayout.setPadding(20, 20, 0, 10);
//                    Outlinearlayout.addView(spinner6, lp);
//                    Outlinearlayout.setGravity(Gravity.CENTER_VERTICAL);
//                    // your code here
                }



                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            spinner6.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Log.e("SELECTED!", String.valueOf(position));
                    //High
                    if(position==0){
                        position_private = position;
                        AlarmForWhat.setText("원하는 온도");
                    }
                    else{
                        position_private = position;
                        AlarmForWhat.setText("원하는 습도");
                    }
//                    List<String> list6 = new ArrayList<String>();
//                    list6.add("TEMPERATURE");
//                    list6.add("HUMIDITY");
//
//                    ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list6);
//                    dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner6.setAdapter(dataAdapter6);
//
//                    Outlinearlayout.setPadding(20, 20, 0, 10);
//                    Outlinearlayout.addView(spinner6, lp);
//                    Outlinearlayout.setGravity(Gravity.CENTER_VERTICAL);
//                    // your code here
                }



                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });




            addButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {


                    Log.e("DeviceName: ", String.valueOf(spinner.getSelectedItem()));
                    Log.e("Type1: ", String.valueOf(spinner6.getSelectedItem()));
                    Log.e("Type2: ", String.valueOf(spinner7.getSelectedItem()));
                    Log.e("Value: ", String.valueOf(startTime.getText()));


                    //
                    String addCare_Url = "http://" + ip + ":3000/api/addCare/" + spinner.getSelectedItem();
                    //String to place our getdevice_result in
                    String addCare_result = null;
                    //Instantiate new instance of our class
                    RestOperation addCareRequest = new RestOperation();
                    //Perform the doInBackground method, passing in our url
                    try {
                        int Type1=0;
                        int Type2 = 0;
                        if (String.valueOf(spinner6.getSelectedItem()) == "온도") {
                            Type1 = 1;
                        }
                        else{
                            Type1 = 2;
                        }
                        if (String.valueOf(spinner7.getSelectedItem()) == "상한선") {
                            Type2 = 1;
                        }
                        else{
                            Type2 = 2;
                        }
                        addCare_result = addCareRequest.execute("POSTCARE", addCare_Url, String.valueOf(Type1), String.valueOf(Type2), String.valueOf(startTime.getText())).get();
                        Log.e("RESTAPI-POST", "Result3:" + addCare_result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            });









            startTime.setText("");















            //Some url endpoint that you may have
            String getdevice_Url2 = "http://" + ip + ":3000/api/getCare";
            //String to place our getdevice_result in
            String getdevice_result2 = null;
            //Instantiate new instance of our class
            RestOperation getRequest2 = new RestOperation();
            //Perform the doInBackground method, passing in our url
            try {
                getdevice_result2 = getRequest2.execute("GET", getdevice_Url2).get();
                Log.e("CHECK------",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("RESTAPI-------", "Result:" + e);
            } catch (ExecutionException e) {
                e.printStackTrace();
                Log.e("RESTAPI-----", "Result:" + e);
            }
            try {
                JSONArray json_array2 = new JSONArray(getdevice_result2);
                List<String> list2 = new ArrayList<String>();
                for (int i = 0; i < json_array2.length(); i++) {
                    final JSONObject device = json_array2.getJSONObject(i);
                    final String deviceName = device.getString("deviceName");
                    final String Type1 = device.getString("Type1");
                    final String Type2 = device.getString("Type2");
                    final String Value = device.getString("Value");

                    String Type3 = "";
                    String Type4 = "";
                    if(Type1=="1"){
                        Type3 = "온도";
                    }
                    else{
                        Type3 = "습도";
                    }
                    if(Type2=="1"){
                        Type4 = "상한선";
                    }
                    else{
                        Type4 = "하한선";
                    }
                    list2.add(deviceName + " / " + Type3 + "," + Type4 + " : " + Value );
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
                        String selected_Type1 = spinner2.getSelectedItem().toString().substring(spinner2.getSelectedItem().toString().indexOf("/") + 1, spinner2.getSelectedItem().toString().indexOf(",")).trim();
                        String selected_Type2 = spinner2.getSelectedItem().toString().substring(spinner2.getSelectedItem().toString().indexOf("/") + 1, spinner2.getSelectedItem().toString().indexOf(",")).trim();
//                        String selected_start_Min = selected_start_time.substring(selected_start_time.indexOf(":")+1,selected_start_time.length());
//
//                        String selected_end_time = spinner2.getSelectedItem().toString().substring(spinner2.getSelectedItem().toString().indexOf("~") + 1, spinner2.getSelectedItem().toString().length()).trim();
//                        String selected_end_Hour = selected_end_time.substring(0,selected_end_time.indexOf(":"));
//                        String selected_end_Min = selected_end_time.substring(selected_end_time.indexOf(":")+1,selected_end_time.length());
//
//                        Log.e("RESULT333", "1" + selected_device_name + "," + "2" + selected_start_time + "," + "3" + selected_end_time);
//                        Log.e("RESULT444", "1" + selected_device_name + "," + "2:" + selected_start_Hour + "," + "3:" + selected_start_Min);
//                        Log.e("RESULT555", "1" + selected_device_name + "," + "2:" + selected_end_Hour + "," + "3:" + selected_end_Min);
//
//                        String Delete_Alarm_Url = "http://" + ip + ":3000/api/DeleteAlarm/" + selected_device_name;
//                        String Delete_Alarm_result = null;
//                        RestOperation DeleteAlarmRequest = new RestOperation();
//                        try {
//                            Delete_Alarm_result = DeleteAlarmRequest.execute("DELETEALARM", Delete_Alarm_Url, selected_start_Hour,selected_start_Min,selected_end_Hour,selected_end_Min).get();
//                            Log.e("RESTAPI-POST", "Result3:" + Delete_Alarm_result);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }

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

    private void showEditEndTimePicker() {

        if (position_private == 0) {
            getFragmentManager().beginTransaction()
                    .addToBackStack("EditStartTimeFragment");

            final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(getContext())
                    .minValue(1)
                    .maxValue(10)
                    .defaultValue(1)
                    .backgroundColor(Color.WHITE)
                    .separatorColor(Color.TRANSPARENT)
                    .textColor(Color.BLACK)
                    .textSize(20)
                    .enableFocusability(false)
                    .wrapSelectorWheel(true)
                    .maxValue(40)
                    .minValue(15)
                    .build();

            new AlertDialog.Builder(getContext())
                    .setTitle("Celcius")
                    .setView(numberPicker)
                    .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("ALERT", String.valueOf(numberPicker.getValue()));
                            startTime.setText(String.valueOf(numberPicker.getValue()));
//                        Snackbar.make(findViewById(R.id.llytLogEdit), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .show();
        } else {
            getFragmentManager().beginTransaction()
                    .addToBackStack("EditStartTimeFragment");

            final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(getContext())
                    .minValue(1)
                    .maxValue(10)
                    .defaultValue(1)
                    .backgroundColor(Color.WHITE)
                    .separatorColor(Color.TRANSPARENT)
                    .textColor(Color.BLACK)
                    .textSize(20)
                    .enableFocusability(false)
                    .wrapSelectorWheel(true)
                    .maxValue(65)
                    .minValue(30)
                    .build();

            new AlertDialog.Builder(getContext())
                    .setTitle("Humidity(%)")
                    .setView(numberPicker)
                    .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("ALERT", String.valueOf(numberPicker.getValue()));
                            startTime.setText(String.valueOf(numberPicker.getValue()));
//                        Snackbar.make(findViewById(R.id.llytLogEdit), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }

    }
}