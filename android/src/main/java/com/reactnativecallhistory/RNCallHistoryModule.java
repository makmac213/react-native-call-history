package com.reactnativecallhistory;


import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.provider.CallLog;
import android.provider.CallLog.Calls;
import java.lang.StringBuffer;
import android.database.Cursor;
import java.util.Date;
import android.content.Context;
import org.json.*;

public class RNCallHistoryModule extends ReactContextBaseJavaModule {
    
    private ReactApplicationContext reactContext;

    // set the activity - pulled in from Main
    public RNCallHistoryModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "CallHistory";
    }

    @ReactMethod
    public void list(Callback callBack) {
        StringBuffer sb = new StringBuffer();
        Cursor cursor = this.context.getContextResolver()
                            .query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        if (cursor == null) {
            callBack.invoke("[]");
            return;
        }

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        JSONArray callArray = new JSONArray();
        while (cursor.moveToNext()) {
            JSONObject callObj = new JSONObject();
            try {
                callObj.put("phoneNumber", phoneNumber);
                callObj.put("name", contactName);
                callArray.put(callObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        callBack.invoke(callArray.toString());
    }
}