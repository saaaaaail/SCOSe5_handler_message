package es.source.code.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import es.source.code.AppGlobal;
import es.source.code.R;
import es.source.code.adapter.HelpAdapter;
import es.source.code.callback.CallBack;
import es.source.code.callback.OnDataFinishedListener;
import es.source.code.model.Function;
import es.source.code.util.MailUtil;

public class SCOSHelper extends BaseActivity {

    private Button diaButton;
    private EditText editTitle;
    private EditText editContent;
    private GridView gridViewHelper;
    private List<Function> functionList;
    private HelpAdapter helpAdapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);
        initView();
    }

    private void initView(){

        functionList = new ArrayList<>();
        gridViewHelper = (GridView)findViewById(R.id.gv_help);

        functionList.add(new Function(R.drawable.help_agree,"使用协议", AppGlobal.FunctionTag.HELP_AGREE));
        functionList.add(new Function(R.drawable.help_about,"关于", AppGlobal.FunctionTag.HELP_ABOUT));
        functionList.add(new Function(R.drawable.help_tel,"电话人工服务", AppGlobal.FunctionTag.HELP_TEL));
        functionList.add(new Function(R.drawable.help_mess,"短信帮助", AppGlobal.FunctionTag.HELP_MESS));
        functionList.add(new Function(R.drawable.help_mail,"邮件帮助", AppGlobal.FunctionTag.HELP_MAIL));

        helpAdapter = new HelpAdapter(mContext,functionList);
        helpAdapter.setCallBack(new CallBack<Function>() {
            @Override
            public void onClick(Function function) {
                doFunction(function);
            }
        });

        gridViewHelper.setAdapter(helpAdapter);

        builder = new AlertDialog.Builder(mContext);
        View mailView = LayoutInflater.from(mContext).inflate(R.layout.dialog_mail,null);
        editTitle = (EditText)mailView.findViewById(R.id.et_title);
        editContent = (EditText)mailView.findViewById(R.id.et_content);
        diaButton = (Button)mailView.findViewById(R.id.btn_dialog);
        diaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                MailUtil mailUtil = MailUtil.getInstance();
                mailUtil.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccess(Object t) {
                        showToast("求助邮件发送成功");
                    }

                    @Override
                    public void onDataFail() {
                        showToast("求助邮件发送失败");
                    }
                });
                mailUtil.sendMail(title,content);
                dialog.dismiss();
            }
        });
        builder.setTitle("发送邮件");
        builder.setView(mailView);

        builder.setCancelable(true); //设置按钮是否可以按返回键取消,false则不可以取消

    }

    public void doFunction(Function function){
        Intent intent;
        switch(function.getTag()){
            case AppGlobal.FunctionTag.HELP_AGREE:


                break;
            case AppGlobal.FunctionTag.HELP_ABOUT:
                intent = new Intent(mContext,FoodOrderView.class);

                startActivity(intent);
                break;
            case AppGlobal.FunctionTag.HELP_TEL:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"5554"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case AppGlobal.FunctionTag.HELP_MESS:
                String mess = "test scos helper";
                SmsManager.getDefault().sendTextMessage("5554",null,mess,null,null);
                showToast("求助短信发送成功");
                break;
            case AppGlobal.FunctionTag.HELP_MAIL:

                dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                break;

        }
    }
}
