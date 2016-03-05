package dev.woody.ext.codemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import dev.woody.ext.adapter.it_bw_list_adapter;
import dev.woody.ext.datainfo.it_bw_code_info;
import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

public class it_bw_list extends Activity {
    final int CODE_OPTION = 1;
    Intent intent;
    String curCnumber;
    String url;
    String config;
    String startDate;
    String endDate;
    String man_hit;
    String women_hit;
    String unknown_hit;
    String mType;
    int manHit;
    int womenHit;
    int unknownHit;

    Spinner spinner;
    ArrayList<String> spinList;
    LinearLayout spinLayout;

    Button cnum1;
    Button cnum2;
    Button cnum3;
    Button cnum4;

    Button optionBtn;
    Button gongBtn;
    Button tongBtn;
    Button delBtn;

    EditText urlText;
    EditText configText;
    EditText dateText;
    EditText hitText;

    public ListView listView;
    public static ArrayList<it_bw_code_info> mCodeListitemArray;
    //public ArrayList<String> selectedStringArr;
    public static it_bw_list_adapter arrAdapt;
    public RelativeLayout code_empty;

    private ProgressDialog pd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw_list);
        intent = getIntent();

        curCnumber = "";
        mContext = this;
        //spinList = new ArrayList<String>();

        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        ImageButton btnback = (ImageButton)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.code_list);
        mCodeListitemArray = new ArrayList<it_bw_code_info>();
        arrAdapt = new it_bw_list_adapter(it_bw_list.this, R.layout.lay_it_bw_list_item, mCodeListitemArray);
        listView.setAdapter(arrAdapt);

        code_empty = (RelativeLayout) findViewById(R.id.code_empty);
        new HttpTask().execute();

        //리스트 아이템 클릭 리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String code = mCodeListitemArray.get(position).getVnumber();
                String config = mCodeListitemArray.get(position).getConfig();
                String url = mCodeListitemArray.get(position).getUrl();
                String start = mCodeListitemArray.get(position).getStart();
                String end = mCodeListitemArray.get(position).getEnd();
                String man_hit = mCodeListitemArray.get(position).getMan_hit();
                String women_hit = mCodeListitemArray.get(position).getWomen_hit();
                String unknown_hit = mCodeListitemArray.get(position).getUnknown_hit();
                String type = mCodeListitemArray.get(position).getType();
                Intent intent = new Intent(it_bw_list.this, it_bw_list_2.class);
                intent.putExtra("position", Integer.toString(position) );
                intent.putExtra("UserID", Mains.UserID);
                intent.putExtra("code",code);
                intent.putExtra("config",config);
                intent.putExtra("url",url);
                intent.putExtra("start",start);
                intent.putExtra("end",end);
                //intent.putExtra("man_hit",man_hit);
                //intent.putExtra("women_hit",women_hit);
                //intent.putExtra("unknown_hit",unknown_hit);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        /*
        cnum1 = (Button)findViewById(R.id.list_vnumber1);
        cnum2 = (Button)findViewById(R.id.list_vnumber2);
        cnum3 = (Button)findViewById(R.id.list_vnumber3);
        cnum4 = (Button)findViewById(R.id.list_vnumber4);

        optionBtn = (Button)findViewById(R.id.list_button_option);
        gongBtn = (Button)findViewById(R.id.list_button_gong);
        tongBtn = (Button)findViewById(R.id.list_button_tong);
        delBtn = (Button)findViewById(R.id.list_button_del);
        optionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), it_bw_list_option.class);
                intent.putExtra("userid",UserID);
                intent.putExtra("cnumber",curCnumber);
                intent.putExtra("url", url);
                intent.putExtra("config",config);
                startActivityForResult(intent, CODE_OPTION);
        }
        });
        gongBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



            }
        });
        tongBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), it_bw_list_tong.class);
                intent.putExtra("manHit",manHit);
                intent.putExtra("womenHit",womenHit);
                intent.putExtra("unknownHit", unknownHit);
                startActivity(intent);
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(it_bw_list.this);
                //input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                PasswordTransformationMethod passwdtm = new PasswordTransformationMethod();
                input.setTransformationMethod(passwdtm);

                final AlertDialog.Builder pwDialog = new AlertDialog.Builder(it_bw_list.this)
                        .setTitle("본인 확인")
                        .setMessage("본인 확인을 위해 비밀번호를 입력 해주시기 바랍니다.")
                        .setView(input)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        HttpTask3 ht3 = new HttpTask3(input.getText().toString());
                                        ht3.execute();
                                    }
                                });
                pwDialog.setNegativeButton(android.R.string.cancel,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                new AlertDialog.Builder(it_bw_list.this)
                        .setTitle("Confirm")
                        .setMessage("코드를 삭제하시면 해당코드의 사용 및 복구가 되지 않습니다. 그래도 삭제 하시겠습니까?")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        pwDialog.create().show();
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which){
                                        dialog.dismiss();
                                    }
                                })
                        .create().show();
            }
        });
        */
//        urlText = (EditText)findViewById(R.id.list_EditText_url);
//        configText = (EditText)findViewById(R.id.list_EditText_config);
//        dateText = (EditText)findViewById(R.id.list_EditText_date);
//        hitText = (EditText)findViewById(R.id.list_EditText_hit);



        /*
        //String test = spinner.getSelectedView().toString();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                curCnumber = spinner.getSelectedItem().toString();
                new HttpTask2().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        /*
        webs = (WebView)findViewById(R.id.webs);
        webs.getSettings().setJavaScriptEnabled(true);
        webs.getSettings().setDefaultTextEncodingName("UTF-8");
        webs.getSettings().setDisplayZoomControls(true);
        webs.getSettings().setSupportZoom(true);
        webs.getSettings().setBuiltInZoomControls(true);
        webs.setWebViewClient(new WebViewClientClass());
        webs.setWebChromeClient(new MyWebChromeClientClass());
        webs.loadUrl("http://incoders.co.kr/incode/list.php?user_id=" + UserID);
        */
    }

    // Spinner Item code LIst 가져오기
    class HttpTask extends AsyncTask<Void, Void, Void> {
        String urlPath = "http://incoders.co.kr/incode/list2.php";
        int count;
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            String value = "";
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));

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
            //StringTokenizer st = new StringTokenizer(value, "#end");
            try {
                //JSONObject object = new JSONObject(value);
                JSONArray objectArray = new JSONArray(value);
                for (int i = 0; i < objectArray.length(); i++) {
                    JSONObject insideObject = objectArray.getJSONObject(i);
                    it_bw_code_info item = new it_bw_code_info();
                    item.setVnumber(insideObject.getString("cnumber") );
                    item.setConfig(insideObject.getString("config"));
                    item.setUrl(insideObject.getString("url"));
                    item.setStart(insideObject.getString("start_date"));
                    item.setEnd(insideObject.getString("date_term"));
                    item.setMan_hit(insideObject.getString("man_hit"));
                    item.setWomen_hit(insideObject.getString("women_hit"));
                    item.setUnknown_hit(insideObject.getString("unknown_hit"));
                    item.setType(insideObject.getString("type"));
                    mCodeListitemArray.add(0,item);
                }

                count = objectArray.length();

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            pd.dismiss();
            //리스트 갱신
            arrAdapt.notifyDataSetChanged();

            if( count == 0) {
                code_empty.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                code_empty.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

        }
    }

    // Select 된 Code의 정보 가져오기기
   class HttpTask2 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/list_view2.php";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("cnumber", curCnumber));

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
            pd.dismiss();
            if(value != null && !value.equals("")) {
                // value = code***config***startDate***endDate***man***women***unknown***url
                StringTokenizer st = new StringTokenizer(value, "***");
                String codeTmp = st.nextToken();
                config = st.nextToken();
                startDate = st.nextToken();
                endDate = st.nextToken();
                manHit = Integer.parseInt(st.nextToken());
                womenHit = Integer.parseInt(st.nextToken());
                unknownHit = Integer.parseInt(st.nextToken());
                url = st.nextToken();

                //code
                //cnum1.setText(codeTmp.substring(0, 1));
                //cnum2.setText(codeTmp.substring(1, 2));
                //cnum3.setText(codeTmp.substring(2, 3));
                //cnum4.setText(codeTmp.substring(3, 4));
                //config
                //configText.setText(config);
                //Date
                //dateText.setText(startDate + " ~ " + endDate);
                //Hit
                //int total = manHit + womenHit + unknownHit;
                //hitText.setText("Total Hit : "+String.valueOf(total));
                //URL
                urlText.setText(url);
            }
        }
    }

    // Select 된 Code를 Delete 하기
    class HttpTask3 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/del.php";
        String pw = "";
        public HttpTask3(String pwin){
            pw = pwin;
        }
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
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("cnumber", curCnumber));
                nameValue.add(new BasicNameValuePair("userid", Mains.UserID));
                nameValue.add(new BasicNameValuePair("pw", pw));

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
            pd.dismiss();
            if(value.equals("success")) {
                new HttpTask().execute();
                new HttpTask2().execute();
                new AlertDialog.Builder(it_bw_list.this)
                        .setTitle("Alert")
                        .setMessage("코드가 정상적으로 삭제되었습니다.")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //onResume();
                                    }
                                })
                        .create().show();
            }
            else if(value.equals("fail")){
                new AlertDialog.Builder(it_bw_list.this)
                        .setTitle("Error")
                        .setMessage("비밀번호가 틀렸습니다.")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                        .create().show();
            }
            else{
                new AlertDialog.Builder(it_bw_list.this)
                        .setTitle("Error")
                        .setMessage("오류가 발생했습니다.")
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                        .create().show();
            }
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();
        //new HttpTask().execute();
        //new HttpTask2().execute();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODE_OPTION) {
            if(resultCode == RESULT_OK) {
                new HttpTask2().execute();
                String result = data.getStringExtra("QUERY");
                if (result.equals("query_success")) {
                    new AlertDialog.Builder(it_bw_list.this)
                            .setTitle("Alert")
                            .setMessage("코드정보가 수정되었습니다.")
                            .setPositiveButton(android.R.string.ok,
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create().show();
                }
            }
        }
    }
}