package com.lab.greenpremium.ui.screen.main.plants

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.data.repository.UserRepository
import com.lab.greenpremium.ui.components.adapters.PlantRecyclerAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.sub_fragment_plants.*


class PlantSubFragment : BaseFragment() {

    companion object {
        fun newInstance(type: Int): PlantSubFragment {
            val fragment = PlantSubFragment()
            val args = Bundle()
            args.putInt(KEY_OBJECT, type)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var type: Plant.Type

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_plants
    }

    override fun initViews() {
        type = Plant.Type.values()[arguments!!.getInt(KEY_OBJECT)]

        recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler_plants.adapter = PlantRecyclerAdapter(UserRepository.plants.filter { it.type == type },
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt(), this)
    }

    override fun onResume() {
        super.onResume()
        recycler_plants.adapter.notifyDataSetChanged()
    }
}