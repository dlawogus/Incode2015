package dev.woody.ext.info;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import dev.woody.ext.incode2015.R;

public class it_yakgwan_1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_yakgwan_1);

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


    }
}
