package com.example.maniaksearch.filters

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.maniaksearch.R

const val TAG = "DialogLang"

class DialogFilterLangFragment(oldCountry: String) : DialogFragment() {
    val countryList= arrayOf("France", "USA", "Royaume-Uni", "Allemagne")
    private var chosenCountry: ISelectedCountry? = null
    private var checkedId = when(oldCountry) {
        "FR" -> 0
        "US" -> 1
        "GB" -> 2
        "DE" -> 3
        else -> 0
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setSingleChoiceItems(countryList, checkedId) { _, which ->
                checkedId = which
            }
                    .setTitle(R.string.dialog_filters_lang_title)
                    .setPositiveButton(R.string.dialog_valid) { dialog, id ->
                        chosenCountry?.onSelectedCountry(countryList[checkedId])
                    }
                    .setNegativeButton(R.string.dialog_cancel) { dialog, id -> }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(requireActivity())
        try {
            chosenCountry = activity as ISelectedCountry?
        } catch (e: ClassCastException) {
            Log.e(TAG, "Activity doesn't implement the ISelectedCountry interface")
        }
    }
}

interface ISelectedCountry {
    fun onSelectedCountry(string: String?)
}