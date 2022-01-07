package ir.majj.golnour_client.elegantviews

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StyleableRes

enum class FontFaceStyle(private val index: Int) {
    NORMAL(0), BOLD(1);

    companion object {
        private const val DEFAULT_INDEX = 0

        fun fromAttrs(
            context: Context,
            attrs: AttributeSet?,
            @StyleableRes styleable: IntArray,
            @StyleableRes styleKey: Int
        ): FontFaceStyle {
            val styledAttrs =
                attrs?.let { context.obtainStyledAttributes(it, styleable) }
            val fontFaceStyleIndex =
                styledAttrs?.getInteger(styleKey, DEFAULT_INDEX) ?: DEFAULT_INDEX
            styledAttrs?.recycle()
            return getByIndex(fontFaceStyleIndex)
        }

        private fun getByIndex(index: Int): FontFaceStyle {
            for (fontFaceStyle in values()) {
                if (fontFaceStyle.index == index) {
                    return fontFaceStyle
                }
            }
            throw IllegalArgumentException("Invalid font style index")
        }
    }
}
