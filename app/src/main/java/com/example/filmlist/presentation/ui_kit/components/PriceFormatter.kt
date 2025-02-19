package com.example.filmlist.presentation.ui_kit.components

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object PriceFormatter {
    private val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    private val decimalFormat = DecimalFormat("#,###.00", symbols)

    fun format(price: Float): String = decimalFormat.format(price) + "$"
}