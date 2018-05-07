package com.firebase.ginggingi.memoappwithserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.ConnServer.UpdateMemoData;

/**
 * Created by GingGingI on 2018-04-15.
 */

public class MemoUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private UpdateMemoData updata;

    private FloatingActionButton fab;

    private Intent intent;
    private int idx;
    private String title,date,content;

    private TextView DateView, TitleView, Contentview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatememo);
        init();
        viewInit();
    }

    private void init() {
        updata = new UpdateMemoData();

        TitleView = findViewById(R.id.TitleView);
        Contentview = findViewById(R.id.ContentView);
        DateView = findViewById(R.id.DateView);
        fab = findViewById(R.id.fab);
    }

    private void viewInit() {
        intent = getIntent();
        idx = intent.getIntExtra("idx",-1);
        title = intent.getStringExtra("Title");
        date = intent.getStringExtra("Date");
        content = intent.getStringExtra("Content");

        fab.setOnClickListener(this);

        TitleView.setText(title);
        Contentview.setText(content);
        DateView.setText(date);


        Toast.makeText(this, idx+".", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                updateData();
                break;
        }
    }

    private void updateData() {
        title = TitleView.getText().toString();
        content = Contentview.getText().toString();
        if (!ChkDataNull() && idx != -1){
                updata.InitData(idx, title, content, this);
                updata.UpdateData();
        }else{
            Log.i("MemoAddActivity :","title: "+title+" or Content:"+content+" is null at:"+ idx);
        }
    }

    private boolean ChkDataNull(){
        if (title.equals("") || title.equals(null) || content.equals("") || content.equals(null)){return true;}
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){return true;}
        //이것도 정규식을사용해서 보안도추가할껏
        return false;
    }

    public void DataUpdated(boolean dataUpdated) {
        if (dataUpdated){
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
