package com.lab.greenpremium.ui.screens.main.favorites

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.components.item.PlantItemView
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.lab.greenpremium.ui.screens.main.plants.sub.PlantRecyclerAdapter
import com.lab.greenpremium.utills.LogUtil
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject


class FavoritesFragment : BaseFragment(), FavoritesContract.View {

    @Inject
    internal lateinit var presenter: FavoritesPresenter

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerFavoritesComponent.builder()
                .appComponent((activity?.application as App).component)
                .favoritesModule(FavoritesModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_favorites
    }

    override fun initViews() {
        //ignore
    }

    override fun initializeFavoritesList(favorites: List<Product>, isDemo: Boolean) {
        if (favorites.isNotEmpty()) {
            label_empty_list.visibility = View.GONE
            recycler_plants.visibility = View.VISIBLE
            recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            recycler_plants.adapter = PlantRecyclerAdapter(favorites, context?.resources?.getDimension(R.dimen.space_24)?.toInt(),
                    PlantItemView.PlantViewType.OTHER, isDemo,
                    listener = object : PlantRecyclerAdapter.PlantsRecyclerListener {
                        override fun onProductSelected(product: Product) {
                            presenter.onProductSelected(product)
                        }

                        override fun onRecyclerBottomReached(size: Int) {
                            //ignore
                        }
                    })
        } else {
            label_empty_list.visibility = View.VISIBLE
            recycler_plants.visibility = View.GONE
        }
    }

    override fun goToDetails(product: Product) {
        (activity as BaseActivity).goToPlantDetailActivity(product)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed()
    }
}