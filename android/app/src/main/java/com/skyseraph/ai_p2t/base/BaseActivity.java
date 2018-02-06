package com.skyseraph.ai_p2t.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.skyseraph.ai_p2t.utils.Constants;

/**
 * Created by SkySeraph on 2017/12/23.
 */

public class BaseActivity extends AppCompatActivity {
    protected LayoutInflater inflater;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = getLayoutInflater();
        activity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Context getContext() {
        return super.getApplicationContext();
    }

    public Activity getActivity() {
        return this.activity;
    }

    protected final void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    protected final void startActivity(int flags, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent(this, targetActivity);
        intent.setFlags(flags);// Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(new Intent(this, targetActivity));
    }

    protected final void startActivity(@NonNull Bundle data, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_NAME.DATA, data);
        intent.setClass(this, targetActivity);
        startActivity(intent);
    }

    protected final void startActivity(@NonNull String extraName, @NonNull String extraValue, @NonNull Class<?> targetActivity) {
        if (TextUtils.isEmpty(extraName)) {
            throw new NullPointerException("传递的值的键名称为null或空");
        }
        final Intent intent = new Intent(getApplicationContext(), targetActivity);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }
}
