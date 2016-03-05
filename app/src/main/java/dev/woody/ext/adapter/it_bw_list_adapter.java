package dev.woody.ext.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.woody.ext.datainfo.it_bw_code_info;
import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

/**
 * Created by imjaehyun on 15. 8. 23..
 */
public class it_bw_list_adapter extends BaseAdapter {
    private ArrayList<it_bw_code_info> mItemList;
    private Context context;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    int layout;

    public interface onButtonClickListener {
        void onButtonGotoBtn(String code);

        void onButtonDelBtn(String code);

        void onButtonLinearLayout(String code);
    }

    /*
    private onButtonClickListener adptCallback = null;

    public void setOnButtonClickListener(onButtonClickListener callback)
    {
        adptCallback = callback;
    }
    */

    public it_bw_list_adapter(Context context, int resource, ArrayList<it_bw_code_info> objects) {
        //super(context, resource, objects);
        this.mItemList = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            //LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.lay_it_bw_list_item, null);
            viewHolder.vnum1 = (TextView) v.findViewById(R.id.history_item_textView_vnum1);
            viewHolder.vnum2 = (TextView) v.findViewById(R.id.history_item_textView_vnum2);
            viewHolder.vnum3 = (TextView) v.findViewById(R.id.history_item_textView_vnum3);
            viewHolder.vnum4 = (TextView) v.findViewById(R.id.history_item_textView_vnum4);
            viewHolder.url = (TextView) v.findViewById(R.id.history_item_textView_url);
            viewHolder.config = (TextView) v.findViewById(R.id.history_item_textView_config);
            viewHolder.date = (TextView) v.findViewById(R.id.history_item_textView_date);
            viewHolder.go = (ImageButton) v.findViewById(R.id.code_item_imagebutton_go);
            viewHolder.background = (RelativeLayout) v.findViewById(R.id.history_item_layout_background);

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
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        final it_bw_code_info item = mItemList.get(position);

        if (item != null)
            viewHolder.vnum1.setText(item.getVnumber().substring(0, 1));
            viewHolder.vnum2.setText(item.getVnumber().substring(1, 2));
            viewHolder.vnum3.setText(item.getVnumber().substring(2, 3));
            viewHolder.vnum4.setText(item.getVnumber().substring(3, 4));
            viewHolder.url.setText(item.getUrl());
            viewHolder.config.setText(item.getConfig());
            viewHolder.date.setText(item.getStart() + " ~ " + item.getEnd());
        {


        //버튼의 포커스를 뺀다
        viewHolder.go.setFocusable(false);
        viewHolder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mItemList.get(position).getVnumber().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://incoders.co.kr/incode/goto.php?vcode=" + code + "&user_id=" + Mains.UserID));
                context.startActivity(intent);
            }
        });


            //Button gotoBtn = (Button)v.findViewById(R.id.history_item_btn_goto);
            //Button delBtn = (Button)v.findViewById(R.id.history_item_btn_delete);
            //LinearLayout itemLayout = (LinearLayout)v.findViewById(R.id.history_item_layout_background);
            //final ImageView checkImage = (ImageView)v.findViewById(R.id.history_item_imageview_check);
            //final ImageView checkedImage = (ImageView)v.findViewById(R.id.history_item_imageview_checked);
            /*
            if(item.getIschecked()){
                checkedImage.setVisibility(View.GONE);
                checkImage.setVisibility(View.VISIBLE);
                item.setIsChecked(false);
            }
            else{
                checkedImage.setVisibility(View.VISIBLE);
                checkImage.setVisibility(View.GONE);
                item.setIsChecked(true);
            }

            gotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adptCallback != null)
                    {
                        String tmp = "";
                        tmp += vnum1.getText().toString();
                        tmp += vnum2.getText().toString();
                        tmp += vnum3.getText().toString();
                        tmp += vnum4.getText().toString();
                        adptCallback.onButtonGotoBtn(tmp);
                    }
                }
            });
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adptCallback != null)
                    {
                        String tmp = "";
                        tmp += vnum1.getText().toString();
                        tmp += vnum2.getText().toString();
                        tmp += vnum3.getText().toString();
                        tmp += vnum4.getText().toString();
                        adptCallback.onButtonDelBtn(tmp);
                    }
                }
            });

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getIschecked()){
                        checkedImage.setVisibility(View.GONE);
                        checkImage.setVisibility(View.VISIBLE);
                        item.setIsChecked(false);
                    }
                    else{
                        checkedImage.setVisibility(View.VISIBLE);
                        checkImage.setVisibility(View.GONE);
                        item.setIsChecked(true);
                    }
                    if(adptCallback != null) {
                        String tmp = "";
                        tmp += vnum1.getText().toString();
                        tmp += vnum2.getText().toString();
                        tmp += vnum3.getText().toString();
                        tmp += vnum4.getText().toString();
                        adptCallback.onButtonLinearLayout(tmp);
                    }
                }
            });
            */
        }
        return v;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    public void deleteSel(ArrayList<String> inStrings){
        for(int i=0; i<inStrings.size(); i++)
        {
            delete(inStrings.get(i));
        }
    }
    */

    public void deleteAll() {
        mItemList.clear();
    }


    /*
     * ViewHolder
     * getView의 속도 향상을 위해 쓴다.
     * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
     */
    class ViewHolder {
        public TextView vnum1 = null;
        public TextView vnum2 = null;
        public TextView vnum3 = null;
        public TextView vnum4 = null;
        public TextView url = null;
        public TextView config = null;
        public TextView date = null;
        public ImageButton go = null;
        public RelativeLayout background = null;
    }
}