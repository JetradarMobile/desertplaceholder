package com.hotellook.desertplaceholder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeResource;
import static com.hotellook.desertplaceholder.DesertPlaceholder.animationEnabled;

public class CloudsView extends View {

  private static final float SPEED_DP_PER_SEC = 20f; // dp/sec
  private final List<Cloud> clouds = new ArrayList<>();

  private Paint paint;
  private float density;
  private double timeStamp = -1;

  public CloudsView(Context context) {
    super(context);
    init(context);
  }

  public CloudsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CloudsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    paint = new Paint();
    Resources res = context.getResources();
    density = context.getResources().getDisplayMetrics().density;
    clouds.add(new Cloud(decodeResource(res, R.drawable.cloud3), 0.3f, 0));
    clouds.add(new Cloud(decodeResource(res, R.drawable.cloud2), 0.6f, (int) (22 * density)));
    clouds.add(new Cloud(decodeResource(res, R.drawable.cloud1), 0.8f, (int) (40 * density)));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    long time = System.currentTimeMillis();
    if (timeStamp != -1) {
      drawClouds(canvas, time);
    } else {
      placeToStartPositions();
    }

    timeStamp = time;
    if (animationEnabled) {
      invalidate();
    }
  }

  private void placeToStartPositions() {
    int width = getWidth();
    float percent = 0.1f;
    for (Cloud cloud : clouds) {
      cloud.x = width * percent;
      percent += 0.25f;
    }
  }

  private void drawClouds(Canvas canvas, long time) {
    for (Cloud cloud : clouds) {
      updatePosition(cloud, (time - timeStamp) / 1000d);
      canvas.drawBitmap(cloud.bitmap, cloud.x, cloud.y, paint);
    }
  }

  private void updatePosition(Cloud cloud, double timeDelta) {
    cloud.x += density * SPEED_DP_PER_SEC * cloud.speedMultiplier * timeDelta;
    int width = getWidth();
    if (cloud.x > width) {
      cloud.x = -cloud.bitmap.getWidth();
    }
  }

  private static class Cloud {
    public final Bitmap bitmap;
    public final float speedMultiplier;
    public final int y;
    public float x;

    private Cloud(Bitmap bitmap, float speedMultiplier, int y) {
      this.bitmap = bitmap;
      this.speedMultiplier = speedMultiplier;
      this.y = y;
    }
  }
}
