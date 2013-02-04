package com.example;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

public class GameActivity extends Activity implements SensorEventListener {
    SensorManager sensorManager;

    public static float[] accelerometerValues = {0, 0, 0};
    public static float[] orientationValues = {0, 0, 0};

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Opzioni:
                Toast.makeText(this, "TODO: implement options menu! :D", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Reset:
                setContentView(new MainMenu(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] sensorValues = sensorEvent.values;

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
