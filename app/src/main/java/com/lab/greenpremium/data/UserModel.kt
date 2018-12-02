package com.lab.greenpremium.data

import com.lab.greenpremium.data.entity.AuthData
import com.lab.greenpremium.data.entity.ContactsData
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.utills.getMockPlantList


object UserModel {

    var authData: AuthData? = null
    var contacts: ContactsData? = null

    //TODO move to another repo
    val plants: List<Plant> = getMockPlantList()

    fun getCountOfItemsInCart(): Int {
        return plants.filter { it.count > 0 }.size
    }

}