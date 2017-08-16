package com.reactnativecallhistory;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

import java.lang.StringBuffer;
import java.util.Date;

import org.json.*;

public class RNCallHistoryModule extends ReactContextBaseJavaModule {
    
    private static final String TAG = RNCallHistoryModule.class.getSimpleName();

    private Context context;

    // set the activity - pulled in from Main
    public RNCallHistoryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "CallHistory";
    }

    @ReactMethod
    public void list(Callback callBack, Callback errorCallBack) {
        StringBuffer sb = new StringBuffer();
        
        Cursor cursor = this.context.getContentResolver().query(
            CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        
        if (cursor == null) {
            callBack.invoke("[]");
            return;
        }

        int idxNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int idxName = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int idxType = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int idxDate = cursor.getColumnIndex(CallLog.Calls.DATE);
        int idxDuration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        JSONArray callArray = new JSONArray();
        
        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(idxNumber);
            String contactName = cursor.getString(idxName);
            String callDuration = cursor.getString(idxDuration);
            String callDate = cursor.getString(idxDate);
            Date callDateTime = new Date(Long.valueOf(callDate));
            
            String callType = null;
            String strCallType = cursor.getString(idxType);
            int intCallType = Integer.parseInt(strCallType);

            switch (intCallType) {

                case CallLog.Calls.INCOMING_TYPE:
                    callType = "INCOMING_TYPE";
                    break;

                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "OUTGOING_TYPE";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    callType = "MISSED_TYPE";
                    break;

                // below three types are for api > level 24
                // case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                //     callType = "ANSWERED_EXTERNALLY_TYPE";
                //     break;

                // case CallLog.Calls.BLOCKED_TYPE:
                //     callType = "BLOCKED_TYPE";
                //     break;

                // case CallLog.Calls.REJECTED_TYPE:
                //     callType = "REJECTED_TYPE";
                //     break;

                case CallLog.Calls.VOICEMAIL_TYPE:
                    callType = "VOICEMAIL_TYPE";
                    break;
            }

            JSONObject callObj = new JSONObject();
            try {
                callObj.put("phoneNumber", phoneNumber);
                callObj.put("name", contactName);
                callObj.put("callDuration", callDuration);
                callObj.put("callDate", callDate);
                callObj.put("callDateTime", callDateTime);
                callObj.put("callType", callType);
                callArray.put(callObj);
            } catch (JSONException e) {
                errorCallBack.invoke(e.getMessage());
            }
        }
        cursor.close();
        callBack.invoke(callArray.toString());
    }
}