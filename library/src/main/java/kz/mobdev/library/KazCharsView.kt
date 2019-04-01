package kz.mobdev.library

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Askar Syzdykov on 4/24/18.
 */

class KazCharsView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs) {

    private val cyrillicChars = arrayOf("ә", "і", "ң", "ғ", "ү", "ұ", "қ", "ө", "һ")
    private val latinChars = arrayOf("á", "i", "ń", "ǵ", "ú", "u", "q", "ó", "h")

    var kazCharsClickListener: KazCharsClickListener? = null

    lateinit var focusedView: EditText

    private var fontSize: Int = 14
    private var textColor: Int = Color.WHITE

    var isAllCaps: Boolean = false
        set(value) {
            field = value
            (0 until childCount).forEach {
                val b = getChildAt(it) as TextView
                b.setAllCaps(isAllCaps)
            }
        }

    var type: Type = Type.CYRILLIC
        set(value) {
            field = value
            val chars = when (type) {
                Type.CYRILLIC -> cyrillicChars
                Type.LATIN -> latinChars
            }
            (0 until childCount).forEachIndexed { index, it ->
                val b = getChildAt(it) as TextView
                b.text = chars[index]
            }
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.KazCharsView, defStyleAttr, defStyleRes)
        (0 until a.indexCount).forEach { i ->
            val attr = a.getIndex(i)
            when (attr) {
                R.styleable.KazCharsView_kcv_allCaps -> isAllCaps = a.getBoolean(attr, false)
                R.styleable.KazCharsView_kcv_fontSize -> fontSize = a.getDimensionPixelSize(attr, 14)
                R.styleable.KazCharsView_kcv_textColor -> textColor = a.getColor(attr, Color.WHITE)
                R.styleable.KazCharsView_kcv_type -> type = Type.valueOf(a.getInt(attr, Type.CYRILLIC.type))!!
            }
        }
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0) {
        init()
    }

    private fun init() {
        val density = context.resources.displayMetrics.density
        val paddingPixel = (8 * density).toInt()

        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        val marginPixel = density.toInt()
        layoutParams.leftMargin = marginPixel
        layoutParams.rightMargin = marginPixel

        val chars = if (type == Type.CYRILLIC) cyrillicChars else latinChars

        chars.forEach {
            val tv = TextView(context)
            tv.layoutParams = layoutParams
            tv.text = if (isAllCaps) it.toUpperCase() else it
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.toFloat())
            tv.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
            tv.setTextColor(textColor)
            tv.gravity = Gravity.CENTER
            tv.setBackgroundResource(R.drawable.btn_black)
            addView(tv)
            tv.setOnClickListener {
                var char = (it as TextView).text[0]
                char = if (isAllCaps) char.toUpperCase() else char.toLowerCase()
                kazCharsClickListener?.onKazakhCharClick(it, char)

                if (this::focusedView.isInitialized) {
                    val cursorPosition = focusedView.selectionStart
                    var text = focusedView.text.toString()
                    if (focusedView.hasSelection()) {
                        text = text.substring(0 until focusedView.selectionStart) + text.substring(focusedView.selectionEnd)
                        focusedView.setText(text)
                    }
                    text = focusedView.text.toString()
                    val newText = text.substring(0, cursorPosition) +
                        char.toString() +
                        text.substring(cursorPosition, text.length)
                    focusedView.setText(newText)
                    focusedView.setSelection(cursorPosition + 1)
                }
            }
        }
    }

    interface KazCharsClickListener {
        fun onKazakhCharClick(view: View, letter: Char)
    }

    enum class Type(val type: Int) {
        CYRILLIC(0),
        LATIN(1);

        companion object {
            fun valueOf(value: Int): Type? = Type.values().find { it.type == value }
        }
    }
}
