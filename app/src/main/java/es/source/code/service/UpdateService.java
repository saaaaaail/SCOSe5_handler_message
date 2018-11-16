package es.source.code.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.logging.Handler;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.model.Food;
import es.source.code.model.FoodContent;
import es.source.code.util.SharedPreferenceUtil;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class UpdateService extends IntentService {
    private Context mContext;

    @TargetApi(16)
    private Notification.Builder builder;

    public UpdateService() {
        super("UpdateService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(": ", "onHandleIntent");
        mContext = this;
        final android.os.Handler handler = new android.os.Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                receiveFoods();
                handler.postDelayed(this,5000);
            }
        });

    }


    private Food addFoodCategory(){
        Food food = new Food("新的菜品",20, 1, AppGlobal.REMARK,0, false, R.drawable.food_cold_jysj);
        return food;
    }

    private void receiveFoods(){
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
        List<Food> coldFoods = spu.getAllFood(0);
        int position = (int)(Math.random() * (coldFoods.size()-1));

        Food newFood = coldFoods.get(position);
        int cate =newFood.getCategory();
        sendNotification(newFood,position,cate);

    }

    private void sendNotification(Food food,int position,int cate ){
        Notification.Builder builder = new Notification.Builder(this);
        String content = "新品上架： "+food.getFoodName()+ ", "+food.getPrice()+"元 ";

        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);

        Intent intent = new Intent(this, FoodDetailed.class);
        intent.putExtra(AppGlobal.IntentKey.CURRENT_CATEGORY,cate);
        intent.putExtra(AppGlobal.IntentKey.CURRENT_DETAILED_POSITION,position);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
