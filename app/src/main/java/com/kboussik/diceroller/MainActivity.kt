package com.kboussik.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputTextNumber : TextView = findViewById(R.id.editTextNumber)
        val rollButton: Button = findViewById(R.id.button)

        //enable button when input is not empty
        inputTextNumber.addTextChangedListener {
            rollButton.isEnabled = it.toString().isNotEmpty()
        }

        rollButton.setOnClickListener {
            if (rollDice() == inputTextNumber.text.toString().toInt()) {
                Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun rollDice():Int {
        // Create new Dice object with 6 sides and roll it
        val dice1 = Dice(6)
        val dice2 = Dice(6)
        val diceRoll1 = dice1.roll()
        val diceRoll2 = dice2.roll()

        // Update the screen with the dice roll
        val resultTextView1: TextView = findViewById(R.id.textView1)
        resultTextView1.text = diceRoll1.toString()

        val resultTextView2: TextView = findViewById(R.id.textView2)
        resultTextView2.text = diceRoll2.toString()

        return diceRoll1+diceRoll2
    }
}
    class Dice(private val numSides: Int) {

        /**
         * Do a random dice roll and return the result.
         */
        fun roll(): Int {
            return (1..numSides).random()
        }
    }