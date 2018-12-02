package com.luckyzhangx.corejavalibs.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that a feature is incubating. This means that the feature is currently a work-in-progress and may
 * change at any time.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Incubating {
}
