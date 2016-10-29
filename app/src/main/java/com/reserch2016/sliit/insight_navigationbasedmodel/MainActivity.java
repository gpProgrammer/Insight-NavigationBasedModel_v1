package com.reserch2016.sliit.insight_navigationbasedmodel;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

// comment
// second comment
public class MainActivity extends Activity implements LocationListener, TextToSpeech.OnInitListener {

    private LocationManager locationManager;
    private String provider;

    private GestureDetector mGestureDetector;
    private GestureDetectorCompat gestureDetector;

    private TextToSpeech tts;
    // This code can be any value you want, its just a checksum.
    private static final int MY_DATA_CHECK_CODE = 1234;

    String addressField = "not available";

    Location location = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the gestures detect
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        mGestureDetector = new GestureDetector(this, android_gesture_detector);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        location = locationManager.getLastKnownLocation(provider);

        // Fire off an intent to check if a TTS engine is installed
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);


        Thread logoTimer = new Thread() {
            public void run() {
                try {

                    sleep(2500);
                    speakWords("Welcome! to navigation services");
                    sleep(4500);
                    speakWords("Long press to get help");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        logoTimer.start();


        // show location button click event
//        btnShowLocation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // create class object
//                gps = new GPSLocation(MainActivity.this);
//
//                // check if GPS enabled
//                if(gps.canGetLocation()){
//
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//
//                    // \n is for new line
//                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                }else{
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps.showSettingsAlert();
//                }
//
//            }
//        });

//        btnGetNearest.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, GetNearestActivity.class));
//            }
//        });
    } // end of onCreate method

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            Criteria criteria = new Criteria();
//            provider = locationManager.getBestProvider(criteria, false);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                return;
//            }
//            location = locationManager.getLastKnownLocation(provider);
//
//            locationManager.requestLocationUpdates(provider, 0,0, this);
//
//            tts = new TextToSpeech(this, this);
//            Thread logoTimer = new Thread() {
//                public void run() {
//                    try {
//                        // Sleep to give a time to initialize the new tts instance
//                        sleep(1000);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            logoTimer.start();
//
//        } catch (SecurityException ex) {
//            speakWords("Permission denied on Resume");
//
//        }

    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        try {
//
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            Criteria criteria = new Criteria();
//            provider = locationManager.getBestProvider(criteria, false);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                return;
//            }
//            location = locationManager.getLastKnownLocation(provider);
//
//            locationManager.removeUpdates(this);
//            //speakWords("Pause navigation services");
//           // tts.stop();
//
//        } catch (SecurityException ex) {
//            speakWords("Permission denied on Pause");
//
//        }

    }

    /**
     * Called when the activity is no longer visible.
     */
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

    /**
     * Called just before the activity is destroyed.
     */
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


    // deactivate back button
    @Override
    public void onBackPressed() {

        speakWords("Exit!");
       // onDestroy();
        moveTaskToBack(true);

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

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.

            String latituteField = String.valueOf(lat);
            String longitudeField = String.valueOf(lng);
            addressField = fnialAddress; //This will display the final address.


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Executed when a new TTS is instantiated. Check the whether Language is support or not.
     *
     * @param status
     */
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
            Intent intent = new Intent(MainActivity.this, WalkingDirection.class);
            startActivity(intent);

            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapUp");



            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            if (location != null) {
                //System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {

////                speakWords("Location not available");
//                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                Criteria criteria = new Criteria();
//
//                provider = locationManager.getBestProvider(criteria, false);
//                try {
//                    location = locationManager.getLastKnownLocation(provider);
//                    onLocationChanged(location);
//                } catch (SecurityException ex) {
//                    speakWords("Permission denied");
//
//                }

                speakWords("GPS not available");
            }

            speakWords("Your current location is, " + addressField);
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Thread logoTimer = new Thread() {
                public void run() {
                    try {

                        Log.d("Gesture ", " onLongPress");
                        sleep(3000);
                        speakWords("Navigation service, help.");
                        sleep(3000);
                        speakWords("Double tap, get your current location.");
                        sleep(3000);
                        speakWords("Swipe right, nearest place service.");
                        sleep(3000);
                        speakWords("Swipe left, public transport service.");
                        sleep(3000);
                        speakWords("long press, to get help on any service.");


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
                tts.shutdown();
                Intent intent = new Intent(MainActivity.this, GetNearestActivity.class);
                startActivity(intent);

            }
            if (e1.getX() > e2.getX()) {

                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");

                Intent intent = new Intent(MainActivity.this, GetBusHaltsActivity.class);
                startActivity(intent);

            }
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            return true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);


    }
}
