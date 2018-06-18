package com.odauday.ui.search.common;

import com.odauday.data.remote.property.model.PropertyResultEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by infamouSs on 6/2/18.
 */
public class SortPropertyEntryUtils {
    
    
    public static List<PropertyResultEntry> sort(List<PropertyResultEntry> list,
        SortType sortType) {
        if(list==null){
            return new ArrayList<>();
        }
        List<PropertyResultEntry> data = new ArrayList<>(list);
        Collections.sort(data, getComparator(sortType));
        
        return data;
    }
    
    public static Comparator<PropertyResultEntry> getComparator(SortType sortType) {
        if (sortType == SortType.NEWEST) {
            return dateComparator();
        } else if (sortType == SortType.HIGHEST_PRICE) {
            return highestPriceComparator();
        } else if (sortType == SortType.LOWEST_PRICE) {
            return lowestPriceComparator();
        } else {
            return null;
        }
        
    }
    
    public static Comparator<PropertyResultEntry> highestPriceComparator() {
        return (o1, o2) -> {
            return Double.compare(o1.getPrice(), o2.getPrice());
        };
    }
    
    public static Comparator<PropertyResultEntry> lowestPriceComparator() {
        return (o1, o2) -> {
            return Double.compare(o2.getPrice(), o1.getPrice());
        };
    }
    
    public static Comparator<PropertyResultEntry> dateComparator() {
        return (o1, o2) -> {
            if (o1.getDateCreated().after(o2.getDateCreated())) {
                return -1;
            }
            if (o1.getDateCreated().before(o2.getDateCreated())) {
                return 1;
            }
            return 0;
        };
    }
}
