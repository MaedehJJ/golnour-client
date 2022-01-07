package ir.majj.golnour_client.elegantviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import ir.majj.golnour_client.R

class ElegantButton : AppCompatButton {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        setFont(attrs)
    }

    constructor(context: Context) : super(context) {
        setFont(null)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setFont(attrs)
    }

    private fun setFont(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }

        val fontFaceStyle = FontFaceStyle.fromAttrs(
            context, attrs, R.styleable.ElegantButton, R.styleable.ElegantButton_fontFaceStyle
        )
        typeface = FontStorage.getFromStyle(fontFaceStyle)
    }
}

class ElegantEditText : AppCompatEditText {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        setFont(attrs)
    }

    constructor(context: Context) : super(context) {
        setFont(null)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setFont(attrs)
    }

    private fun setFont(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }

        val fontFaceStyle = FontFaceStyle.fromAttrs(
            context, attrs, R.styleable.ElegantEditText, R.styleable.ElegantEditText_fontFaceStyle
        )
        typeface = FontStorage.getFromStyle(fontFaceStyle)
    }
}

class ElegantTextView : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        setFont(attrs)
    }

    constructor(context: Context) : super(context) {
        setFont(null)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setFont(attrs)
    }

    private fun setFont(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }

        val fontFaceStyle = FontFaceStyle.fromAttrs(
            context, attrs, R.styleable.ElegantTextView, R.styleable.ElegantTextView_fontFaceStyle
        )
        typeface = FontStorage.getFromStyle(fontFaceStyle)
    }
}

class ElegantTextInputLayout : TextInputLayout {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        setFont(attrs)
    }

    constructor(context: Context) : super(context) {
        setFont(null)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setFont(attrs)
    }

    private fun setFont(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }

        val fontFaceStyle = FontFaceStyle.fromAttrs(
            context,
            attrs,
            R.styleable.ElegantTextInputLayout,
            R.styleable.ElegantTextInputLayout_fontFaceStyle
        )
        typeface = FontStorage.getFromStyle(fontFaceStyle)
    }
}

class ElegantChip : Chip {
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {
        setFont(attrs)
    }

    constructor(context: Context) : super(context) {
        setFont(null)
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setFont(attrs)
    }

    private fun setFont(attrs: AttributeSet?) {
        if (isInEditMode) {
            return
        }

        val fontFaceStyle = FontFaceStyle.fromAttrs(
            context, attrs, R.styleable.ElegantTextView, R.styleable.ElegantTextView_fontFaceStyle
        )
        typeface = FontStorage.getFromStyle(fontFaceStyle)
    }
}
