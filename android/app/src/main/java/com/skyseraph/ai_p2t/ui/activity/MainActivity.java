package com.skyseraph.ai_p2t.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.skyseraph.ai_p2t.R;
import com.skyseraph.ai_p2t.base.BaseActivity;
import com.skyseraph.ai_p2t.ui.adapter.P2TStackAdapter;
import com.skyseraph.ai_p2t.ui.beam.ColorName;
import com.skyseraph.ai_p2t.utils.TaskExecutorN;
import com.skyseraph.ai_p2t.utils.TestData;
import com.skyseraph.ai_p2t.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements CardStackView.ItemExpendListener, P2TStackAdapter.ItemJumpBtnClick {

    private CardStackView tackView;
    private LinearLayout actionButtonContainer;
    private P2TStackAdapter stackAdapter;
    private List<ColorName> colorNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tackView = (CardStackView) findViewById(R.id.stackview_main);
        actionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        tackView.setItemExpendListener(this);
        stackAdapter = new P2TStackAdapter(this);
        stackAdapter.setItemJumpBtnClick(this);
        tackView.setAdapter(stackAdapter);

        for (int i = 0; i < TestData.NAME_DATAS.length; i++) {
            colorNames.add(i, new ColorName(TestData.NAME_DATAS[i], TestData.COLOR_DATAS[i], TestData.DES_DATAS[i]));
        }

        TaskExecutorN.scheduleTaskOnMainThread(200, new Runnable() {
            @Override
            public void run() {
                stackAdapter.updateData(colorNames);
            }
        });
    }

    @Override
    public void onButtonClick(ColorName data) {
        if (data != null) {
            if (data.getName().endsWith(TestData.NAME_DATAS[0])) {
                startActivity(MnistActivity.class);
            } else {
                UIUtils.showToast("click" + data.getName());
            }
        }
    }

    @Override
    public void onItemExpend(boolean expend) {
        actionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

    public void onPreClick(View view) {
        tackView.pre();
    }

    public void onNextClick(View view) {
        tackView.next();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
