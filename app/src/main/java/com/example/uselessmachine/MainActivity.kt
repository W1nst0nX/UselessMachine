package com.example.uselessmachine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // The one function that View.OnCLickListener has is onClick(v: View!)
        // this lambda below is implementing that one function on click without really mentioning it
        // explicitly. The one parameter is referenced by "it". So to access the view, I can use "it"
        button_main_lookBusy.setOnClickListener {
            Toast.makeText( this, "Hello, this is the text on the button ${(it as Button).text.toString()}",
                Toast.LENGTH_SHORT).show()
        }

        // to listen to a switch, you can use the OnCheckChangedListener
        switch_main_useless.setOnCheckedChangeListener { compoundButton, isCheck ->
            // 1. toast the status of the button (checked, or not checked)
            if(isCheck) {
                Toast.makeText(this, "The switch is on", Toast.LENGTH_SHORT).show()

                var randomTimeSwitch = (6000..12000).random().toInt().toLong()
                //Making an anonymous inner class saying this object extends CountDownTimer
                val uncheckTimer = object : CountDownTimer(randomTimeSwitch, 1000) {

                    override fun onTick(p0: Long) {
                        if(!isCheck) {
                            cancel()
                        }
                    }

                    override fun onFinish() {
                        switch_main_useless.toggle()
                    }
                }
                uncheckTimer.start()
            }
            else {
                Toast.makeText( this, "The switch is off", Toast.LENGTH_SHORT).show()
            }
        }

        //Self destruct button
        button_main_selfDestruct.setOnClickListener {
            button_main_selfDestruct.isEnabled = false
            val uncheckTimer = object : CountDownTimer(10000, 1000) {

                var ticFlash = 0
                fun changeColor(){
                    layout_main.setBackgroundColor(Color.rgb((0..255).random(), (0..255).random(), (0..255).random()))
                }
                override fun onTick(p0: Long) {
                    button_main_selfDestruct.text = (p0/1000).toInt().toString()
                    changeColor()
                    ticFlash++
                    when (ticFlash) {
                        in 2..4 -> {
                            Handler().postDelayed({changeColor()}, 500)
                        }
                        in 5..6 -> {
                            Handler().postDelayed({changeColor()}, 333)
                            Handler().postDelayed({changeColor()}, 666)
                        }
                        in 7..8 -> {
                            Handler().postDelayed({ changeColor() }, 250)
                            Handler().postDelayed({ changeColor() }, 500)
                            Handler().postDelayed({ changeColor() }, 750)
                        }
                        in 9..10 -> {
                            Handler().postDelayed({ changeColor() }, 200)
                            Handler().postDelayed({ changeColor() }, 400)
                            Handler().postDelayed({ changeColor() }, 600)
                            Handler().postDelayed({ changeColor() }, 800)
                        }
                    }
                }

                override fun onFinish() {
                    finish()
                }
            }
            uncheckTimer.start()
        }

        // Look Busy Button
        button_main_lookBusy.setOnClickListener {
            button_main_selfDestruct.visibility = GONE
            button_main_lookBusy.visibility = GONE
            switch_main_useless.visibility = GONE
            progressBar_main_progress.visibility = VISIBLE
            text_main_progress.visibility = VISIBLE

            val progressTimer = object : CountDownTimer(10000, 1000) {

                override fun onTick(p0: Long) {
                    progressBar_main_progress.progress = ((10 - p0/1000)*10).toInt()
                    text_main_progress.text = "Loading document " + ((10 - p0/1000)*10).toInt().toString() + "% complete"
                }

                override fun onFinish() {
                    button_main_selfDestruct.visibility = VISIBLE
                    button_main_lookBusy.visibility = VISIBLE
                    switch_main_useless.visibility = VISIBLE
                    progressBar_main_progress.visibility = GONE
                    text_main_progress.visibility = GONE
                }

            }
            progressTimer.start()
        }
    }
}