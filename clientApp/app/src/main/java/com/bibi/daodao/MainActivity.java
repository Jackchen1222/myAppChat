package com.bibi.daodao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bibi.daodao.tool.HttpTool;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Button testFunc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFunc = (Button)findViewById(R.id.testFunction);
        testFunc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.testFunction:
                HttpTool ht = new HttpTool(MainActivity.this);
                ht.get("10001", "chenbin", "我投翔");
                break;
             default:
        }
    }
}
