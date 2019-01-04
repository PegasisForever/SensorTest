package site.pegasis.sensortest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private val l=listener()

    inner class listener:SensorEventListener{
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            tv.text=event.values[0].toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(l,sensor,SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(l)
    }
}
