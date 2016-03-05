package dev.woody.ext.info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;
import dev.woody.ext.incode2015.it_bw_login;
import dev.woody.ext.join.it_bw_reg_manage;

public class it_usercenter extends Activity {
    private ImageButton mBackbtn;
    private RelativeLayout mMenu1;
    private RelativeLayout mMenu2;
    private RelativeLayout mMenu3;
    private RelativeLayout mMenu4;
    private RelativeLayout mMenu5;
    private RelativeLayout mMenu6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_usercenter);

        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        mBackbtn = (ImageButton)findViewById(R.id.backbtn);
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //나의 계정관리
        mMenu1 = (RelativeLayout)findViewById(R.id.usercenter_1);
        mMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Mains.UserID.equals("") ){
                    Intent intent = new Intent(it_usercenter.this, it_bw_login.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(it_usercenter.this, it_bw_reg_manage.class);
                    startActivity(intent);
                }
            }
        });

        //자주 묻는 질문
        mMenu2 = (RelativeLayout)findViewById(R.id.usercenter_2);
        mMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(it_usercenter.this, it_qna.class);
                startActivity(intent);
            }
        });

        //관리자에게 메일보내기
        mMenu3 = (RelativeLayout)findViewById(R.id.usercenter_3);
        mMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("plain/text");
                String[] tos = { "incoder7@naver.com" };
                it.putExtra(Intent.EXTRA_EMAIL, tos);
                startActivity(Intent.createChooser(it, "Choose Email Client"));
            }
        });

        //인코더스 페이스북 바로가기
        mMenu4 = (RelativeLayout)findViewById(R.id.usercenter_4);
        mMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.facebook.com/incoders8");
                Intent it  = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });

        //인코더스 홈페이지 바로가기
        mMenu5 = (RelativeLayout)findViewById(R.id.usercenter_5);
        mMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.incoders.co.kr");
                Intent it  = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });

        //개인정보보호 및 이용약관
        mMenu6 = (RelativeLayout)findViewById(R.id.usercenter_6);
        mMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(it_usercenter.this, it_yakgwan_1.class);
                startActivity(intent);
            }
        });
    }

}
