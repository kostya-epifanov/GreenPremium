package com.lab.greenpremium.ui.screens.calculator

import android.app.Activity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.components.Listener
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject

class CalcActivity : BaseActivity(), CalcContract.View {

    @Inject
    internal lateinit var presenter: CalcPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_calculator
    }

    override fun initializeDaggerComponent() {
        DaggerCalcComponent.builder()
                .appComponent((application as App).component)
                .calcModule(CalcModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        presenter.onViewCreated()

        presenter.initializeDataInput(
                RxTextView.textChanges(input_plants_s1).map { it.toString() }, RxTextView.textChanges(input_pots_s1).map { it.toString() },
                RxTextView.textChanges(input_plants_s2).map { it.toString() }, RxTextView.textChanges(input_pots_s2).map { it.toString() },
                RxTextView.textChanges(input_plants_s3).map { it.toString() }, RxTextView.textChanges(input_pots_s3).map { it.toString() },
                RxTextView.textChanges(input_plants_s4).map { it.toString() }, RxTextView.textChanges(input_pots_s4).map { it.toString() },
                RxTextView.textChanges(input_plants_s5).map { it.toString() }, RxTextView.textChanges(input_pots_s5).map { it.toString() }
        )

        button_request.setOnClickListener { presenter.onClickCalcRequest() }
        button_back.setOnClickListener { onBackPressed() }

        setTouchAnimationShrink(button_request)
    }

    override fun onCalculateSuccess(message: String) {
        showDialogMessage(message, null, object : Listener {
            override fun onEvent() {
                finishWithResult(Activity.RESULT_OK)
            }
        })
    }

    override fun setCalcButtonEnabled(isEnabled: Boolean) {
        button_request.isEnabled = isEnabled
    }
}
