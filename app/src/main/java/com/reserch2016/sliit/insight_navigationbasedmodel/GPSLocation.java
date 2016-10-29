package com.reserch2016.sliit.insight_navigationbasedmodel;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Ganindu Ranasinghe on 04-Jun-16.
 */
public class GPSLocation extends Service implements LocationListener {

    private final Context lContext;

    // Variable for GPS status
    boolean isGPSEnabled = false;

    // Variable for network status
    boolean isNetworkEnabled = false;

    // Variable for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1/2 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    // declaring a address feild
    String addressField = "";

    public  GPSLocation()
    {
        getLocation();
        lContext = null;
    }
    public GPSLocation(Context context) {
        this.lContext = context;
        getLocation();
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    //////////////////////////////////////////////////////////////////////////
//    public String getAddressField()
//    {
//        return this.addressField;
//    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) lContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            //isNetworkEnabled = locationManager
            //      .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // TODO Have to auto enable GPS
                    // TODO add auto enable mobile data
                }
            } else {
                this.canGetLocation = true;
                // if GPS Enabled get lat/long using GPS Services


                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                               // onLocationChanged(location);
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS() {

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                locationManager.removeUpdates(GPSLocation.this);

            }

        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }


    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }


    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(lContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                lContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

//        double lat = location.getLatitude();
//        double lng = location.getLongitude();
//
//        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
//        StringBuilder builder = new StringBuilder();
//        try {
//            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
//            int maxLines = address.get(0).getMaxAddressLineIndex();
//            for (int i=0; i<maxLines; i++) {
//                String addressStr = address.get(0).getAddressLine(i);
//                builder.append(addressStr);
//                builder.append(" ");
//            }
//
//            String fnialAddress = builder.toString(); //This is the complete address.
//
//            String latituteField=String.valueOf(lat);
//            String  longitudeField = String.valueOf(lng);
//            addressField = fnialAddress; //This will display the final address.
//
//
//
//        } catch (IOException e) {
//            // Handle IOException
//        } catch (NullPointerException e) {
//            // Handle NullPointerException
//        }

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
}
