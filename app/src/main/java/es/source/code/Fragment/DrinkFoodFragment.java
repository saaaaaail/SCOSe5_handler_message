package es.source.code.Fragment;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class DrinkFoodFragment extends BaseFoodFragment {
    public static final String TAG = "酒水";

    public static final String index = AppGlobal.Lable.DRINK_LABLE;
    @Override
    protected void initDataList() {
        int store = 1;
        int category = 3;
        dataList.add(new Food("百香多多",20, store,AppGlobal.REMARK,category, false,R.drawable.food_drink_bxdd));
        dataList.add(new Food("草莓香蕉奶昔",18, store,AppGlobal.REMARK,category, false,R.drawable.food_drink_cmxjnx));
        dataList.add(new Food("红茶",10, store,AppGlobal.REMARK,category, false,R.drawable.food_drink_hc));
        dataList.add(new Food("红枣核桃山药饮",15, store, AppGlobal.REMARK,category,false,R.drawable.food_drink_hzhtsyy));
        dataList.add(new Food("玫瑰情人露",22, store,AppGlobal.REMARK,category, false,R.drawable.food_drink_mgqrl));
        SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
        sup.setAllFood(category,dataList);
    }

    @Override
    public String getFoodTag() {
        return TAG;
    }

}
