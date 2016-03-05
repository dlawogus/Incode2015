package dev.woody.ext.info;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import dev.woody.ext.adapter.ExpandableListAdapter;
import dev.woody.ext.datainfo.it_expandable_item;
import dev.woody.ext.incode2015.R;

public class it_qna extends Activity {
    private ImageButton mBackbtn;
    private ArrayList<it_expandable_item> mNoticeList;
    private ExpandableListAdapter mExAdapter;
    private ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_it_qna);

        TextView back_text = (TextView)findViewById(R.id.back_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "korea.ttf.mp3");
        back_text.setTypeface(typeFace);
        mBackbtn = (ImageButton)findViewById(R.id.backbtn);
        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNoticeList = new ArrayList<it_expandable_item>();
        mNoticeList.add(new it_expandable_item("인코드는 어떻게 사용하는 건가요?","인코드는 4자리 문자열로 이루어진 사용자기반코드시스템입니다.\n" +
                "온/오프라인 광고를 통해서 인코드박스에 기재된 4자리 문자열을\n" +
                "사용자가 인지한 후 어플리케이션을 이용하여 그 코드를 입력하면\n" +
                "그 광고에서는 접할 수 없었던 상세정보, 안내정보 등 다양하고 수많은\n" +
                "정보를 접할수 있습니다.\n" +
                "그리고 코드생성 기능을 활용하여 자신이 직접 코드를 생성하고 배포하여\n" +
                "사용할 수 있습니다."));
        mNoticeList.add(new it_expandable_item("비밀번호를 분실 했습니다","인코드 계정의 비밀번호를 분실 했을 경우에 로그인페이지에서\n" +
                "하단의 '비밀번호를 잊으셨나요' 텍스트를 누르게 되면\n" +
                "비밀번호 찾기 페이지로 연결됩니다.\n" +
                "\n" +
                "비밀번호 찾기 페이지에서 본인의 계정(이메일주소)을 입력하시면\n" +
                "해당 메일로 임시비밀번호를 전송해드립니다.\n" +
                "\n" +
                "임시비밀번호로 로그인 하신 후 계정설정 페이지에서 비밀번호 변경이 가능합니다."));
        mNoticeList.add(new it_expandable_item("인코드를 직접 생성하고 싶습니다","로그인을 하신 후 메뉴-코드생성 페이지에 접속하셔서 자신이 코드화 시키고싶은\n" +
                "정보가 담긴 URL주소나 이미지를 입력하신 후 코드생성방식 정하고\n" +
                "기간을 설정하면 코드를 생성할 수 있습니다.\n" +
                "\n" +
                "코드생성방식은 무작위생성 방식과, 사용자가 직접 원하는 코드번호나 문자를 입력할 수 있는\n" +
                "사용자지정 방식이 있습니다.\n" +
                "\n" +
                "코드기간은 자신이 생성 할 코드의 사용기간으로써 지정기간이 지나게 되면\n" +
                "자동으로 삭제/소멸 됩니다."));
        mNoticeList.add(new it_expandable_item("코드관리 페이지는 무엇인가요?","로그인 하신 후 메뉴-코드관리 페이지에 접속하시게 되면\n" +
                "자신이 생성한 코드의 리스트를 확인하실 수 있습니다.\n" +
                "그리고 코드리스트박스의 우측에 있는 화살표를 누르시게 되면\n" +
                "해당코드로 바로 연결 됩니다.\n" +
                "\n" +
                "코드리스트에서 코드리스트박스를 선택하시게 되면\n" +
                "해당 코드가 얼마나 사용되고 있는지에 관한 통계기능과\n" +
                "해당 코드의 URL및 이미지 수정, URL및 이미지의 설명 수정, 기간재설정\n" +
                "등 다양한 기능을 사용하실 수 있습니다."));
        mNoticeList.add(new it_expandable_item("최근사용목록 페이지는 무엇인가요?","메뉴-최근사용목록 페이지에 접속하시게 되면\n" +
                "사용자가 최근에 사용했던 코드들이 최근순으로 나열되게 됩니다.\n" +
                "해당코드의 코드리스트박스를 선택하시면 바로 해당코드로 연결됩니다.\n" +
                "그리고 최근사용한코드의 공유기능과 목록제거 기능 또한 제공됩니다."));
        mNoticeList.add(new it_expandable_item("코드를 인쇄물에 부착하고 싶습니다","자신이 생성한 코드를 광고,인쇄물 등 다양한 용도로 활용하기 위해서는\n" +
                "코드를 생성한 후 코드관리페이지를 통해서\n" +
                "해당코드를 선택한 후 해당코드페이지의 상단바에 공유버튼을 누르시게되면\n" +
                "바로 자신의 사진첩에 이미지로 저장 하실수 있는 버튼과, 그 외 공유기능이 제공됩니다.\n" +
                "\n" +
                "해당코드를 이미지로 저장 혹은 그외 공유기능을 활용하셔서 획득하신 사각형의 인코드이미지를\n" +
                "자신이 부착하고 싶은 곳에 이미지삽입 혹은 부착하시면 됩니다."));
        mNoticeList.add(new it_expandable_item("사용하던 코드의 기간이 끝났습니다","\n" +
                "기간이 종료된 코드가 자신이 생성한 코드라면 메뉴-코드관리 페이지를 통해서\n" +
                "기간재설정을 하신 후 설정하신 기간 만큼 연장하셔서 사용할 수 있습니다.\n" +
                "\n" +
                "하지만 타인이 생성한 코드일 경우 타인이 해당코드의 기간연장을 하지 않는 이상 그 코드는 \n" +
                "원하시는 정보페이지와 더이상 연결 되지 않습니다.\n" +
                "\n" +
                "물론 자신이 그 정보페이지를 코드생성기능을 활용하여 직접코드화시켜서\n" +
                "다시 사용하실수 있습니다."));
        mNoticeList.add(new it_expandable_item("코드의 사용통계를 알고 싶습니다","자신이 생성한 코드의 사용통계를 확인하기 위해서는\n" +
                "메뉴-코드관리 페이지에서 자신이 생성한 코드를 선택한 후\n" +
                "코드정보페이지에서 일별,주별 hit수, total hit, 성별에 따른 hit수 까지\n" +
                "통계정보를 제공받으실수 있습니다.\n"));
        mNoticeList.add(new it_expandable_item("인코드 회원을 탈퇴하고 싶습니다","\n" +
                "인코드 회원을 탈퇴하기 위해서는 로그인 하신후\n" +
                "메뉴-상단계정바의 계정설정 페이지를 통해서 \n" +
                "비밀번호 확인 후 쉽게 회원탈퇴가 가능합니다."));

        listView = (ExpandableListView)findViewById(R.id.notice_expandableListView);
        mExAdapter = new ExpandableListAdapter(this, mNoticeList);
        listView.setAdapter(mExAdapter);
        //listView.expandGroup(1);

        //차일드 클릭 리스너
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {

                return false;
            }
        });

        //그룹 클릭 리스너
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
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
}
