package com.example.user.myapplication.activities;

/**
 * Created by gahyunchoi on 9/5/17.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
/**
 * Created by Adib on 13-Apr-17.
 */
public class ElecFragment extends Fragment {
    final String ip = "192.168.43.123";
    private View viewLoc;

    private TextView total_elec;
    private TextView total_price;
    private ImageView emergency_image;

    public static ElecFragment newInstance() {
        return new ElecFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_elec, container, false);

//        viewLoc = View.inflate(getActivity(), R.layout.activity_elec, null);
        total_elec = (TextView) rootView.findViewById(R.id.ELEC);
        total_price = (TextView) rootView.findViewById(R.id.total_usage5);
        emergency_image = (ImageView) rootView.findViewById(R.id.id_logo);
        float total_elec_2 = 0;
        String getDeviceIP_Url = "http://" + ip + ":3000/api/getTotalElec";
        //String to place our getdevice_result in
        String getDeviceIP_result = null;
        //Instantiate new instance of our class
        RestOperation getDeviceIPRequest = new RestOperation();
        //Perform the doInBackground method, passing in our url
        try {
            getDeviceIP_result = getDeviceIPRequest.execute("GET", getDeviceIP_Url).get();
            Log.e("CHECK", getDeviceIP_result);

            JSONArray json_array = new JSONArray(getDeviceIP_result);

            float total = 0;
            for (int i = 0; i < json_array.length(); i++) {
                final JSONObject device = json_array.getJSONObject(i);
                final String Sum = device.getString("sum");
                Log.e("SUM", Sum);
                String str2 = Sum.substring(1,Sum.length()-1);
                Log.e("WHAT",str2);
                String[] words = str2.split(","); //splits the string based on string

                for (String w : words)
                {
                    if((w!=null)&&(w.length()!=0)&&(w!="")&&(w!="0")){
                        String two = w.substring(1,w.length()-1);
                        Log.e("SUM-DETAIL",two);
                        total += Float.parseFloat(two);
                    }
                }
                Log.e("FINISH","STRING");
                float total_elec_float = total/(60);
                String total_elec_string = String.valueOf(total_elec_float);
                total_elec.setText(total_elec_string);
//                total_elec.setText("Q@E");
                int [] basic = new int[]{410 , 910 , 1600 , 3850 , 7300 , 12940};
                float [] basic_plus = new float[]{(float) 60.7, (float) 125.9, (float) 187.9, (float) 280.6, (float) 417.7, (float) 709.5};


                float total_price_2 = 0;
                total_elec_2 =  total/(60);
                Log.e("ERROR__", String.valueOf(total_elec_2));

                if(total_elec_2<100){
                    total_price_2 += basic[0];
                    for(int j=0;j<total_elec_2;j++){
                        total_price_2 += basic_plus[0];
                    }
                }
                if((total_elec_2<200)&&(total_elec_2>100)){
                    total_price_2 += basic[0];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[0];
                    }
                    total_price_2 += basic[1];
                    for(int j=0;j<total_elec_2-100;j++){
                        total_price_2 += basic_plus[1];
                    }
                }
                if((total_elec_2<300)&&(total_elec_2>200)){
                    total_price_2 += basic[0];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[0];
                    }
                    total_price_2 += basic[1];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[1];
                    }
                    total_price_2 += basic[2];
                    for(int j=0;j<total_elec_2-200;j++){
                        total_price_2 += basic_plus[2];
                    }
                }
                if((total_elec_2<400)&&(total_elec_2>300)){
                    total_price_2 += basic[0];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[0];
                    }
                    total_price_2 += basic[1];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[1];
                    }
                    total_price_2 += basic[2];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[2];
                    }
                    total_price_2 += basic[3];
                    for(int j=0;j<total_elec_2-300;j++){
                        total_price_2 += basic_plus[3];
                    }
                }
                if((total_elec_2<500)&&(total_elec_2>400)){
                    total_price_2 += basic[0];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[0];
                    }
                    total_price_2 += basic[1];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[1];
                    }
                    total_price_2 += basic[2];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[2];
                    }
                    total_price_2 += basic[3];
                    for(int j=0;j<100;j++){
                        total_price_2 += basic_plus[3];
                    }
                    total_price_2 += basic[4];
                    for(int j=0;j<total_elec_2-400;j++){
                        total_price_2 += basic_plus[4];
                    }
                }
                total_price.setText(String.valueOf(total_price_2));
            }
            Log.e("TOTAL_ELEC", String.valueOf(total_elec_2));
            if((total_elec_2)>400){
                Resources res = getResources(); /** from an Activity */
                emergency_image.setImageDrawable(res.getDrawable(R.drawable.emergency));
            }

//            emergency_image.setImageResource();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rootView;
    }
}