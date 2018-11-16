package es.source.code.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import es.source.code.R;
import es.source.code.model.Food;

/**
 * Created by sail on 2018/10/11.
 */

public class FoodDetailedAdapter extends PagerAdapter {

    private ImageView detailedImg;
    private TextView detailedNameText;
    private TextView detailedPriceText;
    private List<Food> foods;
    private List<View> views;
    private Context mContext;
    private int ID;

    public FoodDetailedAdapter(int resourceID ,List<Food> foods, Context mContext){
    //    this.views = views;
        ID=resourceID;
        this.foods = foods;
        this.mContext = mContext;
        initData();
    }

    private void initData(){
        views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i=0;i<foods.size();i++) {
            views.add(inflater.inflate(ID, null));
        }
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = views.get(position);
        Food food = foods.get(position);
        detailedImg = (ImageView)view.findViewById(R.id.detail_food_img);
        detailedNameText = (TextView)view.findViewById(R.id.detail_food_name);
        detailedPriceText = (TextView)view.findViewById(R.id.detail_food_price);

        detailedNameText.setText(food.getFoodName());
        detailedPriceText.setText(String.valueOf(food.getPrice())+"元");
        Glide.with(mContext).load(food.getImgId()).into(detailedImg);
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
/*
    class ViewHolder{
        ImageView detailedImg;
        TextView detailedNameText;
        TextView detailedPriceText;
    }
*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
