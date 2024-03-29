package com.lab.greenpremium.ui.screens.main.map

import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import javax.inject.Inject

class MapPresenter @Inject constructor(val view: MapContract.View) : MapContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    override fun onViewCreated() {
        updateMapObjects()
    }

    override fun updateMapObjects() {
        repository.updateMapObjects(object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                val mapObjectsData = UserModel.mapObjectsResponse
                mapObjectsData?.let {
                    this@MapPresenter.view.placeMarkers(mapObjectsData.features)
                }
            }
        })
    }
}