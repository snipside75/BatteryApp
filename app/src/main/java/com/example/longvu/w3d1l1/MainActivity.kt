package com.example.longvu.w3d1l1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.BatteryManager
import android.widget.TextView
import android.content.IntentFilter




class MainActivity : AppCompatActivity() {

    private lateinit var info: TextView
    private var batteryInfo = ""
    private val batteryReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                // Extract health status
                batteryInfo += "Health: "
                batteryInfo += when (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)) {
                    BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                    BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
                    BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                    BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
                    BatteryManager.BATTERY_HEALTH_UNKNOWN -> "Unknown"
                    else -> "Unknown"
                }
                batteryInfo += "\n"

                // Extract level percentage
                batteryInfo += "Level: "
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                batteryInfo += "${(level*100 / scale)}% \n"

                // Extract charging status
                batteryInfo += "Status: "
                batteryInfo += when (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
                    BatteryManager.BATTERY_STATUS_FULL -> "Full"
                    else -> "Unknown"
                }
                batteryInfo += "\n"

                // Extract plugged status
                batteryInfo += "Plugged: "
                batteryInfo += when (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) {
                    BatteryManager.BATTERY_PLUGGED_USB -> "USB"
                    BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                    else -> "Unplugged"
                }
                batteryInfo += "\n"

                // Extract technology
                val technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
                batteryInfo += "Technology: $technology \n"

                // Extract temperature
                val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                batteryInfo += "Temperature: $temperature \n"

                // Extract voltage
                val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
                batteryInfo += "Voltage: ${voltage.toFloat()/1000} \n"
            }

            info.text = batteryInfo
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info = findViewById(R.id.info) as TextView
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryReceiver)
    }
}
