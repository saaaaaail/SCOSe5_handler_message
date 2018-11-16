package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.source.code.R;
import es.source.code.AppGlobal;
import es.source.code.service.UpdateService;
import es.source.code.util.SharedPreferenceUtil;

public class SCOSEntry extends BaseActivity{

    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(mContext);
        sharedPreferenceUtil.setLoginStatus(AppGlobal.SPValue.LOGIN_FAILURE);

        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //如果从右向左滑超过50mm
                if (e1.getX() - e2.getX() > 50) {
                    showToast(getResources().getString(R.string.toast_welcome));
                    Intent intent = new Intent(mContext, MainScreen.class);
                    intent.putExtra(AppGlobal.IntentKey.FROM, AppGlobal.IntentValue.FROM_VALUE);
                    startActivity(intent);
                    //设置切换动画，从右至左滑动
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }
                return false;
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }
}
