package com.example.timefighter.roundemup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_leaderboard.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        database = Firebase.database.reference

//        val player = Player(12, intent.extras?.get("Address").toString())
//        database.child("players").child(intent.extras?.get("username").toString()).setValue(player)


        val ref = Firebase.database.getReference("players")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var players: LinkedHashMap<String?, Long?> = LinkedHashMap()
                dataSnapshot.children.forEach {
                    players.put(it.key, it.child("highScore").getValue() as Long)
                }

                val sorted : Map<String?, Long?> = players.toList()
                    .sortedBy { (key, value) -> value }
                    .toMap()

                var i = 0
                sorted.keys.forEach {
                    if (i == sorted.size - 1) {
                        textView.text = it + " (" + sorted.get(it) + ")"
                    }
                    if (i == sorted.size - 2) {
                        textView2.text = it + " (" + sorted.get(it) + ")"
                    }
                    if (i == sorted.size - 3) {
                        textView3.text = it + " (" + sorted.get(it) + ")"
                    }
                    i += 1
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    @IgnoreExtraProperties
    data class Player(
        var highScore: Int? = 0,
        var address: String? = ""
    )

    fun launchMap(view: View?) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
