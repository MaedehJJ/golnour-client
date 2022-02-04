package ir.majj.golnour_client.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import ir.majj.golnour_client.R
import ir.majj.golnour_client.databinding.ActivityAboutBinding
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor
import ir.majj.golnour_client.utils.onClick
import ir.majj.golnour_client.utils.string
import timber.log.Timber


class AboutActivity : BoundActivity<ActivityAboutBinding>() {
    override fun inflateLayout(container: ViewGroup?) = ActivityAboutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            website.onClick {
                openWebsite()
            }
            email.onClick {
                sendMail()
            }
        }
    }

    private fun openWebsite() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://${string(R.string.about_website)}")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Timber.w("Could not resolve an activity to open the website")
        }
    }

    private fun sendMail() {
        val mail = string(R.string.about_email)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$mail")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Timber.w("Could not resolve an activity to open mailto")
        }
    }

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<AboutActivity>()
    }
}
