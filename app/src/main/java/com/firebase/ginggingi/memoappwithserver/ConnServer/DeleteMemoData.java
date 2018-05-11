package com.firebase.ginggingi.memoappwithserver.ConnServer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.Interfaces.DeleteDataModel;
import com.firebase.ginggingi.memoappwithserver.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by GingGingI on 2018-05-11.
 */

public class DeleteMemoData implements DeleteDataModel {
    private int idx;
    private MainActivity mActivity;
    public boolean isWorking = false;

    @Override
    public void InitData(int idx, Context context) {
        this.idx = idx;
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void DeleteData() {
        new DelTask().execute();
    }

    @Override
    public boolean ChkIsDataExist(String s) {
        if (s.equals("200\n")) {
            return true;
        }else{
            return false;
        }
    }

    class DelTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isWorking = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            String result = null;
            try {
                String data = URLEncoder.encode("idx", "UTF-8")
                        + "=" + URLEncoder.encode(Integer.toString(idx), "UTF-8");

                URL url = new URL("http://10.0.2.2:8000/deleteData.php/");
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
            ChkIsDataExist(s);
            isWorking = false;
            if (ChkIsDataExist(s)){
                mActivity.DataDeleted(true);
            }else{
                Log.i("DeleteMemo :", "DataDeleteError -> " + s);
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                mActivity.DataDeleted(false);
            }
        }
    }
}
