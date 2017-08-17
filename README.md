
react-native-call-history
-------------------------
Retrieves call logs (Android only).

usage:

    import CallHistory from 'react-native-call-history';
    
    CallHistory.list(
    	(history) => {
    		AsyncStorage.setItem("callHistory", history);
    	},
    	(error) => {
    		console.warn(error);
    	}
    );

**Installation**
$ npm install react-native-call-history --save

$ react-native link

**Permission**
Add permission to android/app/src/mainAndroidMenifest.xmlfile


    <uses-permission android:name="android.permission.READ_CALL_LOG"></uses-permission>

