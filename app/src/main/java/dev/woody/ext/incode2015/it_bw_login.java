package dev.woody.ext.incode2015;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import dev.woody.ext.join.it_bw_reg_person;

public class it_bw_login extends Activity {

    EditText input_email;
    EditText input_password;

    String naver_id;
    String naver_email;
    String naver_sex;
    String naver_name;
    String naver_birthday;

    String facebook_id;
    String facebook_sex;
    String facebook_name;

    public static Activity LoginActiviy;
    public static OAuthLogin mOAuthLoginInstance;
    /**
     * naver client 정보를 넣어준다.
     */
    private static String OAUTH_CLIENT_ID = "Sq6TT8KymnmcK1GUQVV9";
    private static String OAUTH_CLIENT_SECRET = "YNahqJAfP0";
    private static String OAUTH_CLIENT_NAME = "INCODE";

    /**
     * facebook client 정보
     */
    private static String FACEBOOK_APP_ID = "867916543304385";
    //private static String

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    //public static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    private OAuthLoginButton mOAuthLoginButton;

    SQLiteDatabase database;
    String dbName = "incode";
    String createTable = "create table naverjoin (id integer primary key , email text);";

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
        //페이스북 sdk import
        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.lay_it_login);

        LoginActiviy = it_bw_login.this;

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object, GraphResponse response) {
                            // Application code
                            Log.v("LoginActivity", response.toString());
                            facebook_id = object.optString("id");
                            facebook_name = object.optString("name");
                            facebook_sex = object.optString("gender");
                            //Log.d("facebook", response.toString() );
                            //Log.d("facebook", facebook_id + " " + facebook_name + " " + facebook_sex);
                            new FacebookJoinTask().execute();
                        }
                    }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                //new FacebookJoinTask().execute();
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException e) {}
        });

/*        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    "dev.woody.ext.incode2015",
                    PackageManager.GET_SIGNATURES);

            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT) );
            }
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch(NoSuchAlgorithmException e){}*/

        input_email = (EditText) findViewById(R.id.login_input_email1);
        input_password = (EditText) findViewById(R.id.login_input_password);

        ImageButton btnback = (ImageButton) findViewById(R.id.backbtn);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btninput = (Button) findViewById(R.id.btninput);
        btninput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpTask().execute();
            }
        });

        ImageButton btnnew = (ImageButton) findViewById(R.id.btnnew);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new HttpTask2().execute();
                Intent intent;
                intent = new Intent(getApplicationContext(), it_bw_reg_person.class);
                startActivity(intent);
            }
        });

        Button btnfindpw = (Button)findViewById(R.id.btnfindpw);
        btnfindpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,it_bw_find_pw.class);
                startActivity(intent);
            }
        });

        OAuthLoginDefine.DEVELOPER_VERSION = true;
        mContext = this;

        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        //Check if user is currently logged in
        if ( Mains.UserID.equals("") ) {
            LoginManager.getInstance().logOut();
            mOAuthLoginInstance.logoutAndDeleteToken(mContext);
        }

    }

    @Override
    public void onStop(){
        super.onStop();
    }

    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                //Toast.makeText(mContext, "성공", Toast.LENGTH_SHORT).show();
                new InfoApiTask().execute();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class FacebookJoinTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/reg_facebookjoin.php";
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", facebook_id));
                nameValue.add(new BasicNameValuePair("user_pw", "1234"));
                nameValue.add(new BasicNameValuePair("jointype", "facebook"));
                nameValue.add(new BasicNameValuePair("name", facebook_name));
                if( facebook_sex.equals("male") )
                    facebook_sex = "M";
                else
                    facebook_sex = "W";
                nameValue.add(new BasicNameValuePair("sex", facebook_sex));

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

            if(value.equals("-1"))
                Toast.makeText(it_bw_login.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            else
            {
                Mains.UserID = facebook_id;
                Mains.UserPW = "1234";
                Mains.LoginType = "facebook";
                Mains.UserEmail = facebook_name;
                Mains.drawerMenuAdapter.notifyDataSetChanged();
                savePreferences();
                finish();
            }
        }
    }

    private class InfoApiTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {}

        @Override
        protected Void doInBackground(Void... params) {
            String url = "https://apis.naver.com/nidlogin/nid/getUserProfile.xml";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            String result = mOAuthLoginInstance.requestApi(mContext, at, url);
            //Log.d("mxl",result);

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream istream = new ByteArrayInputStream(result.getBytes("utf-8"));
                Document doc = builder.parse(istream);
                Element data = doc.getDocumentElement();

                NodeList items_id = data.getElementsByTagName("id");
                NodeList items_email = data.getElementsByTagName("email");
                NodeList items_sex = data.getElementsByTagName("gender");
                NodeList items_name = data.getElementsByTagName("name");
                NodeList items_birthday = data.getElementsByTagName("birthday");

                Node item_id = items_id.item(0);
                Node item_email = items_email.item(0);
                Node item_sex = items_sex.item(0);
                Node item_name = items_name.item(0);
                Node item_birthday = items_birthday.item(0);
                naver_id = item_id.getFirstChild().getNodeValue().toString();
                naver_email = item_email.getFirstChild().getNodeValue().toString();
                naver_sex = item_sex.getFirstChild().getNodeValue().toString();
                naver_name = item_name.getFirstChild().getNodeValue().toString();
                naver_birthday = item_birthday.getFirstChild().getNodeValue().toString();

            } catch (Exception e) {
                ;
            }

            //Log.d("test xml", email +"");
            //Log.d("test xml", email + " " + name + " " + sex + " " +  birthday);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //pd.dismiss();
            Mains.UserID = naver_id;
            Mains.UserPW = "1234";
            Mains.LoginType = "naver";
            Mains.UserEmail = naver_email;
            Mains.drawerMenuAdapter.notifyDataSetChanged();
            new NaverJoinTask().execute();
            savePreferences();
            //Log.d("test date", "두번째 로그인");
            finish();
        }
    }

    private class NaverJoinTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/reg_naverjoin.php";
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", naver_id));
                nameValue.add(new BasicNameValuePair("user_pw", "1234"));
                nameValue.add(new BasicNameValuePair("email", naver_email));
                nameValue.add(new BasicNameValuePair("name", naver_name));
                nameValue.add(new BasicNameValuePair("sex", naver_sex));
                nameValue.add(new BasicNameValuePair("phone", "010"));
                nameValue.add(new BasicNameValuePair("option", "person"));
                nameValue.add(new BasicNameValuePair("jointype", "naver"));

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

            if(value.equals("-1"))
                Toast.makeText(it_bw_login.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            else
            {
                Mains.UserID = naver_id;
                Mains.UserPW = "1234";
                Mains.LoginType = "naver";
                Mains.UserEmail = naver_email;
                Mains.drawerMenuAdapter.notifyDataSetChanged();
                savePreferences();
                finish();
            }
        }
    }

    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/login.php";
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                String id = input_email.getText().toString();

                nameValue.add(new BasicNameValuePair("user_id", id));
                nameValue.add(new BasicNameValuePair("user_pw", input_password.getText().toString()));

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
                //결과창뿌려주기 - ui 변경시 에러
                //result.setText(total);
                //GetText = total;
                return total;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }

        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            //result.setText(value);
            Log.e("받은값", value);

            if(value.equals("-1"))
            {
                Toast.makeText(it_bw_login.this, "아이디 또는 패스워드를 잘못입력하셨습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(value.equals("0"))
            {
                Toast.makeText(it_bw_login.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(value.charAt(0)=='1'){

                Mains.drawerMenuAdapter.notifyDataSetChanged();
                String id = input_email.getText().toString();
                Mains.UserID = id;
                Mains.UserPW = input_password.getText().toString();
                Mains.LoginType = "email";
                savePreferences();
                finish();
            }
            else{
                Toast.makeText(it_bw_login.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}