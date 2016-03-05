package dev.woody.ext.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import dev.woody.ext.incode2015.R;

/**
 * Created by imjaehyun on 15. 8. 19..
 */

public class InfoPagerAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    public Context mContext;

    public InfoPagerAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // PagerAdapter에서 관리할 View 개수를 반환합니다.
    public int getCount() {
        return 4;
    }

    // ViewPager에서 사용할 View를 생성하고 등록해줍니다.
    public Object instantiateItem(View pager, int position){
        View v = null;

        switch(position){
            case 0:
                v = mInflater.inflate(R.layout.lay_it_info_fr_1, null);
                break;
            case 1:
                v = mInflater.inflate(R.layout.lay_it_info_fr_2, null);
                break;
            case 2:
                v = mInflater.inflate(R.layout.lay_it_info_fr_3, null);
                break;
            case 3:
                v = mInflater.inflate(R.layout.lay_it_info_fr_4, null);
                break;
        }

        ((ViewPager)pager).addView(v, null);

        return v;
    }


    // View를 삭제합니다.
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    // instantiateItem에서 생성한 객체를 이용할 것인지 여부를 반환합니다.
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
}
