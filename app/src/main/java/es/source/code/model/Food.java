package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sail on 2018/10/8.
 */

public class Food implements Parcelable{
    // 菜名
    private String foodName;

    // 价格
    private int price;

    // 库存;
    private int store;

    //备注
    private String remark;

    //类别
    private int category;

    // 是否点单
    private boolean order;

    // 图片资源ID;
    private int imgId;


    public Food(String foodName, int price, int store, String remark, int category, boolean order, int imgId) {
        this.foodName = foodName;
        this.price = price;
        this.store = store;
        this.remark = remark;
        this.category = category;
        this.order = order;
        this.imgId = imgId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.foodName);
        dest.writeInt(this.price);
        dest.writeInt(this.store);
        dest.writeByte(this.order ? (byte) 1 : (byte) 0);
        dest.writeInt(this.imgId);
    }

    protected Food(Parcel in) {
        this.foodName = in.readString();
        this.price = in.readInt();
        this.store = in.readInt();
        this.order = in.readByte() != 0;
        this.imgId = in.readInt();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
