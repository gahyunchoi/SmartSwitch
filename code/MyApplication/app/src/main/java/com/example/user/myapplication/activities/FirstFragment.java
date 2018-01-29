package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import me.drakeet.materialdialog.MaterialDialog;
/**
 * Created by Adib on 13-Apr-17.
 */
public class FirstFragment extends Fragment{
    final String ip = "192.168.43.123";

    private OnFragmentInteractionListener listener;

    private Button add_switch_Button;
    private View viewLoc;
    private EditText ip_text;
    private EditText name_text;
    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);





        viewLoc = View.inflate(getActivity(), R.layout.activity_addswitch, null);



        ip_text = (EditText) viewLoc.findViewById(R.id.switch_ip);
        name_text = (EditText) viewLoc.findViewById(R.id.switch_name);


        add_switch_Button = (Button)rootView.findViewById(R.id.btn_add);


        add_switch_Button.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                final MaterialDialog mMaterialDialog = new MaterialDialog(getContext());
                mMaterialDialog.setTitle("MaterialDialog");
                mMaterialDialog.setMessage("Hello world!");
                mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("OK","1");
                        String addSwitch_Url = "http://" + ip + ":3000/api/addDevice";
                        //String to place our getdevice_result in
                        String addSwitch_result = null;
                        //Instantiate new instance of our class
                        RestOperation addAlarmRequest = new RestOperation();
                        //Perform the doInBackground method, passing in our url
                        try {
                            addSwitch_result = addAlarmRequest.execute("POSTADDSWITCH", addSwitch_Url, ip_text.getText().toString(),name_text.getText().toString()).get();
                            Log.e("RESTAPI-POST", "Result3:" + addSwitch_result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        mMaterialDialog.dismiss();

                    }
                }); mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("CANCEL","2");
                        mMaterialDialog.dismiss();
                    }
                });

                mMaterialDialog.setView(viewLoc);

                mMaterialDialog.show();

                //MaterialDialog


//                Log.e("_____________", String.valueOf(spinner.getSelectedItem()));
//                String start_time_H = startTime.getText().toString().substring(0,startTime.getText().toString().indexOf(":"));
//                String start_time_M = startTime.getText().toString().substring(startTime.getText().toString().indexOf(":")+1,startTime.getText().toString().length());
//                String end_time_H = endTime.getText().toString().substring(0,endTime.getText().toString().indexOf(":"));
//                String end_time_M = endTime.getText().toString().substring(endTime.getText().toString().indexOf(":")+1,endTime.getText().toString().length());
//
//                //
//                String addAlarm_Url = "http://" + ip + ":3000/api/addAlarm/" + spinner.getSelectedItem();
//                //String to place our getdevice_result in
//                String addAlarm_result = null;
//                //Instantiate new instance of our class
//                RestOperation addAlarmRequest = new RestOperation();
//                //Perform the doInBackground method, passing in our url
//                try {
//                    addAlarm_result = addAlarmRequest.execute("POSTALARM", addAlarm_Url, start_time_H, start_time_M, end_time_H,end_time_M).get();
//                    Log.e("RESTAPI-POST", "Result3:" + addAlarm_result);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
            }

        });

















        LinearLayout Outlinearlayout = (LinearLayout)rootView.findViewById(R.id.switchlayout);






        LinearLayout Motionlinearlayout = (LinearLayout)rootView.findViewById(R.id.LinearLayoutMotion);

        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp4.setMargins(0,0,20,0);

        ///////////////////////////initalize

        //Some url endpoint that you may have
        String getdevice_Url4 = "http://"+ip+":3000/api/getDevices";
        //String to place our getdevice_result in
        String getdevice_result4 = null;
        //Instantiate new instance of our class
        RestOperation getRequest4 = new RestOperation();
        //Perform the doInBackground method, passing in our url
        try {
            getdevice_result4 = getRequest4.execute("GET",getdevice_Url4).get();
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
            JSONArray json_array = new JSONArray(getdevice_result4);

            for (int i = 0; i < json_array.length(); i++) {
                final JSONObject device = json_array.getJSONObject(i);
                final String deviceName = device.getString("deviceName");
                final int deviceMotion = device.getInt("MotionChecked");
                Log.e("MOTIONCHECKED",deviceName+","+deviceMotion);

                CheckBox checkbox = new CheckBox(getContext());
                checkbox.setText(deviceName);

                if(deviceMotion==3){
                    Log.e("MOTIONCHECKED-1",deviceName+","+deviceMotion);
                    checkbox.setChecked(true);
                }
                else{
                    Log.e("MOTIONCHECKED-1",deviceName+","+deviceMotion);
                    checkbox.setChecked(false);
                }
                //Set on off
                checkbox.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        //is chkIos checked?
                        if (((CheckBox) v).isChecked()) {

                            String getDeviceIP_Url = "http://"+ip+":3000/api/getIpFromDeviceName/"+deviceName;
                            //String to place our getdevice_result in
                            String getDeviceIP_result = null;
                            //Instantiate new instance of our class
                            RestOperation getDeviceIPRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                getDeviceIP_result = getDeviceIPRequest.execute("GET",getDeviceIP_Url).get();
                                //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            }


                            String updateMotion_Url = "http://"+ip+":3000/api/updateMotion/"+deviceName;
                            //String to place our getdevice_result in
                            String updateMotion_result = null;
                            //Instantiate new instance of our class
                            RestOperation getMotionRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateMotion_result = getMotionRequest.execute("POSTMotion",updateMotion_Url,"3").get();
                                Log.e("RESTAPI-POST","Result1:"+updateMotion_result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            Log.e("EWR","CHECKED");
                        }
                        else{
                            String getDeviceIP_Url = "http://"+ip+":3000/api/getIpFromDeviceName/"+deviceName;
                            //String to place our getdevice_result in
                            String getDeviceIP_result = null;
                            //Instantiate new instance of our class
                            RestOperation getDeviceIPRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                getDeviceIP_result = getDeviceIPRequest.execute("GET",getDeviceIP_Url).get();
                                //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            }


                            String updateMotion_Url = "http://"+ip+":3000/api/updateMotion/"+deviceName;
                            //String to place our getdevice_result in
                            String updateMotion_result = null;
                            //Instantiate new instance of our class
                            RestOperation getMotionRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateMotion_result = getMotionRequest.execute("POSTMotion",updateMotion_Url,"2").get();
                                Log.e("RESTAPI-POST","Result1:"+updateMotion_result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            Log.e("EWR","CHECKED");
                        }
                    }
                });

                LinearLayout linearlayout = new LinearLayout(getContext());
                linearlayout.setPadding(20,20,0,10);
                linearlayout.addView(checkbox,lp4);
                linearlayout.setGravity(Gravity.CENTER_VERTICAL);
                Motionlinearlayout.addView(linearlayout);
            }

        }
        catch(Exception e){

        }











        Outlinearlayout.setPadding(0,50,0,0);
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


//                Log.e("RESTAPI","Result"+i+":"+deviceName);
                LinearLayout linearlayout = new LinearLayout(getContext());
                linearlayout.setPadding(100,30+deviceName.length(),0,20);

                TextView tv = new TextView(getContext());
                tv.setTextSize(20);
                tv.setText(deviceName);
                tv.setPadding(0,0,90,0);


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,20,0);

                linearlayout.addView(tv,lp);

                final com.mahfa.dnswitch.DayNightSwitch switForDevice = new com.mahfa.dnswitch.DayNightSwitch(getContext()); // or create xml view for button and get it with findViewById
//                switForDevice.setLayoutParams(new RelativeLayout.LayoutParams(152*2, 80*2));
                switForDevice.setLayoutParams(new RelativeLayout.LayoutParams(152, 80));
                switForDevice.setId(i);
                if(status==1){
                    switForDevice.setIsNight(false);
                }
                else{
                    switForDevice.setIsNight(true);
                }
                switForDevice.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

//                    Toast.makeText(view.getContext(),
//                            "Button clicked index = " + switForDevice.getId(), Toast.LENGTH_SHORT)
//                            .show();
                        if(switForDevice.isNight()){
                            switForDevice.setIsNight(false);

                            String getDeviceIP_Url = "http://"+ip+":3000/api/getIpFromDeviceName/"+deviceName;
                            //String to place our getdevice_result in
                            String getDeviceIP_result = null;
                            //Instantiate new instance of our class
                            RestOperation getDeviceIPRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                getDeviceIP_result = getDeviceIPRequest.execute("GET",getDeviceIP_Url).get();
                                //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            }


                            String updateStatus_Url = "http://"+ip+":3000/api/updateCurrentStatus/"+deviceName;
                            //String to place our getdevice_result in
                            String updateStatus_result = null;
                            //Instantiate new instance of our class
                            RestOperation getRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateStatus_result = getRequest.execute("POST",updateStatus_Url,"1").get();
                                Log.e("RESTAPI-POST","Result1:"+updateStatus_result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }

                            String updateLog_Url = "http://"+ip+":3000/api/addLogForDevice/"+deviceName;
                            //String to place our getdevice_result in
                            String updateLog_result = null;
                            //Instantiate new instance of our class
                            RestOperation getRequest2 = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateLog_result = getRequest2.execute("POSTLOG",updateLog_Url,"1").get();
                                Log.e("RESTAPI-POST","Result3:"+updateLog_result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            switForDevice.setIsNight(true);

                            String getDeviceIP_Url = "http://"+ip+":3000/api/getIpFromDeviceName/"+deviceName;
                            //String to place our getdevice_result in
                            String getDeviceIP_result = null;
                            //Instantiate new instance of our class
                            RestOperation getDeviceIPRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                getDeviceIP_result = getDeviceIPRequest.execute("GET",getDeviceIP_Url).get();
                                //Log.e("CHECK",getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                Log.e("RESTAPI","Result:"+e);
                            }

                            String updateStatus_Url = "http://"+ip+":3000/api/updateCurrentStatus/"+deviceName;
                            //String to place our getdevice_result in
                            String updateStatus_result = null;
                            //Instantiate new instance of our class
                            RestOperation getRequest = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateStatus_result = getRequest.execute("POST",updateStatus_Url,"0").get();
                                Log.e("RESTAPI-POST","Result1:"+updateStatus_result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }


                            String updateLog_Url = "http://"+ip+":3000/api/addLogForDevice/"+deviceName;
                            //String to place our getdevice_result in
                            String updateLog_result = null;
                            //Instantiate new instance of our class
                            RestOperation getRequest2 = new RestOperation();
                            //Perform the doInBackground method, passing in our url
                            try {
                                updateLog_result = getRequest2.execute("POSTLOG",updateLog_Url,"0").get();
                                Log.e("RESTAPI-POST","Result4:"+updateLog_result);
                            } catch (InterruptedException e) {
                                Log.e("RESTAPI-POST","Result4-2:"+updateLog_result);
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                Log.e("RESTAPI-POST","Result4-3:"+updateLog_result);
                                e.printStackTrace();
                            }

                        }

                    }
                });
                linearlayout.addView(switForDevice);
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














        ArrayList<ArrayList<String>> DATA_TO_SHOW = new ArrayList<ArrayList<String>>();
        ArrayList<String> DATA_TO_INSERT = new ArrayList<String>();

        //Some url endpoint that you may have
        String getdevice_Url3 = "http://"+ip+":3000/api/getDevices";
        //String to place our getdevice_result in
        String getdevice_result3 = null;
        //Instantiate new instance of our class
        RestOperation getRequest3 = new RestOperation();
        //Perform the doInBackground method, passing in our url
        try {
            getdevice_result3 = getRequest3.execute("GET",getdevice_Url3).get();
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
                if(elec == 0){
                    DATA_TO_INSERT.add(Integer.toString(0));
                }
                else{
                    DATA_TO_INSERT.add(Integer.toString(elec-250));
                }



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


            String[] HEADER_STRING = {"스위치","온/오프","습도","온도","인체감지","전력"};
            TableView<String[]> tableView = (TableView<String[]>) rootView.findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(getContext(), DATA_TO_SHOW_ARRAY));
            tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(), HEADER_STRING));

//                        result_json = (String) json2.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return rootView;
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