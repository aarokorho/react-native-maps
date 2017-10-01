package com.airbnb.android.react.maps;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class AirMapHeatMapManager extends ViewGroupManager<AirMapHeatMap> {
  private DisplayMetrics metrics;

  public AirMapHeatMapManager(ReactApplicationContext reactContext) {
    super();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      metrics = new DisplayMetrics();
      ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE))
          .getDefaultDisplay()
          .getRealMetrics(metrics);
    } else {
      metrics = reactContext.getResources().getDisplayMetrics();
    }
  }

  @Override
  public String getName() {
    return "AIRMapHeatMap";
  }

  @Override
  public AirMapHeatMap createViewInstance(ThemedReactContext context) {
    return new AirMapHeatMap(context);
  }

  @ReactProp(name = "coordinates")
  public void setCoordinates(AirMapHeatMap view, ReadableArray coordinates) {
    view.setCoordinates(coordinates);
  }

  @ReactProp(name = "zIndex", defaultFloat = -1.0f)
  public void setZIndex(AirMapHeatMap view, float zIndex) {
    view.setZIndex(zIndex);
  }

  @ReactProp(name = "opacity", defaultFloat = 0.7f)
  public void setOpacity(AirMapHeatMap view, double opacity) {
    view.setOpacity(opacity);
  }

  @ReactProp(name = "radius", defaultInt = 10)
  public void setRadius(AirMapHeatMap view, int radius) {
    view.setRadius(radius);
  }

}
