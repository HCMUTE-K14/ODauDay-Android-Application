package com.odauday.ui.search.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.odauday.R;
import com.odauday.data.remote.property.model.GeoLocation;
import com.odauday.data.remote.property.model.PropertyResultEntry;
import com.odauday.utils.BitmapUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by infamouSs on 4/13/18.
 */
public class MapViewAdapter {
    
    private static final int WIDTH_MARKER = 60;
    private static final int HEIGHT_MARKER = 60;
    
    private HashMap<GeoLocation, List<PropertyResultEntry>> mMapDisplayItems = new HashMap<>();
    private Map<MarkerType, WeakReference<BitmapDescriptor>> mMarketIconBitmapDescriptors = new HashMap<>();
    private BitmapDescriptor mMarkSelectedBitmapDescriptor;
    
    private OnUpdatedListLocation mOnUpdatedListLocation;
    private Context mContext;
    
    public MapViewAdapter(Context context) {
        this.mContext = context;
        mMarkSelectedBitmapDescriptor = buildMarkSelectedBitmapDescriptor();
    }
    
    
    public void setData(List<PropertyResultEntry> listings) {
        this.mMapDisplayItems.clear();
        for (PropertyResultEntry listing : listings) {
            GeoLocation location = listing.getLocation();
            if (location != null) {
                List<PropertyResultEntry> entries = this.mMapDisplayItems.get(location);
                if (entries == null) {
                    entries = new LinkedList<>();
                    this.mMapDisplayItems.put(location, entries);
                }
                entries.add(listing);
            }
        }
        if (this.mOnUpdatedListLocation != null) {
            this.mOnUpdatedListLocation.onUpdatedListLocation(this.mMapDisplayItems.keySet());
        }
    }
    
    public void setOnUpdatedListLocation(
              OnUpdatedListLocation onUpdatedListLocation) {
        mOnUpdatedListLocation = onUpdatedListLocation;
    }
    
    public PropertyResultEntry getEntryWithId(String entryId) {
        if (entryId == null) {
            return null;
        }
        for (List<PropertyResultEntry> entries : this.mMapDisplayItems.values()) {
            for (PropertyResultEntry entry : entries) {
                if (entryId.equals(entry.getId())) {
                    return entry;
                }
            }
        }
        return null;
    }
    
    public List<PropertyResultEntry> getEntriesAtLocation(LatLng location) {
        return this.mMapDisplayItems.get(GeoLocation.fromLatLng(location));
    }
    
    public List<PropertyResultEntry> getEntriesAtLocation(GeoLocation location) {
        return this.mMapDisplayItems.get(location);
    }
    
    private BitmapDescriptor buildMarkSelectedBitmapDescriptor() {
        int resourceId = R.drawable.ic_map_pin_selected;
        Bitmap resizeMarker = BitmapUtils.resize(mContext, resourceId, 140, WIDTH_MARKER);
        
        return BitmapDescriptorFactory.fromBitmap(resizeMarker);
    }
    
    public BitmapDescriptor getMarkSelectedBitmapDescriptor() {
        return mMarkSelectedBitmapDescriptor;
    }
    
    @SuppressWarnings("unchecked")
    public BitmapDescriptor getMarkerIconForLocation(GeoLocation entryLatLng) {
        MarkerType markerType = getMapPin(entryLatLng);
        BitmapDescriptor bitmapDescriptor = null;
        if (this.mMarketIconBitmapDescriptors.containsKey(markerType)) {
            bitmapDescriptor = (BitmapDescriptor) ((WeakReference) this.mMarketIconBitmapDescriptors
                      .get(markerType)).get();
        }
        if (bitmapDescriptor != null) {
            return bitmapDescriptor;
        }
        int resourceId = MarkerType.valueOf(markerType.name()).getResourceId();
        Bitmap resizeMarker = BitmapUtils.resize(mContext, resourceId, HEIGHT_MARKER, WIDTH_MARKER);
        
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizeMarker);
        this.mMarketIconBitmapDescriptors.put(markerType, new WeakReference(bitmapDescriptor));
        return bitmapDescriptor;
    }
    
    public MarkerType getMapPin(GeoLocation location) {
        List<PropertyResultEntry> entriesAtPosition = getEntriesAtLocation(location);
        if (isListEntryEmpty(entriesAtPosition)) {
            return MarkerType.DEFAULT;
        }
        if (entriesAtPosition.size() == 1) {
            return getIconForEntry(entriesAtPosition.get(0));
        }
        MarkerType[] pins = new MarkerType[entriesAtPosition.size()];
        for (int i = 0; i < entriesAtPosition.size(); i++) {
            pins[i] = getIconForEntry(entriesAtPosition.get(i));
        }
        Arrays.sort(pins);
        return pins[0];
    }
    
    public MarkerType getIconForEntry(PropertyResultEntry entry) {
        if (entry.isFavorite()) {
            return MarkerType.FAVORITE;
        } else if (entry.isVisited()) {
            return MarkerType.VISITED;
        } else {
            return MarkerType.DEFAULT;
        }
    }
    
    private boolean isListEntryEmpty(List<PropertyResultEntry> list) {
        return list == null || list.isEmpty();
    }
    
    public interface OnUpdatedListLocation {
        
        void onUpdatedListLocation(Collection<GeoLocation> locations);
    }
    
    public interface ListingsUpdateListener {
        
        void onListingsUpdated(Collection<GeoLocation> collection);
    }
}
