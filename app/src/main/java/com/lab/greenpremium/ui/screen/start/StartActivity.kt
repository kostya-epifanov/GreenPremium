package com.lab.greenpremium.ui.screen.start

import android.graphics.LinearGradient
import android.graphics.Shader
import android.support.v4.content.ContextCompat
import android.view.ViewTreeObserver
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_start
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        button_auth.setOnClickListener { goToAuthScreen() }

        initializeGradientTitle()
    }

    private fun initializeGradientTitle() {
        val context = this
        container_main.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                container_main.viewTreeObserver.removeOnGlobalLayoutListener(this)
                title_gradient.paint.shader = LinearGradient(0f, 0f, title_gradient.width * 0.66f, 0f,
                        intArrayOf(ContextCompat.getColor(context, R.color.green_1), ContextCompat.getColor(context, R.color.green_3)),
                        floatArrayOf(0.5f, 1f), Shader.TileMode.CLAMP)
            }
        })
    }

}

