package com.example;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements SensorEventListener {
    SensorManager sensorManager;

    public static float[] accelerometerValues = {0, 0, 0};
    public static float[] orientationValues = {0, 0, 0};

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometro, SensorManager.SENSOR_DELAY_FASTEST);

        Sensor orientamento = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, orientamento, SensorManager.SENSOR_DELAY_FASTEST);

        Game.getInstance().initialize(this);
        setContentView(new MainMenu(this));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] sensorValues = sensorEvent.values;
        boolean mInitialized = false;
        float mLastX = 0;
        float mLastY = 0;
        float mLastZ = 0;
        float deltaX = 0;
        float deltaY = 0;
        float deltaZ = 0;
        final float NOISE = (float) 2.0;

        switch (sensor.getType()){
            case Sensor.TYPE_ORIENTATION:
                this.orientationValues = sensorValues;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float x = sensorValues[0]; if (x > -0.5 && x < 0.5) x = 0;
                float y = sensorValues[1]; if (y > -0.5 && y < 0.5) y = 0;
                float z = sensorValues[2]; if (z > -0.5 && z < 0.5) z = 0;

                this.accelerometerValues[0] = x;
                this.accelerometerValues[1] = y;
                this.accelerometerValues[2] = z;
                break;

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }
}
