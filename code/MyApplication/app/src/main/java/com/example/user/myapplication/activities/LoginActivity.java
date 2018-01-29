package com.example.user.myapplication.activities;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.views.ClearableEditText;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
public class LoginActivity extends Activity {

    final String ip = "192.168.43.123";


    Button btn_login;

    EditText et_name;
    EditText et_password;
    SoftKeyboard softKeyboard;
    ScrollView llBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        FirebaseInstanceId.getInstance().getToken();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("FCM", token);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = (Button) this.findViewById(R.id.btn_login);
        et_name = (ClearableEditText) this.findViewById(R.id.edt_name);
        et_password = (ClearableEditText) this.findViewById(R.id.edt_pwd);
        llBg= (ScrollView) findViewById(R.id.viewScroll);
        llBg.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));

        InputMethodManager controlManager = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(llBg, controlManager);

        final String rasp_id = et_name.getText().toString();
        final String rasp_pw = et_password.getText().toString();

        Log.e("LOGIN-ID-PW","Result:"+rasp_id+","+rasp_pw);

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                int flag = 0;
                //Check ID

                String getuser_Url = "http://"+ip+":3000/api/getUsers";
                //String to place our getdevice_result in
                String getuser_result = null;
                //Instantiate new instance of our class
                RestOperation getUserRequest = new RestOperation();
                //Perform the doInBackground method, passing in our url
                try {
                    getuser_result = getUserRequest.execute("GET",getuser_Url).get();
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
                    JSONArray json_array = new JSONArray(getuser_result);

                    for (int i = 0; i < json_array.length(); i++) {
                        JSONObject device = json_array.getJSONObject(i);
                        final String userID = device.getString("userID");
                        if(userID.equals(et_name.getText().toString())){
                            flag = 1;
                        }
                        else{
                            flag = 0;
                        }

                    }
                }
                catch (Exception e){

                }


                //check PW


                if(flag == 1){
                    String getdevice_Url = "http://" + ip + ":3000/api/checkUser/" + et_name.getText().toString();
                    //String to place our getdevice_result in
                    String getdevice_result = null;
                    //Instantiate new instance of our class
                    RestOperation getRequest = new RestOperation();
                    //Perform the doInBackground method, passing in our url
                    try {
                        getdevice_result = getRequest.execute("GET", getdevice_Url).get();
                        Log.e("LOGIN", "Result:" + getdevice_result);
//            Log.e("RESTAPI","Result:"+getdevice_result.substring(1,getdevice_result.length()-1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e("RESTAPI", "Result:" + e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.e("RESTAPI", "Result:" + e);
                    }
                    Log.e("LOGIN2", "Result:" + getdevice_result.substring(1,getdevice_result.length()-1)+","+et_password.getText().toString());
                    if(et_password.getText().toString().equals(getdevice_result.substring(1,getdevice_result.length()-1))){
                        Log.e("LOGIN3-1", "Result:" + getdevice_result);
                        Intent intent = new Intent(LoginActivity.this, NavActivity.class);
//                Intent intent = new Intent(LoginActivity.this,BubbleChartActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Log.e("LOGIN3-2", "Result:" + getdevice_result);
                    }
                }

            }
        });



        et_name.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                softKeyboard.openSoftKeyboard();
            }
        });
        et_password.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                softKeyboard.openSoftKeyboard();
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        softKeyboard.unRegisterSoftKeyboardCallback();
    }
}

