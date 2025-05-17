package com.example.checkingagp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object  Helper {


    object TextFieldStyle {

        @Composable
        fun myTextFieldColor() = OutlinedTextFieldDefaults.colors(


            cursorColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedPlaceholderColor = Color.Black,

            unfocusedBorderColor = Color.Black,
            focusedBorderColor = Color.Black,

            unfocusedTextColor = Color.Black ,
            focusedTextColor = Color.Black,

            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black


        )
        val defaultShape = RoundedCornerShape(20.dp)

    }
}