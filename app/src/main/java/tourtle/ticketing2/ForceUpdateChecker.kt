package tourtle.ticketing2

import android.content.Context

class ForceUpdateChecker(context: Context,
                         onUpdateNeededListener: OnUpdateNeededListener?) {
    private val onUpdateNeededListener: OnUpdateNeededListener?
    private val context: Context?

    interface OnUpdateNeededListener {
        open fun onUpdateNeeded(updateUrl: String?)
    }

    init {
        this.context = context
        this.onUpdateNeededListener = onUpdateNeededListener
    }
}