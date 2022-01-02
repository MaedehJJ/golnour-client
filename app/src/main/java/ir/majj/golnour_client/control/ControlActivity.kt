package ir.majj.golnour_client.control

import android.content.Context
import android.view.ViewGroup
import ir.majj.golnour_client.databinding.ActivityControlBinding
import ir.majj.golnour_client.utils.BoundActivity
import ir.majj.golnour_client.utils.intentFor

class ControlActivity : BoundActivity<ActivityControlBinding>() {
    override fun inflateLayout(container: ViewGroup?) =
        ActivityControlBinding.inflate(layoutInflater)

    companion object {
        fun getOpenIntent(context: Context) = context.intentFor<ControlActivity>()
    }
}
