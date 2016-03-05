package dev.woody.ext.codecreation;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

/**
 * Created by imjaehyun on 15. 8. 27..
 */

public class it_bw_cre_fr_1 extends Fragment{

    private EditText url_editText_1;
    private EditText url_editText_2;
    private Button cre_btn_random;
    private Button cre_btn_user;
    public static TextView code_item_textView_vnum1;
    public static TextView code_item_textView_vnum2;
    public static TextView code_item_textView_vnum3;
    public static TextView code_item_textView_vnum4;
    private ImageButton cre_code_gene;
    private Button cre_btn_date;
    private Button cre_btn_ok;
    private boolean mIs_random = true;
    private ProgressDialog pd;
    private Context mContext;
    public static String mCode = "";
    public static boolean is_check_date = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.lay_it_cre_fragment_one, container, false);

        mContext = getActivity();

        url_editText_1 = (EditText) view.findViewById(R.id.url_editText_1);
        url_editText_2 = (EditText) view.findViewById(R.id.url_editText_2);
        code_item_textView_vnum1 = (TextView) view.findViewById(R.id.code_item_textView_vnum1);
        code_item_textView_vnum2 = (TextView) view.findViewById(R.id.code_item_textView_vnum2);
        code_item_textView_vnum3 = (TextView) view.findViewById(R.id.code_item_textView_vnum3);
        code_item_textView_vnum4 = (TextView) view.findViewById(R.id.code_item_textView_vnum4);

        cre_btn_random = (Button) view.findViewById(R.id.cre_btn_random);
        cre_btn_user = (Button) view.findViewById(R.id.cre_btn_user);
        cre_btn_random.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  cre_btn_random.setBackgroundColor(Color.rgb(24, 112, 156));
                  cre_btn_random.setTextColor(Color.rgb(255, 255, 255));
                  cre_btn_user.setBackgroundResource(R.drawable.border_cre);
                  cre_btn_user.setTextColor(Color.rgb(24, 112, 156));
                  code_item_textView_vnum1.setText("");
                  code_item_textView_vnum2.setText("");
                  code_item_textView_vnum3.setText("");
                  code_item_textView_vnum4.setText("");
                  mIs_random = true;
                  mCode = "";
              }
          }
        );
        cre_btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cre_btn_user.setBackgroundColor(Color.rgb(24, 112, 156));
                cre_btn_user.setTextColor(Color.rgb(255, 255, 255));
                cre_btn_random.setBackgroundResource(R.drawable.border_cre);
                cre_btn_random.setTextColor(Color.rgb(24, 112, 156));
                code_item_textView_vnum1.setText("");
                code_item_textView_vnum2.setText("");
                code_item_textView_vnum3.setText("");
                code_item_textView_vnum4.setText("");
                mIs_random = false;
                mCode = "";
            }
        });

        cre_code_gene = (ImageButton) view.findViewById(R.id.cre_code_gene);
        cre_code_gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if( mIs_random ){       //랜덤
                if( url_editText_1.getText().toString().equals("") || url_editText_2.getText().toString().equals("")){
                    Toast.makeText(mContext,"빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }else {
                    new HttpTask().execute();
                }
            }else{
                //사용자
                Intent intent = new Intent(getActivity(),it_bw_cre_user.class);
                intent.putExtra("index","1");
                startActivity(intent);
            }
            }
        });

        cre_btn_date = (Button) view.findViewById(R.id.cre_btn_date_re);
        cre_btn_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), it_bw_cre_date.class);
                        startActivity(intent);

                }
            }
        );

        cre_btn_ok = (Button) view.findViewById(R.id.cre_btn_ok);
        cre_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !is_check_date ) {
                    Toast.makeText(getActivity(), "기간을 설정해주세요", Toast.LENGTH_SHORT).show();
                }
                else if( url_editText_1.getText().toString().equals("") || url_editText_2.getText().toString().equals("")) {
                    Toast.makeText(mContext, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }
                else if ( !mCode.equals("") ) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("코드생성이 완료되었습니다")
                            .setMessage("코드관리 메뉴를 통해 수정 및 삭제가 가능합니다")
                            .setPositiveButton("완료",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            new HttpTask2().execute();
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("취소",
                                    new AlertDialog.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                }else{
                    Toast.makeText(getActivity(),"코드를 생성해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/input_random.php";

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
                nameValue.add(new BasicNameValuePair("config", url_editText_2.getText().toString() ));
                nameValue.add(new BasicNameValuePair("tag", "url"));
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

            if(value.substring(0,5).equals("code="))
            {
                Toast.makeText(mContext, "무작위코드가 생성되었습니다.", Toast.LENGTH_SHORT).show();

                code_item_textView_vnum1.setText(value.substring(5, 6));
                code_item_textView_vnum2.setText(value.substring(6, 7));
                code_item_textView_vnum3.setText(value.substring(7, 8));
                code_item_textView_vnum4.setText(value.substring(8, 9));
                mCode = value.substring(5,9);
            }
            else if(value.equals("301"))
            {
                new AlertDialog.Builder(mContext)
                        .setTitle("Error")
                        .setMessage("잘못된 입렵입니다.")
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
                new AlertDialog.Builder(mContext)
                        .setTitle("Error")
                        .setMessage(value)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        //onResume();
                                    }
                                })
                        .create().show();
            }

            pd.dismiss();
        }
    }

    class HttpTask2 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/input_1.php";
        InputStream im = null;

        @Override
        protected String doInBackground(Void... voids) {
            try {

                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("config", url_editText_2.getText().toString()));
                nameValue.add(new BasicNameValuePair("url", url_editText_1.getText().toString()));
                nameValue.add(new BasicNameValuePair("code", mCode ));
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));
                nameValue.add(new BasicNameValuePair("date", it_bw_cre.mDate));
                nameValue.add(new BasicNameValuePair("tag", "url"));

                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                HttpEntity entityResponse = res.getEntity();
                im = entityResponse.getContent();
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


            if(value.equals("101"))
            {
                Toast.makeText(mContext, "코드가 정상 등록되었습니다.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }else if(value.equals("201")){
                Toast.makeText(mContext, "이미 등록된 코드입니다.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                new AlertDialog.Builder(mContext)
                        .setTitle("Error")
                        .setMessage(value)
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
            //getActivity().finish();
    }
}
