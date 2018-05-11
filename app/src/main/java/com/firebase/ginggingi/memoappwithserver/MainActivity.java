package com.firebase.ginggingi.memoappwithserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.Adapter.MRecyclerViewAdapter;
import com.firebase.ginggingi.memoappwithserver.ConnServer.GetJson;
import com.firebase.ginggingi.memoappwithserver.DataModels.MemoDataModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "MainAcvitiy ";

    public JSONArray JArr;
    private GetJson GJson;

    private ArrayList<MemoDataModel> MDModelList = new ArrayList<>();
    private MemoDataModel MDModel = null;

    private MRecyclerViewAdapter adapter;

    private RecyclerView rv;
    private LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInit();
        getJsons();
    }

    private void viewInit() {
        adapter = new MRecyclerViewAdapter();
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        findViewById(R.id.AddMemoBtn).setOnClickListener(this);

        rv = findViewById(R.id.RecyclerView);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    private void getJsons() {
        if (JArr != null){JArr = new JSONArray();}
        if (MDModelList != null){MDModelList = new ArrayList<>();}
        GJson = new GetJson();
        GJson.InitDatas("http://10.0.2.2:8000", this);
        GJson.ParseJsonFromURL();
        //GetJson 클래스를 실행
    }

    public void IsGetJsonArr(){
        //GetJson 이 파싱을 하고 데이터를 보냈을때 실행.
        for (int i = 0; i < JArr.length(); i++){
            MDModel = new MemoDataModel();
            try{
                int idx = JArr.getJSONObject(i).getInt("idx");
                String Time = JArr.getJSONObject(i).getString("Time");
                String Title = JArr.getJSONObject(i).getString("Title");
                String Content = JArr.getJSONObject(i).getString("Content");

                MDModel.setDatas(idx,Time,Title,Content);
                MDModelList.add(MDModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.setItems(MDModelList);
            adapter.notifyDataSetChanged();
            Log.i(TAG,  "{\n"+MDModelList.get(i).getIdx()+"\n"+MDModelList.get(i).getTime()+"\n"+MDModelList.get(i).getTitle()+"\n"+MDModelList.get(i).getContent()+"\n}");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.AddMemoBtn:
                    SendToAddView();
                break;
        }
    }

    private void SendToAddView() {
        Intent intent = new Intent(MainActivity.this, MemoAddActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String ToastStr;
        switch (requestCode){
            case 0:
                ToastStr = "메모작성 실패..!";
                ChkActivityResult(resultCode, ToastStr);
                break;
            case 1:
                ToastStr = "업데이트 실패..!";
                ChkActivityResult(resultCode, ToastStr);
                break;
        }
    }

    private void ChkActivityResult(int resultCode, String ToastStr){
        if (resultCode == RESULT_OK){
            getJsons();
            Toast.makeText(this, "성공..!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, ToastStr, Toast.LENGTH_SHORT).show();
        }
    }

    public void DataDeleted(boolean isDataDeleted) {
        if (isDataDeleted){
            getJsons();
            Toast.makeText(this, "데이터 삭제..!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "삭제 실패..?", Toast.LENGTH_SHORT).show();
        }

    }
}
