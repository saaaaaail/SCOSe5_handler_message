package es.source.code.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.adapter.OrderRecycleViewAdapter;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class OrderedFragment extends BaseOrderFragment {


    private boolean showButton = false;
    private static final String TAG = "已下订单";
    public static final int index = AppGlobal.Lable.ORDERED_LABLE;

    public String getFoodTag(){
        return TAG;
    }
    public int getIndex(){return index;}

    @Override
    protected void initDataList() {

        dataList = spu.getPayedFood();
    }



    @Override
    protected boolean initButton() {
        return showButton;
    }
}
