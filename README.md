# ProgressTextView

### Preview

![screen-capture1](https://raw.githubusercontent.com/hyb1996/ProgressTextView/master/screen-captures/ss01.png)

![screen-capture1](https://raw.githubusercontent.com/hyb1996/ProgressTextView/master/screen-captures/ss02.gif)

### Dependency

#### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

#### Step 2. Add the dependency
```
    dependencies {
            compile 'com.github.hyb1996:EnhancedFloaty:0.14'
    }
```

### Usage

The attributes of ProgressTextView:

* textColor: The text color of unreached region and the background color of reached region.
* ptv_reached_text_color: The text color of reached region and the background color of unreached region. Default value is white.
* ptv_round_radius: The radius of rounded corners. Default value is 0.
* ptv_border_width: The width of rounded border. Valid only if ptv_round_radius > 0. Default value is 3px.
* ptv_enable_pressed_anim: Determine whether the animation likes Button should be enabled. If enabled, it will have a elevation when pressed. Default value is false.


For example:
```
<com.stardust.widget.ProgressTextView
        android:id="@+id/ptv"
        android:layout_width="50dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:gravity="center"
        android:maxLines="1"
        android:paddingBottom="4dp"
        android:paddingTop="4dp"
        android:text="@string/download"
        android:textColor="#1d82ff"
        android:textSize="12sp"
        app:ptv_border_width="1dp"
        app:ptv_enable_pressed_anim="true"
        app:ptv_reached_text_color="@android:color/white"
        app:ptv_round_radius="2dp"/>
```

You can also set above attributes programmatically.  
For more information, see [sample](https://github.com/hyb1996/ProgressTextView/tree/master/sample).
