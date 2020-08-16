package com.example.timefighter.roundemup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchGame(view: View?){
        val userName = userEditText.text
        //static
        val address = AddressEditText.text
        // var changes
        var intent = Intent(this, GameActivity::class.java)
        intent.putExtra("Username", userName)
        intent.putExtra("Address", address)
        this.startActivity(intent)
    }
}
