package dev.woody.ext.codemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import dev.woody.ext.incode2015.R;

public class it_bw_list_date extends Activity {
    private ImageButton imageButton_1;
    private ImageButton imageButton_2;
    private ImageButton imageButton_3;
    private ImageButton imageButton_4;
    private Button cre_date_ok;
    private Button cre_date_cancel;

    private String mDate_tmp ="7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.lay_it_bw_list_date);

        imageButton_1 = (ImageButton) findViewById(R.id.imageButton_1);
        imageButton_2 = (ImageButton) findViewById(R.id.imageButton_2);
        imageButton_3 = (ImageButton) findViewById(R.id.imageButton_3);
        imageButton_4 = (ImageButton) findViewById(R.id.imageButton_4);

        if( it_bw_list_2.mDate == null )
            it_bw_list_2.mDate = "7";

        if( it_bw_list_2.mDate.equals("7") )
            imageButton_1.setBackgroundResource(R.drawable.cre_date_check_1);
        else if( it_bw_list_2.mDate.equals("14") )
            imageButton_2.setBackgroundResource(R.drawable.cre_date_check_1);
        else if( it_bw_list_2.mDate.equals("21") )
            imageButton_3.setBackgroundResource(R.drawable.cre_date_check_1);
        else if( it_bw_list_2.mDate.equals("30") )
            imageButton_4.setBackgroundResource(R.drawable.cre_date_check_1);

        imageButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_1.setBackgroundResource(R.drawable.cre_date_check_1);
                imageButton_2.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_3.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_4.setBackgroundResource(R.drawable.cre_date_check);
                mDate_tmp = "7";
            }
        });
        imageButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_1.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_2.setBackgroundResource(R.drawable.cre_date_check_1);
                imageButton_3.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_4.setBackgroundResource(R.drawable.cre_date_check);
                mDate_tmp = "14";
            }
        });
        imageButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_1.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_2.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_3.setBackgroundResource(R.drawable.cre_date_check_1);
                imageButton_4.setBackgroundResource(R.drawable.cre_date_check);
                mDate_tmp = "21";
            }
        });
        imageButton_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton_1.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_2.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_3.setBackgroundResource(R.drawable.cre_date_check);
                imageButton_4.setBackgroundResource(R.drawable.cre_date_check_1);
                mDate_tmp = "30";
            }
        });

        cre_date_ok = (Button) findViewById(R.id.cre_date_okay);
        cre_date_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it_bw_list_2.mDate = mDate_tmp;
                Intent intent = new Intent();
                intent.putExtra("result", "0");
                setResult(0, intent);
                finish();
            }
        });

        cre_date_cancel = (Button) findViewById(R.id.cre_date_cancel);
        cre_date_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", "1");
                setResult(0, intent);
                finish();
            }
        });
    }

}
