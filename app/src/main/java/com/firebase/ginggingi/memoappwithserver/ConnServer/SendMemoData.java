package com.firebase.ginggingi.memoappwithserver.ConnServer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.Interfaces.SendDataModel;
import com.firebase.ginggingi.memoappwithserver.MemoAddActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SendMemoData implements SendDataModel {

    private MemoAddActivity MAActivity;
    public Boolean isWorking = false;
    String title,content;

    @Override
    public void InitData(String title, String content, Context context) {
        this.title = title;
        this.content = content;
        this.MAActivity = (MemoAddActivity) context;
    }

    @Override
    public void SendData() {
        new SendDataTask().execute();
    }

    @Override
    public boolean ChkIsValidData(String s) {
        if (s.equals("200\n")) {
            return true;
        }else{
            return false;
        }
    }

    class SendDataTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isWorking = true;
            //Dialog
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            String result = null;
            try {
                String data = URLEncoder.encode("title", "UTF-8")
                        + "=" + URLEncoder.encode(title, "UTF-8");

                data += "&" + URLEncoder.encode("content", "UTF-8")
                        + "=" + URLEncoder.encode(content, "UTF-8");

                URL url = new URL("http://10.0.2.2:8000/sendData.php/");
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);

                //Post로 값보내기
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                //값보낸후 resultCode받기
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                //result가 뜰때까지 대기...
                while((line = br.readLine()) != null){
                    if (!line.equals(""))
                        sb.append(line + "\n");
                }
                result = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            isWorking = false;
            if (ChkIsValidData(s)){
                MAActivity.DataSended(true);
            }else{
                Log.i("SendMemo :", "DataSendError");
                Toast.makeText(MAActivity, s, Toast.LENGTH_SHORT).show();
                MAActivity.DataSended(false);
            }
        }
    }
}
