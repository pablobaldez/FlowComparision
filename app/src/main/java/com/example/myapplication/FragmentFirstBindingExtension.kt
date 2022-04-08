package com.example.myapplication

import android.view.View
import com.example.myapplication.databinding.FragmentFirstBinding

fun FragmentFirstBinding.bind(loading: Boolean, value: Int, isEven: String) {
    if (loading) {
        progressCircular.visibility = View.VISIBLE
        textviewIsEven.visibility = View.INVISIBLE
    } else {
        progressCircular.visibility = View.INVISIBLE
        textviewIsEven.visibility = View.VISIBLE
    }
    textviewValue.text = value.toString()
    textviewIsEven.text = isEven
}