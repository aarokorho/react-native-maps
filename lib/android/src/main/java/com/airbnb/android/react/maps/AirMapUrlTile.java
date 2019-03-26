package com.airbnb.android.react.maps;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class AirMapUrlTile extends AirMapFeature {

  class AIRMapUrlTileProvider extends UrlTileProvider {
    private String urlTemplate;

    public AIRMapUrlTileProvider(int width, int height, String urlTemplate) {
      super(width, height);
      this.urlTemplate = urlTemplate;
    }

    @Override
    public synchronized URL getTileUrl(int x, int y, int zoom) {

      if (AirMapUrlTile.this.flipY == true) {
        y = (1 << zoom) - y - 1;
      }

      LatLong a = calculateLatLong(x + 1, y + 1, zoom);
      LatLong b = calculateLatLong(x, y, zoom);

      String s = this.urlTemplate
          .replace("{minLon}", Double.toString(b.getLong()))
          .replace("{minLat}", Double.toString(a.getLat()))
          .replace("{maxLon}", Double.toString(a.getLong()))
          .replace("{maxLat}", Double.toString(b.getLat()));
      URL url = null;

      if(AirMapUrlTile.this.maximumZ > 0 && zoom > maximumZ) {
        return url;
      }

      if(AirMapUrlTile.this.minimumZ > 0 && zoom < minimumZ) {
        return url;
      }

      try {
        url = new URL(s);
      } catch (MalformedURLException e) {
        throw new AssertionError(e);
      }
      return url;
    }

    public void setUrlTemplate(String urlTemplate) {
      this.urlTemplate = urlTemplate;
    }
  }

  private TileOverlayOptions tileOverlayOptions;
  private TileOverlay tileOverlay;
  private AIRMapUrlTileProvider tileProvider;

  private String urlTemplate;
  private float zIndex;
  private float maximumZ;
  private float minimumZ;
  private boolean flipY;
  private float opacity;

  public AirMapUrlTile(Context context) {
    super(context);
  }

  public void setUrlTemplate(String urlTemplate) {
    this.urlTemplate = urlTemplate;
    if (tileProvider != null) {
      tileProvider.setUrlTemplate(urlTemplate);
    }
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setZIndex(float zIndex) {
    this.zIndex = zIndex;
    if (tileOverlay != null) {
      tileOverlay.setZIndex(zIndex);
    }
  }

  public void setMaximumZ(float maximumZ) {
    this.maximumZ = maximumZ;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setMinimumZ(float minimumZ) {
    this.minimumZ = minimumZ;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setFlipY(boolean flipY) {
    this.flipY = flipY;
    if (tileOverlay != null) {
      tileOverlay.clearTileCache();
    }
  }

  public void setOpacity(float opacity) {
    this.opacity = opacity;
    if (tileOverlay != null) {
      tileOverlay.setTransparency(opacity);
    }
  }

  public TileOverlayOptions getTileOverlayOptions() {
    if (tileOverlayOptions == null) {
      tileOverlayOptions = createTileOverlayOptions();
    }
    return tileOverlayOptions;
  }

  private TileOverlayOptions createTileOverlayOptions() {
    TileOverlayOptions options = new TileOverlayOptions();
    options.zIndex(zIndex);
    options.transparency(opacity);
    this.tileProvider = new AIRMapUrlTileProvider(256, 256, this.urlTemplate);
    options.tileProvider(this.tileProvider);
    return options;
  }

  private LatLong calculateLatLong(int x, int y, int zoom) {
    double n = Math.pow(2, zoom);
    double lon_deg = x / n * 360 - 180;
    double lat_rad = Math.atan(Math.sinh(Math.PI * (1 - 2 * y / n)));
    double lat_deg = Math.toDegrees(lat_rad);
    LatLong coordinates = new LatLong(lat_deg, lon_deg);
    return coordinates;
  }

  @Override
  public Object getFeature() {
    return tileOverlay;
  }

  @Override
  public void addToMap(GoogleMap map) {
    this.tileOverlay = map.addTileOverlay(getTileOverlayOptions());
  }

  @Override
  public void removeFromMap(GoogleMap map) {
    tileOverlay.remove();
  }
}

class LatLong {
  private double latitude;
  private double longitude;

  public LatLong(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLat() {
    return this.latitude;
  }

  public double getLong() {
    return this.longitude;
  }
}
