package dev.woody.ext.incode2015;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Geoji on 2015-04-30.
 */
public class Loading extends Activity implements Runnable{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.lay_loading);

        (new Thread(this)).start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(1500);
        } catch (Exception e) {}
        // 화면 이동
        Intent intent = new Intent(this, Mains.class);
        startActivity(intent);
        //인트로 화면으로 돌아오지 않도록 인트로 화면을 종료
        finish();
    }

    @Override
    public void finish(){
        super.finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onDestroy() {
        //Adapter가 있으면 어댑터에서 생성한 recycle메소드를 실행
        GarbageCollection.recursiveRecycle(getWindow().getDecorView());
        System.gc();
        super.onDestroy();
    }
}
