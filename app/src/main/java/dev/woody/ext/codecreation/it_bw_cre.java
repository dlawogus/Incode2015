package dev.woody.ext.codecreation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dev.woody.ext.incode2015.R;


public class it_bw_cre  extends FragmentActivity {
    public static final int CODE_PICK_IMAGE = 0;
    public static final int CODE_CAMERA_IMAGE = 1;

    public static String image_path = "";

    public FragmentManager fragmentManager;

    public static String mDate = "7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lay_it_cre);

        //뒤로 가기
        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //탭1
        final Button cre_tab_1 = (Button)findViewById(R.id.cre_tab_1);
        final View cre_tab_indicator_1 = (View)findViewById(R.id.cre_tab_indicator_1);
        //탭2
        final Button cre_tab_2 = (Button)findViewById(R.id.cre_tab_2);
        final View cre_tab_indicator_2 = (View)findViewById(R.id.cre_tab_indicator_2);
        cre_tab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cre_tab_1.setTextColor(Color.rgb(255, 255, 255));
                cre_tab_indicator_1.setBackgroundColor(Color.rgb(24,112, 156));
                cre_tab_2.setTextColor(Color.rgb(210, 210, 210));
                cre_tab_indicator_2.setBackgroundColor(Color.rgb(25, 189, 196));
                selectItem(1);
            }
        });
        cre_tab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cre_tab_2.setTextColor(Color.rgb(255, 255, 255));
                cre_tab_indicator_2.setBackgroundColor(Color.rgb(24,112, 156));
                cre_tab_1.setTextColor(Color.rgb(210, 210, 210));
                cre_tab_indicator_1.setBackgroundColor(Color.rgb(25, 189, 196));
                selectItem(2);
            }
        });

        selectItem(1);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment=null;

        if( position == 1 ) {
            fragment = new it_bw_cre_fr_1();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.cre_fragment_place, fragment).commit();
        }else if( position == 2){
            fragment = new it_bw_cre_fr_2();
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.cre_fragment_place, fragment).commit();
        }
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

       //Log.d("이미지 result", Integer.toString(requestCode)+"  : "+Integer.toString(resultCode) );
        if (requestCode == CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imgUri = data.getData();
                    String imagePath = getRealPathFromURI(imgUri);
                    image_path = imagePath;
                    it_bw_cre_fr_2.setText();
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
                    it_bw_cre_fr_2.setText();
                    //Log.d("이미지", it_bw_cre.image_path + "");
                    //Toast.makeText(this,image_path,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}