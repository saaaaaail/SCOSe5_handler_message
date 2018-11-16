package es.source.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.source.code.Fragment.BaseFoodFragment.OnListFragmentInteractionListener;
import es.source.code.Fragment.BaseFoodFragment.OnListFragmentInteractionBtnListener;
import es.source.code.model.Food;

import es.source.code.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link es.source.code.model.Food} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.ViewHolder> {

    private List<Food> mValues;
    protected Context mContext;

    public FoodRecyclerViewAdapter(List<Food> items,Context mContext) {
        mValues = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.foodNameView.setText(mValues.get(position).getFoodName());
        Glide.with(mContext).load(mValues.get(position).getImgId()).into(holder.imageView);
        holder.foodStoreView.setText("剩余："+mValues.get(position).getStore()+"份");
        holder.foodPriceView.setText(mValues.get(position).getPrice()+"元");
        holder.foodOrderBtn.setText(holder.mItem.isOrder()? R.string.btn_cancel_order :holder.mItem.getStore()>0?R.string.btn_order : R
                .string.btn_empty);
        holder.foodOrderBtn.setEnabled(holder.mItem.getStore() > 0 || holder.mItem.isOrder());
        holder.FoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onclick(holder.mItem);
                }
            }
        });

        holder.foodOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mBtnListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mBtnListener.onClick(holder.mItem);
                }
            }
        });


    }

    public void setData(List<Food> items){
        mValues=items;
    }

    public void updateData(List<Food> items) {
        setData(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        public final TextView foodNameView;
        public final TextView foodStoreView;
        public final TextView foodPriceView;
        public final TextView foodOrderBtn;

        public final LinearLayout FoodItem;
        public Food mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.iv_food);
            foodNameView = (TextView) view.findViewById(R.id.tv_food_name);
            foodStoreView = (TextView) view.findViewById(R.id.tv_food_store);
            foodPriceView = (TextView) view.findViewById(R.id.tv_food_price);
            foodOrderBtn = (TextView) view.findViewById(R.id.btn_order) ;
            FoodItem = (LinearLayout)view.findViewById(R.id.food_item);

        }

    }

    OnListFragmentInteractionListener mListener;
    public void setOnListFragmentInteractionListener(OnListFragmentInteractionListener mListener){
        this.mListener=mListener;
    }

    OnListFragmentInteractionBtnListener mBtnListener;
    public void setOnListFragmentInteractionBtnListener(OnListFragmentInteractionBtnListener mBtnListener){
        this.mBtnListener=mBtnListener;
    }
}
