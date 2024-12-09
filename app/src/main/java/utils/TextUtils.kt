package utils

import android.widget.TextView

/**
 * Set the text of the TextView and show it if it is not null or empty, else hide the TextView.
 */
fun TextView.setTextOrHide(text: String?) {
    if (!text.isNullOrEmpty()) {
        this.text = text
        this.visibility = android.view.View.VISIBLE
    } else {
        this.visibility = android.view.View.GONE
    }
}


/**
 * Update the visibility of the TextView based on the given condition, and apply the given block.
 */
fun TextView.updateVisibility(condition: Boolean?, block: (TextView) -> Unit) {
    if (condition == true) {
        block(this)
        this.visibility = android.view.View.VISIBLE
    } else {
        this.visibility = android.view.View.GONE
    }
}