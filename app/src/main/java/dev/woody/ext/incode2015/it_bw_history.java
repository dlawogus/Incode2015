package dev.woody.ext.incode2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import dev.woody.ext.adapter.it_bw_history_adapter;
import dev.woody.ext.datainfo.it_bw_code_info;
import dev.woody.ext.dialog.CustomDialog_gong;

public class it_bw_history  extends Activity {
    public ListView listView;
    public static ArrayList<it_bw_code_info> mListitemArray;
    public static it_bw_history_adapter arrAdapt;
    private ProgressDialog pd;
    private Context mContext;

    public ImageButton gong_button;
    public ImageButton del_button;
    public RelativeLayout empty;
    private CustomDialog_gong mCustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_history);
        mContext = this;

        Intent intent = getIntent();


        del_button = (ImageButton)findViewById(R.id.history_btn_del);
        empty = (RelativeLayout)findViewById(R.id.history_empty);
        //selectedStringArr = new ArrayList<String>();

        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        ImageView btnback = (ImageView)findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        gong_button = (ImageButton)findViewById(R.id.history_btn_gong);
        gong_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for(int i=0; i<mListitemArray.size(); i++) {
                    if (mListitemArray.get(i).getIschecked()) {
                        count ++;
                    }
                }
                if( count == 0){
                    Toast.makeText(it_bw_history.this,"공유할 코드를 선택해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if( count != 0){
                    mCustomDialog = new CustomDialog_gong(it_bw_history.this,
                        new View.OnClickListener() {       //이미지 저장
                            @Override
                            public void onClick(View v) {

                                for(int i=0; i<mListitemArray.size(); i++) {
                                    if(mListitemArray.get(i).getIschecked()){
                                        RelativeLayout capture = (RelativeLayout)findViewById(R.id.share_code_layout);
                                        TextView code1 = (TextView)findViewById(R.id.share_textView_vnum1);
                                        TextView code2 = (TextView)findViewById(R.id.share_textView_vnum2);
                                        TextView code3 = (TextView)findViewById(R.id.share_textView_vnum3);
                                        TextView code4 = (TextView)findViewById(R.id.share_textView_vnum4);
                                        String code = mListitemArray.get(i).getVnumber();
                                        code1.setText(code.substring(0, 1));
                                        code2.setText(code.substring(1, 2));
                                        code3.setText(code.substring(2, 3));
                                        code4.setText(code.substring(3, 4));
                                        capture.buildDrawingCache();
                                        Bitmap captureView = capture.getDrawingCache();

                                        FileOutputStream fos;
                                        try {
                                            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(i).getVnumber()+".png");
                                            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                            galleryAddPic(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(i).getVnumber()+".png");
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        //Log.d("pic", code);
                                    }
                                }
                                Toast.makeText(it_bw_history.this, "사진을 저장하였습니다.", Toast.LENGTH_SHORT).show();
                                mCustomDialog.dismiss();
                            }
                        },
                        new View.OnClickListener() {       //더보기
                            @Override
                            public void onClick(View v) {
                                ArrayList<File> fileName = new ArrayList<File>();
                                ArrayList<Uri> ImageUri = new ArrayList<Uri>();
                                for(int i=0; i<mListitemArray.size(); i++) {
                                    if(mListitemArray.get(i).getIschecked()){
                                        RelativeLayout capture = (RelativeLayout)findViewById(R.id.share_code_layout);
                                        TextView code1 = (TextView)findViewById(R.id.share_textView_vnum1);
                                        TextView code2 = (TextView)findViewById(R.id.share_textView_vnum2);
                                        TextView code3 = (TextView)findViewById(R.id.share_textView_vnum3);
                                        TextView code4 = (TextView)findViewById(R.id.share_textView_vnum4);
                                        String code = mListitemArray.get(i).getVnumber();
                                        code1.setText(code.substring(0, 1));
                                        code2.setText(code.substring(1, 2));
                                        code3.setText(code.substring(2, 3));
                                        code4.setText(code.substring(3, 4));
                                        capture.buildDrawingCache();
                                        Bitmap captureView = capture.getDrawingCache();

                                        FileOutputStream fos;
                                        try {
                                            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(i).getVnumber()+".png");
                                            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                            galleryAddPic(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(i).getVnumber()+".png");
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }

                                        fileName.add(new File(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(i).getVnumber()+".png"));
                                    }
                                }

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("image/png");
                                for( int i=0; i<fileName.size(); i++) {
                                    ImageUri.add(Uri.fromFile(fileName.get(i)));
                                }
                                intent.putExtra(Intent.EXTRA_STREAM, ImageUri);
                                startActivity(Intent.createChooser(intent, "공유하기")); //Activity를 이용하여 호출 합니다.
                                mCustomDialog.dismiss();
                            }});

                mCustomDialog.show();
                }


            }
        });

        del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (int i = 0; i < mListitemArray.size(); i++) {
                    if (mListitemArray.get(i).getIschecked() == true)
                        count++;
                }

                AlertDialog alert = new AlertDialog.Builder(mContext)
                        .setTitle(Integer.toString(count) + "개 목록이 선택되었습니다.")
                        .setMessage("목록에서 제거하시겠습니까?")
                        .setPositiveButton("취소",
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("제거",
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ArrayList<it_bw_code_info> remove_item = new ArrayList<it_bw_code_info>();
                                        for (int i = 0; i < mListitemArray.size(); i++) {
                                            if ( mListitemArray.get(i).getIschecked()) {
                                                String sql = "delete from codehistory where code='" + mListitemArray.get(i).getVnumber().toString()+"'";
                                                Mains.database.execSQL(sql);
                                                remove_item.add(mListitemArray.get(i));
                                            }
                                        }
                                        for(int i=0; i<remove_item.size(); i++) {
                                            mListitemArray.remove( remove_item.get(i) );
                                        }
                                        arrAdapt.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }).create();

                if( count == 0 ){
                    Toast.makeText(it_bw_history.this,"선택된 코드가 없습니다",Toast.LENGTH_SHORT).show();
                }else{
                    alert.show();
                }
            }
        });


        listView = (ListView)findViewById(R.id.history_listView);
        mListitemArray = new ArrayList<it_bw_code_info>();
        arrAdapt = new it_bw_history_adapter(it_bw_history.this, R.layout.lay_it_history_item, mListitemArray);
        listView.setAdapter(arrAdapt);

        //리스트 아이템 클릭 리스너
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String code = mListitemArray.get(position).getVnumber();
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://incoders.co.kr/incode/goto.php?vcode=" + code + "&user_id=" + Mains.UserID));
                startActivity(intent);

/*                mCustomDialog = new CustomDialog_gong(context,
                        new View.OnClickListener() {       //이미지 저장
                            @Override
                            public void onClick(View v) {
                                viewHolder.container.buildDrawingCache();
                                viewHolder.captureView = viewHolder.container.getDrawingCache();

                                FileOutputStream fos;
                                try {
                                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(position).getVnumber()+".png");
                                    viewHolder.captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    galleryAddPic(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(position).getVnumber()+".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(context, "사진을 저장하였습니다.", Toast.LENGTH_SHORT).show();
                                mCustomDialog.dismiss();
                            }
                        },
                        new View.OnClickListener() {       //더보기
                            @Override
                            public void onClick(View v) {
                                viewHolder.container.buildDrawingCache();
                                Bitmap captureView = viewHolder.container.getDrawingCache();
                                //Bitmap captureView = mCache.get(position);
                                FileOutputStream fos;
                                try {
                                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(position).getVnumber()+".png");
                                    viewHolder.captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    galleryAddPic(Environment.getExternalStorageDirectory().toString() + "/incode/" + it_bw_history.mListitemArray.get(position).getVnumber()+".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                File dirName = new File(Environment.getExternalStorageDirectory().toString()+"/incode/"+it_bw_history.mListitemArray.get(position).getVnumber()+".png");
                                Uri mSaveImageUri = Uri.fromFile(dirName);
                                Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                                intent.setType("image/png"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                                intent.putExtra(Intent.EXTRA_STREAM, mSaveImageUri); //사진의 Uri를 가지고 옵니다.
                                context.startActivity(Intent.createChooser(intent, "공유하기")); //Activity를 이용하여 호출 합니다.
                                mCustomDialog.dismiss();
                            }});

                mCustomDialog.show();*/
            }
        });

        new HttpTask().execute();
    }


    //갤러리 갱신
    private void galleryAddPic(String s_pic_filename) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(s_pic_filename);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // History log LIst 가져오기
    class HttpTask extends AsyncTask<Void, Void, Void> {
        String urlPath = "http://incoders.co.kr/incode/history.php";
        String historycode = "";
        int count;
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(mContext);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress);


            String sql = "select count(*) from codehistory";
            Cursor result = Mains.database.rawQuery(sql, null);
            result.moveToFirst();
            int count = result.getInt(0);

            String sql1 = "select * from codehistory";
            Cursor result1 = Mains.database.rawQuery(sql1, null);
            result1.moveToFirst();
            for(int i=0; i<count; i++){
                historycode += result1.getString(0);
                if( i != (count-1) )
                    historycode += "|";
                result1.moveToNext();
            }

            Log.d("히스토리", historycode);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String total = "";

            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("code", historycode) );
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
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                //JSONObject object = new JSONObject(value);
                JSONArray objectArray = new JSONArray(total);
                for (int i = 0; i < objectArray.length(); i++) {
                    JSONObject insideObject = objectArray.getJSONObject(i);
                    it_bw_code_info item = new it_bw_code_info();
                    item.setVnumber(insideObject.getString("cnumber"));
                    item.setConfig(insideObject.getString("config"));
                    item.setUrl(insideObject.getString("url"));
                    item.setStart(insideObject.getString("start_date"));
                    item.setEnd(insideObject.getString("date_term"));
                    item.setMan_hit(insideObject.getString("man_hit"));
                    item.setWomen_hit(insideObject.getString("women_hit"));
                    item.setUnknown_hit(insideObject.getString("unknown_hit"));
                    item.setType(insideObject.getString("type"));
                    mListitemArray.add(0,item);
                }

                count = objectArray.length();
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            if( count == 0 ){
                empty.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }else{
                empty.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            arrAdapt.notifyDataSetChanged();
            pd.dismiss();

        }
    }

}

