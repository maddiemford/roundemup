package com.example.timefighter.roundemup

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    var score: Int = 0
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        score = 0
        database= Firebase.database.reference



        //hit option enter on method name to import
            object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer.setText("Timer: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                val player = Player(score, intent.extras?.get("Address").toString())
                database.child("players").child(intent.extras?.get("Username").toString()).setValue(player)
                val intent2=Intent(this@GameActivity, LeaderboardActivity::class.java)
                intent2.putExtra("Username", intent.extras?.get("Username").toString())
                intent2.putExtra("Address", intent.extras?.get("Address").toString())
                this@GameActivity.startActivity(intent2)
            }
        }.start()

    }

    @IgnoreExtraProperties
    data class Player(
        var highScore: Int? = 0,
        var address: String? = ""
    )


    fun increaseScore(view: View?){
        score+=1
        highScoreRecord.text = "Score: " + score
    }

    fun findWinner(view: View?){
        val playerScore = highScoreRecord.text
        var intent = Intent(this, LeaderboardActivity::class.java)
        intent.putExtra("Player's Score", playerScore)
        intent.putExtra("Username", intent.extras?.get("Username").toString())
        intent.putExtra("Address", intent.extras?.get("Address").toString())
        this.startActivity(intent)
    }
}