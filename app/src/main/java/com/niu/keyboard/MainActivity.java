package com.niu.keyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * 自定义键盘
 * 身份证号码输入键盘
 */
public class MainActivity extends AppCompatActivity implements KeyboardUtil.onWhichKeyClickListener,View.OnClickListener {

    private TextInputLayout idCardInput;
    private EditText etIdCard;
    private KeyboardUtil keyboardUtil;
    private Button btnUp;
    private Button btnDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnUp = (Button) findViewById(R.id.btn_up);
        btnDown = (Button) findViewById(R.id.btn_down);
        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        idCardInput = (TextInputLayout) findViewById(R.id.input_idcard);
        etIdCard = idCardInput.getEditText();
        //自定义银行卡键盘
        keyboardUtil = new KeyboardUtil(this, this, etIdCard, R.id.keyboard_view, this);
        keyboardUtil.showKeyboard();


        etIdCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = etIdCard.getInputType();
                etIdCard.setInputType(InputType.TYPE_NULL);
                etIdCard.onTouchEvent(event);
                isShowAndroidKeyBoard(true);
                etIdCard.setInputType(inType);  
                String content = etIdCard.getText().toString();
                etIdCard.setSelection(content.length());
                return true;
            }
        });
    }

    /**
     * 系统键盘是否隐藏
     *
     * @param isShow true隐藏
     *               false显示
     */
    private void isShowAndroidKeyBoard(boolean isShow) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            //隐藏键盘，显示自定义键盘
            inputMethodManager.hideSoftInputFromWindow(etIdCard.getWindowToken(), 0);
            keyboardUtil.showKeyboard();
        } else {
            //隐藏自定义键盘
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            keyboardUtil.hideKeyboard();
        }

    }

    @Override
    public void keyDown(int keyCode) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_up:
                keyboardUtil.showKeyboard();
                break;
            case R.id.btn_down:
                keyboardUtil.hideKeyboard();
                break;
        }
    }
}
