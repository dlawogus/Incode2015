package dev.woody.ext.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import dev.woody.ext.datainfo.it_bw_code_info;
import dev.woody.ext.dialog.CustomDialog_gong;
import dev.woody.ext.incode2015.R;
import dev.woody.ext.incode2015.it_bw_history;

/**
 * Created by Geoji on 2015-06-08.
 */
public class it_bw_history_adapter extends BaseAdapter {
    private ArrayList<it_bw_code_info> mItemList;
    private ArrayList<Bitmap> mCache;
    private Context context;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private CustomDialog_gong mCustomDialog;
    int layout;

    public interface onButtonClickListener
    {
        void onButtonGotoBtn(String code);
        void onButtonDelBtn(String code);
        void onButtonLinearLayout(String code);
    }

    public it_bw_history_adapter(Context context, int resource, ArrayList<it_bw_code_info> objects) {
        //super(context, resource, objects);
        this.mItemList = objects;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCache = new ArrayList<Bitmap>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if(v == null) {
            //LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.lay_it_history_item, null);
            viewHolder.vnum1 = (TextView) v.findViewById(R.id.history_item_textView_vnum1);
            viewHolder.vnum2 = (TextView) v.findViewById(R.id.history_item_textView_vnum2);
            viewHolder.vnum3 = (TextView) v.findViewById(R.id.history_item_textView_vnum3);
            viewHolder.vnum4 = (TextView) v.findViewById(R.id.history_item_textView_vnum4);
            viewHolder.url = (TextView) v.findViewById(R.id.history_item_textView_url);
            viewHolder.config = (TextView) v.findViewById(R.id.history_item_textView_config);
            viewHolder.date = (TextView) v.findViewById(R.id.history_item_textView_date);
            viewHolder.check = (ImageButton) v.findViewById(R.id.history_item_imagebutton_check);
            viewHolder.background = (RelativeLayout) v.findViewById(R.id.history_item_layout_background);
            viewHolder.container = (RelativeLayout) v.findViewById(R.id.code_layout);

            Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf.mp3");
            Typeface typeFace1 = Typeface.createFromAsset(context.getAssets(), "arialbd.ttf.mp3");
            viewHolder.vnum1.setTypeface(typeFace1);
            viewHolder.vnum2.setTypeface(typeFace1);
            viewHolder.vnum3.setTypeface(typeFace1);
            viewHolder.vnum4.setTypeface(typeFace1);
            viewHolder.url.setTypeface(typeFace);
            viewHolder.date.setTypeface(typeFace);
            viewHolder.config.setTypeface(typeFace);

            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)v.getTag();
        }

        it_bw_code_info item = mItemList.get(position);

        if(item != null) {
            viewHolder.vnum1.setText(item.getVnumber().substring(0, 1));
            viewHolder.vnum2.setText(item.getVnumber().substring(1, 2));
            viewHolder.vnum3.setText(item.getVnumber().substring(2, 3));
            viewHolder.vnum4.setText(item.getVnumber().substring(3, 4));
            viewHolder.url.setText(item.getUrl());
            viewHolder.config.setText(item.getConfig());
            viewHolder.date.setText(item.getStart() + " ~ " + item.getEnd());
        }

        //버튼의 포커스를 뺀다
        viewHolder.check.setFocusable(false);
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (it_bw_history.mListitemArray.get(position).getIschecked()) {//눌러져 있을때
                    it_bw_history.mListitemArray.get(position).setIsChecked(false);
                    it_bw_history.arrAdapt.notifyDataSetChanged();
                } else {                                                         //안눌러져 있을때
                    it_bw_history.mListitemArray.get(position).setIsChecked(true);
                    it_bw_history.arrAdapt.notifyDataSetChanged();
                }
            }
        });

        if( it_bw_history.mListitemArray.get(position).getIschecked() ){//눌러져 있을때
            viewHolder.check.setImageResource(R.drawable.history_check1);
            viewHolder.background.setBackgroundColor(Color.rgb(239, 64, 137));
        }else {                                                         //안눌러져 있을때
            viewHolder.check.setImageResource(R.drawable.history_check);
            viewHolder.background.setBackgroundColor(Color.rgb(255, 246, 238));
        }

        return v;
    }


    @Override
    public int getCount()
    {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    //갤러리 갱신
    private void galleryAddPic(String s_pic_filename) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(s_pic_filename);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public it_bw_code_info delete(String in)
    {
        it_bw_code_info deletedItem = null;
        for(int i=0; i< mItemList.size(); i++)
        {
            deletedItem = mItemList.get(i);
            if(in.equals(deletedItem.getVnumber()))
            {
                mItemList.remove(i);
                break;
            }
        }
        return deletedItem;
    }

    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder{
        public TextView vnum1 = null;
        public TextView vnum2 = null;
        public TextView vnum3 = null;
        public TextView vnum4 = null;
        public TextView url = null;
        public TextView config = null;
        public TextView date = null;
        public ImageButton check = null;
        public RelativeLayout background = null;
        public RelativeLayout container = null;
        public Bitmap captureView;
    }

}
