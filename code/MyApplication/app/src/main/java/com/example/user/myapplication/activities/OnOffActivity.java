package com.example.user.myapplication.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
public class OnOffActivity extends Activity {

    private SwipeRefreshLayout mSwipeRefresh;

    final String ip = "192.168.43.123";
    private ScrollView mScrollview;
    static int Activity_id = 0;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        mScrollview = (ScrollView) findViewById(R.id.MScrollView);


        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);



        //

        int ButtonNumber = 3;
        LinearLayout Outlinearlayout = (LinearLayout)findViewById(R.id.switchlayout);


        // adding buttons

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
                LinearLayout linearlayout = new LinearLayout(this);
                linearlayout.setPadding(20,20,0,10);


                TextView tv = new TextView(this);
                tv.setText(deviceName);


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,20,0);


                linearlayout.addView(tv,lp);

                final com.mahfa.dnswitch.DayNightSwitch switForDevice = new com.mahfa.dnswitch.DayNightSwitch(this); // or create xml view for button and get it with findViewById

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
//                lp.setLayoutDirection();
                linearlayout.addView(switForDevice,lp);
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



    }


}