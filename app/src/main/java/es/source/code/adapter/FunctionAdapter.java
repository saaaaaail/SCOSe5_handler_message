package es.source.code.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import es.source.code.R;
import es.source.code.callback.CallBack;
import es.source.code.model.Function;

/**
 * Created by sail on 2018/10/7.
 */

public class FunctionAdapter extends BaseAdapter {

    private List<Function> functionList;
    private Context mContext;
    private LayoutInflater inflater;

    public FunctionAdapter(Context mContext, List<Function>functionList){
        this.functionList=functionList;
        this.mContext=mContext;
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return functionList.size();
    }

    @Override
    public Object getItem(int position) {
        return functionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // 若无可重用的 view 则进行加载
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_grid_icon, null);
            // 初始化 ViewHolder ,存起来方便重用
            viewHolder = new ViewHolder();
            viewHolder.Function_btn = (Button)convertView.findViewById(R.id.btn_gv);
            convertView.setTag(viewHolder);
        }else{// 否则进行重用
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Function function = (Function) getItem(position);
        viewHolder.Function_btn.setText(function.getName());
        Drawable drawable = mContext.getResources().getDrawable(function.getImg());// 获取res下的图片drawable
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 一定要设置setBounds();
        viewHolder.Function_btn.setCompoundDrawables(drawable,null,null,null);
        viewHolder.Function_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null){
                    callBack.onClick(function);
                }
            }
        });

        return convertView;
    }

    public void setData(List<Function>functionList){
        this.functionList = functionList;
    }

    class ViewHolder{
        Button Function_btn;
    }

    private CallBack<Function> callBack;
    public void setCallBack(CallBack<Function> callBack){
        this.callBack=callBack;
    }
}
