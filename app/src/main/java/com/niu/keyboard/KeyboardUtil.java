package com.niu.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * Created by niutingting on 2017/5/23.
 */

public class KeyboardUtil {


    private Context ctx;
    private Activity act;
    private KeyboardView keyboardView;
    private Keyboard keyboard;// 数字键盘
    private onWhichKeyClickListener keyClickListener;

    private EditText ed;

    public KeyboardUtil(Activity act, Context ctx, EditText edit, int res, onWhichKeyClickListener keyClickListener) {
        this.act = act;
        this.ctx = ctx;
        this.ed = edit;
        this.keyClickListener = keyClickListener;
        keyboard = new Keyboard(ctx, R.xml.idcard_keyboard);
        keyboardView = (KeyboardView) act.findViewById(res);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(listener);
        hideKeyboard();
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                keyClickListener.keyDown(Keyboard.KEYCODE_CANCEL);
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 删除
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else if (primaryCode == 58) {//清空
                editable.clear();
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        TranslateAnimation animation = new TranslateAnimation(0f, 0f, keyboardView.getHeight(), Animation.RELATIVE_TO_SELF);
        animation.setDuration(300);
        keyboardView.setAnimation(animation);

        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            //加入退出动画
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        TranslateAnimation animation = new TranslateAnimation(0f, 0f, Animation.RELATIVE_TO_SELF, keyboardView.getHeight());
        animation.setDuration(400);
        keyboardView.setAnimation(animation);
        if (visibility == View.VISIBLE) {
            //加入进入动画
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 是否显示了键盘
     *
     * @return
     */
    public boolean isShowing() {
        return keyboardView.getVisibility() == View.VISIBLE ? true : false;
    }


    public interface onWhichKeyClickListener {
        void keyDown(int keyCode);
    }
}
