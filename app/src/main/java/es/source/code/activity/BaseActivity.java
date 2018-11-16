package es.source.code.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import es.source.code.R;

/**
 * Created by sail on 2018/10/5.
 */

public class BaseActivity extends AppCompatActivity {
    Context mContext = this;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(int toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }


    /**
     * @description: 显示加载对话框
     * @author: Daniel
     */
    protected void showProgress(int messageRes) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(getResources().getString(R.string.app_name));
        progressDialog.setMessage(getResources().getString(messageRes));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void showProgress() {
        showProgress(R.string.dialog_loading);
    }

    /**
     * @description: 取消显示对话框
     * @author: Daniel
     */
    protected void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
