package es.source.code.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.Fragment.BaseFragment;
import es.source.code.Fragment.BaseOrderFragment;
import es.source.code.Fragment.OrderedFragment;
import es.source.code.Fragment.UnOrderFragment;
import es.source.code.R;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.adapter.OrderRecycleViewAdapter;
import es.source.code.model.Food;
import es.source.code.model.User;
import es.source.code.util.PayUtil;
import es.source.code.util.SharedPreferenceUtil;

public class FoodOrderView extends BaseActivity {

    private TextView orderTextLable;
    private TabLayout orderTabLayout;
    private ViewPager orderViewPager;
    private Toolbar orderToolbar;
    private TextView submitButton;
    private SharedPreferenceUtil sup;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private User user;
    private String userString;
    private List<Food> foods;
    private ProgressBar progressBar;

    private static int currentPageIndex ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_view);

        initView();
    }

    private void initView(){
        Gson gson = new Gson();
        Intent intent = getIntent();
        currentPageIndex = intent.getIntExtra(AppGlobal.IntentKey.CURRENT_INDEX,0);
        userString = intent.getStringExtra(AppGlobal.IntentKey.USER_INFO);
        System.out.println(userString);
        user = gson.fromJson(userString,User.class);

        orderTabLayout = (TabLayout)findViewById(R.id.order_tablayout);
        orderViewPager = (ViewPager)findViewById(R.id.order_viewpager);
        orderToolbar = (Toolbar)findViewById(R.id.order_toolbar);
        orderTextLable = (TextView)findViewById(R.id.order_text);
        submitButton = (TextView)findViewById(R.id.btn_submit);
        progressBar = (ProgressBar)findViewById(R.id.pb_pay);
        progressBar.setVisibility(View.GONE);
        orderTextLable.setText("订单");

        OrderedFragment orderedFragment = new OrderedFragment();
        UnOrderFragment unOrderFragment = new UnOrderFragment();

        fragmentList.add(orderedFragment);
        fragmentList.add(unOrderFragment);
        setSupportActionBar(orderToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initToolbar();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPageIndex == AppGlobal.Lable.ORDERED_LABLE){
                    sup = SharedPreferenceUtil.getInstance(mContext);
                    user = sup.getUser();
                    if(user!=null&&user.getOldUser()){
                        showToast(R.string.text_olduser);
                    }
                    foods = sup.getPayedFood();
                    PayUtil payUtil = PayUtil.getInstance();
                    payUtil.paying(mContext,progressBar,foods);
                }else{
                    sup = SharedPreferenceUtil.getInstance(mContext);
                    showToast(R.string.text_orderbill);
                    sup.clearOrderedFood();
                }
            }
        });



        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),fragmentList);

        orderViewPager.setAdapter(fragmentViewPagerAdapter);
        orderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;

                setButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        orderTabLayout.setupWithViewPager(orderViewPager);
        orderViewPager.setCurrentItem(currentPageIndex,true);

        setButton(currentPageIndex);


    }

    private void setButton(int currentPageIndex){
        if(currentPageIndex == AppGlobal.Lable.ORDERED_LABLE){
            submitButton.setText(R.string.btn_bill);
        }else if(currentPageIndex == AppGlobal.Lable.UNORDER_LABLE){
            submitButton.setText(R.string.btn_submit);
        }

    }

    private void initToolbar(){
        orderToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
