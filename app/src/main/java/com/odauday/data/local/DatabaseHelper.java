package com.odauday.data.local;

import android.content.Context;
import com.odauday.data.local.history.DaoMaster.OpenHelper;
import javax.inject.Singleton;
import org.greenrobot.greendao.database.Database;
import timber.log.Timber;

/**
 * Created by infamouSs on 4/9/18.
 */
@Singleton
public class DatabaseHelper extends OpenHelper {
    
    public DatabaseHelper(Context context, String name) {
        super(context, name);
    }
    
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Timber.d("DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                break;
            default:
                break;
        }
    }
}
