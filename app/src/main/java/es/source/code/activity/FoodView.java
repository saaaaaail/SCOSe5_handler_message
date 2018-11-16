package es.source.code.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.Fragment.BaseFoodFragment;
import es.source.code.Fragment.BaseFragment;
import es.source.code.Fragment.ColdFoodFragment;
import es.source.code.Fragment.DrinkFoodFragment;
import es.source.code.Fragment.HotFoodFragment;
import es.source.code.Fragment.SeaFoodFragment;
import es.source.code.R;
import es.source.code.adapter.FragmentFoodViewPagerAdapter;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.model.FoodContent;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;

public class FoodView extends BaseActivity {

    Messenger cMessenger;
    Messenger sMessenger;

    private static final int FRAGMENT_COLD_FOOD = 0;
    private static final int FRAGMENT_HOT_FOOD = 1;
    private static final int FRAGMENT_SEA_FOOD = 2;
    private static final int FRAGMENT_DRINKING = 3;

    private TextView textLable;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private List<BaseFoodFragment> fragmentList;
    private User user;
    private String userString;
    private boolean isbound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sMessenger = new Messenger(service);
            isbound = true;
            System.out.println(" onServiceConnected: "+isbound);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sMessenger = null;
            isbound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);

        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initBind();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isbound){
            unbindService(mConnection);
            isbound = false;
        }
    }

    private void initView(){
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        textLable = (TextView)findViewById(R.id.title);

        textLable.setText("点餐");

        Intent intent = getIntent();
        userString = intent.getStringExtra(AppGlobal.IntentKey.USER_INFO);
        Gson gson = new Gson();
        user = gson.fromJson(userString,User.class);
        System.out.println(userString);
        fragmentList = new ArrayList<>();

        ColdFoodFragment coldFoodFragment = new ColdFoodFragment();
        HotFoodFragment hotFoodFragment = new HotFoodFragment();
        SeaFoodFragment seaFoodFragment = new SeaFoodFragment();
        DrinkFoodFragment drinkingFragment = new DrinkFoodFragment();

        fragmentList.add(coldFoodFragment);//0
        fragmentList.add(hotFoodFragment);//1
        fragmentList.add(seaFoodFragment);//2
        fragmentList.add(drinkingFragment);//3

        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initToolbar();
        FragmentFoodViewPagerAdapter fragmentViewPagerAdapter = new FragmentFoodViewPagerAdapter(getSupportFragmentManager(),fragmentList);

        viewPager.setAdapter(fragmentViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initToolbar(){
        toolbar.inflateMenu(R.menu.menu_order);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch(menuItemId){
                    case R.id.action_order:
                        Intent intent = new Intent(mContext,FoodOrderView.class);
                        intent.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                        intent.putExtra(AppGlobal.IntentKey.CURRENT_INDEX, AppGlobal.Lable.UNORDER_LABLE);
                        startActivity(intent);
                        break;
                    case R.id.action_bill:
                        Intent intent1 = new Intent(mContext,FoodOrderView.class);
                        intent1.putExtra(AppGlobal.IntentKey.USER_INFO,userString);
                        intent1.putExtra(AppGlobal.IntentKey.CURRENT_INDEX, AppGlobal.Lable.ORDERED_LABLE);
                        startActivity(intent1);
                        break;
                    case R.id.action_serve:
                        break;
                    case R.id.action_update:
                        AutoRefresh(item);
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    //设置menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu); //解析menu布局文件到menu
        return true;
    }

    private void initBind(){
        cMessenger = new Messenger(sMessageHandler);
        Intent service = new Intent(getApplicationContext(), ServerObserverService.class);
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
    }



    public void AutoRefresh(MenuItem item){
        String title = item.getTitle().toString();
        int what=0;
        if(getString(R.string.menu_auto_refresh_on).equals(title)){
            title = getString(R.string.menu_auto_refresh_off);
            what = AppGlobal.Message.GET_FOOD_MSG;
        }else {
            title = getString(R.string.menu_auto_refresh_on);
            what = AppGlobal.Message.STOP_FOOD_MSG;
        }
        if(isbound) {
            try {
                Message message = Message.obtain();
                message.what = what;
                message.replyTo = cMessenger;
                sMessenger.send(message);
                System.out.println(" AutoRefresh: "+what);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            item.setTitle(title);
        }
    }

    SMessageHandler sMessageHandler = new SMessageHandler();

    class SMessageHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case AppGlobal.Message.GET_FOOD_SUCCESS:
                    Bundle bundle = msg.getData();
                    String fdString = bundle.getString(AppGlobal.BundleKey.FOOD_CONTENT);
                    FoodContent foodContent = new Gson().fromJson(fdString,FoodContent.class);
                    System.out.println("获取返回数据："+foodContent.getColdFoodList().get(0).getFoodName());
                    loadFoodData(foodContent);
                    break;
            }
        }

    }

    public void loadFoodData(FoodContent foodContent){
        fragmentList.get(FRAGMENT_COLD_FOOD).RefreshData(foodContent.getColdFoodList());
        fragmentList.get(FRAGMENT_HOT_FOOD).RefreshData(foodContent.getHotFoodList());
        fragmentList.get(FRAGMENT_SEA_FOOD).RefreshData(foodContent.getSeaFoodList());
        fragmentList.get(FRAGMENT_DRINKING).RefreshData(foodContent.getDrinkingList());
    }
}
