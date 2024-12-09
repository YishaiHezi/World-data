package utils

import android.view.View

fun setSameVisibility(source: View, dependent: View) {
    dependent.visibility = source.visibility
}