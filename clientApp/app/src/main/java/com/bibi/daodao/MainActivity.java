package com.bibi.daodao;

import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bibi.daodao.tool.HttpTool;
import com.bibi.daodao.tool.MyLog;
import com.bibi.daodao.tool.WebSocketManager;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button testFunc;
    HttpTool httpTool;
    public static TextView showMessage;
    private EditText sendContent;
    private WebSocketManager webSocketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMessage = (TextView)findViewById(R.id.showMessage);
        sendContent = (EditText)findViewById(R.id.sendContent);
        testFunc = (Button)findViewById(R.id.connectWebsocket);
        findViewById(R.id.sendMessage).setOnClickListener(this);
        testFunc.setOnClickListener(this);
        httpTool = new HttpTool(this);
        webSocketManager = new WebSocketManager();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.connectWebsocket:
//                ht.get("10001", "chenbin", "我投翔");
                webSocketManager.connect();
                break;
            case R.id.sendMessage:
                if(!sendContent.getText().toString().trim().equals("")){
//                    httpTool.sendMassage(sendContent.getText().toString().trim());

                    webSocketManager.sendMessage(sendContent.getText().toString().trim());
                }
                break;
             default:
        }
    }
}
