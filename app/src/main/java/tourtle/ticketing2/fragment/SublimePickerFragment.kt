package tourtle.ticketing2.fragment

import androidx.fragment.app.DialogFragment
import com.appeaser.sublimepickerlibrary.SublimePicker
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption

class SublimePickerFragment : DialogFragment() {
    var mListener: SublimeListenerAdapter? = object : SublimeListenerAdapter() {
        override fun onCancelled() {

        }

        override fun onDateTimeRecurrenceSet(sublimeMaterialPicker: SublimePicker?,
                                             selectedDate: SelectedDate?,
                                             hourOfDay: Int, minute: Int,
                                             recurrenceOption: RecurrenceOption?,
                                             recurrenceRule: String?) {
        }
    }


}