package dev.woody.ext.incode2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import dev.woody.ext.adapter.DrawerMenuAdapter;
import dev.woody.ext.codecreation.it_bw_cre;
import dev.woody.ext.codemanager.it_bw_list;
import dev.woody.ext.info.it_info;
import dev.woody.ext.info.it_notice;
import dev.woody.ext.info.it_usercenter;
import dev.woody.ext.join.it_bw_reg_manage;


public class Mains extends Activity{
    public String Version = "1";
    public static String UserID = "";
    public static String LoginType = "";
    public static String UserPW = "";
    public static String UserEmail = "";
    public String vcodex = "";
    //public String UserLoginType = "";
    private ArrayList<String> codeArr;
    private String grant = "";
    private Fragment topsfragment;
    private long backkeyPressedTime;

    public static FragmentManager fragmentManager;				//드로어
    public static DrawerLayout mDrawerLayout;					//드로어 레이아웃
    public static ListView mDrawerList;							//드로어 리스트
    //public static int fragmentIndex = -1;						//프래그먼트 인덱스
    public static DrawerMenuAdapter drawerMenuAdapter = null;
    public static String mDrawerState = "close";				//드로어 상태
    private Context mContext;

    public static SQLiteDatabase database;
    public static String dbName = "incode";
    String createTable = "create table codehistory (code text primary key);";

    // 값 저장하기zzzzzzz
    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", UserID);
        editor.putString("pw", UserPW);
        editor.putString("logintype", LoginType);
        editor.putString("email", UserEmail);
        editor.commit();
    }

    // 값 불러오기
    private void getPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        UserID = pref.getString("id", "");
        UserPW = pref.getString("pw", "");
        UserEmail = pref.getString("email","");
        LoginType = pref.getString("logintype", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_mains);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        backkeyPressedTime = System.currentTimeMillis();

        mContext = this;
        getPreferences();
        createDatabase();
        createTable();

        if(getIntent()!=null){
            Uri uri = getIntent().getData();
            if(uri != null){

                Uri uriData = getIntent().getData();
                String xicode = uriData.getQueryParameter("code");
                if(xicode.length() >0)
                {
                    vcodex = xicode;
                }
            }
        }

        if(!UserID.equals("") && !UserPW.equals("")){
            new HttpTask().execute();
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //드로어 오픈시 그림자 설정
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        drawerMenuAdapter = new DrawerMenuAdapter(this);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(drawerMenuAdapter);							//어댑터
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());	//드로어 아이템 클릭리스
        mDrawerLayout.setDrawerListener(myDrawerListener);					//드로어 오픈/클로즈 감시 리스너

        if (savedInstanceState == null) {
            //selectItem(0);
            topsfragment = new fr_pad();
            FragmentManager topsfragmentManager = getFragmentManager();
            topsfragmentManager.beginTransaction().replace(R.id.contents, topsfragment).commit();
        }

        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/incode");

        if (!path.exists())
            path.mkdirs();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }


    DrawerListener myDrawerListener = new DrawerListener() {
        public void onDrawerClosed(View drawerView) {
            mDrawerState = "close";
        }
        public void onDrawerOpened(View drawerView) {
            mDrawerState = "open";
        }
        public void onDrawerSlide(View drawerView, float slideOffset) {}
        public void onDrawerStateChanged(int arg0) {}
    };

    /* 드로어 리스트 아이템 클릭 리스너 */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), "checkedPosition= "+checkedPosition, toast.LENGTH_SHORT).show();
            //selectItem(position);
            int pos = position;
            if( pos == 0){
                if( !UserID.equals("") ) {
                    Intent intent = new Intent(Mains.this, it_bw_reg_manage.class);
                    startActivity(intent);
                }
            }else if( pos == 1){//최근사용목록
                Intent intent;
                intent = new Intent(Mains.this, it_bw_history.class);
                startActivity(intent);
            }else if( pos == 2){//코드생성
                if( !UserID.equals("") ) {
                    new HttpTask3().execute();
                    Intent intent = new Intent(Mains.this, it_bw_cre.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(mContext)
                            .setTitle("로그인후 사용하실수 있는 메뉴입니다.")
                            .setMessage("확인을 누르시면 로그인페이지로 이동합니다")
                            .setPositiveButton("확인",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Mains.this, it_bw_login.class);
                                            startActivity(intent);
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("취소",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();

                }
            }else if( pos == 3){//코드관리
                Intent intent = new Intent(Mains.this, it_bw_list.class);
                startActivity(intent);
            }else if( pos == 4){//인코드란?
                Intent intent = new Intent(Mains.this, it_info.class);
                startActivity(intent);
            }else if( pos == 5){//공지사항
                Intent intent = new Intent(Mains.this, it_notice.class);
                startActivity(intent);
            }else if( pos == 6){//버전
                //nothing
            }else if( pos == 7){//고객센터
                Intent intent = new Intent(Mains.this, it_usercenter.class);
                startActivity(intent);
            }else if( pos == 8){//로그인
                if( UserID.length() == 0 ) {//로그인 안되있을때
                    Intent intent = new Intent(Mains.this, it_bw_login.class);
                    startActivity(intent);
                }else{                      //로그인 되있을때
                    logout();
                    drawerMenuAdapter.notifyDataSetChanged();
                }
            }

            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;
        FragmentTransaction ft = null;

        //fragmentIndex = fragmentManager.getBackStackEntryCount();

        if( position == 0 ){
            fragment = new fr_pad();
            ft = fragmentManager.beginTransaction();
            ft.replace(R.id.contents, fragment).setBreadCrumbTitle("main").commit();
            //viewTitle.setText("게시물 관리");
            //actionBtn.setImageResource(R.drawable.home);
            //fragmentIndex = FRAGMENT_SUTDYMANAGER;
        }
    }

    public void intent_web(String vcode)
    {
        insertData(vcode);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://incoders.co.kr/incode/goto.php?vcode=" + vcode + "&user_id=" + UserID));
        startActivity(intent);
    }

    public void logout()
    {

        UserID = "";
        LoginType= "";
        UserPW = "";
        UserEmail ="";
        savePreferences();
    }


    //자동로그인
    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/login.php";
        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try {
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", UserID));
                nameValue.add(new BasicNameValuePair("user_pw", UserPW));

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

        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value) {
            super.onPostExecute(value);

            //result.setText(value);
            Log.e("받은값", value);

            if(value.equals("-1"))
            {
                drawerMenuAdapter.notifyDataSetChanged();
                Toast.makeText(Mains.this, "아이디 또는 패스워드를 잘못입력하셨습니다.", Toast.LENGTH_SHORT).show();
                UserID = "";
                UserPW = "";
                LoginType = "";
                UserEmail = "";
                savePreferences();
            }
            else if(value.equals("0"))
            {
                Toast.makeText(Mains.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                UserID = "";
                UserPW = "";
                LoginType = "";
                UserEmail = "";
                savePreferences();
            }
            else if(value.charAt(0)=='1'){
                Toast.makeText(Mains.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Mains.this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
                UserID = "";
                UserPW = "";
                LoginType = "";
                UserEmail = "";
                savePreferences();
            }
        }
    }

    //코드관리
    class HttpTask2 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/list.php";
        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", UserID));

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

            if(value.equals("NONE_CODE"))
            {
                codeArr.add("등록된 코드가 없습니다");
            }
            else
            {
                StringTokenizer st = new StringTokenizer(value, ",");
                while(st.hasMoreTokens())
                    codeArr.add(st.nextToken());
            }
        }
    }

    //코드생성
    class HttpTask3 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/grant.php";
        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", UserID));

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

            if(value.equals("OK"))
            {
                grant = "OK";
            }
            else
            {
                grant = "NO";
            }
        }
    }

    public interface onKeyBackPressedListener{
        public void onBack();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }


    @Override
    public void onBackPressed() {
        if( Mains.mDrawerState.equals("open") ){
            mDrawerLayout.closeDrawers();
        }
        else if (mOnKeyBackPressedListener != null)
            mOnKeyBackPressedListener.onBack();
        else{
            super.onBackPressed();
        }
    }

    public void createDatabase(){
        database = openOrCreateDatabase(dbName, MODE_WORLD_WRITEABLE, null);
    }

    public void createTable(){
        try{
            database.execSQL(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(String title){

        database.beginTransaction();

        try{
            String sql = "insert into codehistory values ('"+ title +"');";
            database.execSQL(sql);
            database.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            database.endTransaction();
        }

    }

    public int selectData(String email){
        String sql = "select count(*) from codehistory";
        Cursor result = database.rawQuery(sql, null);
        result.moveToFirst();
        int count = result.getInt(0);
        result.close();
        return count;
    }

}

