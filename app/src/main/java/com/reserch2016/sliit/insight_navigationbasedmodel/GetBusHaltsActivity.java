package com.reserch2016.sliit.insight_navigationbasedmodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class GetBusHaltsActivity extends Activity implements TextToSpeech.OnInitListener {

    private static final String SERVICE_URL = "http://localhost:8085/navigation/webapi/myresource";

    private static final String TAG = "GetNearestActivity";

    private TextToSpeech tts;
    // This code can be any value you want, its just a checksum.
    private static final int MY_DATA_CHECK_CODE = 1234;

    ViewGroup viewGroup = null;

    int readCount = -2;

    // GPSLocation class
    GPSLocation gps;

    private GestureDetector mGestureDetector;
    private GestureDetectorCompat gestureDetector;

    static ArrayList<String> readList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bus_halts);

        // Android Gestures
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        mGestureDetector = new GestureDetector(this, android_gesture_detector);

        // Fire off an intent to check if a TTS engine is installed
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        readList = new ArrayList<String>();

        Thread logoTimer = new Thread() {
            public void run() {
                try {

                    sleep(1000);
                    speakWords("Activated, public transport service ");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        logoTimer.start();

    } // end of onCreate method

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);


    }
    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        //speakWords("on Start public transport service");
        //Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        tts = new TextToSpeech(this, this);
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    // Sleep to give a time to initialize the new tts instance
                    sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        logoTimer.start();

    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();

    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        // tts shutdown!
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();

    }
    /**
     * Called when the activity restarts after stopping it
     */
    @Override
    protected void onRestart() {

        tts = new TextToSpeech(this, this);
        Thread logoTimer = new Thread() {
            public void run() {
                try {

                    sleep(1000);
                    //speakWords("Restart navigation services");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        logoTimer.start();
        super.onRestart();


    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // tts shutdown!
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    // back button
    public void onBackPressed() {
        speakWords("Back");
        Intent intent = new Intent(GetBusHaltsActivity.this, MainActivity.class);
        startActivity(intent);

    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    /**
     * This is the callback from the TTS engine check, if a TTS is installed we
     * create a new TTS instance (which in turn calls onInit), if not then we will
     * create an intent to go off and install a TTS engine
     *
     * @param requestCode int Request code returned from the check for TTS engine.
     * @param resultCode  int Result code returned from the check for TTS engine.
     * @param data        Intent Intent returned from the TTS check.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = new TextToSpeech(this, this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
    private void speakWords(String speech) {
// speak straight away
        if (tts != null) {
            tts.setSpeechRate(1);
            //tts.speak(speech, TextToSpeech.QUEUE_ADD, null);

            // Drop all pending entries in the playback queue.
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

        }
    }



    // Android_Gesture_Detector Class
    class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Gesture ", " onDown");

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapConfirmed");
            return true;
        }

        String selected = null;
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapUp");

            if(selected != null)
            {
//                if(selected == "Welivita")
//                {
//                    //TODO give route
//                }

                if("Welivita bus stop, Kaduwela".equals(selected)) {
                    Thread logoTimer = new Thread() {
                        public void run() {
                            try {

                                sleep(1000);
                                speakWords("You have selected, " + selected + " calculating route.");

                                speakWords("Toward S l i i t open area foot path");
                                sleep(1000);
                                speakWords("Turn left");
                                speakWords("Turn right onto S l i i t  entrance ");
                                sleep(1000);
                                speakWords("Turn left");
                                speakWords("Turn right onto S l i i t  entrance");
                                speakWords("Turn right onto 177, Kaduwela kollupitiya road ");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    logoTimer.start();

                }
                else if("Gemunupura Bus Stop, Malabe".equals(selected))
                {
                    Thread logoTimer = new Thread() {
                        public void run() {
                            try {

                                sleep(1000);
                                speakWords("You have selected, " + selected + " calculating route.");

                                speakWords("Toward S l i i t open area foot path");
                                sleep(1000);
                                speakWords("Turn left");
                                speakWords("Turn right onto S l i i t  entrance ");
                                sleep(1000);
                                speakWords("Turn left");
                                speakWords("Turn right onto S l i i t  entrance");
                                speakWords("Turn right onto Kaduwela kollupitiya road");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    logoTimer.start();

                }


                else if("Campus Bus Stop".equals(selected) )
                {
                    Thread logoTimer = new Thread() {
                        public void run() {
                            try {

                                sleep(1000);
                                speakWords("You have selected, " + selected + " calculating route.");

                                speakWords("Toward S l i i t open area foot path");
                                sleep(1000);
                                speakWords("Turn left");
                                speakWords("Turn right onto S l i i t  entrance ");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    logoTimer.start();

                }
                else
                {
                    speakWords("Sorry, route not founded");
                }

            }

            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTap");
            selected = null;


            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTapEvent");
            selected = null;
            gps = new GPSLocation(GetBusHaltsActivity.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                try {
                    retrieveSampleData(viewGroup);
                    selected = null;
                } catch (Exception e1) {
                    speakWords("Data not available");
                }

                readCount = -1;
            } else {
                //TODO activate gps automaticall
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();

            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            Log.d("Gesture ", " onLongPress");
            Thread logoTimer = new Thread() {
                public void run() {
                    try {

                        Log.d("Gesture ", " onLongPress");
                        sleep(3000);
                        speakWords("public transport service , help.");
                        sleep(3000);
                        speakWords("Double tap, get nearest bus halts");
                        sleep(3000);
                        speakWords("Swipe down, Swipe up, access nearest list.");
                        sleep(3000);
                        speakWords("Single Tap, select");
                        sleep(3000);
                        speakWords("Device back button, back to application home page.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            logoTimer.start();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("Gesture ", " onScroll");
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", " Scroll Down");
            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", " Scroll Up");
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() < e2.getX()) {

                Log.d("Gesture ", "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");

            }
            if (e1.getX() > e2.getX()) {

                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");

//                onDestroy();
//
//                gps = new GPSLocation(GetBusHaltsActivity.this);
//
//                // check if GPS enabled
//                if (gps.canGetLocation()) {
//
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//                    retrieveSampleData(viewGroup);
//                    // \n is for new line
//                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                } else {
//                    //TODO activate gps automaticall
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps.showSettingsAlert();
//
//                }

                //postData(viewGroup);
            }
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");

                if (e1.getAction() == MotionEvent.ACTION_DOWN) {

                    readCount++;

                    if (readCount >= 0 && readCount < readList.size()) {

                        speakWords(readList.get(readCount));
                        selected = readList.get(readCount);

                    } else {
                        speakWords("Double tap to get nearest bus stops");
                    }

                }

            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");

                if (e1.getAction() == MotionEvent.ACTION_DOWN) {

                    readCount--;

                    if (readCount >= 0 && readCount < readList.size()) {


                        speakWords(readList.get(readCount));
                        selected = readList.get(readCount);

                    }else {
                        speakWords("Double tap to get nearest bus stops");
                    }

//                    if (readCount > readList.size()) {
//                        speakWords("You have access all list, double tap to get bus halts again ");
//
//                    }
//                    if (readCount < -1) {
//                        speakWords("You have access all list, double tap to get bus halts again ");
//
//                    }
                }
            }
            return true;
        }
    }


// call this for get method
    public void retrieveSampleData(View vw) {

        // create class object
        GPSLocation gps = new GPSLocation(GetBusHaltsActivity.this);
        double latitude = 0.0;
        double longitude = 0.0;
        // check if GPS enabled
        if (gps.canGetLocation()) {

             latitude = gps.getLatitude();
             longitude = gps.getLongitude();
        }

        String getURL = SERVICE_URL + "/getNearestbusHalts/"+longitude+"/"+latitude+"/"; // put suitable path and edit this in web service also

        WebServiceTask wst = new WebServiceTask(WebServiceTask.GET_TASK, this, "Getting data...");
//        wst.addNameValuePair("CurrentLongitude", longitude + "");
//        wst.addNameValuePair("CurrentLatitude",  latitude + "");

        wst.execute(new String[]{getURL});

    }

    public void postData(View vw) {

        String postURL = SERVICE_URL + "/postLon_Lat"; // put suitable path and edit this in web service also

        // create class object
        GPSLocation gps = new GPSLocation(GetBusHaltsActivity.this);
        WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this, "Posting data...");

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            wst.addNameValuePair("CurrentLongitude", longitude + "");
            wst.addNameValuePair("CurrentLatitude",  latitude + "");

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //TODO Strat GPS and Mobile data automatically
            gps.showSettingsAlert();
        }

        // the passed String is the URL we will POST to
        wst.execute(new String[]{postURL});
    }

    // To handle response..
    public void handleResponse(String response) {

        try {

            Gson googleJson = new Gson();
            ArrayList jsonObjList = googleJson.fromJson(response, ArrayList.class);
            if(jsonObjList.size() == 0)
            {
                speakWords("Sorry , There are no any bus halts within 500 meters ");
            }
            Toast.makeText(GetBusHaltsActivity.this, "response sise = " + jsonObjList.size(),
                    Toast.LENGTH_LONG).show();

            for (int i = 0; i < jsonObjList.size(); i++) {
                readList.add(jsonObjList.get(i).toString());
            }


        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    private class WebServiceTask extends AsyncTask<String, Integer, String> {

        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;

        private static final String TAG = "WebServiceTask";

        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;

        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;

        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Processing..."; // TODO add Text-to-Speach

        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        // constructor
        public WebServiceTask(int taskType, Context mContext, String processMessage) {

            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }

        private ProgressDialog pDlg = null;

        private void showProgressDialog() {

            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }



        public void addNameValuePair(String name, String value) {

            params.add(new BasicNameValuePair(name, value));
        }

        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {

            HttpParams htpp = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

            return htpp;
        }

        private HttpResponse doResponse(String url) {

            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());

            HttpResponse response = null;

            try {
                switch (taskType) {
                    // TODO for POST request
                    case POST_TASK:
                        HttpPost httppost = new HttpPost(url);
                        // Add parameters
                        httppost.setEntity(new UrlEncodedFormEntity(params));

                        response = httpclient.execute(httppost);
                        break;
                    case GET_TASK:
                        // create HttpGet object from url
                        HttpGet httpget = new HttpGet(url);
                        response = httpclient.execute(httpget);
                        break;
                }
            } catch (Exception e) {

                Log.e(TAG, e.getLocalizedMessage(), e);

            }

            return response;
        }

        private String inputStreamToString(InputStream is) {

            String line = "";
            StringBuilder total = new StringBuilder();

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }

            // Return full string
            return total.toString();
        }

        /**
         * AsyncTask class methods
         * occurs communication with the web service
         * @param urls
         * @return
         */
        @Override
        protected String doInBackground(String... urls) {

            String url = urls[0];
            String result = null;

            HttpResponse response = doResponse(url);

            if (response == null) {
                return result;
            } else {

                try {

                    result = inputStreamToString(response.getEntity().getContent());

                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }

            }

            return result;

        }
        /**
         * AsyncTask class methods
         * use to prepare for the background process - display a progress dialog.
         */
        @Override
        protected void onPreExecute() {

            showProgressDialog(); //  display a progress dialog.
        }

        /**
         * AsyncTask class methods
         * use to do any required clean-up after the background process is complete - remove the progress dialog.
         * @param response
         */
        @Override
        protected void onPostExecute(String response) {

           // speakWords("Wait a moment ");
            speakWords("Downloading data. ");

            handleResponse(response);
            pDlg.dismiss(); //  remove the progress dialog.


        }
    }

}
