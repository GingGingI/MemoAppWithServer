package com.firebase.ginggingi.memoappwithserver.ConnServer;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.ginggingi.memoappwithserver.Interfaces.GetJsonModel;
import com.firebase.ginggingi.memoappwithserver.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJson implements GetJsonModel{

    private static final String TAG = "GETJSON ";
    private MainActivity mActivity;
    protected String url;
    protected JSONArray JsonArr;

    @Override
    public void InitDatas(String url, MainActivity mActivity) {
        this.mActivity = mActivity;
        this.url = url;
        //10.0.2.2:8000
    }

    @Override
    public void ParseJsonFromURL() {
        GetJsonTask JTask = new GetJsonTask();
        JTask.execute();
    }

    @Override
    public void GiveJsonArr() {
        mActivity.JArr = JsonArr;
        mActivity.IsGetJsonArr();
    }

    private class GetJsonTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try{
                result = getStringFromUrl(url);

                JSONObject JObject = new JSONObject(result);
                JsonArr = JObject.getJSONArray("memos");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            GiveJsonArr();
        }

        private String getStringFromUrl(String url) {
            BufferedReader buffReader = null;
            HttpURLConnection urlConn = null;
            InputStream is = null;

            StringBuffer sb = new StringBuffer();
            try{
                URL HttpUrl = new URL(url);
                urlConn = (HttpURLConnection) HttpUrl.openConnection();
                is = urlConn.getInputStream();

                buffReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String lines = null;

                while((lines = buffReader.readLine()) != null){
                    Log.i(TAG, lines);
                    sb.append(lines);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //연결닫기
                try {
                    if (buffReader != null)
                        buffReader.close();
                    if (is != null)
                        is.close();
                    if (urlConn != null)
                        urlConn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
}
