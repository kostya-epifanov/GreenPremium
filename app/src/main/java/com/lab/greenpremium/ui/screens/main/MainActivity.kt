package com.lab.greenpremium.ui.screens.main

import android.animation.Animator
import android.content.Intent
import android.os.Handler
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.lab.greenpremium.*
import com.lab.greenpremium.data.*
import com.lab.greenpremium.ui.components.BottomNavigationViewHelper
import com.lab.greenpremium.ui.components.Listener
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.main.cart.CartFragment
import com.lab.greenpremium.ui.screens.main.contacts.ContactsFragment
import com.lab.greenpremium.ui.screens.main.favorites.FavoritesFragment
import com.lab.greenpremium.ui.screens.main.map.MapFragment
import com.lab.greenpremium.ui.screens.main.plants.PlantsFragment
import com.lab.greenpremium.ui.screens.main.portfolio.PortfolioFragment
import com.lab.greenpremium.ui.screens.main.profile.ProfileFragment
import com.lab.greenpremium.ui.screens.message.MessageScreenType
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    internal lateinit var presenter: MainPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDaggerComponent() {
        DaggerMainComponent.builder()
                .appComponent((application as App).component)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    private var lastClickedMenuItemId = R.id.nav_profile

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        BottomNavigationViewHelper.setUncheckable(navigation, false)
        button_favorite.setImageResource(R.drawable.ic_favorites)
        button_cart.setImageResource(R.drawable.ic_cart)

        if (lastClickedMenuItemId == item.itemId) return@OnNavigationItemSelectedListener false
        else lastClickedMenuItemId = item.itemId

        when (item.itemId) {
            R.id.nav_profile -> {
                title_text.setText(R.string.screen_title_profile_2)
                swapFragment(ProfileFragment.newInstance())
                showFabMenuAndLogout(true)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_plants -> {
                title_text.setText(R.string.screen_title_plants)
                swapFragment(PlantsFragment.newInstance())
                showFabMenuAndLogout(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                title_text.setText(R.string.screen_title_portfolio)
                swapFragment(PortfolioFragment.newInstance())
                showFabMenuAndLogout(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                title_text.setText(R.string.screen_title_contacts)
                swapFragment(ContactsFragment.newInstance())
                showFabMenuAndLogout(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                title_text.setText(R.string.screen_title_map)
                swapFragment(MapFragment.newInstance())
                showFabMenuAndLogout(false)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun initViews() {
        title_text.setText(R.string.screen_title_profile_2)
        swapFragment(ProfileFragment.newInstance())

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        button_favorite.setOnClickListener {
            if (lastClickedMenuItemId != R.id.button_favorite){
                title_text.setText(R.string.screen_title_favorites)
                button_favorite.setImageResource(R.drawable.ic_favorites_choosen)
                button_cart.setImageResource(R.drawable.ic_cart)
                swapFragment(FavoritesFragment.newInstance())
                BottomNavigationViewHelper.setUncheckable(navigation, true)
                showFabMenuAndLogout(false)
                lastClickedMenuItemId = R.id.button_favorite
            }
        }

        button_cart.setOnClickListener {
            if (lastClickedMenuItemId != R.id.button_cart){
                title_text.setText(R.string.screen_title_basket)
                button_cart.setImageResource(R.drawable.ic_basket_choosen)
                button_favorite.setImageResource(R.drawable.ic_favorites)
                swapFragment(CartFragment.newInstance())
                BottomNavigationViewHelper.setUncheckable(navigation, true)
                showFabMenuAndLogout(false)
                lastClickedMenuItemId = R.id.button_cart
            }
        }

        button_logout.setOnClickListener {
            showDialogQuestion(R.string.dialog_on_logout, object : Listener {
                override fun onEvent() {
                    presenter.onClickLogout()
                }
            })
        }

        showFabMenuAndLogout(true)
        fab_project.setOnClickListener { goToMessageScreen(MessageScreenType.NEW_PROJECT).also { fab_menu.collapse() } }
        fab_letter.setOnClickListener { goToMessageScreen(MessageScreenType.LETTER).also { fab_menu.collapse() } }
        fab_rating.setOnClickListener { goToMessageScreen(MessageScreenType.RATING).also { fab_menu.collapse() } }
        fab_complain.setOnClickListener { goToMessageScreen(MessageScreenType.COMPLAIN).also { fab_menu.collapse() } }
    }

    /* Кнопки логаут и FAB показываем только в профиле */
    private fun showFabMenuAndLogout(enabled: Boolean) {
        fab_menu.visibility = if (enabled) VISIBLE else GONE
        button_logout.visibility = if (enabled) VISIBLE else GONE
        button_favorite.visibility = if (enabled) GONE else VISIBLE

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

    fun selectMenuItem(@IdRes itemResId: Int) {
        navigation.selectedItemId = itemResId
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent) {
        when (event) {
            is ProductQuantityChangedEvent -> presenter.onProductQuantityChanged(event.product)
        }
    }

    override fun updateCartIndicator(count: Int) {
        button_cart.updateIndicator(count)
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        presenter.onViewStarted()
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        showDialogQuestion(R.string.dialog_on_logout, object : Listener {
            override fun onEvent() {
                finishAffinity()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            KEY_RESULT_CALCULATOR -> {
                Handler().post { EventBus.getDefault().post(ServiceCalculatedEvent()) }
            }
            KEY_RESULT_MESSAGE_SENT -> {
                Handler().post { EventBus.getDefault().post(MessageSentEvent()) }
            }
            KEY_RESULT_ADD_MEETING -> {
                Handler().post {
                    data?.let {
                        EventBus.getDefault().post(MeetingAddedEvent())
                        val message = data.getStringExtra(KEY_OBJECT)
                        showSnackbar(message)
                    }
                }
            }

        }
    }

    override fun onLogout() {
        goToStartScreen()
    }
}
