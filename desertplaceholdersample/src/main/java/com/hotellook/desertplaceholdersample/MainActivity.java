package com.hotellook.desertplaceholdersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.hotellook.desertplaceholder.DesertPlaceholder;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DesertPlaceholder desertPlaceholder = (DesertPlaceholder) findViewById(R.id.placeholder);
    desertPlaceholder.setOnButtonClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
