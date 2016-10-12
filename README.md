DesertPlaceholder
=================

Animated placeholder in desert style

![image](https://github.com/JetradarMobile/DesertPlaceholder/blob/master/art/desertplaceholder.gif)

Compatibility
-------------

This library is compatible from API 14 (Android 4.0)

Usage
-----

Add view to your layout

``` xml
  <com.jetradar.desertplaceholder.DesertPlaceholder
      android:id="@+id/placeholder"
      app:message="Use this nice placeholder if you have nothing to show"
      app:buttonText="retry"
      android:layout_width="match_parent"
      android:layout_height="match_parent"/>
```

Set up listener to button click

``` java
   DesertPlaceholder desertPlaceholder = (DesertPlaceholder) findViewById(R.id.placeholder);
    desertPlaceholder.setOnButtonClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // do stuff
      }
    });
```

#### Testing

``` java
  @Rule
  public final ActivityTestRule<MainActivity> activityRule  = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      //...
      DesertPlaceholder.animationEnabled = false;
    }
  };

  @Test
  public void placeholder() {
    onView(withId(R.id.placeholder))
        .perform(click()) // will freeze here if animation enabled
        .check(matches(isDisplayed()));
  }
```

Download
--------

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```


Add the dependency

```groovy
dependencies {
    compile 'com.github.JetradarMobile:DesertPlaceholder:1.0.1'
}
```

Credentials
-----------

Designed by [Max Klimchuk](https://dribbble.com/maxklimchuk)


License
-------

    Copyright 2016 JetRadar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
