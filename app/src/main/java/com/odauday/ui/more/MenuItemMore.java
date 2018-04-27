package com.odauday.ui.more;

import android.content.Context;
import com.odauday.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunsubin on 4/19/2018.
 */

public class MenuItemMore {
    
    private String id;
    private String icon;
    private String name;
    
    public MenuItemMore(String id, String icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }
    
    public static List<MenuItemMore> getListMenuMore(Context context, String role) {
        List<MenuItemMore> list = new ArrayList<>();
        
        MenuItemMore menuItemMorePropertyManager = new MenuItemMore(ItemType.PROPERTY_MANAGER,
            "ic_home", context.getString(
            R.string.property_manager));
        MenuItemMore menuItemMorePostNews = new MenuItemMore(ItemType.POST_NEWS, "ic_newspaper",
            context.getString(
                R.string.post_news));
        MenuItemMore menuItemMoreConfirmProperty = new MenuItemMore(ItemType.CONFIRM_PROPERTY,
            "ic_checked", context.getString(
            R.string.confirm_property));
        MenuItemMore menuItemMoreSetting = new MenuItemMore(ItemType.SETTINGS, "ic_settings",
            context.getString(
                R.string.action_settings));
        MenuItemMore menuItemMoreFeedback = new MenuItemMore(ItemType.FEEDBACK, "ic_feedback",
            context.getString(
                R.string.feedback));
        MenuItemMore menuItemMoreShareThisApp = new MenuItemMore(ItemType.SHARE_THIS_APP,
            "ic_share_this_app", context.getString(
            R.string.share_this_app));
        
        list.add(menuItemMorePropertyManager);
        list.add(menuItemMorePostNews);
        if (role.equals("admin")) {
            list.add(menuItemMoreConfirmProperty);
        }
        list.add(menuItemMoreSetting);
        list.add(menuItemMoreFeedback);
        list.add(menuItemMoreShareThisApp);
        
        return list;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
