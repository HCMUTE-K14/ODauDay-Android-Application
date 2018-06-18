package com.odauday.ui.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import com.odauday.MainActivity;
import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.data.local.cache.PrefKey;
import com.odauday.data.local.cache.PreferencesHelper;
import java.util.Locale;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by kunsubin on 4/27/2018.
 */

public class ChooseLanguageHelper {
    
    private static final String[] LANGUAGE = new String[]{AppConfig.LANGUAGE.EN.getDisplayString(),
        AppConfig.LANGUAGE.VI.getDisplayString()};
    
    
    private PreferencesHelper mPreferencesHelper;
    
    @Inject
    public ChooseLanguageHelper(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
    }
    
    public void change(final AppCompatActivity activity) {
        final AlertDialog.Builder alertDialog = new Builder(activity);
        
        alertDialog.setTitle(R.string.settings_choose_language);
        
        int index = getCurrentLanguage().equals("en") ? 0 : 1;
    
        Timber.tag("WWW").d(getCurrentLanguage());
        
        alertDialog.setSingleChoiceItems(LANGUAGE, index, (dialog, which) -> {
            String code = "en";
            switch (which) {
                case 0:
                    code = "en";
                    break;
                case 1:
                    code = "vi";
                    break;
                default:
                    break;
                
            }
            doChangeLanguage(activity, code);
            dialog.dismiss();
        });
        alertDialog.create().show();
    }
    
    private String getCurrentLanguage() {
        return mPreferencesHelper.get(PrefKey.PREF_LANGUAGE, "en");
    }
    
    private void doChangeLanguage(AppCompatActivity activity, String code) {
        changeConfig(activity, code);
        savePref(code);
        refresh(activity);
    }
    
    public void changeConfig(AppCompatActivity activity, String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
            activity.getBaseContext().getResources().getDisplayMetrics());
    }
    
    private void savePref(String code) {
        mPreferencesHelper.put(PrefKey.PREF_LANGUAGE, code);
    }
    
    private void refresh(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
