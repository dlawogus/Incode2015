package dev.woody.ext.join;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

public class it_bw_reg_password_check extends Activity {

    private ProgressDialog pd;
    private Context mContext;
    private EditText password_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
        //        WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        WindowManager.LayoutParams lpWindow_1 = new WindowManager.LayoutParams();
        lpWindow_1.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND;

        getWindow().setAttributes(lpWindow_1);
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.lay_it_bw_reg_password_check);

        mContext = this;

        password_check = (EditText)findViewById(R.id.password_check);

        Button cre_date_okay = (Button)findViewById(R.id.cre_date_okay);
        cre_date_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserCheckTask().execute();
            }
        });

        Button cre_date_cancel = (Button)findViewById(R.id.cre_date_cancel);
        cre_date_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it_bw_reg_manage.manageActivity.finish();
                finish();
            }
        });
    }

    private class UserCheckTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/user_check.php";
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
                nameValue.add(new BasicNameValuePair("user_pw", password_check.getText().toString()) );

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
            if( value.equals("is_ok") ){
                finish();
            }else{
                Toast.makeText(it_bw_reg_password_check.this,"비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show();
                it_bw_reg_manage.manageActivity.finish();
                finish();
            }

            pd.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
