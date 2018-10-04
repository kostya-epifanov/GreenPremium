package com.lab.greenpremium.ui.screen.auth

import com.lab.greenpremium.data.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by user on 7/14/17.
 */

class AuthPresenter @Inject constructor(val view: AuthContract.View) : AuthContract.Presenter {

    @Inject
    internal lateinit var repository: AuthRepository

    override fun auth(login: String, password: String) {
        repository.auth(login, password)
    }
}