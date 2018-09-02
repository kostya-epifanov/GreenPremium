package com.lab.greenpremium.ui.components.adapters


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.data.entity.Plant.Type.Companion.getTitles
import com.lab.greenpremium.ui.screen.main.plants.PlantSubFragment

class PlantsPagerAdapter(fm: FragmentManager?, private val context: Context?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PlantSubFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getTitles(context)[position]
    }

    override fun getCount(): Int {
        return Plant.Type.values().size
    }
}