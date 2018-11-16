package es.source.code.Fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.adapter.OrderRecycleViewAdapter;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class UnOrderFragment extends BaseOrderFragment {


    private boolean showButton = true;
    private static final String TAG = "未下订单";
    public static final int index = AppGlobal.Lable.UNORDER_LABLE;
    public String getFoodTag(){
        return TAG;
    }
    public int getIndex(){return index;}

    @Override
    protected boolean initButton() {
        return showButton;
    }
    @Override
    protected void initDataList() {

        dataList = spu.getOrderedFood();

    }


}
