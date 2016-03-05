package dev.woody.ext.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.woody.ext.datainfo.it_expandable_item;
import dev.woody.ext.incode2015.R;

/**
 * Created by imjaehyun on 15. 8. 20..
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<it_expandable_item> noticeList;
    private ViewHolder  viewHolder;

    public ExpandableListAdapter(Context context, ArrayList<it_expandable_item> noticeList) {
        this.mContext = context;
        this.noticeList = noticeList;
        this.mInflater = (LayoutInflater)context.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return noticeList.get(groupPosition).getContents();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            v = getParentGenericView();

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) v.findViewById(R.id.group_title);
            viewHolder.expand = (ImageView) v.findViewById(R.id.expand);
            /*
            viewHolder.typeface= Typeface.createFromAsset(mContext.getAssets(),
                    "NanumBarunGothicBold.ttf");
            viewHolder.text.setTypeface(viewHolder.typeface);
            */
            Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "korea.ttf.mp3");
            viewHolder.title.setTypeface(typeFace);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }


        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if (isExpanded)
            viewHolder.expand.setImageResource(R.drawable.drop_down_1);
        else
            viewHolder.expand.setImageResource(R.drawable.drop_down);

        //제목 붙이기
        viewHolder.title.setText(noticeList.get(groupPosition).getTitle());
        viewHolder.title.setSelected(true);
        return v;
    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            v = getChildGenericView();

            viewHolder = new ViewHolder();
            viewHolder.contents = (TextView)v.findViewById(R.id.child_contents);
            /*
            viewHolder.typeface=Typeface.createFromAsset(mContext.getAssets(),
                    "NanumBarunGothic.ttf");
            viewHolder.text.setTypeface(viewHolder.typeface);
            */
            Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "korea.ttf.mp3");
            viewHolder.contents.setTypeface(typeFace);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.contents.setText(noticeList.get(groupPosition).getContents());

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return noticeList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return noticeList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    public View getChildGenericView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lay_it_notice_child, null);
        return view;
    }

    public View getParentGenericView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lay_it_notice_group, null);
        return view;
    }
    //어댑터 제거
    @Override
    protected void finalize() throws Throwable {
        free();
        super.finalize();
    }
    private void free(){
        viewHolder = null;
        mContext = null;
    }

    class ViewHolder {
        public TextView contents = null;
        public TextView title = null;
        public ImageView expand = null;
        public Typeface typeface;
        //public Button favoritebtn1 = null;
    }
}