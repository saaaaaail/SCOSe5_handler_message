package es.source.code.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.adapter.OrderRecycleViewAdapter;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseOrderFragment.OnFragmentInteractionBtnListener} interface
 * to handle interaction events.
 */
public abstract class BaseOrderFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OrderRecycleViewAdapter orderRecycleViewAdapter;
    private RecyclerView recyclerView;
    private TextView allFoodNumber;
    private TextView allAmount;

    protected SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
    private static final String TAG = "Order";
    public static final int  index = AppGlobal.Lable.BASE_LABLE;
    public static int currentindex;
    private int amount;
    private boolean showButton;
    public List<Food> getData() {
        return dataList;
    }

    public void setData(List<Food> dataList) {
        this.dataList = dataList;
    }

    List<Food> dataList= new ArrayList<>();

    public BaseOrderFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(setLayoutResourceID(), container, false);
        showButton = initButton();
        currentindex = getIndex();
        initDataList();
        initView(view);
        countNumber();
        return view;
    }

    protected abstract boolean initButton();

    protected abstract void initDataList();

    protected void initView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.order_list);
        allFoodNumber = (TextView)view.findViewById(R.id.tv_food_amount);
        allAmount = (TextView)view.findViewById(R.id.tv_order_amount);



        /*
        System.out.println("initView"+currentindex);
        switch (currentindex){
            case AppGlobal.Lable.ORDERED_LABLE:
                System.out.println("ORDERED"+currentindex);
                dataList = spu.getPayedFood();
                break;
            case AppGlobal.Lable.UNORDER_LABLE:
                System.out.println("UNORDER"+currentindex);
                dataList = spu.getOrderedFood();
                break;
        }
        */


        orderRecycleViewAdapter = new OrderRecycleViewAdapter(dataList,mContext,showButton);
        orderRecycleViewAdapter.setOnListFragmentInteractionBtnListener(new OnFragmentInteractionBtnListener() {
            @Override
            public void onClick(Food food) {
                dataList.remove(food);
                orderRecycleViewAdapter.updateData(dataList);
            }
        });
        recyclerView.setAdapter(orderRecycleViewAdapter);

    }
    public void countNumber(){
            System.out.println(dataList);
            String foodAmount = getString(R.string.text_food_amount) + dataList.size();
            amount = 0; // 订单总价
            for (Food food : dataList) {
                amount += food.getPrice();
            }
            String orderAmount = getString(R.string.text_order_amount) + String.valueOf(amount);
            allFoodNumber.setText(foodAmount);
            allAmount.setText(orderAmount);
    }


    public void RefreshData(){
        orderRecycleViewAdapter.updateData(dataList);
    }

    /**
     * 判断fragment的隐藏，没有隐藏则请求数据
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        System.out.println("BaseOrder");
        if(!hidden){
            RefreshData();
        }
    }

    public String getFoodTag(){
        return TAG;
    }

    public int getIndex(){return index;}
/*
    public void RefreshData(List<Food> items){
        setData(items);
        if(orderRecycleViewAdapter!=null){
            orderRecycleViewAdapter.updateData(items);
        }
    }
*/
    public int getPrice(){
        return amount;
    }
    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order_list;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionBtnListener {
        // TODO: Update argument type and name
        void onClick(Food food);
    }
}
