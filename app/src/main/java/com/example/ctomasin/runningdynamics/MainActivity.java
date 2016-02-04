package com.example.ctomasin.runningdynamics;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Sensor mSensor;
    private SensorManager mSensorManager;
//    private CSVWriter mCSVWriter;

    private TextView[] mTextValues = new TextView[LinearAcceleration.AXIS_NUM];
    private ProgressBar[] mProgressValues = new ProgressBar[LinearAcceleration.AXIS_NUM];
    private AccTrack mAccTrack;

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] linear_accel = LinearAcceleration.shared().linearAcceleration(event.values);
            if(LinearAcceleration.shared().hasValues()) {
                updateUI(linear_accel);
                store(linear_accel,event.timestamp);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        protected final void updateUI(float[] linear_accel) {
            for(int i = 0; i < linear_accel.length && i < mTextValues.length; ++i) {
                float v = linear_accel[i];
                mTextValues[i].setText(String.format("%.3f", v));
                mProgressValues[i].setProgress(50 + Math.round(v / (2 * LinearAcceleration.G_VAL) * 100));
            }
        }

        protected final void store(float[] linear_accel,long timestamp) {
            // TODO: store the sensor event
            // unix_timestamp,accel_X,accel_Y,accel_Z
            // 123456,1.34556,1.3333,-0.15646
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextValues[0] = (TextView)findViewById(R.id.tvwXValue);
        mTextValues[1] = (TextView)findViewById(R.id.tvwYValue);
        mTextValues[2] = (TextView)findViewById(R.id.tvwZValue);

        mProgressValues[0] = (ProgressBar)findViewById(R.id.pbXValue);
        mProgressValues[1] = (ProgressBar)findViewById(R.id.pbYValue);
        mProgressValues[2] = (ProgressBar)findViewById(R.id.pbZValue);

        mAccTrack = findViewById(R.id.svTrack);

//        mCSVWriter = openStoreFile();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Log.d("sensors", mSensorManager.getSensorList(Sensor.TYPE_ALL).toString());
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(null == mSensor) mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (null == mSensor) {
            TextView status = (TextView) findViewById(R.id.tvwStatusValue);
            status.setText(R.string.sensor_not_available);
        } else {
            TextView status = (TextView) findViewById(R.id.tvwStatusValue);
            status.setText(mSensor.getVendor() + mSensor.getName());
            mSensorManager.registerListener(mSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
            LinearAcceleration.shared().resetGravity();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != mSensorManager) mSensorManager.unregisterListener(mSensorListener);
        TextView status = (TextView)findViewById(R.id.tvwStatusValue);
        status.setText(R.string.val_undef);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    protected final CSVWriter openStoreFile() {
//        // TODO: aprire il CSVWriter
//
//    }
}
