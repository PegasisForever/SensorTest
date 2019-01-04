package site.pegasis.sensortest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var intentFilter: IntentFilter

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra("level", 0) //电池电量，数字
            val plugged = intent.getIntExtra("plugged", 0) //充电类型
            val voltage = intent.getIntExtra("voltage", 0) //电+池伏数
            val temperature = intent.getIntExtra("temperature", 0)//电池温度
            val acString=when (plugged) {
                BatteryManager.BATTERY_PLUGGED_AC ->"交流电充电"
                BatteryManager.BATTERY_PLUGGED_USB ->"usb充电"
                else -> ""
            }
            val s = ("电量:" + level + "%\n"
                    + "电压:" + voltage + "mv\n"
                    + "温度:" + temperature/10 + "℃\n"
                    + acString + "\n")

            runOnUiThread {
                tv.text=s
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}
