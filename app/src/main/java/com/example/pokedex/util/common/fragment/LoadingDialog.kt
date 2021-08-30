package com.example.pokedex.util.common.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.pokedex.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog : DialogFragment() {

    companion object {
        val TAG = LoadingDialog::class.java.canonicalName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.dialog_loading)
        val gif = dialog.iv_loading_gif
        Glide.with(requireContext()).load(R.drawable.loading_pokeball).into(gif)
        return dialog
    }

    /**
     * En caso de que no exista un fragment manager
     */
    fun safeDismiss() {
        try {
            dismiss()
        } catch (e: IllegalStateException) {
        }
    }

    fun safeShow(fragmentManager: FragmentManager) {
        val loadingFragment = fragmentManager.findFragmentByTag(TAG)
        if (loadingFragment == null) {
            show(fragmentManager, TAG)
        }
    }

}