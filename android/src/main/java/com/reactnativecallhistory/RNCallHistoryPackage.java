package com.reactnativecallhistory;

import android.app.Activity;

import java.util.*;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class RNCallHistoryModule implements ReactPackage {
    private RNCallHistoryModule mModuleInstance;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      mModuleInstance = new RNCallHistoryModule(reactContext);
      return Arrays.<NativeModule>asList(mModuleInstance);
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

}
