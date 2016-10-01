package com.hotellook.desertplaceholder;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DesertPlaceholder extends FrameLayout {

  public static boolean animationEnabled = true;

  private TextView button;

  public DesertPlaceholder(Context context) {
    super(context);
    init(context, null);
  }

  public DesertPlaceholder(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public DesertPlaceholder(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (isInEditMode()) {
      return;
    }
    LayoutInflater.from(context).inflate(R.layout.place_holder, this, true);
    TextView message = (TextView) findViewById(R.id.placeholder_message);
    button = (TextView) findViewById(R.id.placeholder_button);

    final TypedArray attributes = context
        .obtainStyledAttributes(attrs, R.styleable.DesertPlaceholder, 0, 0);
    String messageText = attributes.getString(R.styleable.DesertPlaceholder_message);
    String buttonText = attributes.getString(R.styleable.DesertPlaceholder_buttonText);
    message.setText(messageText);
    if (TextUtils.isEmpty(buttonText)) {
      button.setVisibility(GONE);
    } else {
      button.setText(buttonText);
    }
    attributes.recycle();
    setBackgroundColor(getResources().getColor(R.color.background_desert));
  }

  public void setOnButtonClickListener(OnClickListener clickListener) {
    button.setOnClickListener(clickListener);
  }
}
