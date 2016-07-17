package com.acadgild.balu.acd_an_session_20_assignment_1_main;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by BALU on 6/20/2016.
 */
public class MyService extends Service implements SensorEventListener
{
    private static final float SHAKE_THRESHOLD = 3.25f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    long  firstShakeTime = System.currentTimeMillis();;
    private SensorManager mSensorMgr;
    Integer count = 0;
    public MyService() {
    }
    SensorManager mSensorManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("entered","onCreate");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("entered","onDestroy");
        mSensorManager.unregisterListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("entered","onStartCommand");
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("entered","onBind");
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;

                if(count == 0)
                {
                    firstShakeTime = System.currentTimeMillis();
                }
                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    Log.d("shaked", "Acceleration is " + acceleration + "m/s^2");
                    if(firstShakeTime - System.currentTimeMillis() < 2000) {
                        count++;
                    }
                    else
                    {
                        count = 0;
                    }
                }
                if(count == 3)
                {
                    count = 0;
                    Log.d("thrice", "Acceleration is " + acceleration + "m/s^2");
                    Intent intent = new Intent("SHAKED");
                    sendBroadcast(intent);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
