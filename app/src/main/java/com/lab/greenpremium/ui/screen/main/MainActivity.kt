package com.lab.greenpremium.ui.screen.main

import android.animation.Animator
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.lab.greenpremium.DURATION_FAST
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.basket.BasketFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsFragment
import com.lab.greenpremium.ui.screen.main.favorites.FavoritesFragment
import com.lab.greenpremium.ui.screen.main.map.MapFragment
import com.lab.greenpremium.ui.screen.main.plants.PlantFragment
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioFragment
import com.lab.greenpremium.ui.screen.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        BottomNavigationViewHelper.setUncheckable(navigation, false)
        button_favorites.setImageResource(R.drawable.ic_favorites)
        button_basket.setImageResource(R.drawable.ic_basket)

        when (item.itemId) {
            R.id.nav_profile -> {
                message.setText(R.string.title_profile)
                swapFragment(ProfileFragment.newInstance())
                activateFabMenu(true)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_plants -> {
                message.setText(R.string.title_plants)
                swapFragment(PlantFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                message.setText(R.string.title_portfolio)
                swapFragment(PortfolioFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                message.setText(R.string.title_contacts)
                swapFragment(ContactsFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                message.setText(R.string.title_map)
                swapFragment(MapFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initViews() {
        swapFragment(ProfileFragment.newInstance())
        activateFabMenu(true)

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        button_favorites.setOnClickListener {
            message.setText(R.string.title_favorites)
            button_favorites.setImageResource(R.drawable.ic_favorites_choosen)
            button_basket.setImageResource(R.drawable.ic_basket)
            swapFragment(FavoritesFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
        }

        button_basket.setOnClickListener {
            message.setText(R.string.title_basket)
            button_basket.setImageResource(R.drawable.ic_basket_choosen)
            button_favorites.setImageResource(R.drawable.ic_favorites)
            swapFragment(BasketFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
        }
    }

    private fun activateFabMenu(enabled: Boolean) {
        fab_menu.visibility = if (enabled) VISIBLE else GONE
        if (!enabled) {
            fab_menu.collapse()
            obstructor.visibility = GONE
        }

        fab_menu.setOnFloatingActionsMenuUpdateListener(object : FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                val x = obstructor.right
                val y = obstructor.bottom
                val startRadius = 0
                val endRadius = Math.hypot(obstructor.width.toDouble(), obstructor.height.toDouble())
                val animation = ViewAnimationUtils.createCircularReveal(obstructor, x, y, startRadius.toFloat(), endRadius.toFloat())
                animation.duration = DURATION_FAST
                obstructor.visibility = View.VISIBLE
                animation.start()
            }

            override fun onMenuCollapsed() {
                val x = obstructor.right
                val y = obstructor.bottom
                val startRadius = Math.max(obstructor.width, obstructor.height)
                val endRadius = 0
                val animation = ViewAnimationUtils.createCircularReveal(obstructor, x, y, startRadius.toFloat(), endRadius.toFloat())
                animation.duration = DURATION_FAST
                animation.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                        //ignore
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        obstructor.visibility = View.GONE
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                        //ignore
                    }

                    override fun onAnimationStart(p0: Animator?) {
                        //ignore
                    }
                })
                animation.start()
            }

        })
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
