package com.firebase.ginggingi.memoappwithserver;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.ConnServer.SendMemoData;

public class MemoAddActivity extends AppCompatActivity implements OnClickListener{

    private SendMemoData sendData;

    private FloatingActionButton fab;
    private InputMethodManager imm;

    private EditText TitleView;
    private EditText ContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);

        sendData = new SendMemoData();

        TitleView = findViewById(R.id.TitleView);
        ContentView = findViewById(R.id.ContentView);

        fab = findViewById(R.id.fab);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        fab. setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (!sendData.isWorking) {
                    sendDatas();
                }else {
                    Toast.makeText(this, "alreadyWorking", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void sendDatas() {
        String Title,Content;
        Title = TitleView.getText().toString();
        Content = ContentView.getText().toString();
        if (!ChkDataNull(Title,Content)){
            sendData.InitData(Title, Content, this);
            sendData.SendData();
        }else{
            Log.i("MemoAddActivity :","Title or Content is null");
        }
    }

    private boolean ChkDataNull(String title, String content) {
        if (title.equals("") || title.equals(null) || content.equals("") || content.equals(null)){return true;}
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){return true;}
        //나중에 정규식을사용해서 보안도추가할껏
        return false;
    }

    public void DataSended(boolean datasended) {
        if (datasended){
            setResult(RESULT_OK);
            finish();
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
