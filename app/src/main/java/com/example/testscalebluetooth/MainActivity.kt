package com.example.testscalebluetooth

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT:Int =1
    private val REQUEST_CODE_DISCOVERABLE_BT: Int =2
    //bluetooth adapter
    lateinit var bAdapter:BluetoothAdapter

     @SuppressLint("SetTextI18n")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init bluetooth adapter
         bAdapter = BluetoothAdapter.getDefaultAdapter()
         //check if bluetooth is on/off
         if(bAdapter==null){
             name_bt.text = "Bluetooth is not available"
         }
         else name_bt.text = "Bluetooth is available"

         if(bAdapter.isEnabled)  search_bt.setImageResource(R.drawable.ic_action_search)
         else search_bt.setImageResource(R.drawable.ic_action_off)
// Turn on bluetooth
         turn_on.setOnClickListener{
             if(bAdapter.isEnabled){
                 //already enabled
                 Toast.makeText(this, "already on", Toast.LENGTH_LONG).show()
             }
             else {
                 //turn on bluetooth
                 val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                 startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
             }
         }
// Turn off Bluetooth
         turn_off.setOnClickListener{
             if(!bAdapter.isEnabled){
                 //already off
                 Toast.makeText(this, "already off", Toast.LENGTH_LONG).show()
             }
             else {
                 bAdapter.disable()
                 search_bt.setImageResource(R.drawable.ic_action_off)
                 Toast.makeText(this, "Bluetooth Turned off", Toast.LENGTH_LONG).show()
             }
         }
// Discover Bluetooth
         discoverable.setOnClickListener{
             if(!bAdapter.isDiscovering){
                 Toast.makeText(this, "Making Your device discoverable", Toast.LENGTH_LONG).show()
                 val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                 startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
             }
         }
 //get list of paired devices
         get_paired.setOnClickListener{
             if(bAdapter.isEnabled){
                 pairedTV.text = "Paired Devices"
                 //get list of paired devices
                 val devices = bAdapter.bondedDevices
                 for (device in devices){
                     val deviceName = device.name
                     val deviceAddress = device
                     pairedTV.append("\nDevice: $deviceName , $device")
                 }
             }
             else{
                 Toast.makeText(this, "Turn on bluetooth first", Toast.LENGTH_LONG).show()
             }
         }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK){
                    search_bt.setImageResource(R.drawable.ic_action_search)
                    Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_LONG).show()
                }
            else{
                    //search_bt.setImageResource(R.drawable.ic_action_off)
                    Toast.makeText(this, "Could not connect bluetooth", Toast.LENGTH_LONG).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}