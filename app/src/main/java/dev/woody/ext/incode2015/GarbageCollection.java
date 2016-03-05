package dev.woody.ext.incode2015;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GarbageCollection {
	private void garbageCollection(){};

	public static void recursiveRecycle(View root) {
		
		if (root == null)	
			return;

		//배경이미지 제거
		root.setBackgroundDrawable(null);

		//뷰 그룹 제거
		if (root instanceof ViewGroup) {
			ViewGroup group = (ViewGroup)root;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				recursiveRecycle(group.getChildAt(i));
			}
			if (!(root instanceof AdapterView)) {
				group.removeAllViews();
			}
		}

		//이미지 뷰 제거
		if (root instanceof ImageView) {
			((ImageView)root).setImageDrawable(null);
		}

		//이미지버튼 제거
		if (root instanceof ImageButton) {
			((ImageButton)root).setImageDrawable(null);
		}

		//텍스트 뷰 제거
		if (root instanceof TextView) {
			((TextView)root).setText(null);
		}

		root = null;

		return;
	}
}
