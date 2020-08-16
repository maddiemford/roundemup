package com.example.timefighter.roundemup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        score = 0
    }
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