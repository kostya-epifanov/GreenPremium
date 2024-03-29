package com.lab.greenpremium.ui.screens.main.cart

import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.MakeOrderResponse
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import java.io.Serializable
import javax.inject.Inject

class CartPresenter @Inject constructor(val view: CartContract.View) : CartContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        view.updateTotalCost(CartModel.getCartTotalCost())
        view.setBillButtonEnabled(!repository.isInDemoMode())
        CartModel.cart?.let {
            view.initializeCartProductsList(CartModel.cart!!.products, repository.isInDemoMode())
            view.initializeServiceText(CartModel.cart!!.service_text)
        }
    }

    override fun onCartUpdatedEvent() {
        view.updateTotalCost(CartModel.getCartTotalCost())
    }

    override fun onClickBillRequest() {
        repository.makeOrder(object : DefaultCallbackListener(view) {
            override fun onSuccess(item: Serializable?) {
                CartModel.clearCart()
                this@CartPresenter.view.updateTotalCost(0.0)
                this@CartPresenter.view.onBillRequestSuccess((item as MakeOrderResponse).message)
                CartModel.clearCatalog()
            }
        })
    }
}