package dev.woody.ext.join;

/**
 * Created by Geoji on 2015-05-26.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;
import dev.woody.ext.incode2015.it_bw_login;

public class it_bw_reg_person extends Activity {
    String Fid = "ibuserid.txt";
    String Fpw = "ibuserpw.txt";
    String Fye = "ibuserye.txt";

    LinearLayout pwline1;
    LinearLayout pwline2;
    LinearLayout emailline;

    Button btncheck;
    Button btnnext;

    TextWatcher tw;

    Intent intent;
    String person_company;
    String checked_id;
    boolean isChecked;

    String mUserid;
    String mPassword;
    String mPassword_re;
    String mSex;
    String mLocal;
    String mType = "person";

    boolean mMan_check = false;
    boolean mWoman_check = false;
    boolean mUnknown_check = false;
    public static boolean mYakgwan_chek = false;

    EditText reg_person_input_email;
    EditText reg_person_input_pw;
    EditText reg_person_input_pw2;
    Spinner mSpiner;
    ImageButton checkbox_man;
    ImageButton checkbox_woman;
    ImageButton checkbox_unknown;

    // 값 저장하기
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", Mains.UserID);
        editor.putString("pw", Mains.UserPW);
        editor.putString("logintype", Mains.LoginType);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_reg_person);

        mSpiner = (Spinner) findViewById(R.id.spinner);
        mSpiner.setPrompt("지역선택");
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
                new ArrayAdapter<String>(it_bw_reg_person.this, android.R.layout.simple_spinner_item, mlist);
        mSpinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpiner.setAdapter(mSpinerAdapter);

        ImageButton btnback = (ImageButton)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        reg_person_input_email = (EditText) findViewById(R.id.reg_person_input_email);
        reg_person_input_pw = (EditText) findViewById(R.id.reg_person_input_pw);
        reg_person_input_pw2 = (EditText) findViewById(R.id.reg_person_input_pw2);

        //완료 버튼
        Button reg_btn_ok = (Button)findViewById(R.id.reg_btn_ok);
        reg_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserid = reg_person_input_email.getText().toString();
                mPassword = reg_person_input_pw.getText().toString();
                mPassword_re = reg_person_input_pw2.getText().toString();
                mLocal = mSpiner.getSelectedItem().toString();

                if( !mPassword.equals(mPassword_re) ){
                    Toast.makeText(it_bw_reg_person.this,"비밀번호 확인해주세요",Toast.LENGTH_SHORT).show();
                }else if( mUserid == null || mSex == null ){
                    Toast.makeText(it_bw_reg_person.this,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                }else if( !mYakgwan_chek ){
                    Toast.makeText(it_bw_reg_person.this,"약관에 동의해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    if ( isEmail( mUserid ) )
                     new HttpTask2().execute();
                    else{
                        Toast.makeText(it_bw_reg_person.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //약관 동의
        Button reg_btn_yakgwan = (Button)findViewById(R.id.reg_btn_yakgwan);
        reg_btn_yakgwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(it_bw_reg_person.this, it_bw_reg_yakgwan.class);
                startActivity(intent);
            }
        });

        checkbox_man = (ImageButton) findViewById(R.id.checkbox_man);
        checkbox_woman = (ImageButton) findViewById(R.id.checkbox_woman);
        checkbox_unknown = (ImageButton) findViewById(R.id.checkbox_unknown);
        checkbox_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMan_check) {
                    checkbox_man.setImageResource(R.drawable.checkbox_1);
                    checkbox_woman.setImageResource(R.drawable.checkbox);
                    checkbox_unknown.setImageResource(R.drawable.checkbox);
                    mSex = "M";
                    mMan_check = true;
                }else{
                    checkbox_man.setImageResource(R.drawable.checkbox);
                    checkbox_woman.setImageResource(R.drawable.checkbox);
                    checkbox_unknown.setImageResource(R.drawable.checkbox);
                    mSex = null;
                    mMan_check = false;
                }
            }
        });
        checkbox_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mWoman_check) {
                    checkbox_man.setImageResource(R.drawable.checkbox);
                    checkbox_woman.setImageResource(R.drawable.checkbox_1);
                    checkbox_unknown.setImageResource(R.drawable.checkbox);
                    mSex = "W";
                    mWoman_check = true;
                }else{
                    checkbox_man.setImageResource(R.drawable.checkbox);
                    checkbox_woman.setImageResource(R.drawable.checkbox);
                    checkbox_unknown.setImageResource(R.drawable.checkbox);
                    mSex = null;
                    mWoman_check = false;
                }
            }
        });
        checkbox_unknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mUnknown_check ) {
                    checkbox_man.setImageResource(R.drawable.checkbox);
                    checkbox_woman.setImageResource(R.drawable.checkbox);
                    checkbox_unknown.setImageResource(R.drawable.checkbox_1);
                    mSex = "U";
                    mUnknown_check = true;
                }else{
                    checkbox_man.setImageResource(R.drawable.checkbox);
                    checkbox_woman.setImageResource(R.drawable.checkbox);
                    checkbox_unknown.setImageResource(R.drawable.checkbox);
                    mSex = null;
                    mUnknown_check = false;
                }
            }
        });

    }

    public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches(
                "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",
                email.trim());
        return b;
    }
    /*
    ID exist check
     */
    /*
    class HttpTask extends AsyncTask<Void, Void, String>
    {
        String urlPath = "http://incoders.co.kr/incode/reg_isIdExist.php";
        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", email));

                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();
                return total;
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            Log.e("받은값", value);

            if(value.equals("101"))
            {
                isChecked = true;
                new HttpTask2().execute();
            }
            else if(value.equals("201"))
            {
                emailText1.requestFocus();
                isChecked = false;
                new AlertDialog.Builder(it_bw_reg_person.this)
                        .setTitle("Alert")
                        .setMessage("존재하는 아이디입니다.")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //onResume();
                                    }
                                })
                        .create().show();
            }
            else if(value.equals("301"))
            {
                emailText1.requestFocus();
                isChecked = false;
                new AlertDialog.Builder(it_bw_reg_person.this)
                        .setTitle("Error")
                        .setMessage("오류가 발생했습니다")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //onResume();
                                    }
                                })
                        .create().show();
            }
            else
            {
                emailText1.requestFocus();
                isChecked = false;
                new AlertDialog.Builder(it_bw_reg_person.this)
                        .setTitle("Error")
                        .setMessage("오류가 발생했습니다")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //onResume();
                                    }
                                })
                        .create().show();
            }
        }
    }*/

    class HttpTask2 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/reg_person.php";
        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", mUserid));
                nameValue.add(new BasicNameValuePair("user_pw", mPassword));
                nameValue.add(new BasicNameValuePair("option", mType));
                nameValue.add(new BasicNameValuePair("sex", mSex));
                nameValue.add(new BasicNameValuePair("local", mLocal));
                nameValue.add(new BasicNameValuePair("jointype", "email"));


                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();
                return total;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            Log.e("받은값", value);

            if(value.equals("0"))
            {
                Toast.makeText(it_bw_reg_person.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
            }
            else if(value.equals("-1"))
            {
                Toast.makeText(it_bw_reg_person.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(it_bw_reg_person.this, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                it_bw_login bActivity = (it_bw_login)it_bw_login.LoginActiviy;
                bActivity.finish();

                Mains.UserID = mUserid;
                Mains.UserPW = mPassword;
                Mains.LoginType = "email";
                Mains.drawerMenuAdapter.notifyDataSetChanged();
                savePreferences();
                finish();
            }
        }
    }
}