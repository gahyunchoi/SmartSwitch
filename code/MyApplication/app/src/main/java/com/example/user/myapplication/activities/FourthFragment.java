package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Adib on 13-Apr-17.
 */

public class FourthFragment extends Fragment {

    final String ip = "192.168.43.123";

    public static FourthFragment newInstance() {
        return new FourthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fourth, container, false);

        LinearLayout Motionlinearlayout = (LinearLayout)rootView.findViewById(R.id.LinearLayoutMotion);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,20,0);

        ///////////////////////////initalize

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
                checkbox.setOnClickListener(new OnClickListener() {


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
                linearlayout.addView(checkbox,lp);
                linearlayout.setGravity(Gravity.CENTER_VERTICAL);
                Motionlinearlayout.addView(linearlayout);
            }

        }
        catch(Exception e){

        }




        return rootView;
    }

}