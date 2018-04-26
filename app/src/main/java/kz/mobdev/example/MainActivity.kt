package kz.mobdev.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kz.mobdev.library.KazCharsView


class MainActivity : AppCompatActivity(), KazCharsView.KazCharsClickListener, ViewTreeObserver.OnGlobalFocusChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kazCharsView.kazCharsClickListener = this

        switchAllCaps.setOnCheckedChangeListener { _, b -> kazCharsView.isAllCaps = b }
        switchType.setOnCheckedChangeListener { _, b ->
            val type = if (b) KazCharsView.Type.LATIN else KazCharsView.Type.CYRILLIC
            kazCharsView.type = type
            kazCharsView.invalidate()
        }
    }

    override fun onGlobalFocusChanged(oldFocus: View?, newFocus: View?) {
        if (newFocus is EditText) {
            kazCharsView.focusedView = newFocus
        }
    }

    override fun onResume() {
        super.onResume()
        rootView.viewTreeObserver.addOnGlobalFocusChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        rootView.viewTreeObserver.removeOnGlobalFocusChangeListener(this)
    }

    override fun onKazakhCharClick(view: View, letter: Char) {
        Toast.makeText(this, "Pressed $letter button", Toast.LENGTH_SHORT).show()
    }

}
