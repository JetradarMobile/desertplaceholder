/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jetradar.desertplaceholder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

import static com.jetradar.desertplaceholder.DesertPlaceholder.animationEnabled;

public class TumbleweedView extends View {

  private static final int INVALID_TIME = -1;
  private static final float START_POSITION_PERCENT_OF_SCREEN = 0.3f;
  private static final float SHADOW_SCALE_FACTOR = 0.7f;
  private static final float MAX_JUMP_HEIGHT_IN_METERS = 1f;
  private static final float DEFAULT_SPEED = 50f; // dp/sec
  private static final float MAX_SPEED = DEFAULT_SPEED * 1.2f;
  private static final float MIN_SPEED = DEFAULT_SPEED * 0.8f;
  private static final float SPEED_RANDOM_DELTA = 0.05f * DEFAULT_SPEED;
  private static final float ROTATION_SPEED = 360;
  private static final float g = SensorManager.GRAVITY_MARS;

  private float density;
  private double timeStamp = INVALID_TIME;
  private float meterInDp;
  private float currentVerticalSpeed;
  private float currentSpeed = DEFAULT_SPEED;

  private Bitmap tumbleweed;
  private Bitmap shadow;
  private Random random;
  private float x;
  private float y;
  private float angle;
  private Paint paint;
  private Matrix matrix = new Matrix();
  private float bottomPosition;
  private float topPosition;

  public TumbleweedView(Context context) {
    super(context);
    init(context);
  }

  public TumbleweedView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public TumbleweedView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    Resources res = context.getResources();
    paint = new Paint();
    tumbleweed = BitmapFactory.decodeResource(res, R.drawable.tumbleweed);
    shadow = BitmapFactory.decodeResource(res, R.drawable.shadow_tumbleweed);
    density = context.getResources().getDisplayMetrics().density;
    random = new Random();
    meterInDp = tumbleweed.getHeight();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    long time = System.currentTimeMillis();

    if (timeStamp != INVALID_TIME) {
      double delta = (time - timeStamp) / 1000d;
      updatePosition(delta);
      drawShadow(canvas);
      drawTumbleweed(canvas, delta);
    } else {
      bottomPosition = getHeight() - tumbleweed.getHeight();
      topPosition = MAX_JUMP_HEIGHT_IN_METERS * density;
      x = getWidth() * START_POSITION_PERCENT_OF_SCREEN;
      y = bottomPosition;
    }

    timeStamp = time;
    if (animationEnabled) {
      invalidate();
    }
  }

  private void drawShadow(Canvas canvas) {
    float scale = 1 - SHADOW_SCALE_FACTOR * ((bottomPosition - y) / (bottomPosition - topPosition));
    Bitmap toDraw;
    if (scale == 1) {
      toDraw = shadow;
    } else {
      int width = Math.round(shadow.getWidth() * scale);
      int height = Math.round(shadow.getHeight() * scale);
      if (width == 0 || height == 0) return;
      toDraw = Bitmap.createScaledBitmap(shadow, width, height, true);
    }
    canvas.drawBitmap(toDraw, x, getHeight() - shadow.getHeight(), paint);
  }

  private void drawTumbleweed(Canvas canvas, double delta) {
    updateAngle(delta);
    matrix.setTranslate(x, y);
    matrix.postRotate(angle, x + tumbleweed.getWidth() / 2, y + tumbleweed.getHeight() / 2);
    canvas.drawBitmap(tumbleweed, matrix, paint);
  }

  private void updateAngle(double delta) {
    angle += delta * ROTATION_SPEED;
    angle %= 360;
  }

  private void updatePosition(double delta) {
    recalculateCurrentSpeed();
    x += density * currentSpeed * delta;
    if (x > getWidth() + 5 * tumbleweed.getWidth()) {
      x = -tumbleweed.getWidth();
    }

    if (y != bottomPosition || currentVerticalSpeed > 0) {
      currentVerticalSpeed -= delta * g * meterInDp;
      y -= delta * currentVerticalSpeed;
      if (y > bottomPosition) {
        y = bottomPosition;
      }
    } else {
      calculateJumpSpeed();
    }
  }

  private void calculateJumpSpeed() {
    currentVerticalSpeed = getRandom(0.2f * MAX_JUMP_HEIGHT_IN_METERS, MAX_JUMP_HEIGHT_IN_METERS) * meterInDp * density;
  }

  private void recalculateCurrentSpeed() {
    currentSpeed += getRandom(-SPEED_RANDOM_DELTA, SPEED_RANDOM_DELTA);

    if (currentSpeed < MIN_SPEED) {
      currentSpeed = MIN_SPEED;
    }
    if (currentSpeed > MAX_SPEED) {
      currentSpeed = MAX_SPEED;
    }
  }

  private float getRandom(float min, float max) {
    float random = this.random.nextFloat();
    return min + random * (max - min);
  }
}