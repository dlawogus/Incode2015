package dev.woody.ext.info;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import dev.woody.ext.adapter.ExpandableListAdapter;
import dev.woody.ext.datainfo.it_expandable_item;
import dev.woody.ext.incode2015.R;

public class it_notice extends Activity {
    private ImageButton mBackbtn;
    private ArrayList<it_expandable_item> mNoticeList;
    private ExpandableListAdapter mExAdapter;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_notice);

        mBackbtn = (ImageButton)findViewById(R.id.backbtn);
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNoticeList = new ArrayList<it_expandable_item>();
        listView = (ExpandableListView)findViewById(R.id.notice_expandableListView);
        mExAdapter = new ExpandableListAdapter(this, mNoticeList);
        listView.setAdapter(mExAdapter);
        //listView.expandGroup(1);

        new HttpTask().execute();

        //차일드 클릭 리스너
        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {

                return false;
            }
        });

        //그룹 클릭 리스너
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = mExAdapter.getGroupCount();
                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        listView.collapseGroup(i);
                }
            }
        });
    }

    class HttpTask extends AsyncTask<Void, Void, Void> {
        String urlPath = "http://incoders.co.kr/incode/notice.php";
        String total = "";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpPost request = new HttpPost(urlPath);
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));


                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //JSONObject object = new JSONObject(value);
                JSONArray objectArray = new JSONArray(total);
                for (int i = 0; i < objectArray.length(); i++) {
                    JSONObject insideObject = objectArray.getJSONObject(i);
                    it_expandable_item item = new it_expandable_item();
                    item.setTitle(insideObject.getString("title"));
                    item.setContents(insideObject.getString("contents"));
                    item.setDate(insideObject.getString("date"));
                    mNoticeList.add(item);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            mExAdapter.notifyDataSetChanged();
        }
    }
}
