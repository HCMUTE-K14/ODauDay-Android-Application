package com.odauday.di.modules;

import com.odauday.MainActivity;
import com.odauday.MainActivityModule;
import com.odauday.di.scopes.PerActivity;
import com.odauday.di.scopes.PerFragment;
import com.odauday.ui.addeditproperty.AddEditPropertyActivity;
import com.odauday.ui.addeditproperty.AddEditPropertyModule;
import com.odauday.ui.addeditproperty.step1.Step1Provider;
import com.odauday.ui.addeditproperty.step2.Step2Provider;
import com.odauday.ui.addeditproperty.step3.Step3Provider;
import com.odauday.ui.addeditproperty.step4.Step4Provider;
import com.odauday.ui.admin.ActivityAdminManager;
import com.odauday.ui.admin.propertymanager.ConfirmPropertyProvider;
import com.odauday.ui.admin.usermanager.UserManagerProvider;
import com.odauday.ui.admin.usermanager.userdetail.ActivityUserDetail;
import com.odauday.ui.admin.usermanager.userdetail.UserHistoryProvide;
import com.odauday.ui.alert.ActivityDetailNotification;
import com.odauday.ui.alert.AlertTabProvider;
import com.odauday.ui.alert.service.FirebaseMessaging;
import com.odauday.ui.favorite.FavoriteTabProvider;
import com.odauday.ui.more.MoreTabProvider;
import com.odauday.ui.propertydetail.PropertyDetailActivity;
import com.odauday.ui.propertydetail.PropertyDetailModule;
import com.odauday.ui.propertymanager.ActivityPropertyManager;
import com.odauday.ui.propertymanager.PropertyManagerModule;
import com.odauday.ui.savedsearch.SavedSearchProvider;
import com.odauday.ui.search.SearchTabProvider;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceActivity;
import com.odauday.ui.search.autocomplete.AutoCompletePlaceModule;
import com.odauday.ui.selectlocation.SelectLocationActivity;
import com.odauday.ui.selectlocation.SelectLocationModule;
import com.odauday.ui.settings.ActivitySettings;
import com.odauday.ui.settings.SettingsModule;
import com.odauday.ui.user.buypoint.BuyPointActivity;
import com.odauday.ui.user.buypoint.BuyPointModule;
import com.odauday.ui.user.forgotpassword.ForgotPasswordActivity;
import com.odauday.ui.user.forgotpassword.ForgotPasswordModule;
import com.odauday.ui.user.login.LoginActivity;
import com.odauday.ui.user.login.LoginModule;
import com.odauday.ui.user.profile.ProfileUserActivity;
import com.odauday.ui.user.profile.ProfileUserModule;
import com.odauday.ui.user.profile.detail.ProfileDetailProvider;
import com.odauday.ui.user.profile.history.HistoryPropertyProvider;
import com.odauday.ui.user.register.RegisterActivity;
import com.odauday.ui.user.register.RegisterModule;
import com.odauday.ui.user.subscribe.SubscribePremiumActivity;
import com.odauday.ui.user.subscribe.SubscribePremiumModule;
import com.odauday.ui.welcome.WelcomeActivity;
import com.odauday.ui.welcome.WelcomeActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by infamouSs on 2/27/18.
 */

@Module
public abstract class ViewBuildersModule {
    
    @ContributesAndroidInjector(modules = LoginModule.class)
    @PerActivity
    abstract LoginActivity bindLoginActivity();
    
    @ContributesAndroidInjector(modules = RegisterModule.class)
    @PerActivity
    abstract RegisterActivity bindRegisterActivity();
    
    @ContributesAndroidInjector(modules = ForgotPasswordModule.class)
    @PerActivity
    abstract ForgotPasswordActivity bindForgotPasswordActivity();
    
    @ContributesAndroidInjector(modules = AutoCompletePlaceModule.class)
    @PerActivity
    abstract AutoCompletePlaceActivity bindAutoCompletePlaceActitivty();
    
    @ContributesAndroidInjector(modules = {
        AddEditPropertyModule.class,
        Step1Provider.class,
        Step2Provider.class,
        Step3Provider.class,
        Step4Provider.class
    })
    @PerActivity
    abstract AddEditPropertyActivity bindAddEditPropertyActivity();
    
    @ContributesAndroidInjector(modules = SelectLocationModule.class)
    @PerActivity
    abstract SelectLocationActivity bindSelectLocationActivity();
    
    @ContributesAndroidInjector(modules = WelcomeActivityModule.class)
    @PerActivity
    abstract WelcomeActivity bindWelcomeActivity();
    
    @ContributesAndroidInjector(modules = PropertyDetailModule.class)
    @PerActivity
    abstract PropertyDetailActivity bindPropertyDetailActivity();
    
    @ContributesAndroidInjector(modules = {
        SearchTabProvider.class,
        AlertTabProvider.class,
        FavoriteTabProvider.class,
        SavedSearchProvider.class,
        MoreTabProvider.class,
        MainActivityModule.class
    })
    @PerActivity
    abstract MainActivity bindMainActivity();
    
    @ContributesAndroidInjector(modules = PropertyManagerModule.class)
    @PerActivity
    abstract ActivityPropertyManager provideActivityPropertyManager();
    
    @ContributesAndroidInjector(modules = SettingsModule.class)
    @PerActivity
    abstract ActivitySettings provideActivitySettings();
    
    @ContributesAndroidInjector(modules = {ConfirmPropertyProvider.class, UserManagerProvider.class})
    @PerActivity
    abstract ActivityAdminManager provideActivityAdminManager();
    
    @ContributesAndroidInjector
    abstract FirebaseMessaging contributeFirebaseMessaging();
    @ContributesAndroidInjector(modules = {
        ProfileUserModule.class,
        HistoryPropertyProvider.class,
        ProfileDetailProvider.class
    })
    @PerActivity
    abstract ProfileUserActivity provideProfileUserActivity();
    
    @ContributesAndroidInjector(modules = BuyPointModule.class)
    @PerActivity
    abstract BuyPointActivity provideBuyPointActivity();
    
    @ContributesAndroidInjector(modules = SubscribePremiumModule.class)
    @PerActivity
    abstract SubscribePremiumActivity provideSubscribePremiumActivity();
    
    @ContributesAndroidInjector(modules = UserHistoryProvide.class)
    @PerActivity
    abstract ActivityUserDetail provideActivityUserDetail();
    
    @ContributesAndroidInjector
    @PerActivity
    abstract ActivityDetailNotification provideActivityDetailNotification();
}
