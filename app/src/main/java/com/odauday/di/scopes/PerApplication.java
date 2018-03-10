package com.odauday.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by infamouSs on 2/27/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
    
}
