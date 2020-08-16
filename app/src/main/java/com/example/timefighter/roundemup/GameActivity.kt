package com.example.timefighter.roundemup

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
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
        database = Firebase.database.reference


        //0,0 = g1
        //0,1 = g2--
        //0,2 = g3 --
        //1,0 = g2
        //2,0 = g1
        //1,1 = g3
        //1,2 = g3
        //2,1 = g2--
        //2,2 = g1


        //hit option enter on method name to import
        object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer.setText("Timer: " + millisUntilFinished / 1000)
                val R = (0..2).random()
                if (R == 0) {
                    imageView12.visibility = View.INVISIBLE
                    imageView13.visibility = View.INVISIBLE
                    imageView.visibility = View.VISIBLE
                    imageView11.visibility = View.VISIBLE
                    imageView8.visibility = View.INVISIBLE
                    imageView7.visibility = View.VISIBLE
                    imageView10.visibility = View.VISIBLE

                }else if(R == 1){
                    imageView11.visibility = View.INVISIBLE
                    imageView12.visibility = View.INVISIBLE
                    imageView4.visibility = View.VISIBLE
                    imageView2.visibility = View.VISIBLE
                    imageView8.visibility = View.VISIBLE
                    imageView13.visibility = View.VISIBLE
                    imageView5.visibility = View.INVISIBLE

                }else{
                    imageView13.visibility = View.INVISIBLE
                    imageView11.visibility = View.INVISIBLE
                    imageView9.visibility = View.VISIBLE
                    imageView5.visibility = View.VISIBLE
                    imageView6.visibility = View.VISIBLE
                    imageView12.visibility = View.VISIBLE
                    imageView4.visibility = View.INVISIBLE
                }
            }

            override fun onFinish() {
                val player = Player(score, intent.extras?.get("Address").toString())
                database.child("players").child(intent.extras?.get("Username").toString())
                    .setValue(player)
                val intent2 = Intent(this@GameActivity, LeaderboardActivity::class.java)
                intent2.putExtra("Username", intent.extras?.get("Username").toString())
                intent2.putExtra("Address", intent.extras?.get("Address").toString())
               intent2.putExtra("Score", score)
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
        view?.visibility=View.INVISIBLE
    }
    fun decreaseScore(view: View?){
        score-=1
        highScoreRecord.text = "Score: " + score
        view?.visibility=View.INVISIBLE
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