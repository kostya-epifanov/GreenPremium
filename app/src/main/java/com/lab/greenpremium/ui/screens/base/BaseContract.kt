package com.lab.greenpremium.ui.screens.base

import com.lab.greenpremium.ui.components.Listener


interface BaseContract {
    interface BaseView {
        fun showError(throwable: Throwable)
        fun showSnackbar(text: String? = null, textResId: Int? = null)
        fun showLoadingDialog(show: Boolean = true)
        fun finishWithMessage(message: String?)
        fun showDialogMessage(text: String?, textResId: Int?, listener: Listener?)
        fun finishWithResult(result: Int)
        fun showDialogQuestion(textResId: Int?, listener: Listener?)
    }

    interface BasePresenter
}