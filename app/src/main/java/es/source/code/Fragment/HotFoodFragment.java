package es.source.code.Fragment;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class HotFoodFragment extends BaseFoodFragment {
    public static final String TAG = "热菜";
    public static final String index = AppGlobal.Lable.HOT_LABLE;
    @Override
    protected void initDataList() {
        int store = 1;
        int category = 1;
        dataList.add(new Food("干煸豆角",20, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_gbdj));
        dataList.add(new Food("宫保鸡丁",30, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_gbjd));
        dataList.add(new Food("红烧茄子",20, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_hsqz));
        dataList.add(new Food("红烧肉",45, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_hsr));
        dataList.add(new Food("红烧鱼",40, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_hsy));
        dataList.add(new Food("可乐鸡翅",30, store, AppGlobal.REMARK,1,false,R.drawable.food_hot_kljc));
        dataList.add(new Food("麻婆豆腐",25, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_mpdf));
        dataList.add(new Food("羊肉汤",40, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_yrt));
        dataList.add(new Food("孜然羊肉",50, store,AppGlobal.REMARK,1, false,R.drawable.food_hot_zryr));
        SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
        sup.setAllFood(category,dataList);
    }

    @Override
    public String getFoodTag() {
        return TAG;
    }

}
