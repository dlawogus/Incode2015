package dev.woody.ext.incode2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;


public class it_bw_find_pw extends Activity {
    private ImageButton backbtn;
    private EditText edit_email;
    private LinearLayout lay_email_check;
    private Button pw_send;
    private ProgressDialog pd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw_find_pw);

        mContext = this;

        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        ImageButton btnback = (ImageButton)findViewById(R.id.backbtn);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_email = (EditText)findViewById(R.id.edit_email);
        lay_email_check = (LinearLayout)findViewById(R.id.lay_email_check);
        pw_send = (Button)findViewById(R.id.pw_send);
        pw_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HttpTask().execute();
                //Toast.makeText(it_bw_find_pw.this,"이메일로 가입한 고객님만 사용하실 수 있습니다.",Toast.LENGTH_SHORT).show();

            }
        });
    }

    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/change_passwd.php";

        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String total = "";
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", edit_email.getText().toString() ));

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
            return total;
        }

        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            pd.dismiss();
            if(value.equals("not_email")){
                Toast.makeText(it_bw_find_pw.this,"이메일로 가입한 고객님만 사용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
            }else if( value.equals("no") ){
                lay_email_check.setVisibility(View.VISIBLE);
            }else{
                new AlertDialog.Builder(mContext)
                    .setTitle("임시 비밀번호가 발송되었습니다")
                    .setMessage("로그인 하신 계정설정 페이지에서 비밀번호를 변경하세요")
                    .setPositiveButton("확인",
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                    .create().show();
            }



        }
    }
}
