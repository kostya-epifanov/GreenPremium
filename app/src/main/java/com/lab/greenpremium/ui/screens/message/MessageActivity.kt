package com.lab.greenpremium.ui.screens.message

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.asksira.bsimagepicker.BSImagePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.components.Listener
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_message.*
import javax.inject.Inject


class MessageActivity : BaseActivity(), MessageContract.View, BSImagePicker.OnMultiImageSelectedListener {

    @Inject
    internal lateinit var presenter: MessagePresenter

    override fun layoutResId(): Int {
        return com.lab.greenpremium.R.layout.activity_message
    }

    override fun initializeDaggerComponent() {
        DaggerMessageComponent.builder()
                .appComponent((application as App).component)
                .messageModule(MessageModule(this))
                .build()
                .inject(this)
    }

    @SuppressLint("CheckResult")
    override fun initViews() {
        presenter.onViewCreated(intent.extras.getSerializable(KEY_OBJECT) as MessageScreenType)
        button_back.setOnClickListener { finishWithResult(Activity.RESULT_CANCELED) }

        rating_bar.setOnRatingChangeListener { v, rating -> presenter.onRatingChanged(rating) }

        presenter.initializeThemeInput(RxTextView.textChanges(input_subject).map { it.toString() })
        presenter.initializeMessageInput(RxTextView.textChanges(input_message.getTextView()).map { it.toString() })

        button_send.setOnClickListener { presenter.onClickSend() }
        setTouchAnimationShrink(button_send)
    }

    override fun initViewByType(type: MessageScreenType) {
        title_label.text = getString(type.titleResId)
        rating_bar.visibility = if (type.hasRatingBar) View.VISIBLE else View.GONE
        container_subject.visibility = if (type.hasSubjectInput) View.VISIBLE else View.GONE
        input_message.visibility = if (type.hasMessageInput) View.VISIBLE else View.GONE

        if (type.hasPhotoAdding) InitializeRecyclerPhotos()
    }

    override fun onSentSuccess(messageResId: Int) {
        showDialogMessage(null, messageResId, object : Listener {
            override fun onEvent() {
                finishWithResult(Activity.RESULT_OK)
            }
        })
    }

    override fun setSendButtonEnabled(isEnabled: Boolean) {
        button_send.isEnabled = isEnabled
    }

    override fun InitializeRecyclerPhotos() {
        recycler_photo.visibility = View.VISIBLE

        recycler_photo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_photo.adapter = RecyclerPhotosAdapter(resources.getDimensionPixelSize(R.dimen.view_add_photo_size),
                object : AddPhotoViewListener {
                    override fun onClickDelete(index: Int) {
                        (recycler_photo.adapter as RecyclerPhotosAdapter).removeItem(index)
                    }

                    override fun onClickPhoto(index: Int) {
                        //todo go to gallery
                    }

                    override fun onClickAdd() {
                        presenter.onClickAddPhoto()
                    }
                })
    }

    override fun showPhotoPickerDialog() {
        BSImagePicker.Builder("com.yourdomain.yourpackage.fileprovider")
                .isMultiSelect()
                .setMinimumMultiSelectCount(1)
                .setMaximumMultiSelectCount(9)
                .setMultiSelectBarBgColor(android.R.color.white)
                .disableOverSelectionMessage()
                .build()
                .show(supportFragmentManager, "photo_picker")
    }

    override fun onMultiImageSelected(uriList: MutableList<Uri>?, tag: String?) {
        (recycler_photo.adapter as RecyclerPhotosAdapter).addItems(uriList)
    }
}
