package utils

import android.view.View

/**
 * Set the visibility of a set of views to the same value.
 */
fun setSameVisibility(source: View, dependent: View) {
    dependent.visibility = source.visibility
}


/**
 * Update the visibility of a set of views based on a condition.
 */
fun updateVisibility(vararg views: View, condition: Boolean?) {
    val visibility = if (condition == true) View.VISIBLE else View.GONE
    views.forEach { it.visibility = visibility }
}