package dev.woody.ext.info;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import dev.woody.ext.adapter.InfoPagerAdapter;
import dev.woody.ext.incode2015.R;

public class it_info  extends Activity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ImageButton mClosebtn;
    private int position;
    private ImageView mIndicater1;
    private ImageView mIndicater2;
    private ImageView mIndicater3;
    private ImageView mIndicater4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_info);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new InfoPagerAdapter(this));

        mClosebtn = (ImageButton)findViewById(R.id.btn_close);
        mClosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIndicater1 = (ImageView)findViewById(R.id.slide_scroll_1);
        mIndicater2 = (ImageView)findViewById(R.id.slide_scroll_2);
        mIndicater3 = (ImageView)findViewById(R.id.slide_scroll_3);
        mIndicater4 = (ImageView)findViewById(R.id.slide_scroll_4);
        //position=pager.getCurrentItem();

        pager.setOnPageChangeListener(new OnPageChangeListener(){
            @Override
            public void onPageSelected(int position)
            {
                // if listener is set - when using an indicator, must update that here
                //mIndicator.setCurrentItem(position);
                if (position == 0){
                    mIndicater1.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                }else if(position == 1){
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                }else if(position == 2){
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll_1);
                    mIndicater4.setImageResource(R.drawable.slide_scroll);
                }else if(position == 3){
                    mIndicater1.setImageResource(R.drawable.slide_scroll);
                    mIndicater2.setImageResource(R.drawable.slide_scroll);
                    mIndicater3.setImageResource(R.drawable.slide_scroll);
                    mIndicater4.setImageResource(R.drawable.slide_scroll_1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {}
        });


    }

}
