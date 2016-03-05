package dev.woody.ext.dialog;

/**
 * Created by imjaehyun on 15. 8. 23..
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import dev.woody.ext.incode2015.R;

public class CustomDialog_gong extends Dialog{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.custom_dialog_gong);
        setLayout();
        setClickListener(mLeftClickListener , mRightClickListener);
    }

    public CustomDialog_gong(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    public CustomDialog_gong(Context context, String title,
                                View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }

    public CustomDialog_gong(Context context,View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);

        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }


    private void setClickListener(View.OnClickListener left , View.OnClickListener right){
        if(left!=null && right!=null){
            mFirstButton.setOnClickListener(left);
            mSecondButton.setOnClickListener(right);
        }else if(left!=null && right==null){
            mFirstButton.setOnClickListener(left);
        }else {

        }
    }

    private ImageButton mFirstButton;
    private ImageButton mSecondButton;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    private void setLayout(){
        mFirstButton = (ImageButton) findViewById(R.id.save_image);
        mSecondButton = (ImageButton) findViewById(R.id.share_image);
    }

}