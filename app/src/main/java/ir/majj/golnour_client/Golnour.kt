package ir.majj.golnour_client

import android.app.Application
import ir.majj.golnour_client.elegantviews.FontStorage
import ir.majj.golnour_client.preferences.Preferences

class Golnour : Application() {
    override fun onCreate() {
        super.onCreate()

        Preferences.setup(this)
        FontStorage.setNormalPath(this, "fonts/IRANYekanMobileRegular.ttf")
        FontStorage.setBoldPath(this, "fonts/IRANYekanMobileBold.ttf")
    }
}
