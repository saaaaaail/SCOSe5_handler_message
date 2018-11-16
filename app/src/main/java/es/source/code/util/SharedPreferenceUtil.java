package es.source.code.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.model.Food;
import es.source.code.model.FoodContent;
import es.source.code.model.User;

/**
 * Created by sail on 2018/10/8.
 */

public class SharedPreferenceUtil {

    private SharedPreferences sharedPreferences;

    Context mContext;
    private static SharedPreferenceUtil sharedPreferenceUtil;

    private SharedPreferenceUtil(Context mContext){
        this.mContext=mContext;
    }

    public static SharedPreferenceUtil getInstance(Context mContext){
        if(sharedPreferenceUtil == null){
            sharedPreferenceUtil = new SharedPreferenceUtil(mContext);
        }
        return sharedPreferenceUtil;
    }
    public void setUser(User user){
        Gson gson = new Gson();
        sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        System.out.println("app:"+user);
        String userString = gson.toJson(user);
        //仅仅起传值的作用
        System.out.println("app:"+user);
        editor.putString(AppGlobal.SPKey.USER,userString).apply();
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        String userString = sharedPreferences.getString(AppGlobal.SPKey.USER, null);
        return new Gson().fromJson(userString, User.class);
    }


    public void setLoginStatus(int loginStatus) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(AppGlobal.SPKey.LOGIN_STATUS, loginStatus).apply();

    }

    public int getLoginStatus() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        int loginStatus = sharedPreferences.getInt(AppGlobal.SPKey.LOGIN_STATUS, 0);
        return loginStatus;
    }

    //获得选取的食物
    public List<Food> getOrderedFood(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        String foodListString = sharedPreferences.getString(AppGlobal.Store.ORDEREDFOODS,null);
        Type type = new TypeToken<ArrayList<Food>>() {
        }.getType();
        return new Gson().fromJson(foodListString,type);
    }

    //保存已经选择的食物
    public void setOrderedFood(List<Food> dataList){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String foodListString = new Gson().toJson(dataList);
        editor.putString(AppGlobal.Store.ORDEREDFOODS,foodListString).apply();
    }

    // 清空已订食物
    public void clearOrderedFood(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Food>dataList = getOrderedFood();
        setPayedFood(dataList);//保存付款的食物
        dataList.clear();
        if(dataList.isEmpty()){
            String foodListString = new Gson().toJson(dataList);
            editor.putString(AppGlobal.Store.ORDEREDFOODS,foodListString).apply();
        }
    }

    //添加食物或删除食物
    public void operateFood(Food food,boolean isOrder){
        Log.v("dsfs","operate");
        List<Food> foods = getOrderedFood();
        boolean flag = true;
        if(foods == null){
            foods = new ArrayList<>();
        }
        for (Food orderFood : foods) {
            if (orderFood.getFoodName().equals(food.getFoodName())) {
                if (isOrder) {
                    // 避免重复添加
                    flag = false;
                } else {
                    foods.remove(orderFood);
                }
                break;
            }
        }
        if(isOrder && flag){foods.add(food);}
        setOrderedFood(foods);
    }


    //保存已经付款的食物清单
    public void setPayedFood(List<Food> dataList){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String foodListString = new Gson().toJson(dataList);
        editor.putString(AppGlobal.Store.PAYEDFOODS,foodListString).apply();
    }
    //获得已付款的食物
    public List<Food> getPayedFood(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        String foodListString = sharedPreferences.getString(AppGlobal.Store.PAYEDFOODS,null);
        Type type = new TypeToken<ArrayList<Food>>() {
        }.getType();
        return new Gson().fromJson(foodListString,type);
    }

    //保存所有食物
    public void setAllFood(int category,List<Food> dataList){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String foodListString = new Gson().toJson(dataList);
        switch (category){
            case 0:editor.putString(AppGlobal.Store.COLD_FOOD,foodListString).apply();break;
            case 1:editor.putString(AppGlobal.Store.HOT_FOOD,foodListString).apply();break;
            case 2:editor.putString(AppGlobal.Store.SEA_FOOD,foodListString).apply();break;
            case 3:editor.putString(AppGlobal.Store.DRINK_FOOD,foodListString).apply();break;
        }

    }

    public List<Food> getAllFood(int category){
        String foodListString=null;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        switch (category){
            case 0 :foodListString = sharedPreferences.getString(AppGlobal.Store.COLD_FOOD,null);break;
            case 1 :foodListString = sharedPreferences.getString(AppGlobal.Store.HOT_FOOD,null);break;
            case 2 :foodListString = sharedPreferences.getString(AppGlobal.Store.SEA_FOOD,null);break;
            case 3 :foodListString = sharedPreferences.getString(AppGlobal.Store.DRINK_FOOD,null);break;
        }
        Type type = new TypeToken<ArrayList<Food>>() {
        }.getType();
        return new Gson().fromJson(foodListString,type);
    }

    public void setFoodContent( FoodContent foodContent){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String foodContentString = new Gson().toJson(foodContent);
        editor.putString(AppGlobal.Store.FOOD_CONTENT,foodContentString);
        editor.apply();
    }

    public FoodContent getFoodContent(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppGlobal.SPKey.RESOURCE, Context.MODE_MULTI_PROCESS);
        String foodContentString = sharedPreferences.getString(AppGlobal.Store.FOOD_CONTENT,null);
        return new Gson().fromJson(foodContentString,FoodContent.class);
    }
}