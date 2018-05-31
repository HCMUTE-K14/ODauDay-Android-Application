package com.odauday.ui.user.buypoint.common;

import com.odauday.R;

/**
 * Created by infamouSs on 5/31/18.
 */
public enum SubscriptionInfor {
    
    BRONZE(0, R.string.txt_subscription_bronze, R.drawable.ic_bronze_premium),
    SILVER(1, R.string.txt_subscription_silver, R.drawable.ic_silver_premium),
    GOLD(2, R.string.txt_subscription_gold, R.drawable.ic_gold_premium);
    
    
    private int id;
    private int name;
    private int image;
    
    SubscriptionInfor(int id, int name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    
    public static SubscriptionInfor getById(String id) {
        return getById(Integer.valueOf(id));
    }
    
    public static SubscriptionInfor getById(int id) {
        
        switch (id) {
            case 0:
                return BRONZE;
            case 1:
                return SILVER;
            case 2:
                return GOLD;
            default:
                return null;
        }
    }
    
    public int getId() {
        return id;
    }
    
    public int getName() {
        return name;
    }
    
    public int getImage() {
        return image;
    }
}
