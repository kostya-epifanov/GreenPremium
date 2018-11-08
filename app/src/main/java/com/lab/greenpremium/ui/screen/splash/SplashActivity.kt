package com.lab.greenpremium.ui.screen.splash

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    internal lateinit var presenter: SplashPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initializeDaggerComponent() {
        DaggerSplashComponent.builder()
                .appComponent((application as App).component)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        goToStartScreen()
    }

}
