package es.source.code.Fragment;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class SeaFoodFragment extends BaseFoodFragment {
    public static final String TAG = "海鲜";
    public static final String index = AppGlobal.Lable.SEA_LABLE;
    @Override
    protected void initDataList() {
        int store = 1;
        int category = 2;
        dataList.add(new Food("大虾两吃",50, store,AppGlobal.REMARK,category, false,R.drawable.food_sea_dxlc));
        dataList.add(new Food("海螺炒韭菜",40, store,AppGlobal.REMARK,category, false,R.drawable.food_sea_hlcjc));
        dataList.add(new Food("海鲜汤",40, store, AppGlobal.REMARK,category,false,R.drawable.food_sea_hxt));
        dataList.add(new Food("节瓜章鱼鸡脚汤",55, store,AppGlobal.REMARK,category, false,R.drawable.food_sea_jgzyjjt));
        dataList.add(new Food("麻辣小龙虾",50, store,AppGlobal.REMARK, category,false,R.drawable.food_sea_mlxlx));
        dataList.add(new Food("啤酒海螺",40, store,AppGlobal.REMARK,category, false,R.drawable.food_sea_pjhl));
        dataList.add(new Food("清炒虾仁",44, store,AppGlobal.REMARK,category, false,R.drawable.food_sea_qcxr));
        dataList.add(new Food("清蒸梭子蟹",60, store,AppGlobal.REMARK, category,false,R.drawable.food_sea_qzszx));
        dataList.add(new Food("水蟹冬瓜汤",60, store,AppGlobal.REMARK, category,false,R.drawable.food_sea_sxdgt));
        SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
        sup.setAllFood(category,dataList);
    }

    @Override
    public String getFoodTag() {
        return TAG;
    }


}
