package dev.woody.ext.codecreation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import dev.woody.ext.incode2015.R;

public class it_bw_cre_user extends Activity {
    private ImageButton close;

    int padtype;
    String vcode = "";
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button opad2_btn0, opad2_btn1, opad2_btn2, opad2_btn3,opad2_btn4, opad2_btn5, opad2_btn6, opad2_btn7, opad2_btn8, opad2_btn9;
    Button btnq, btnw, btne, btnr, btnt, btny, btnu, btni, btno, btnp;
    Button btna, btns, btnd, btnf, btng, btnh, btnj, btnk, btnl;
    Button btnz, btnx, btnc, btnv, btnb,btnn, btnm;
    ImageView btnback1;
    ImageView btnback2;
    Button btnchange;
    ImageView btnchange2;
    ImageButton btnin;
    Button vnumber1, vnumber2, vnumber3, vnumber4;
    LinearLayout opad;
    LinearLayout opad2;
    LinearLayout upback;

    String fromIndex;

    RelativeLayout code_layout;
    TextView text_dont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_bw_cre_user);

        close = (ImageButton)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        fromIndex = intent.getStringExtra("index");

        padtype = 1;
        vnumber1 = (Button)findViewById(R.id.code_item_textView_vnum1);
        vnumber2 = (Button)findViewById(R.id.code_item_textView_vnum2);
        vnumber3 = (Button)findViewById(R.id.code_item_textView_vnum3);
        vnumber4 = (Button)findViewById(R.id.code_item_textView_vnum4);
        vnumber1.setOnClickListener(onclicks3);
        vnumber2.setOnClickListener(onclicks3);
        vnumber3.setOnClickListener(onclicks3);
        vnumber4.setOnClickListener(onclicks3);

        //Numeric pad
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf.mp3");
        opad = (LinearLayout)findViewById(R.id.opad);
        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btn0.setTypeface(typeFace);
        btn1.setTypeface(typeFace);
        btn2.setTypeface(typeFace);
        btn3.setTypeface(typeFace);
        btn4.setTypeface(typeFace);
        btn5.setTypeface(typeFace);
        btn6.setTypeface(typeFace);
        btn7.setTypeface(typeFace);
        btn8.setTypeface(typeFace);
        btn9.setTypeface(typeFace);
        btnback1 = (ImageView)findViewById(R.id.opad_btnback);
        btnchange = (Button)findViewById(R.id.btnchange);
        btnchange.setTypeface(typeFace);
        btn0.setOnClickListener(onclicks);
        btn1.setOnClickListener(onclicks);
        btn2.setOnClickListener(onclicks);
        btn3.setOnClickListener(onclicks);
        btn4.setOnClickListener(onclicks);
        btn5.setOnClickListener(onclicks);
        btn6.setOnClickListener(onclicks);
        btn7.setOnClickListener(onclicks);
        btn8.setOnClickListener(onclicks);
        btn9.setOnClickListener(onclicks);
        btnback1.setOnClickListener(onclicks);
        btnchange.setOnClickListener(onclicks);

        //Alphabet $ Numeric pad
        opad2 = (LinearLayout)findViewById(R.id.opad2);
        opad2_btn0 = (Button)findViewById(R.id.opad2_btn0);
        opad2_btn1 = (Button)findViewById(R.id.opad2_btn1);
        opad2_btn2 = (Button)findViewById(R.id.opad2_btn2);
        opad2_btn3 = (Button)findViewById(R.id.opad2_btn3);
        opad2_btn4 = (Button)findViewById(R.id.opad2_btn4);
        opad2_btn5 = (Button)findViewById(R.id.opad2_btn5);
        opad2_btn6 = (Button)findViewById(R.id.opad2_btn6);
        opad2_btn7 = (Button)findViewById(R.id.opad2_btn7);
        opad2_btn8 = (Button)findViewById(R.id.opad2_btn8);
        opad2_btn9 = (Button)findViewById(R.id.opad2_btn9);
        opad2_btn0.setTypeface(typeFace);
        opad2_btn1.setTypeface(typeFace);
        opad2_btn2.setTypeface(typeFace);
        opad2_btn3.setTypeface(typeFace);
        opad2_btn4.setTypeface(typeFace);
        opad2_btn5.setTypeface(typeFace);
        opad2_btn6.setTypeface(typeFace);
        opad2_btn7.setTypeface(typeFace);
        opad2_btn8.setTypeface(typeFace);
        opad2_btn9.setTypeface(typeFace);
        opad2_btn0.setOnClickListener(onclicks2);
        opad2_btn1.setOnClickListener(onclicks2);
        opad2_btn2.setOnClickListener(onclicks2);
        opad2_btn3.setOnClickListener(onclicks2);
        opad2_btn4.setOnClickListener(onclicks2);
        opad2_btn5.setOnClickListener(onclicks2);
        opad2_btn6.setOnClickListener(onclicks2);
        opad2_btn7.setOnClickListener(onclicks2);
        opad2_btn8.setOnClickListener(onclicks2);
        opad2_btn9.setOnClickListener(onclicks2);

        btnq = (Button)findViewById(R.id.opad2_btnq);
        btnw = (Button)findViewById(R.id.opad2_btnw);
        btne = (Button)findViewById(R.id.opad2_btne);
        btnr = (Button)findViewById(R.id.opad2_btnr);
        btnt = (Button)findViewById(R.id.opad2_btnt);
        btny = (Button)findViewById(R.id.opad2_btny);
        btnu = (Button)findViewById(R.id.opad2_btnu);
        btni = (Button)findViewById(R.id.opad2_btni);
        btno = (Button)findViewById(R.id.opad2_btno);
        btnp = (Button)findViewById(R.id.opad2_btnp);
        btnq.setTypeface(typeFace);
        btnw.setTypeface(typeFace);
        btne.setTypeface(typeFace);
        btnr.setTypeface(typeFace);
        btnt.setTypeface(typeFace);
        btny.setTypeface(typeFace);
        btnu.setTypeface(typeFace);
        btni.setTypeface(typeFace);
        btno.setTypeface(typeFace);
        btnp.setTypeface(typeFace);
        btnq.setOnClickListener(onclicks2);
        btnw.setOnClickListener(onclicks2);
        btne.setOnClickListener(onclicks2);
        btnr.setOnClickListener(onclicks2);
        btnt.setOnClickListener(onclicks2);
        btny.setOnClickListener(onclicks2);
        btnu.setOnClickListener(onclicks2);
        btni.setOnClickListener(onclicks2);
        btno.setOnClickListener(onclicks2);
        btnp.setOnClickListener(onclicks2);

        btna = (Button)findViewById(R.id.opad2_btna);
        btns = (Button)findViewById(R.id.opad2_btns);
        btnd = (Button)findViewById(R.id.opad2_btnd);
        btnf = (Button)findViewById(R.id.opad2_btnf);
        btng = (Button)findViewById(R.id.opad2_btng);
        btnh = (Button)findViewById(R.id.opad2_btnh);
        btnj = (Button)findViewById(R.id.opad2_btnj);
        btnk = (Button)findViewById(R.id.opad2_btnk);
        btnl = (Button)findViewById(R.id.opad2_btnl);
        btna.setOnClickListener(onclicks2);
        btns.setOnClickListener(onclicks2);
        btnd.setOnClickListener(onclicks2);
        btnf.setOnClickListener(onclicks2);
        btng.setOnClickListener(onclicks2);
        btnh.setOnClickListener(onclicks2);
        btnj.setOnClickListener(onclicks2);
        btnk.setOnClickListener(onclicks2);
        btnl.setOnClickListener(onclicks2);

        btnz = (Button)findViewById(R.id.opad2_btnz);
        btnx = (Button)findViewById(R.id.opad2_btnx);
        btnc = (Button)findViewById(R.id.opad2_btnc);
        btnv = (Button)findViewById(R.id.opad2_btnv);
        btnb = (Button)findViewById(R.id.opad2_btnb);
        btnn = (Button)findViewById(R.id.opad2_btnn);
        btnm = (Button)findViewById(R.id.opad2_btnm);
        btnz.setTypeface(typeFace);
        btnx.setTypeface(typeFace);
        btnc.setTypeface(typeFace);
        btnv.setTypeface(typeFace);
        btnb.setTypeface(typeFace);
        btnn.setTypeface(typeFace);
        btnm.setTypeface(typeFace);
        btnz.setOnClickListener(onclicks2);
        btnx.setOnClickListener(onclicks2);
        btnc.setOnClickListener(onclicks2);
        btnv.setOnClickListener(onclicks2);
        btnb.setOnClickListener(onclicks2);
        btnn.setOnClickListener(onclicks2);
        btnm.setOnClickListener(onclicks2);

        btnback2 = (ImageView)findViewById(R.id.opad2_btnback);
        btnchange2 = (ImageView)findViewById(R.id.opad2_btnchange);
        btnback2.setOnClickListener(onclicks2);
        btnchange2.setOnClickListener(onclicks2);

        //In button pad
        btnin = (ImageButton)findViewById(R.id.btnin);
        btnin.setOnClickListener(onclicks3);

        code_layout = (RelativeLayout)findViewById(R.id.code_layout);
        text_dont = (TextView)findViewById(R.id.text_dont);

    }

    Button.OnClickListener onclicks = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn0:
                    btn_set("0");
                    break;
                case R.id.btn1:
                    btn_set("1");
                    break;
                case R.id.btn2:
                    btn_set("2");
                    break;
                case R.id.btn3:
                    btn_set("3");
                    break;
                case R.id.btn4:
                    btn_set("4");
                    break;
                case R.id.btn5:
                    btn_set("5");
                    break;
                case R.id.btn6:
                    btn_set("6");
                    break;
                case R.id.btn7:
                    btn_set("7");
                    break;
                case R.id.btn8:
                    btn_set("8");
                    break;
                case R.id.btn9:
                    btn_set("9");
                    break;
                case R.id.opad_btnback:
                    btn_back();
                    break;
                case R.id.btnchange:
                    padtype = 2;
                    pad_set(padtype);
                    break;
            }
        }
    };

    Button.OnClickListener onclicks2 = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.opad2_btn0:
                    btn_set("0");
                    break;
                case R.id.opad2_btn1:
                    btn_set("1");
                    break;
                case R.id.opad2_btn2:
                    btn_set("2");
                    break;
                case R.id.opad2_btn3:
                    btn_set("3");
                    break;
                case R.id.opad2_btn4:
                    btn_set("4");
                    break;
                case R.id.opad2_btn5:
                    btn_set("5");
                    break;
                case R.id.opad2_btn6:
                    btn_set("6");
                    break;
                case R.id.opad2_btn7:
                    btn_set("7");
                    break;
                case R.id.opad2_btn8:
                    btn_set("8");
                    break;
                case R.id.opad2_btn9:
                    btn_set("9");
                    break;
                case R.id.opad2_btnq:
                    btn_set("Q");
                    break;
                case R.id.opad2_btnw:
                    btn_set("W");
                    break;
                case R.id.opad2_btne:
                    btn_set("E");
                    break;
                case R.id.opad2_btnr:
                    btn_set("R");
                    break;
                case R.id.opad2_btnt:
                    btn_set("T");
                    break;
                case R.id.opad2_btny:
                    btn_set("Y");
                    break;
                case R.id.opad2_btnu:
                    btn_set("U");
                    break;
                case R.id.opad2_btni:
                    btn_set("I");
                    break;
                case R.id.opad2_btno:
                    btn_set("O");
                    break;
                case R.id.opad2_btnp:
                    btn_set("P");
                    break;
                case R.id.opad2_btna:
                    btn_set("A");
                    break;
                case R.id.opad2_btns:
                    btn_set("S");
                    break;
                case R.id.opad2_btnd:
                    btn_set("D");
                    break;
                case R.id.opad2_btnf:
                    btn_set("F");
                    break;
                case R.id.opad2_btng:
                    btn_set("G");
                    break;
                case R.id.opad2_btnh:
                    btn_set("H");
                    break;
                case R.id.opad2_btnj:
                    btn_set("J");
                    break;
                case R.id.opad2_btnk:
                    btn_set("K");
                    break;
                case R.id.opad2_btnl:
                    btn_set("L");
                    break;
                case R.id.opad2_btnz:
                    btn_set("Z");
                    break;
                case R.id.opad2_btnx:
                    btn_set("X");
                    break;
                case R.id.opad2_btnc:
                    btn_set("C");
                    break;
                case R.id.opad2_btnv:
                    btn_set("V");
                    break;
                case R.id.opad2_btnb:
                    btn_set("B");
                    break;
                case R.id.opad2_btnn:
                    btn_set("N");
                    break;
                case R.id.opad2_btnm:
                    btn_set("M");
                    break;
                case R.id.opad2_btnback:
                    btn_back();
                    break;
                case R.id.opad2_btnchange:
                    padtype = 1;
                    pad_set(padtype);
                    break;
            }
        }
    };

    Button.OnClickListener onclicks3 = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vnumber1:
                    pad_set(padtype);
                    break;
                case R.id.vnumber2:
                    pad_set(padtype);
                    break;
                case R.id.vnumber3:
                    pad_set(padtype);
                    break;
                case R.id.vnumber4:
                    pad_set(padtype);
                    break;
                case R.id.btnin:
                    //;
                    if( vcode.length() != 4){
                        Toast.makeText(it_bw_cre_user.this,"4자리 코드를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else{
                        new HttpTask().execute();
                    }
                    break;
                case R.id.contents2:
                    pad_set(3);
                    break;
            }
        }
    };

    public void btn_set(String str)
    {
        if(vcode.length()<4)
        {
            pad_set(padtype);
            vcode = vcode + str;
            btn_display();
        }
        else
        {
            pad_set(3);
            btn_display();
        }
    }

    public void btn_display()
    {
        vnumber1.setText(" ");
        vnumber2.setText(" ");
        vnumber3.setText(" ");
        vnumber4.setText(" ");
        pad_set(padtype);
        for(int i=0;i<vcode.length();i++)
        {
            if(i==0) vnumber1.setText(vcode.substring(0,1));
            if(i==1) vnumber2.setText(vcode.substring(1,2));
            if(i==2) vnumber3.setText(vcode.substring(2,3));
            if(i==3)
            {
                vnumber4.setText(vcode.substring(3,4));
                pad_set(3);
                btnin.setEnabled(true);
            }
        }
    }

    public void btn_back()
    {
        if(vcode.length() <= 1)
            vcode = "";
        else
            vcode = vcode.substring(0,vcode.length()-1);

        btn_display();
    }

    public void pad_set(int no)
    {
        if(no==1) {
            opad.setVisibility(View.VISIBLE);
            opad2.setVisibility(View.GONE);
        }
        else if(no==2)
        {
            opad.setVisibility(View.GONE);
            opad2.setVisibility(View.VISIBLE);
        }
    }

    class HttpTask extends AsyncTask<Void, Void, String> {
        String urlPath = "http://incoders.co.kr/incode/user_code_check.php";
        InputStream im = null;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("code", vcode));
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

            if(value.equals("no")) {
                text_dont.setVisibility(View.VISIBLE);
                code_layout.setBackgroundResource(R.drawable.border_code_1);
            }else{
                if( fromIndex.equals("1") ) {
                    it_bw_cre_fr_1.mCode = vcode;
                    it_bw_cre_fr_1.code_item_textView_vnum1.setText(vcode.substring(0,1));
                    it_bw_cre_fr_1.code_item_textView_vnum2.setText(vcode.substring(1,2));
                    it_bw_cre_fr_1.code_item_textView_vnum3.setText(vcode.substring(2,3));
                    it_bw_cre_fr_1.code_item_textView_vnum4.setText(vcode.substring(3,4));
                }else {
                    it_bw_cre_fr_2.mCode = vcode;
                    it_bw_cre_fr_2.code_item_textView_vnum1.setText(vcode.substring(0,1));
                    it_bw_cre_fr_2.code_item_textView_vnum2.setText(vcode.substring(1,2));
                    it_bw_cre_fr_2.code_item_textView_vnum3.setText(vcode.substring(2,3));
                    it_bw_cre_fr_2.code_item_textView_vnum4.setText(vcode.substring(3,4));
                }
                finish();
            }
        }
        //getActivity().finish();
    }
}
