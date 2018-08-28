package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.currencyFormat
import kotlinx.android.synthetic.main.view_item_plant.view.*


class PlantItemView : RelativeLayout {

    private lateinit var plant: Plant

    private val repeatUpdateHandler = Handler()
    private var isIncrementing = false
    private var isDecrementing = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_item_plant, this, true)

        button_add.run {
            setOnClickListener { setCounter(++plant.count) }
            setOnLongClickListener {
                isIncrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { v, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isIncrementing) {
                    isIncrementing = false
                }
                false
            }
        }

        button_remove.run {
            setOnClickListener { setCounter(--plant.count) }
            setOnLongClickListener {
                isDecrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { v, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isDecrementing) {
                    isDecrementing = false
                }
                false
            }
        }

    }

    internal inner class RptUpdater : Runnable {
        override fun run() {
            if (isIncrementing) {
                setCounter(++plant.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)

            } else if (isDecrementing) {
                setCounter(--plant.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)
            }
        }
    }

    fun setData(plant: Plant) {
        this.plant = plant

        text_name.text = plant.name
        text_info_1.text = plant.info1
        text_info_2.text = plant.info2
        text_price.text = currencyFormat(plant.price)
        text_discount.text = currencyFormat(plant.discount)
        plant.drawableResId?.let { image.setImageResource(it) }

        updateByType(plant.type)
        setCounter(plant.count)
    }

    private fun updateByType(type: Plant.Type) {
        when (type) {
            Plant.Type.LIVING, Plant.Type.ARTIFICIAL -> {
                showHeightSelector(false)
            }

            Plant.Type.BIG -> {
                text_info_2.text = context.getText(R.string.title_height)
                showHeightSelector(true)
            }
        }
    }

    private fun setCounter(n: Int) {
        plant.count = if (n < 0) 0 else n
        text_counter.text = plant.count.toString()
    }

    private fun showHeightSelector(enabled: Boolean) {
        height_selector.visibility = if (enabled) View.VISIBLE else View.GONE
        space.visibility = if (enabled) View.VISIBLE else View.GONE
        height_selector.text = "15 м" //TODO прибрать
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }
}