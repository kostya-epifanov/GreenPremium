package com.lab.greenpremium.ui.screens.auth

import android.annotation.SuppressLint
import com.lab.greenpremium.R
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject


class AuthPresenter @Inject constructor(val view: AuthContract.View) : AuthContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private var login: String? = null
    private var password: String? = null

    @SuppressLint("CheckResult")
    override fun initializeDataInput(login: Observable<String>, password: Observable<String>) {
        login.subscribe { s ->
            run {
                if (!s.isEmpty()) {
                    this.login = s
                    view.setLoginInputError(null)
                } else {
                    this.login = null
                }
            }
        }

        password.subscribe { s ->
            run {
                if (!s.isEmpty()) {
                    this.password = s
                    view.setPasswordInputError(null)
                } else {
                    this.password = null
                }
            }
        }
    }

    override fun validateDataAndProceedAuth() {
        when {

            login == null -> {
                view.setLoginInputError(R.string.error_empty_field, R.string.input_title_email)
                return
            }

            password == null -> {
                view.setPasswordInputError(R.string.error_empty_field, R.string.input_title_password)
                return
            }

            else -> {
                view.setLoginInputError(null)
                view.setPasswordInputError(null)

                repository.auth(login!!, password!!, object : DefaultCallbackListener(view) {
                    override fun onSuccess() {
                        this@AuthPresenter.view.goToMain()
                    }
                })

            }
        }
    }

    override fun onRestorePasswordEmailSend(email: String, listener: CallbackListener) {
        repository.passwordRecovery(email, object : DefaultCallbackListener(view) {
            override fun doBefore() {
                listener.doBefore()
            }

            override fun doAfter() {
                listener.doAfter()
            }

            override fun onError(throwable: Throwable) {
                listener.onError(throwable)
            }

            override fun onSuccess() {
                listener.onSuccess()
                this@AuthPresenter.view.onRestorePasswordSuccess()
            }
        })
    }
}