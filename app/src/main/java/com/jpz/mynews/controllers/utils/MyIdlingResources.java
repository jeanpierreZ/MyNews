package com.jpz.mynews.controllers.utils;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.jpz.mynews.BuildConfig;

public class MyIdlingResources {
    // For testing Espresso

    private CountingIdlingResource espressoTestIdlingResource;

    @VisibleForTesting
    public CountingIdlingResource getEspressoIdlingResource() {
        return espressoTestIdlingResource;
    }

    public void configureEspressoIdlingResource(){
        espressoTestIdlingResource = new CountingIdlingResource("UI_Call");
    }

    public void incrementIdleResource(){
        if (BuildConfig.DEBUG)
            espressoTestIdlingResource.increment();
    }

    public void decrementIdleResource(){
        if (BuildConfig.DEBUG)
            espressoTestIdlingResource.decrement();
    }
}