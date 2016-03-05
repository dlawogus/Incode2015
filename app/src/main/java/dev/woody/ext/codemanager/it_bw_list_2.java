package dev.woody.ext.codemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import dev.woody.ext.dialog.CustomDialog_gong;
import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

public class it_bw_list_2 extends Activity {
    public final int CODE_CAMERA_IMAGE = 1;
    public final int CODE_PICK_IMAGE = 2;

    public String mPosition;
    public String mCode;
    public String mConfig;
    public String mUrl;
    public String mStart;
    public String mEnd;
    public String mMan_hit = "0";
    public String mWomen_hit = "0";
    public String mUnknown_hit ="0";
    public String mToday_hit;
    public String mWeek_hit;
    public String mType;

    private CustomDialog_gong mCustomDialog;
    public static String mDate = "7";
    public String image_path = "";

    private TextView code_date;
    private TextView code_today_hit;
    private TextView code_week_hit;
    private EditText code_url;
    private EditText code_config;
    private TextView code_woman_hit;
    private TextView code_man_hit;
    private TextView code_unknown_hit;
    private TextView code_total_hit;

    private ProgressDialog pd;
    private Context mContext;
    private boolean mIs_imagechange = false;
    int serverResponseCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw_list_2);

        mContext = this;

        Intent intent = getIntent();
        mPosition = intent.getStringExtra("position");
        mCode = intent.getStringExtra("code");
        mConfig = intent.getStringExtra("config");
        mUrl = intent.getStringExtra("url");
        mStart = intent.getStringExtra("start");
        mEnd = intent.getStringExtra("end");
        //mMan_hit = intent.getStringExtra("man_hit");
        //mWomen_hit = intent.getStringExtra("women_hit");
        //mUnknown_hit = intent.getStringExtra("unknown_hit");
        mType = intent.getStringExtra("type");

        //뒤로가기
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //공유버튼
        ImageButton list_btn_gong = (ImageButton) findViewById(R.id.list_btn_gong);
        list_btn_gong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new CustomDialog_gong(it_bw_list_2.this,
                        new View.OnClickListener() {       //이미지 저장
                            @Override
                            public void onClick(View v) {
                                RelativeLayout container = (RelativeLayout) findViewById(R.id.code_layout);
                                container.buildDrawingCache();
                                Bitmap captureView = container.getDrawingCache();

                                FileOutputStream fos;
                                try {
                                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/incode/" + mCode + ".png");
                                    captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    galleryAddPic(Environment.getExternalStorageDirectory().toString() + "/incode/" + mCode + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(it_bw_list_2.this, "사진을 저장하였습니다.", Toast.LENGTH_SHORT).show();
                                mCustomDialog.dismiss();
                            }
                        },
                        new View.OnClickListener() {       //더보기
                            @Override
                            public void onClick(View v) {
                                RelativeLayout container = (RelativeLayout) findViewById(R.id.code_layout);
                                container.buildDrawingCache();
                                Bitmap captureView = container.getDrawingCache();

                                FileOutputStream fos;
                                try {
                                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/incode/" + mCode + ".png");
                                    captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    galleryAddPic(Environment.getExternalStorageDirectory().toString() + "/incode/" + mCode + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                File dirName = new File(Environment.getExternalStorageDirectory().toString() + "/incode/" + mCode + ".png");
                                Uri mSaveImageUri = Uri.fromFile(dirName);
                                Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                                intent.setType("image/png"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                                intent.putExtra(Intent.EXTRA_STREAM, mSaveImageUri); //사진의 Uri를 가지고 옵니다.
                                startActivity(Intent.createChooser(intent, "공유하기")); //Activity를 이용하여 호출 합니다.
                                mCustomDialog.dismiss();
                            }
                        });
                mCustomDialog.show();

            }
        });

        //삭제 버튼
        ImageButton list_btn_del = (ImageButton) findViewById(R.id.list_btn_del);
        list_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("코드를 삭제하시겠습니까?")
                        .setMessage("코드를 삭제하시면 사용 및 복구가 되지 않습니다")
                        .setPositiveButton("삭제",
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        new HttpTask().execute();
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
        });

        //기간재설정
        Button code_btn_change = (Button) findViewById(R.id.code_btn_change);
        code_btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(it_bw_list_2.this, it_bw_list_date.class);
                startActivityForResult(intent, 0);
            }
        });

        //완료 버튼
        Button code_btn_ok = (Button) findViewById(R.id.code_btn_ok);
        code_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpOkTask().execute();
            }
        });

        TextView list_actionbar_code = (TextView) findViewById(R.id.list_actionbar_code);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf.mp3");
        list_actionbar_code.setTypeface(typeFace);
        list_actionbar_code.setText(mCode);

        //코드
        TextView code_item_textView_vnum1 = (TextView) findViewById(R.id.code_item_textView_vnum1);
        TextView code_item_textView_vnum2 = (TextView) findViewById(R.id.code_item_textView_vnum2);
        TextView code_item_textView_vnum3 = (TextView) findViewById(R.id.code_item_textView_vnum3);
        TextView code_item_textView_vnum4 = (TextView) findViewById(R.id.code_item_textView_vnum4);
        code_item_textView_vnum1.setText(mCode.substring(0, 1));
        code_item_textView_vnum2.setText(mCode.substring(1, 2));
        code_item_textView_vnum3.setText(mCode.substring(2, 3));
        code_item_textView_vnum4.setText(mCode.substring(3, 4));

        //코드 기간
        code_date = (TextView) findViewById(R.id.code_date);
        code_date.setText(mStart + " ~ " + mEnd);

        //토탈 히트
        code_total_hit = (TextView) findViewById(R.id.code_total_hit);
        code_today_hit = (TextView) findViewById(R.id.code_today_hit);
        code_week_hit = (TextView) findViewById(R.id.code_week_hit);

        //남자 히트
        code_man_hit = (TextView) findViewById(R.id.code_man_hit);

        //여자 히트
        code_woman_hit = (TextView) findViewById(R.id.code_woman_hit);

        //알수없음 히트
        code_unknown_hit = (TextView) findViewById(R.id.code_unknown_hit);


        //url주소
        code_url = (EditText) findViewById(R.id.code_url);
        code_url.setText(mUrl);

        //config
        code_config = (EditText) findViewById(R.id.code_config);
        code_config.setText(mConfig);

        TextView code_url_text_1 = (TextView) findViewById(R.id.code_url_text_1);
        TextView code_url_text_2 = (TextView) findViewById(R.id.code_url_text_2);
        if (mType.equals("image")) {
            code_url_text_1.setText("사진 경로");
            code_url_text_2.setText("이미지 설명");
        }

        ImageButton btn_camera = (ImageButton)findViewById(R.id.btn_camera);
        ImageButton btn_gallery = (ImageButton)findViewById(R.id.btn_gallery);
        if( mType.equals("url") ){
            btn_camera.setVisibility(View.GONE);
            btn_gallery.setVisibility(View.GONE);
        }else{
            btn_camera.setVisibility(View.VISIBLE);
            btn_gallery.setVisibility(View.VISIBLE);
        }
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), CODE_CAMERA_IMAGE);
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, CODE_PICK_IMAGE);
            }
        });

        new HttpHitTask().execute();
    }

    //갤러리 갱신
    private void galleryAddPic(String s_pic_filename) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(s_pic_filename);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public String getRealPathFromURI(Uri contentUri){
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("이미지 result", Integer.toString(requestCode)+"  : "+Integer.toString(resultCode) );
        if( requestCode == 0 ){
            String key = data.getStringExtra("result");
            if( key.equals("0")){
                new HttpTask2().execute();
            }
        }else if (requestCode == CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imgUri = data.getData();
                    String imagePath = getRealPathFromURI(imgUri);
                    image_path = imagePath;
                    code_url.setText(image_path);
                    mIs_imagechange = true;
                    //Log.d("이미지", it_bw_cre.image_path + "");
                    //Toast.makeText(this,image_path,Toast.LENGTH_SHORT).show();
                    //imageText.setText(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if( requestCode == CODE_CAMERA_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri currImageURI = data.getData();
                    String imagePath = getRealPathFromURI(currImageURI);
                    image_path = imagePath;
                    code_url.setText(image_path);
                    mIs_imagechange = true;
                    //Log.d("이미지", it_bw_cre.image_path + "");
                    //Toast.makeText(this,image_path,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //완료
    class HttpOkTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/change_code.php";
        @Override
        protected void onPreExecute() {
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
                nameValue.add(new BasicNameValuePair("cnumber", mCode));
                nameValue.add(new BasicNameValuePair("user_id", Mains.UserID));
                nameValue.add(new BasicNameValuePair("user_pw", Mains.UserPW));
                if( mType.equals("url") ) {
                    nameValue.add(new BasicNameValuePair("config", code_config.getText().toString()));
                    nameValue.add(new BasicNameValuePair("url", code_url.getText().toString()));
                }else{
                    if( mIs_imagechange ){
                        uploadFile(image_path);
                    }
                    nameValue.add(new BasicNameValuePair("config", code_config.getText().toString()));
                    nameValue.add(new BasicNameValuePair("url", "http://incoders.co.kr/uploads/"+mCode+"_upload.jpg"));
                }
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
        //"http://incoders.co.kr/uploads/"+mCode+"_upload.jpg"
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            Log.e("받은값", value);
            if( value.equals("success") ){
                if( mType.equals("url")) {
                    it_bw_list.mCodeListitemArray.get(Integer.parseInt(mPosition)).setUrl(code_url.getText().toString());
                    it_bw_list.mCodeListitemArray.get(Integer.parseInt(mPosition)).setConfig(code_config.getText().toString());
                    it_bw_list.arrAdapt.notifyDataSetChanged();
                }else{
                    it_bw_list.mCodeListitemArray.get(Integer.parseInt(mPosition)).setUrl("http://incoders.co.kr/uploads/"+mCode+"_upload.jpg");
                    it_bw_list.mCodeListitemArray.get(Integer.parseInt(mPosition)).setConfig(code_config.getText().toString());
                    it_bw_list.arrAdapt.notifyDataSetChanged();
                }
                Toast.makeText(it_bw_list_2.this,"변경을 완료하였습니다",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(it_bw_list_2.this,"변경에 실패하였습니다",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }

    // Select 된 Code를 Delete 하기
    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/del.php";
        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("cnumber", mCode));
                nameValue.add(new BasicNameValuePair("userid", Mains.UserID));

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
            if (value.equals("success")) {
                for (int i = 0; i < it_bw_list.mCodeListitemArray.size(); i++) {
                    if (it_bw_list.mCodeListitemArray.get(i).getVnumber().equals(mCode)) {
                        it_bw_list.mCodeListitemArray.remove(i);
                    }
                }
                it_bw_list.arrAdapt.notifyDataSetChanged();
                finish();
            } else {
                Toast.makeText(it_bw_list_2.this, "삭제에 실패하였습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
    // 기간재설정
    class HttpTask2 extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/date_change.php";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("cnumber", mCode));
                nameValue.add(new BasicNameValuePair("userid", Mains.UserID));
                nameValue.add(new BasicNameValuePair("period", mDate));

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
            if (value.equals("fail")) {
                //Toast.makeText(it_bw_list_2.this, "기간 갱신에 실패하였습니다", Toast.LENGTH_LONG).show();
            } else {
                //it_bw_list.mCodeListitemArray
                for (int i = 0; i < it_bw_list.mCodeListitemArray.size(); i++) {
                    if (it_bw_list.mCodeListitemArray.get(i).getVnumber().equals(mCode)) {
                        it_bw_list.mCodeListitemArray.get(i).setEnd(value + "");
                        break;
                    }
                }
                mEnd = value;
                code_date.setText(mStart + " ~ " + mEnd);
                it_bw_list.arrAdapt.notifyDataSetChanged();
                Toast.makeText(it_bw_list_2.this, "기간을 재설정 하였습니다", Toast.LENGTH_LONG).show();

            }
        }
    }

    class HttpHitTask extends AsyncTask<Void, Void, Void> {
        String urlPath = "http://incoders.co.kr/incode/list2_hit.php";

        @Override
        protected void onPreExecute() {
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
                nameValue.add(new BasicNameValuePair("code", mCode));
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
                JSONObject object = new JSONObject(value);

                mToday_hit = object.getString("today");
                mWeek_hit = object.getString("week");
                mWomen_hit = object.getString("women");
                mMan_hit = object.getString("man");
                mUnknown_hit = object.getString("unknown");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            pd.dismiss();
            //리스트 갱신
            code_today_hit.setText(mToday_hit);
            code_week_hit.setText(mWeek_hit);
            code_man_hit.setText(mMan_hit);
            code_unknown_hit.setText(mUnknown_hit);
            code_woman_hit.setText(mWomen_hit);

            int hit_count = Integer.parseInt(mMan_hit) + Integer.parseInt(mWomen_hit) + Integer.parseInt(mUnknown_hit);
            code_total_hit.setText(Integer.toString(hit_count));
            code_man_hit.setText(mMan_hit);
            code_unknown_hit.setText(mUnknown_hit);
            code_woman_hit.setText(mWomen_hit);
        }
    }


    //Image 파일 전송
    public int uploadFile(String sourceFileUri){
        String fileName = mCode+"_upload.jpg";

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        int maxBufferSize = 1 * 1024 * 1024;

        Bitmap srcBmp = BitmapFactory.decodeFile(sourceFileUri);
        int iWidth   = 520;   // 축소시킬 너비
        int iHeight  = 520;   // 축소시킬 높이
        float fWidth  = srcBmp.getWidth();
        float fHeight = srcBmp.getHeight();
        // 원하는 널이보다 클 경우의 설정
        if(fWidth > iWidth) {
            float mWidth = (float) (fWidth / 100);
            float fScale = (float) (iWidth / mWidth);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
            // 원하는 높이보다 클 경우의 설정
        }else if (fHeight > iHeight) {
            float mHeight = (float) (fHeight / 100);
            float fScale = (float) (iHeight / mHeight);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
        }
        FileOutputStream fosObj = null;
        try {
            // 리사이즈 이미지 동일파일명 덮어 쒸우기 작업
            Bitmap resizedBmp = Bitmap.createScaledBitmap(srcBmp, (int)fWidth, (int)fHeight, true);
            fosObj = new FileOutputStream(sourceFileUri);
            resizedBmp.compress(Bitmap.CompressFormat.JPEG, 100, fosObj);
            fosObj.flush();
            fosObj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile())
        {
            //dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" +sourceFileUri);

            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet

                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL("http://incoders.co.kr/incode/input_image.php");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while (true)
                {
                    line = rd.readLine();
                    Log.i("uploadFile","HTTP Response is : " + line);
                    if (line == null)
                        break;
                }

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){}

                //close the streams //

                fileInputStream.close();
                dos.flush();
                dos.close();
            }
            catch (MalformedURLException ex)
            {
                //dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(it_bw_list_2.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }

                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            }
            catch (Exception e)
            {
                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(it_bw_list_2.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }

            //dialog.dismiss();
            return serverResponseCode;
        } // End else block

    }

}
