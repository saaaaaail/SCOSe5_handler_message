package es.source.code.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.source.code.Fragment.BaseOrderFragment;
import es.source.code.R;
import es.source.code.model.Food;
import es.source.code.util.SharedPreferenceUtil;

/**
 * Created by sail on 2018/10/10.
 */

public class OrderRecycleViewAdapter extends RecyclerView.Adapter<OrderRecycleViewAdapter.ViewHolder> {
    private List<Food> dataList;
    private Context mContext;

    private boolean showButton;

    public OrderRecycleViewAdapter(List<Food> dataList,Context mContext,boolean showButton){
        this.dataList = dataList;
        this.mContext = mContext;
        this.showButton = showButton;
    }

    @Override
    public OrderRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new OrderRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderRecycleViewAdapter.ViewHolder holder, int position) {
        holder.mItem = dataList.get(position);

        holder.foodNameView.setText(dataList.get(position).getFoodName());
        holder.foodPriceView.setText(dataList.get(position).getPrice()+"元");
        Glide.with(mContext).load(dataList.get(position).getImgId()).into(holder.imageView);

        holder.foodOrderBtn.setVisibility(showButton?View.VISIBLE:View.GONE);
        holder.foodOrderBtn.setText(R.string.btn_cancel_order);
        holder.foodOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBtnListener!=null){
                    mBtnListener.onClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataList!=null) {
            return dataList.size();
        }else{
            return 0;
        }
    }

    public void setData(List<Food> items){
        dataList=items;
    }

    public void updateData(List<Food> items) {
        SharedPreferenceUtil spu = SharedPreferenceUtil.getInstance(mContext);
        setData(items);
        spu.setOrderedFood(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final ImageView imageView;
        public final TextView foodNameView;
        public final TextView foodStoreView;
        public final TextView foodPriceView;
        public final TextView foodOrderBtn;
        public final LinearLayout FoodItem;
        public Food mItem;
        public ViewHolder(View view){
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.iv_food_img);
            foodNameView = (TextView) view.findViewById(R.id.tv_name);
            foodStoreView = (TextView) view.findViewById(R.id.tv_number);
            foodPriceView = (TextView) view.findViewById(R.id.tv_price);
            foodOrderBtn = (TextView) view.findViewById(R.id.btn_food_order) ;
            FoodItem = (LinearLayout)view.findViewById(R.id.food_order_item);
        }
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

    BaseOrderFragment.OnFragmentInteractionBtnListener mBtnListener;
    public void setOnListFragmentInteractionBtnListener(BaseOrderFragment.OnFragmentInteractionBtnListener mBtnListener){
        this.mBtnListener=mBtnListener;
    }

}
