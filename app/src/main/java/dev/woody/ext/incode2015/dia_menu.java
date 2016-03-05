package dev.woody.ext.incode2015;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class dia_menu extends Dialog implements View.OnClickListener {
    Dialog dialogv;
    Mains actv;
    int myLovelyVariable;

    public dia_menu(Context context,int myLovelyVariable) {
        super(context);
        actv = ((Mains)context);
        this.myLovelyVariable = myLovelyVariable;
    }

    @Override
    public void onClick(View v)
    {
        dialogv = new Dialog(v.getContext(), android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialogv.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogv.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogv.setCancelable(true);
        dialogv.setContentView(R.layout.lay_dia_menu);
        dialogv.setCanceledOnTouchOutside(true);

        Button logx = (Button) dialogv.findViewById(R.id.logx);
        TextView login = (TextView)dialogv.findViewById(R.id.dia_user_text);

        if(actv.UserID.length() == 0)
        {
            logx.setText("로그인");
            login.setText("State : log out");
            logx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //actv.intent_list(3);
                    dialogv.dismiss();
                }
            });
        }
        else
        {
            login.setText("Log in : "+actv.UserID);
            login.setTextColor(Color.argb(100,0,0,255));
            logx.setText("로그아웃");
            logx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actv.logout();
                    dialogv.dismiss();
                }
            });
        }
        Button btn_info = (Button) dialogv.findViewById(R.id.bt_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actv.intent_list(1);
                dialogv.dismiss();
            }
        });
        Button btn_list = (Button) dialogv.findViewById(R.id.bt_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actv.intent_list(3);
                dialogv.dismiss();
            }
        });
        Button btn_cre = (Button) dialogv.findViewById(R.id.bt_cre);
        btn_cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actv.intent_list(2);
                dialogv.dismiss();
            }
        });
        Button bt_his = (Button) dialogv.findViewById(R.id.bt_history);
        bt_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actv.intent_list(4);
                dialogv.dismiss();
            }
        });
        dialogv.show();
    }
}
