package es.source.code.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.source.code.R;
import es.source.code.adapter.FunctionAdapter;
import es.source.code.callback.CallBack;
import es.source.code.model.Food;
import es.source.code.model.Function;
import es.source.code.AppGlobal;
import es.source.code.model.User;
import es.source.code.util.SharedPreferenceUtil;

public class MainScreen extends BaseActivity {

    private GridView gridView;
    private List<Function> functionList;
    private FunctionAdapter functionAdapter;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private User user;
    private String userString;
    private List<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initData();
    }

    private void initView(){

        /*
        Intent intent = getIntent();

        if(!AppGlobal.IntentValue.FROM_VALUE.equals(intent.getStringExtra(AppGlobal.IntentKey.FROM)) || !AppGlobal
                .IntentValue.LOGIN_SUCCESS.equals(intent.getStringExtra(AppGlobal.IntentKey.LOGIN_STATUS))){
            Order_btn.setVisibility(View.GONE);
            List_btn.setVisibility(View.GONE);
        }else{
            Order_btn.setVisibility(View.VISIBLE);
            List_btn.setVisibility(View.VISIBLE);
        }
        */
        /*
        Order_btn = (Button)findViewById(R.id.btn_order);
        List_btn = (Button)findViewById(R.id.btn_list);
        Account_btn = (Button)findViewById(R.id.btn_account);
        Help_btn = (Button)findViewById(R.id.btn_help);
        */


        /*
        Order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginOrRegister.class);
                startActivityForResult(intent,AppGlobal.ResultCode.MAIN_SCREEN);

            }
        });
        List_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        */
    }

    private void initData(){
        foods = new ArrayList<>();
        Gson gson = new Gson();
        functionList = new ArrayList();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(mContext);
        user = sharedPreferenceUtil.getUser();
        sharedPreferenceUtil.setPayedFood(foods);
        sharedPreferenceUtil.setOrderedFood(foods);
        sharedPreferenceUtil.setAllFood(0,foods);
        sharedPreferenceUtil.setAllFood(1,foods);
        sharedPreferenceUtil.setAllFood(2,foods);
        sharedPreferenceUtil.setAllFood(3,foods);
        String userString = gson.toJson(user);
        System.out.println(userString);
        int loginStatus = sharedPreferenceUtil.getLoginStatus();

        if(loginStatus == AppGlobal.SPValue.LOGIN_SUCCESS){
            functionList.add(new Function(R.drawable.order, getResources().getString(R.string.label_order), AppGlobal.FunctionTag.ORDER));
            functionList.add(new Function(R.drawable.list, getResources().getString(R.string.label_list), AppGlobal.FunctionTag.ORDER_LIST));
        }
        functionList.add(new Function(R.drawable.account,getResources().getString(R.string.label_account), AppGlobal.FunctionTag.ACCOUNT));
        functionList.add(new Function(R.drawable.help,getResources().getString(R.string.label_help), AppGlobal.FunctionTag.HELP));

        gridView = (GridView)findViewById(R.id.gl);

        //设置适配器
        functionAdapter = new FunctionAdapter(mContext,functionList);
        functionAdapter.setCallBack(new CallBack<Function>() {
            @Override
            public void onClick(Function function) {
                doFunction(function);
            }
        });
        gridView.setAdapter(functionAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case AppGlobal.ResultCode.LOGIN_OR_REGISTER:{
                if(AppGlobal.IntentValue.LOGIN_SUCCESS.equals(intent.getStringExtra(AppGlobal.IntentKey.LOGIN_STATUS))){
                    showToast(R.string.toast_login_success);
                }else if(AppGlobal.IntentValue.REGISTER_SUCCESS.equals(intent.getStringExtra(AppGlobal.IntentKey.LOGIN_STATUS))){
                    showToast(R.string.toast_new_account);
                }
            }
            initData();
            functionAdapter.setData(functionList);
            functionAdapter.notifyDataSetChanged();
        }

    }


    public void doFunction(Function function){
        Intent intent;
        switch(function.getTag()){
            case AppGlobal.FunctionTag.ORDER:
                intent = new Intent(mContext,FoodView.class);
                intent.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                startActivity(intent);
                break;
            case AppGlobal.FunctionTag.ORDER_LIST:
                intent = new Intent(mContext,FoodOrderView.class);
                intent.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                intent.putExtra(AppGlobal.IntentKey.CURRENT_INDEX, AppGlobal.Lable.ORDERED_LABLE);
                startActivity(intent);
                break;
            case AppGlobal.FunctionTag.ACCOUNT:
                intent = new Intent(mContext,LoginOrRegister.class);
                intent.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                startActivityForResult(intent, AppGlobal.ResultCode.MAIN_SCREEN);
                break;
            case AppGlobal.FunctionTag.HELP:
                intent = new Intent(mContext,SCOSHelper.class);
                intent.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                startActivity(intent);
                break;

        }
    }

}
