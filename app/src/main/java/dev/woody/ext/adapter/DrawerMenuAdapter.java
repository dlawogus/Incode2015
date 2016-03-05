package dev.woody.ext.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dev.woody.ext.incode2015.Mains;
import dev.woody.ext.incode2015.R;

public class DrawerMenuAdapter extends BaseAdapter{
	private static String datainfo[];
	private LayoutInflater inflater;
	private Context con;
	private ViewHolder viewHolder;
	
	public DrawerMenuAdapter(Context context) {
		this.datainfo = context.getResources().getStringArray(R.array.drawmenu);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.con = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datainfo.length;
	}
	@Override
	public String getItem(int position) {
		return datainfo[position];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int pos = position;
		
		View v = convertView;
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.lay_it_drawer_item, null);

			//viewHolder.layout_item_param = (LayoutParams) v.getLayoutParams();
			viewHolder.drawer_id_logo = (ImageView)v.findViewById(R.id.drawer_id_logo);
			viewHolder.drawer_id_image = (ImageView)v.findViewById(R.id.drawer_id_image);
			viewHolder.drawer_id_text = (TextView)v.findViewById(R.id.drawer_id_text);
			viewHolder.drawer_layout_item_1 = (ViewGroup)v.findViewById(R.id.drawer_layout_item_1);

			viewHolder.drawer_imageview_2 = (ImageView)v.findViewById(R.id.drawer_imageview_2);
			viewHolder.drawer_menutext_2 = (TextView)v.findViewById(R.id.drawer_menutext_2);
			viewHolder.drawer_layout_item_2 = (ViewGroup)v.findViewById(R.id.drawer_layout_item_2);
			viewHolder.drawer_border = (View)v.findViewById(R.id.drawer_border);

			viewHolder.drawer_imageview_3 = (ImageView)v.findViewById(R.id.drawer_imageView3);
			viewHolder.drawer_menutext_3 = (TextView)v.findViewById(R.id.drawer_menutext_3);
			viewHolder.drawer_menutext_3_version = (TextView)v.findViewById(R.id.drawer_menutext_3_version);
			viewHolder.drawer_layout_item_3 = (ViewGroup)v.findViewById(R.id.drawer_layout_item_3);

			Typeface typeFace = Typeface.createFromAsset(con.getAssets(), "korea.ttf.mp3");
			viewHolder.drawer_menutext_2.setTypeface(typeFace);
			viewHolder.drawer_menutext_3.setTypeface(typeFace);
			v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}

		//viewHolder.menuText.setText(datainfo[pos]);


		if( pos == 0){
			viewHolder.drawer_layout_item_1.setVisibility(View.VISIBLE);
			viewHolder.drawer_layout_item_2.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_3.setVisibility(View.GONE);

			if(Mains.UserID.equals("")) {
				viewHolder.drawer_id_text.setVisibility(View.GONE);
				viewHolder.drawer_id_image.setVisibility(View.GONE);
				viewHolder.drawer_id_logo.setVisibility(View.VISIBLE);
			}else{
				viewHolder.drawer_id_text.setVisibility(View.VISIBLE);
				viewHolder.drawer_id_image.setVisibility(View.VISIBLE);
				viewHolder.drawer_id_logo.setVisibility(View.GONE);

				if( Mains.LoginType.equals("facebook")){
					viewHolder.drawer_id_text.setText(Mains.UserEmail);
				}else if( Mains.LoginType.equals("naver")){
					viewHolder.drawer_id_text.setText(Mains.UserEmail);
				}else{
					viewHolder.drawer_id_text.setText(Mains.UserID);
				}
				Typeface typeFace = Typeface.createFromAsset(con.getAssets(), "Roboto-Light.ttf.mp3");
				viewHolder.drawer_id_text.setTypeface(typeFace);
			}
		}else if( pos == 1){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.VISIBLE);
			viewHolder.drawer_layout_item_3.setVisibility(View.GONE);
			viewHolder.drawer_menutext_2.setText(datainfo[pos]);
			viewHolder.drawer_imageview_2.setImageResource(R.drawable.drawer1);
			viewHolder.drawer_border.setVisibility(View.GONE);
		}else if( pos == 2){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.VISIBLE);
			viewHolder.drawer_layout_item_3.setVisibility(View.GONE);
			viewHolder.drawer_menutext_2.setText(datainfo[pos]);
			viewHolder.drawer_imageview_2.setImageResource(R.drawable.drawer2);
			viewHolder.drawer_border.setVisibility(View.GONE);
		}else if( pos == 3){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.VISIBLE);
			viewHolder.drawer_layout_item_3.setVisibility(View.GONE);
			viewHolder.drawer_menutext_2.setText(datainfo[pos]);
			viewHolder.drawer_imageview_2.setImageResource(R.drawable.drawer3);
			viewHolder.drawer_border.setVisibility(View.GONE);
		}else if( pos == 4){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.VISIBLE);
			viewHolder.drawer_layout_item_3.setVisibility(View.GONE);
			viewHolder.drawer_menutext_2.setText(datainfo[pos]);
			viewHolder.drawer_imageview_2.setImageResource(R.drawable.drawer4);
		}else if( pos == 5){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_3.setVisibility(View.VISIBLE);
			viewHolder.drawer_menutext_3.setText(datainfo[pos]);
			viewHolder.drawer_menutext_3_version.setVisibility(View.GONE);
		}else if( pos == 6){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_3.setVisibility(View.VISIBLE);
			viewHolder.drawer_menutext_3.setText(datainfo[pos]);
			viewHolder.drawer_imageview_3.setImageResource(R.drawable.drawer5);
		}else if( pos == 7){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_3.setVisibility(View.VISIBLE);
			viewHolder.drawer_menutext_3.setText(datainfo[pos]);
			viewHolder.drawer_menutext_3_version.setVisibility(View.GONE);
		}else if( pos == 8){
			viewHolder.drawer_layout_item_1.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_2.setVisibility(View.GONE);
			viewHolder.drawer_layout_item_3.setVisibility(View.VISIBLE);
			viewHolder.drawer_menutext_3.setText(datainfo[pos]);
			viewHolder.drawer_menutext_3_version.setVisibility(View.GONE);
			if(Mains.UserID.length() == 0) {
				viewHolder.drawer_menutext_3.setText("로그인");
				viewHolder.drawer_imageview_3.setImageResource(R.drawable.drawer6);
			}else{
				viewHolder.drawer_menutext_3.setText("로그아웃");
				viewHolder.drawer_imageview_3.setImageResource(R.drawable.drawer6_1);
			}
		}
		
		return v;
	}
	
	/*
	 * ViewHolder 
	 * getView의 속도 향상을 위해 쓴다.
	 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
	 */
	class ViewHolder{
		public TextView drawer_id_text = null;
		public ImageView drawer_id_image = null;
		public ImageView drawer_id_logo = null;
		public ViewGroup drawer_layout_item_1 = null;

		public ImageView drawer_imageview_2 = null;
		public TextView	drawer_menutext_2 = null;
		public View drawer_border = null;
		public ViewGroup drawer_layout_item_2 = null;

		public ImageView drawer_imageview_3 = null;
		public TextView	drawer_menutext_3 = null;
		public TextView drawer_menutext_3_version = null;
		public ViewGroup drawer_layout_item_3 = null;
		//public RelativeLayout drawer_layout_item = null;
	}
	
}