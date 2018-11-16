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
 * Created by sail on 2018/10/19.
 */

public class HelpAdapter extends BaseAdapter {

    private Context mContext;
    private List<Function> helpList;
    public HelpAdapter(Context mContext, List<Function> helpList){
        this.mContext = mContext;
        this.helpList = helpList;
    }

    @Override
    public int getCount() {
        return helpList.size();
    }

    @Override
    public Object getItem(int position) {
        return helpList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_grid_icon,null);
            viewHolder = new ViewHolder();
            viewHolder.helpButton = (Button)convertView.findViewById(R.id.btn_gv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Function function = helpList.get(position);
        viewHolder.helpButton.setText(function.getName());
        Drawable drawable = mContext.getResources().getDrawable(function.getImg());// 获取res下的图片drawable
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 一定要设置setBounds();
        viewHolder.helpButton.setCompoundDrawables(drawable,null,null,null);
        viewHolder.helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(callBack!=null){callBack.onClick(function);}
                    }
        });
        return convertView;
    }


    class ViewHolder{
        Button helpButton;
    }

    private CallBack<Function> callBack;
    public void setCallBack(CallBack<Function> callBack){
        this.callBack = callBack;
    }
}
