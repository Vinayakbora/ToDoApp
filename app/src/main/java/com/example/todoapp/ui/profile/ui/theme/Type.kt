package com.example.todoapp.ui.profile.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todoapp.R

val Lato_light = FontFamily(
    Font(R.font.lato_light)
)
val Lato_regular = FontFamily(
    Font(R.font.lato_regular)
)
val Lato_bold = FontFamily(
    Font(R.font.lato_bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily =Lato_regular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h6 =  TextStyle(
        fontFamily = Lato_bold,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp
    ),
    h5 = TextStyle(
        fontFamily = Lato_light,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp
    ),
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)