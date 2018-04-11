package com.odauday.ui.search.mapview;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.odauday.R;
import com.odauday.data.remote.search.model.CoreSearchRequest;
import com.odauday.data.remote.search.model.Location;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import com.odauday.utils.permissions.PermissionHelper.Permission;

/**
 * Created by infamouSs on 4/11/18.
 */

public class MapUtils {
    
    public static final String[] LOCATION_PERMISSION = new String[]{
              Permission.ACCESS_COARSE_LOCATION, Permission.FINE_LOCATION};
    
    public static CoreSearchRequest getCoreSearchRequest(GoogleMap map, int orientation) {
        VisibleRegion vr = map.getProjection().getVisibleRegion();
        double top = vr.latLngBounds.northeast.latitude;
        double left = vr.latLngBounds.southwest.longitude;
        
        Location locationCenter = new Location(map.getCameraPosition().target);
        
        switch (orientation) {
            
            case Configuration.ORIENTATION_LANDSCAPE:
                Location locationLeft = new Location(locationCenter.getLatitude(), left);
                return new CoreSearchRequest(locationCenter, locationLeft);
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_SQUARE:
            case Configuration.ORIENTATION_UNDEFINED:
            default:
                Location locationTop = new Location(top, locationCenter.getLongitude());
                return new CoreSearchRequest(locationCenter, locationTop);
        }
    }
    
    public static void drawCircle(GoogleMap map, LatLng center, double radius) {
        CircleOptions circleOptions = new CircleOptions()
                  .center(center)
                  .radius(radius)
                  .strokeColor(Color.BLACK)
                  .fillColor(0x30ff0000)
                  .strokeWidth(2);
        
        map.addCircle(circleOptions);
    }
    
    public static void drawBound(GoogleMap map) {
        LatLngBounds b = map.getProjection().getVisibleRegion().latLngBounds;
        
        PolygonOptions po = new PolygonOptions();
        
        LatLng northwest = new LatLng(b.southwest.latitude, b.northeast.longitude);
        LatLng southeast = new LatLng(b.northeast.latitude, b.southwest.longitude);
        
        po.add(b.northeast, northwest);
        po.add(northwest, b.southwest);
        po.add(b.southwest, southeast);
        po.add(southeast, b.northeast);
        map.addMarker(
                  new MarkerOptions().position(b.getCenter()).title("CENTER"));
        map.addPolygon(po);
    }
    
    
    public static void moveMap(GoogleMap map, LatLng location, float zoom, boolean withAnimate) {
        if (withAnimate) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }
    
    public static void moveMap(GoogleMap map, LatLng location, float zoomLevel) {
        moveMap(map, location, zoomLevel, true);
    }
    
    public static void requireLocationPermission(Activity activity,
              PermissionCallBack permissionCallBack) {
        if (PermissionHelper.shouldShowRequestPermissionRationale(activity,
                  PermissionHelper.Permission.READ_EXTERNAL_STORAGE)) {
            String message = activity.getString(R.string.message_permission_location_request);
            String action = activity.getString(R.string.txt_ok);
            SnackBarUtils.showSnackBar(activity.findViewById(android.R.id.content), action, message,
                      view -> {
                          PermissionHelper.askForPermission(activity,
                                    LOCATION_PERMISSION, permissionCallBack);
                      });
        } else {
            PermissionHelper
                      .askForPermission(activity,
                                LOCATION_PERMISSION,
                                permissionCallBack);
        }
    }
    
    public static boolean isHasLocationPermission(Activity activity) {
        return PermissionHelper.isHasPermission(activity, LOCATION_PERMISSION);
    }
    
}
