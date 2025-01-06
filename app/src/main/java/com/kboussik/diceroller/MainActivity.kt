package com.kboussik.diceroller

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var selectedNumberText: TextView
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBar: SeekBar = findViewById(R.id.seekBar)
        selectedNumberText = findViewById(R.id.selectedNumberText)
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)

        updateSelectedNumber(seekBar.progress + 2)
        rollDice(seekBar.progress + 2)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selectedNumber = progress + 2
                updateSelectedNumber(selectedNumber)
                rollDice(selectedNumber)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSelectedNumber(number: Int) {
        selectedNumberText.text = "Nombre sélectionné: $number"
    }

    private fun celebrateWin() {
        val scaleX1 = ObjectAnimator.ofFloat(imageView1, View.SCALE_X, 1f, 1.2f, 1f)
        val scaleY1 = ObjectAnimator.ofFloat(imageView1, View.SCALE_Y, 1f, 1.2f, 1f)
        val rotation1 = ObjectAnimator.ofFloat(imageView1, View.ROTATION, 0f, 360f)

        val scaleX2 = ObjectAnimator.ofFloat(imageView2, View.SCALE_X, 1f, 1.2f, 1f)
        val scaleY2 = ObjectAnimator.ofFloat(imageView2, View.SCALE_Y, 1f, 1.2f, 1f)
        val rotation2 = ObjectAnimator.ofFloat(imageView2, View.ROTATION, 0f, 360f)

        AnimatorSet().apply {
            playTogether(scaleX1, scaleY1, rotation1, scaleX2, scaleY2, rotation2)
            duration = 1000
            start()
        }
    }

    private fun rollDice(selectedNumber: Int) {
        val dice1 = Dice(6)
        val dice2 = Dice(6)
        val diceRoll1 = dice1.roll()
        val diceRoll2 = dice2.roll()

        val drawableResource1 = when (diceRoll1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        val drawableResource2 = when (diceRoll2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        imageView1.setImageResource(drawableResource1)
        imageView2.setImageResource(drawableResource2)

        val total = diceRoll1 + diceRoll2
        if (total == selectedNumber) {
            celebrateWin()
            Toast.makeText(this, "Bravo!", Toast.LENGTH_SHORT).show()
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}