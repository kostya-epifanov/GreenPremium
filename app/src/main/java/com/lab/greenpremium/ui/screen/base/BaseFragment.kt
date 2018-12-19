package com.lab.greenpremium.ui.screen.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screen.main.plants.sub.PlantRecyclerAdapter


abstract class BaseFragment : Fragment(), PlantRecyclerAdapter.OnProductSelectedListener, BaseContract.BaseView {

    lateinit var TAG: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        TAG = this::class.java.simpleName
        initializeDaggerComponent()
        return inflater.inflate(layoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    protected abstract fun initializeDaggerComponent()

    protected abstract fun layoutResId(): Int

    protected abstract fun initViews()

    override fun onPlantSelected(product: Product) {
        (activity as BaseActivity).goToPlantDetailActivity(product)
    }

    override fun showError(throwable: Throwable) {
        (activity as BaseActivity).showError(throwable)
    }

    override fun showSnackbar(text: String?, textResId: Int?) {
        (activity as BaseActivity).showSnackbar(text, textResId)
    }

    override fun showDialogMessage(text: String?, textResId: Int?) {
        (activity as BaseActivity).showDialogMessage(text, textResId)
    }

    override fun showLoadingDialog(show: Boolean) {
        (activity as BaseActivity).showLoadingDialog(show)
    }
}
