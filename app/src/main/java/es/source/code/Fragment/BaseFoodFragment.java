package es.source.code.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.adapter.FoodRecyclerViewAdapter;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public abstract class BaseFoodFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private static final String TAG = "Food";
    public static final int index = AppGlobal.Lable.BASE_LABLE;
    private FoodRecyclerViewAdapter foodRecyclerViewAdapter;

    public List<Food> getData() {
        return dataList;
    }

    public void setData(List<Food> dataList) {
        this.dataList = dataList;
    }

    List<Food> dataList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BaseFoodFragment() {
    }
/*
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BaseFoodFragment newInstance(int columnCount) {
        BaseFoodFragment fragment = new BaseFoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResourceID(), container, false);
        initData(view);

        return view;
    }



    protected void initData(View view){
        initDataList();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(dataList,mContext);
            foodRecyclerViewAdapter.setOnListFragmentInteractionBtnListener(new OnListFragmentInteractionBtnListener() {
                @Override
                public void onClick(Food item) {//添加食物
                    item.setOrder(!item.isOrder());

                    foodRecyclerViewAdapter.updateData(dataList);
                    SharedPreferenceUtil sup = SharedPreferenceUtil.getInstance(mContext);
                    sup.operateFood(item,item.isOrder());
                }
            });
            foodRecyclerViewAdapter.setOnListFragmentInteractionListener(new OnListFragmentInteractionListener() {
                @Override
                public void onclick(Food item) {//跳转详细页面
                    int cate = item.getCategory();
                    SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
                    List<Food> tmpfoods = spu.getAllFood(cate);

                    //判断位置
                    int i=0;
                    for(Food food:tmpfoods){
                        if(food.getFoodName().equals(item.getFoodName())){break;}
                        i++;
                    }
                    int index = i;

                    Intent intent = new Intent(mContext, FoodDetailed.class);
                    intent.putExtra(AppGlobal.IntentKey.CURRENT_CATEGORY,cate);
                    intent.putExtra(AppGlobal.IntentKey.CURRENT_DETAILED_POSITION,index);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(foodRecyclerViewAdapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    protected abstract void initDataList();

    public void RefreshData(List<Food> items){
        setData(items);
        if(foodRecyclerViewAdapter!=null){
            foodRecyclerViewAdapter.updateData(items);
        }
    }

    public String getFoodTag(){
        return TAG;
    }
    public int getIndex(){return index;}

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_food_list;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onclick(Food item);
    }

    public interface OnListFragmentInteractionBtnListener {
        // TODO: Update argument type and name
        public void onClick(Food item);
    }
}
