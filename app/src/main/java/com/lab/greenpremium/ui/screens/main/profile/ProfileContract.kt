package com.lab.greenpremium.ui.screens.main.profile

import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.screens.base.BaseContract


interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun showLoadingStub(show: Boolean)
        fun initializeContactsCarousel(contacts: List<Contact>)
        fun initializeServiceCostSection(payment: Double?)
        fun initializeEventsList(events: List<Event>)
        fun initializeOrdersSection(order_id: Int?, order_supply_date: String?)
        fun showNoOrdersContainer(show: Boolean)

        fun goToServiceCalculatorScreen()
        fun goToDeliveryScreen(order_id: Int?)
    }

    interface Presenter {
        fun onViewCreated()
        fun updateEvents(forced: Boolean)
    }
}