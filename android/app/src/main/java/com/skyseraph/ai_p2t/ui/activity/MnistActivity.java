package com.skyseraph.ai_p2t.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skyseraph.ai_p2t.R;
import com.skyseraph.ai_p2t.base.BaseActivity;
import com.skyseraph.ai_p2t.tensorflow.base.BaseClassifier;
import com.skyseraph.ai_p2t.tensorflow.mnist.ImageClassifier;
import com.skyseraph.ai_p2t.ui.view.DrawModel;
import com.skyseraph.ai_p2t.ui.view.DrawView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by skyserpah on 2018/1/1.
 */

public class MnistActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvRes;
    private DrawView drawView;
    private DrawModel drawModel;
    private Button btnClear;
    private Button btnDetect;

    private static final int PIXEL_WIDTH = 28;
    private BaseClassifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnist);

        // init
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDetect = (Button) findViewById(R.id.btnDetect);
        tvRes = (TextView) findViewById(R.id.tv_res);
        btnClear.setOnClickListener(this);
        btnDetect.setOnClickListener(this);
        btnDetect.setVisibility(View.INVISIBLE);

        drawModel = new DrawModel(PIXEL_WIDTH, PIXEL_WIDTH);
        drawView = (DrawView) findViewById(R.id.view_draw);
        drawView.setModel(drawModel);

        initTensorFlowAndLoadModel();
    }


    @Override
    protected void onResume() {
        drawView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        drawView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                drawModel.clear();
                drawView.reset();
                drawView.invalidate();
                tvRes.setText("");
                break;
            case R.id.btnDetect:
                float pixels[] = drawView.getPixelData();
                final List<BaseClassifier.Recognition> results = classifier.recognizeImage(pixels);
                if (results.size() > 0) {
                    String value = "识别到的数字是: " + results.get(0).getTitle();
                    tvRes.setText(value);
                }
                break;
        }
    }

    private static final int INPUT_SIZE = 28;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";
    private static final String MODEL_FILE = "file:///android_asset/mnist_model_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/mnist_labels.txt";

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = ImageClassifier.create(
                            getAssets(),
                            MODEL_FILE,
                            LABEL_FILE,
                            INPUT_SIZE,
                            INPUT_NAME,
                            OUTPUT_NAME);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnDetect.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
}
