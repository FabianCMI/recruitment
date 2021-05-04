package com.example.maniaksearch.filters

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.maniaksearch.R

class FiltersDialogFragment(val oldLimit: Int) : DialogFragment() {

    private var selectedLimit: ISelectedLimit? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = it.layoutInflater

            val dialogView = (inflater.inflate(R.layout.dialog_filters, null))

            // set a change listener on the SeekBar
            val seekBar = dialogView.findViewById<SeekBar>(R.id.seekbar)
            val seekBarLabel = dialogView.findViewById<TextView>(R.id.seekbarText)
            seekBar.progress = oldLimit
            seekBarLabel.text = oldLimit.toString()
            seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    if (seekBar != null) {
                        seekBarLabel.text = seekBar.progress.toString()
                    }
                }
            })
            builder.setView(dialogView)
                    .setMessage(R.string.dialog_filters)
                    .setTitle(R.string.dialog_filters_title)
                    .setPositiveButton(R.string.dialog_valid) {
                        dialog, id -> selectedLimit?.onSelectedLimit(seekBar.progress) }
                    .setNegativeButton(R.string.dialog_cancel ) {
                        dialog, id -> seekBarLabel.text = oldLimit.toString() }


            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(requireActivity())
        try {
            selectedLimit = activity as ISelectedLimit?
        } catch (e: ClassCastException) {
            Log.e(TAG, "Activity doesn't implement the ISelectedCountry interface")
        }
    }
}


interface ISelectedLimit {
    fun onSelectedLimit(limit: Int?)
}