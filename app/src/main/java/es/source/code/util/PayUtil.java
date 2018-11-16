package es.source.code.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import es.source.code.model.Food;

/**
 * Created by sail on 2018/10/21.
 */

public class PayUtil {

    public static class InstanceHolder{
        private static PayUtil instance = new PayUtil();
    }

    public static PayUtil getInstance(){
        return PayUtil.InstanceHolder.instance;
    }

    private PayUtil(){}

    class PayTask extends AsyncTask<Void,Void,Integer>{
        private Context mContext;
        private ProgressBar progressBar;
        private List<Food> foods;

        public PayTask(Context mContext,ProgressBar progressBar,List foods){
            this.mContext=mContext;
            this.progressBar = progressBar;
            this.foods = foods;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(mContext);
            foods = sharedPreferenceUtil.getPayedFood();
            int amount=0;
            for(Food food: foods){
                amount+=food.getPrice();
            }
            foods.clear();
            sharedPreferenceUtil.setPayedFood(foods);
            SystemClock.sleep(3000);
            return amount;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(mContext, "本次结账金额为"+integer+"元", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void paying(Context mContext,ProgressBar progressBar,List<Food> foodList){
        PayTask payTask = new PayTask(mContext,progressBar,foodList);
        payTask.execute();
    }
}
