package com.example.user.myapplication.activities;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class RestOperation extends AsyncTask<String, Void, String> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    @Override
    protected String doInBackground(String... params){
        String result = null;
        if(params[0]=="GET"){
            String stringUrl = params[1];
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(params[0]=="POST"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("currentStatus", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="POSTLOG"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("status", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="POSTMotion"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("MotionChecked", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="POSTALARM"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("StartAlarmHour", params[2]));
                nameValuePairs.add(new BasicNameValuePair("StartAlarmMinute", params[3]));
                nameValuePairs.add(new BasicNameValuePair("EndAlarmHour", params[4]));
                nameValuePairs.add(new BasicNameValuePair("EndAlarmMinute", params[5]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="DELETEALARM"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("StartAlarmHour", params[2]));
                nameValuePairs.add(new BasicNameValuePair("StartAlarmMinute", params[3]));
                nameValuePairs.add(new BasicNameValuePair("EndAlarmHour", params[4]));
                nameValuePairs.add(new BasicNameValuePair("EndAlarmMinute", params[5]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="POSTCARE"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("Type1", params[2]));
                nameValuePairs.add(new BasicNameValuePair("Type2", params[3]));
                nameValuePairs.add(new BasicNameValuePair("Value", params[4]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }
        else if(params[0]=="POSTADDSWITCH"){
            String stringUrl = params[1];
            InputStream inputStream = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringUrl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("deviceName", params[3]));
                nameValuePairs.add(new BasicNameValuePair("deviceIP", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    result = "Did work!";
                else
                    result = "Did not work!";
            }
            catch (Exception e){

            }
        }

        return result;
    }

}