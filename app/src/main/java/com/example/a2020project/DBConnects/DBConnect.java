package com.example.a2020project.DBConnects;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static android.os.AsyncTask.Status.FINISHED;


public class DBConnect {

    String mJsonString1;

    /*public interface AsyncResponse {
        void processFinish(String result);
    }*/

    public static class GetData extends AsyncTask<String, Void, String> {

        //ProgressDialog progressDialog;
        String errorString = null;
        String mJsonString;

        public interface AsyncResponse {
            void processFinish(String result);
        }

        private AsyncResponse delegate = null;

        public GetData (AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*progressDialog = ProgressDialog.show(getApplicationContext(),
                    "Please Wait", null, true, true);*/
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            mJsonString = result;
            Log.w(TAG, "response - " + result);

            if(delegate != null){
                delegate.processFinish(result);
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String query  = params[0];
            String datanum = params[1];

            String serverURL = "http://218.150.183.181:280/db_connect_test.php";
            String postParameters = "query=" + query + "&datanum=" + datanum;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // Http URL 연결
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                // 쿼리문 전송
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //Log.d(TAG, "response code - " + responseStatusCode);

                // 전송에 성공한 경우, 받을 때
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }


}
