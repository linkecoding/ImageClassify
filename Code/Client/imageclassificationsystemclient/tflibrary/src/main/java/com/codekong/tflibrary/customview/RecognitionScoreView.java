/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.codekong.tflibrary.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.codekong.tflibrary.utils.Classifier;

import java.util.List;

public class RecognitionScoreView extends View implements ResultsView {
    private static final float TEXT_SIZE_DIP = 18;
    private List<Classifier.Recognition> results;
    private final float textSizePx;
    private final Paint fgPaint;
    private final Paint bgPaint;
    private static final int bgColor = 0xcc4285f4;

    public RecognitionScoreView(final Context context, final AttributeSet set) {
        super(context, set);

        textSizePx =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
        fgPaint = new Paint();
        fgPaint.setAntiAlias(true);
        fgPaint.setTextSize(textSizePx);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
    }

    @Override
    public void setResults(final List<Classifier.Recognition> results) {
        Log.d("TensorFlow ", "setResults: " + Thread.currentThread().getName());
        this.results = results;
        postInvalidate();
    }

    @Override
    public void onDraw(final Canvas canvas) {
        final int x = 10;
        int y = (int) (fgPaint.getTextSize() * 1.5f);

        canvas.drawPaint(bgPaint);

        if (results != null) {
            for (final Classifier.Recognition recog : results) {
                String confidence = String.format("%.1f%% ", recog.getConfidence() * 100.0f);
                canvas.drawText(recog.getTitle() + ": " + confidence, x, y, fgPaint);
                y += fgPaint.getTextSize() * 1.5f;
            }
        }
    }
}
