package com.odauday.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.odauday.R;
import com.odauday.data.remote.property.model.CoreSearchRequest;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import com.odauday.utils.permissions.PermissionHelper.Permission;

/**
 * Created by infamouSs on 4/11/18.
 */


public class MapUtils {
    
    public static final String[] LOCATION_PERMISSION = new String[]{
              Permission.ACCESS_COARSE_LOCATION, Permission.FINE_LOCATION};
    
    private static final double SMALL_DISTANCE_THRESHOLD = 0.002d;
    
    private static final double DISTANCE_THRESHOLD = 0.02d;
    
    public static CoreSearchRequest getCoreSearchRequestFromCurrentLocation(GoogleMap map,
              int orientation) {
        VisibleRegion vr = map.getProjection().getVisibleRegion();
        
        double top = vr.latLngBounds.northeast.latitude;
        double left = vr.latLngBounds.southwest.longitude;
        
        GeoLocation geoLocationCenter = new GeoLocation(map.getCameraPosition().target);
        
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                GeoLocation geoLocationLeft = new GeoLocation(geoLocationCenter.getLatitude(),
                          left);
                return new CoreSearchRequest(geoLocationCenter, geoLocationLeft, vr.latLngBounds);
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_SQUARE:
            case Configuration.ORIENTATION_UNDEFINED:
            default:
                GeoLocation geoLocationTop = new GeoLocation(top, geoLocationCenter.getLongitude());
                return new CoreSearchRequest(geoLocationCenter, geoLocationTop, vr.latLngBounds);
        }
    }
    
    public static void drawCircle(GoogleMap map, LatLng center, double radius) {
        CircleOptions circleOptions = new CircleOptions()
                  .center(center)
                  .radius(radius)
                  .strokeColor(Color.RED)
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
    
    public static boolean isVisibleInBounds(GoogleMap map, GeoLocation location) {
        return map.getProjection().getVisibleRegion().latLngBounds.contains(location.toLatLng());
    }
    
    public static boolean isVisibleInBounds(GoogleMap map, LatLng location) {
        return map.getProjection().getVisibleRegion().latLngBounds.contains(location);
    }
    
    
    public static void requireLocationPermission(Activity activity,
              PermissionCallBack permissionCallBack) {
        if (PermissionHelper.shouldShowRequestPermissionRationale(activity,
                  PermissionHelper.Permission.READ_EXTERNAL_STORAGE)) {
            String message = activity.getString(R.string.message_permission_location_request);
            String action = activity.getString(R.string.txt_ok);
            SnackBarUtils.showSnackBar(activity.findViewById(android.R.id.content), action, message,
                      view -> PermissionHelper.askForPermission(activity,
                                LOCATION_PERMISSION, permissionCallBack));
        } else {
            PermissionHelper
                      .askForPermission(activity,
                                LOCATION_PERMISSION,
                                permissionCallBack);
        }
    }
    
    public static BitmapDescriptor buildMarkSelectedBitmapDescriptor(Context context) {
        int resourceId = R.drawable.ic_map_pin_selected_no_padding;
        Bitmap resizeMarker = BitmapUtils.resize(context, resourceId, 110, 45);
        
        return BitmapDescriptorFactory.fromBitmap(resizeMarker);
    }
    
    @SuppressLint("MissingPermission")
    public static void moveToMyLocation(Activity context,
              OnSuccessListener<Location> onSuccessListener, OnFailureListener onFailureListener) {
        if (context == null) {
            return;
        }
        LocationServices
                  .getFusedLocationProviderClient(context)
                  .getLastLocation()
                  .addOnSuccessListener(context, onSuccessListener)
                  .addOnFailureListener(onFailureListener);
    }
    
    
    public static boolean isCamPositionEqual(CameraPosition cam1, CameraPosition cam2) {
        if (cam1 == null || cam2 == null) {
            return false;
        }
        if (cam1.equals(cam2)) {
            return true;
        }
        if (cam1.tilt != cam2.tilt || cam1.bearing != cam2.bearing || cam1.zoom != cam2.zoom) {
            return false;
        }
        double distanceThreshold =
                  cam1.zoom > 15.0f ? SMALL_DISTANCE_THRESHOLD : DISTANCE_THRESHOLD;
        if (Math.abs((cam1.target.latitude - cam2.target.latitude) * 10.0d) >= distanceThreshold ||
            Math.abs((cam1.target.longitude - cam2.target.longitude) * 10.0d) >=
            distanceThreshold) {
            return false;
        }
        return true;
    }
    
    public static boolean isHasLocationPermission(Activity activity) {
        return PermissionHelper.isHasPermission(activity, LOCATION_PERMISSION);
    }
    
}
