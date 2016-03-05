package dev.woody.ext.join;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

public class it_bw_reg_manage extends Activity {
    private ImageButton btn_back;
    private TextView email;
    private ImageView manage_naver;
    private ImageView manage_facebook;
    private ImageView manage_email;
    private EditText new_password;
    private EditText re_password;
    private LinearLayout password_layout;
    private ImageButton man_check;
    private ImageButton woman_check;
    private ImageButton none_check;
    private Spinner local_spinner;
    private Button manage_btn_1;
    private Button manage_btn_ok;

    private String mSex = "";
    private String mArea = "";
    private ProgressDialog pd;
    private Context mContext;

    public static Activity manageActivity;


    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", Mains.UserID);
        editor.putString("pw", Mains.UserPW);
        editor.putString("email", Mains.UserEmail);
        editor.putString("logintype", Mains.LoginType);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw_reg_manage);

        mContext = this;

        manageActivity = it_bw_reg_manage.this;


        if(Mains.LoginType.equals("email")){
            Intent intent = new Intent(it_bw_reg_manage.this,it_bw_reg_password_check.class);
            startActivity(intent);
        }

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email = (TextView)findViewById(R.id.email);
        if( Mains.LoginType.equals("email") ){
            email.setText(Mains.UserID.toString());
        }else {
            email.setText(Mains.UserEmail.toString());
        }

        manage_naver = (ImageView)findViewById(R.id.manage_naver);
        manage_facebook = (ImageView)findViewById(R.id.manage_facebook);
        manage_email = (ImageView)findViewById(R.id.manage_email);
        password_layout = (LinearLayout)findViewById(R.id.password_layout);
        manage_btn_1 = (Button)findViewById(R.id.manage_btn_1);
        if(Mains.LoginType.equals("email")){
            manage_email.setImageResource(R.drawable.email_1);
            manage_naver.setImageResource(R.drawable.naver);
            manage_facebook.setImageResource(R.drawable.facebook);
            password_layout.setVisibility(View.VISIBLE);
            manage_btn_1.setText("회원탈퇴");
        }else if(Mains.LoginType.equals("facebook")){
            manage_email.setImageResource(R.drawable.email);
            manage_naver.setImageResource(R.drawable.naver);
            manage_facebook.setImageResource(R.drawable.facebook_1);
            password_layout.setVisibility(View.GONE);
            manage_btn_1.setText("로그아웃");
        }else if(Mains.LoginType.equals("naver")){
            manage_email.setImageResource(R.drawable.email);
            manage_naver.setImageResource(R.drawable.naver_1);
            manage_facebook.setImageResource(R.drawable.facebook);
            password_layout.setVisibility(View.GONE);
            manage_btn_1.setText("로그아웃");
        }

        new_password = (EditText)findViewById(R.id.new_password);
        re_password = (EditText)findViewById(R.id.re_password);

        man_check = (ImageButton)findViewById(R.id.man_check);
        woman_check = (ImageButton)findViewById(R.id.woman_check);
        none_check = (ImageButton)findViewById(R.id.none_check);
        man_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man_check.setImageResource(R.drawable.manage_check_box_1);
                woman_check.setImageResource(R.drawable.manage_check_box);
                none_check.setImageResource(R.drawable.manage_check_box);
                mSex = "M";
            }
        });
        woman_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man_check.setImageResource(R.drawable.manage_check_box);
                woman_check.setImageResource(R.drawable.manage_check_box_1);
                none_check.setImageResource(R.drawable.manage_check_box);
                mSex = "W";
            }
        });
        none_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man_check.setImageResource(R.drawable.manage_check_box);
                woman_check.setImageResource(R.drawable.manage_check_box);
                none_check.setImageResource(R.drawable.manage_check_box_1);
                mSex = "N";
            }
        });

        local_spinner = (Spinner)findViewById(R.id.local_spinner);
        local_spinner.setPrompt("지역선택");
        ArrayList mlist = new ArrayList<String>();
        mlist.add("선택안함");
        mlist.add("서울");
        mlist.add("인천/경기");
        mlist.add("강원");
        mlist.add("부산/울산/경남");
        mlist.add("대구/경북");
        mlist.add("충북");
        mlist.add("대전/충남");
        mlist.add("광주/전남");
        mlist.add("전북");
        mlist.add("제주");
        ArrayAdapter<String> mSpinerAdapter =
                new ArrayAdapter<String>(it_bw_reg_manage.this, android.R.layout.simple_spinner_item, mlist);
        mSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        local_spinner.setAdapter(mSpinerAdapter);

        manage_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Mains.LoginType.equals("email")) { //회원탈퇴
                    new AlertDialog.Builder(mContext)
                            .setTitle("인코드 계정을 삭제하시겠습니까?")
                            .setMessage("계정을 삭제하시면 생성코드 및 정보가 삭제되면 복구가 불가능합니다.")
                            .setPositiveButton("확인",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            new DeleteUserTask().execute();
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("취소",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                } else if (Mains.LoginType.equals("naver")) {
                    Mains.LoginType = "";
                    Mains.UserID = "";
                    Mains.UserPW = "";
                    Mains.UserEmail = "";
                    savePreferences();
                    Mains.drawerMenuAdapter.notifyDataSetChanged();
                    finish();
                } else {
                    Mains.LoginType = "";
                    Mains.UserID = "";
                    Mains.UserPW = "";
                    Mains.UserEmail = "";
                    savePreferences();
                    Mains.drawerMenuAdapter.notifyDataSetChanged();
                    finish();
                }
            }
        });

        manage_btn_ok = (Button)findViewById(R.id.manage_btn_ok);
        manage_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !new_password.getText().toString().equals(re_password.getText().toString()) && Mains.LoginType.equals("email") ){
                    Toast.makeText(it_bw_reg_manage.this,"비밀번호를 재확인 해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    new ChangeUserTask().execute();
                }
            }
        });

        new UserInfoTask().execute();
    }

    private class UserInfoTask extends AsyncTask<Void, Void, Void> {
        String urlPath = "http://incoders.co.kr/incode/user_info.php";
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }
        @Override
        protected Void doInBackground(Void... params) {
            String value = "";
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));
                nameValue.add(new BasicNameValuePair("user_pw", Mains.UserPW));
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        value += tmp;
                    }
                }

                im.close();
                //return value;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("받은값", value);


            try {
                JSONObject object = new JSONObject(value);

                mSex = object.getString("sex");
                mArea = object.getString("area");
                Log.d("ddd!!!!",object.toString());
                Log.d("ddd",mArea+" "+mSex);
                Log.d("ddd~~~",Mains.UserID +" "+Mains.UserPW);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            //result.setText(value)
            if(mSex.equals("M")) {
                man_check.setImageResource(R.drawable.manage_check_box_1);
                woman_check.setImageResource(R.drawable.manage_check_box);
                none_check.setImageResource(R.drawable.manage_check_box);
            }else if(mSex.equals("W")){
                man_check.setImageResource(R.drawable.manage_check_box);
                woman_check.setImageResource(R.drawable.manage_check_box_1);
                none_check.setImageResource(R.drawable.manage_check_box);
                mSex = "W";
            }else{
                man_check.setImageResource(R.drawable.manage_check_box);
                woman_check.setImageResource(R.drawable.manage_check_box);
                none_check.setImageResource(R.drawable.manage_check_box_1);
                mSex = "N";
            }

            if( mArea.equals("서울")) {
                local_spinner.setSelection(1);
            }else if( mArea.equals("인천/경기") ){
                local_spinner.setSelection(2);
            }else if( mArea.equals("강원") ){
                local_spinner.setSelection(3);
            }else if( mArea.equals("부산/울산/경남") ){
                local_spinner.setSelection(4);
            }else if( mArea.equals("대구/경북") ){
                local_spinner.setSelection(5);
            }else if( mArea.equals("충북") ){
                local_spinner.setSelection(6);
            }else if( mArea.equals("대전/충남") ){
                local_spinner.setSelection(7);
            }else if( mArea.equals("광주/전남") ){
                local_spinner.setSelection(8);
            }else if( mArea.equals("전북") ){
                local_spinner.setSelection(9);
            }else if( mArea.equals("제주") ){
                local_spinner.setSelection(10);
            }else{
                local_spinner.setSelection(0);
            }
            pd.dismiss();
        }
    }

    private class DeleteUserTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/delete_user.php";
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));
                nameValue.add(new BasicNameValuePair("user_pw", Mains.UserPW));

                //웹 접속 - utf-8 방식으로
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();

                return total;
            }catch(Exception e){

            }
            return null;
        }
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            //result.setText(value);
            Log.e("받은값", value);
            Mains.LoginType = "";
            Mains.UserID = "";
            Mains.UserPW = "";
            Mains.UserEmail = "";
            savePreferences();
            Mains.drawerMenuAdapter.notifyDataSetChanged();
            finish();
            pd.dismiss();
        }
    }

    private class ChangeUserTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/change_user.php";
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));
                nameValue.add(new BasicNameValuePair("user_pw", Mains.UserPW));
                nameValue.add(new BasicNameValuePair("user_new_pw",  new_password.getText().toString() ));
                nameValue.add(new BasicNameValuePair("sex", mSex));
                nameValue.add(new BasicNameValuePair("area", local_spinner.getSelectedItem().toString() ));

                //웹 접속 - utf-8 방식으로
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();

                return total;
            }catch(Exception e){

            }
            return null;
        }
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            //result.setText(value);
            Log.e("받은값", value);
            if( new_password.getText().toString().equals(re_password.getText().toString()) && new_password.getText().length() !=0 ){
                Mains.UserPW = new_password.getText().toString();
            }
            pd.dismiss();

            Toast.makeText(it_bw_reg_manage.this,"성공적으로 변경하였습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
