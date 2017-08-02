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
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DesertPlaceholder extends FrameLayout {

  public static boolean animationEnabled = true;

  private TextView button;
  private TextView message;

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
    LayoutInflater.from(context).inflate(R.layout.placeholder, this, true);
    message = (TextView) findViewById(R.id.placeholder_message);
    button = (TextView) findViewById(R.id.placeholder_button);

    TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DesertPlaceholder);
    try {
      String messageText = attributes.getString(R.styleable.DesertPlaceholder_dp_message);
      String buttonText = attributes.getString(R.styleable.DesertPlaceholder_dp_buttonText);
      setMessage(messageText);
      setButtonText(buttonText);
    } finally {
      attributes.recycle();
    }
    setBackgroundColor(getResources().getColor(R.color.background_desert));
  }

  public void setOnButtonClickListener(OnClickListener clickListener) {
    button.setOnClickListener(clickListener);
  }

  public void setMessage(String msg) {
    message.setText(msg);
  }

  public void setButtonText(String action) {
    if (TextUtils.isEmpty(action)) {
      button.setVisibility(GONE);
    } else {
      button.setText(action);
      button.setVisibility(VISIBLE);
    }
  }
}