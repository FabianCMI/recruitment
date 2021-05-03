package com.example.maniaksearch.filters

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.maniaksearch.R

class FiltersDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_filters)
                    .setTitle(R.string.dialog_filters_title)
                    .setPositiveButton(R.string.dialog_valid) { dialog, id -> }
                    .setNegativeButton(R.string.dialog_cancel ) { dialog, id -> }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
