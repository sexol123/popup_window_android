package com.sergeimaleev.test

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.view.MotionEvent
import android.view.Gravity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.my_lay.*


class MainActivity : AppCompatActivity() {

   lateinit var btnFloating: Button
   lateinit var constraintLayout: ConstraintLayout
   lateinit var popupWindow: PopupWindow
   lateinit var popupView: View
    var mCurrentX: Int = 0
    var mCurrentY:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnFloating = findViewById(R.id.btnFloating) as Button
        constraintLayout = findViewById<ConstraintLayout>(R.id.activity_main)
        initui()

    }
    private fun initui(){
        btnFloating.setOnClickListener(View.OnClickListener {
            val layoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            popupView = layoutInflater.inflate(R.layout.my_lay, null)

            popupWindow = PopupWindow(
                popupView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            object: CountDownTimer(3000, 1000){
                override fun onFinish() {
                    popupView.findViewById<ImageView>(R.id.imgb).apply {
                        this.setImageResource(R.drawable.eaye)
                    }
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.also { it.start() }

            val btnClose = popupView.findViewById(R.id.btnClose) as Button

            btnClose.setOnClickListener { popupWindow.dismiss() }


            mCurrentX = 20
            mCurrentY = 50

            popupWindow.showAtLocation(constraintLayout, Gravity.NO_GRAVITY, mCurrentX, mCurrentY)



            popupView.setOnTouchListener(object : View.OnTouchListener {
                private var mDx: Float = 0.toFloat()
                private var mDy: Float = 0.toFloat()

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    val action = event.action
                    if (action == MotionEvent.ACTION_DOWN) {
                        mDx = mCurrentX - event.rawX
                        mDy = mCurrentY - event.rawY
                    } else if (action == MotionEvent.ACTION_MOVE) {
                        mCurrentX = (event.rawX + mDx).toInt()
                        mCurrentY = (event.rawY + mDy).toInt()
                        popupWindow.update(mCurrentX, mCurrentY, -1, -1)
                    }
                    return true
                }
            })
        })
    }

}
