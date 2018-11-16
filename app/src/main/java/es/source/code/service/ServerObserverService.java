package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.model.Food;
import es.source.code.model.FoodContent;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/25.
 */

public class ServerObserverService extends Service {

    private Context mContext;
    private Handler cMessageHandler = new CMessageHandler();
    private final Messenger sMessenger = new Messenger(cMessageHandler);
    private Messenger cMessenger;
    private boolean isrun = false;

    private Runnable receiveRun;
    private Runnable sendRun;
    /**
     * 绑定服务时才会执行
     * 必须实现的方法
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sMessenger.getBinder();
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置（调用onstartcommand或onbind之前）
     * 如果服务已经运行了，该方法不会被调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * 每次通过startService方法启动时都会被调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("service: onUnbind");
        return super.onUnbind(intent);

    }

    /**
     *
     * 服务销毁时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("service: ondestory:");
        if(sendRun!=null){cMessageHandler.removeCallbacks(sendRun);}
        if(receiveRun!=null){cMessageHandler.removeCallbacks(receiveRun);}
    }

    class CMessageHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case AppGlobal.Message.GET_FOOD_MSG:
                    cMessenger = msg.replyTo;
                    isrun=true;
                    receiveFood();
                    System.out.println(" handleMessage: isUIProcess：  "+isUIProcess(mContext));
                    System.out.println(" handleMessage: cMessenger:  "+cMessenger);
                    if(isUIProcess(mContext) && cMessenger!=null){
                        sendFood();
                    }
                    break;
                case AppGlobal.Message.STOP_FOOD_MSG:
                    isrun=false;
                    if(sendRun!=null){cMessageHandler.removeCallbacks(sendRun);}
                    if(receiveRun!=null){cMessageHandler.removeCallbacks(receiveRun);}
                    break;
            }
        }
    }

    public boolean isUIProcess(Context mContext){
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        System.out.println("isUIProcess: "+mainProcessName);
        //int Pid = android.os.Process.myPid();
        for(ActivityManager.RunningAppProcessInfo processInfo:processInfos){
            if( mainProcessName.equals(processInfo.processName)){
                return true;
            }
        }
        return false;
    }

    public List<Food> setFoodStore(List<Food> foods){
        for(Food food:foods){
            food.setStore((int)(Math.random()*10));
        }
        return foods;
    }

    public void receiveFood(){
        receiveRun = new Runnable() {
            @Override
            public void run() {

                SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
                List<Food> coldFoods = setFoodStore(spu.getAllFood(0));
                List<Food> hotFoods = setFoodStore(spu.getAllFood(1));
                List<Food> seaFoods = setFoodStore(spu.getAllFood(2));
                List<Food> drinkFoods = setFoodStore(spu.getAllFood(3));
                FoodContent foodContent = new FoodContent(coldFoods,hotFoods,seaFoods,drinkFoods);

                spu.setAllFood(0,coldFoods);
                spu.setAllFood(1,hotFoods);
                spu.setAllFood(2,seaFoods);
                spu.setAllFood(3,drinkFoods);
                spu.setFoodContent(foodContent);

                cMessageHandler.postDelayed(this,3000);
            }
        };
        cMessageHandler.post(receiveRun);
    }

    public void sendFood(){
        sendRun = new Runnable() {
            @Override
            public void run() {
               // while(isrun){
                    try {
                        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
                        FoodContent fd = spu.getFoodContent();
                        Bundle bundle = new Bundle();
                        bundle.putString(AppGlobal.BundleKey.FOOD_CONTENT,new Gson().toJson(fd));
                        Message message = Message.obtain(null,AppGlobal.Message.GET_FOOD_SUCCESS);
                        message.setData(bundle);
                        cMessenger.send(message);
                        System.out.println("sendFood threadstart");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                cMessageHandler.postDelayed(this,300);
              //  }
            }
        };
        cMessageHandler.post(sendRun);
    }

}
