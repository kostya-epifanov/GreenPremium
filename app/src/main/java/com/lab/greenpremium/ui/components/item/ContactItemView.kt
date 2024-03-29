package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.network.glide.GlideApp
import kotlinx.android.synthetic.main.view_item_contact.view.*


class ContactItemView : RelativeLayout {

    var contact: Contact? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_item_contact, this, true)

        button_call.setOnClickListener {
            contact?.phone?.let {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$it")
                context.startActivity(intent)
            }
        }

        button_mail.setOnClickListener {
            contact?.email?.let {
                try {
                    //todo (CRUSH) проверка на наличие активити которое хэндлит ACTION_SENDTO
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", it, null))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    //todo show snackbar message
                }
            }
        }

        /*
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        text_phone.setOnClickListener {
            clipboardManager.primaryClip = ClipData.newPlainText("phone", text_phone.text)
            (context as BaseActivity).showToast("Phone ${text_phone.text} copied to clipboard")
        }

        text_mail.setOnClickListener {
            clipboardManager.primaryClip = ClipData.newPlainText("mail", text_mail.text)
            (context as BaseActivity).showToast("Mail ${text_mail.text} copied to clipboard")
        }
        */

    }

    fun updateView() {
        contact?.let {c ->
            text_name.text = c.name
            text_position.text = c.position

            text_phone.text = c.phone
            button_call.visibility = if (c.phone.isNullOrEmpty()) GONE else VISIBLE

            text_mail.text = c.email
            button_mail.visibility = if (c.email.isNullOrEmpty()) GONE else VISIBLE

            text_schedule.text = c.schedule

            if (!c.photo.isNullOrEmpty()) {
                GlideApp.with(context)
                        .load(contact?.photo)
                        .into(image)
            }
        }
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }

}