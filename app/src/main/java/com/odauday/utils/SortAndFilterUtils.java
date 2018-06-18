package com.odauday.utils;

import com.odauday.model.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kunsubin on 4/28/2018.
 */

public class SortAndFilterUtils {
    
    
    public static List<Property> getListPropertyByType(List<Property> list, String type) {
        List<Property> propertyList = new ArrayList<>();
        for (Property property : list) {
            if (property.getType_id().equals(type)) {
                propertyList.add(property);
            }
        }
        return propertyList;
    }
    public static List<Property> sortListPropertyLowestPrice(List<Property> list) {
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            if (property1.getPrice() < property2.getPrice()) {
                return -1;
            }
            if (property1.getPrice() > property2.getPrice()) {
                return 1;
            }
            return 0;
        };
        Collections.sort(propertyList, comparator);
        return propertyList;
    }
    public static List<Property> sortListPropertyHighestPrice(List<Property> list) {
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            if (property1.getPrice() > property2.getPrice()) {
                return -1;
            }
            if (property1.getPrice() < property2.getPrice()) {
                return 1;
            }
            return 0;
        };
        Collections.sort(propertyList, comparator);
        return propertyList;
    }
    public static List<Property> sortFavoriteLastAdded(List<Property> list) {
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            if (property1.getFavorite().getDate_created()
                .after(property2.getFavorite().getDate_created())) {
                return -1;
            }
            if (property1.getFavorite().getDate_created()
                .before(property2.getFavorite().getDate_created())) {
                return 1;
            }
            return 0;
        };
        Collections.sort(propertyList, comparator);
        return propertyList;
    }
    public static List<Property> sortAddressPropertyAscending(List<Property> list){
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            return property1.getAddress().compareTo(property2.getAddress());
        };
        Collections.sort(propertyList, comparator);
        return propertyList;
    }
    public static List<Property> sortAddressPropertyDescending(List<Property> list){
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            return property2.getAddress().compareTo(property1.getAddress());
        };
        Collections.sort(propertyList, comparator);
        return propertyList;
    }
    public static List<Property> sortPropertyLastAdded(List<Property> list){
        List<Property> propertyList = new ArrayList<>(list);
        Comparator<Property> comparator = (property1, property2) -> {
            if (property1.getDate_created()
                .after(property2.getDate_created())) {
                return -1;
            }
            if (property1.getDate_created()
                .before(property2.getDate_created())) {
                return 1;
            }
            return 0;
        };
        Collections.sort(propertyList,comparator);
        return propertyList;
    }
}
