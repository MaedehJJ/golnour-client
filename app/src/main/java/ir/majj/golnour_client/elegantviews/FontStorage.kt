package ir.majj.golnour_client.elegantviews

import android.content.Context
import android.graphics.Typeface

object FontStorage {
    lateinit var normal: Typeface
        private set
    lateinit var bold: Typeface
        private set

    fun setNormalPath(context: Context, path: String) {
        normal = Typeface.createFromAsset(context.assets, path)
    }

    fun setBoldPath(context: Context, path: String) {
        bold = Typeface.createFromAsset(context.assets, path)
    }

    fun getFromStyle(fontFaceStyle: FontFaceStyle): Typeface {
        return when (fontFaceStyle) {
            FontFaceStyle.BOLD -> bold
            FontFaceStyle.NORMAL -> normal
        }
    }
}
