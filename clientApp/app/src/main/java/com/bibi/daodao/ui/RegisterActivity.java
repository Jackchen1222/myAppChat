package com.bibi.daodao.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bibi.daodao.R;
import com.bibi.daodao.tool.HttpTool;
import com.bibi.daodao.tool.MyLog;
import com.bibi.daodao.tool.UserInfo;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";

    private boolean sex_selected;
    private ImageView manicon, womanicon;
    private TextView mantext, womantext;
    private EditText etPassword, etNickname;
    private HttpTool httpTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_two);

        initView();
        initValue();
    }

    private void initView(){
        etNickname = (EditText) findViewById(R.id.et_register_username);
        etPassword = (EditText) findViewById(R.id.et_register_pwd_input);
        findViewById(R.id.bt_register_submit).setOnClickListener(this);
        manicon = (ImageView)findViewById(R.id.iv_register_man);
        manicon.setOnClickListener(this);
        womanicon = (ImageView)findViewById(R.id.iv_register_female);
        womanicon.setOnClickListener(this);
        mantext = (TextView)findViewById(R.id.tv_register_man);
        womantext = (TextView)findViewById(R.id.tv_register_female);
    }

    private void initValue(){
        httpTool = new HttpTool(this);
        sex_selected = true;
        if(sex_selected){
            manicon.setBackgroundColor(getResources().getColor(R.color.royalblue));
            womanicon.setBackgroundResource(0);
        }
    }

    private UserInfo getEditContent(){
        UserInfo ui = new UserInfo();
        ui.nickname = etNickname.getText().toString().trim();
        ui.password = etPassword.getText().toString().trim();
        ui.sex = sex_selected;
        if(!ui.nickname.equals("") && ui.password.length() >=6 ) {
            return ui;
        }else{
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_submit:
                UserInfo ui = getEditContent();
                httpTool.registerPost(ui);
                break;
            case R.id.iv_register_man:
                manicon.setBackgroundColor(getResources().getColor(R.color.royalblue));
                womanicon.setBackgroundResource(0);
                sex_selected = true;
                break;
            case R.id.iv_register_female:
                womanicon.setBackgroundColor(getResources().getColor(R.color.hotpink));
                manicon.setBackgroundResource(0);
                sex_selected = false;
                break;
            case R.id.ib_navigation_back:
                finish();
                break;
            default:
        }
    }
}