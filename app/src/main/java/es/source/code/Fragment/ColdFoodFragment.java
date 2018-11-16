package es.source.code.Fragment;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/9.
 */

public class ColdFoodFragment extends BaseFoodFragment {

    public static final String TAG = "冷菜";
    public static final String index = AppGlobal.Lable.COLD_LABLE;
    @Override
    protected void initDataList() {
        int store = 1;
        int category=0;
        dataList.add(new Food("椒油素鸡",10, store, AppGlobal.REMARK,0, false, R.drawable.food_cold_jysj));
        dataList.add(new Food("开胃泡菜",12, store,AppGlobal.REMARK ,0,false,R.drawable.food_cold_kwpc));
        dataList.add(new Food("凉拌海带丝",10, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_lbhds));
        dataList.add(new Food("凉拌黄瓜",10, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_lbhg));
        dataList.add(new Food("卤牛肉",30, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_lnr));
        dataList.add(new Food("东北家拌凉菜",20, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_dbjlbc));
        dataList.add(new Food("浇汁豆腐",15, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_jzdf));
        dataList.add(new Food("青椒拌干丝",10, store,AppGlobal.REMARK,0, false,R.drawable.food_cold_qjbgs));
        SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
        sup.setAllFood(category,dataList);
    }

    @Override
    public String getFoodTag() {
        return TAG;
    }

}
