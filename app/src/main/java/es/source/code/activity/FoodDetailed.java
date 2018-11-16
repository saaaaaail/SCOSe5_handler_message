package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.adapter.FoodDetailedAdapter;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/5.
 */

public class FoodDetailed extends BaseActivity {

    private ViewPager detailedViewPager;
    private Button detailedButton;
    private Toolbar detailedToolbar;
    private TextView detailedTitle;
    private List<Food> foods;
    private List<View> views;
    private int category;
    private int detailedPosition;
    private FoodDetailedAdapter foodDetailedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detailed);
        initView();
        initData();
    }

    protected  void initView(){
        detailedButton = (Button)findViewById(R.id.btn_detailed);
        detailedViewPager = (ViewPager)findViewById(R.id.detail_viewpager);
        detailedToolbar = (Toolbar)findViewById(R.id.detailed_toolbar);
        detailedTitle = (TextView)findViewById(R.id.detail_title);
    }

    private void initData(){
        Intent intent =getIntent();
        detailedPosition = intent.getIntExtra(AppGlobal.IntentKey.CURRENT_DETAILED_POSITION,0);
        Log.v("fooddetailed:","dd"+detailedPosition);
        category = intent.getIntExtra(AppGlobal.IntentKey.CURRENT_CATEGORY,0);
        SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
        foods = sup.getAllFood(category);
        detailedTitle.setText(R.string.label_food_detail);
        foodDetailedAdapter = new FoodDetailedAdapter(R.layout.item_food_detailed,foods,mContext);
        detailedViewPager.setAdapter(foodDetailedAdapter);
        detailedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                detailedPosition=position;
                setDetailedButton(foods.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        detailedViewPager.setCurrentItem(detailedPosition,true);

        detailedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = foods.get(detailedPosition);
                food.setOrder(!food.isOrder());
                setDetailedButton(food);
                SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
                spu.operateFood(food,food.isOrder());
            }
        });
        setSupportActionBar(detailedToolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        detailedToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDetailedButton(foods.get(detailedPosition));
    }

    private void setDetailedButton(Food food){
        if(food.isOrder()){
            detailedButton.setText(R.string.btn_cancel_order);
        }else if(food.getStore()>0){
            detailedButton.setText(R.string.btn_order);
        }else{
            detailedButton.setEnabled(false);
            detailedButton.setText(R.string.btn_empty);
        }
    }
}
