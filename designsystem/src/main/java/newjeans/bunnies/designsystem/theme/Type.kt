package newjeans.bunnies.designsystem.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import newjeans.bunnies.designsystem.R


val pretendard = FontFamily(
    Font(R.font.pretendard_bold),
    Font(R.font.pretendard_extra_bold),
    Font(R.font.pretendard_light),
    Font(R.font.pretendard_medium),
    Font(R.font.pretendard_semi_bold),
    Font(R.font.pretendard_regular),
    Font(R.font.pretendard_thin)
)

val authText = Typography(
    headlineLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.Black
    ),
    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = AuthTextMainColor
    ),

    //edittext hint 텍스트
    bodySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = AuthTextHintColor
    ),

    //버튼 텍스트
    titleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = AuthTextMainColor
    ),


    //애러 텍스트
    titleSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = AuthTextErrorColor
    ),

    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 16.sp,
        color = AuthTextMainColor
    ),

    headlineMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = AuthRadioButtonTextColor
    ),

    labelLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.Black
    )




)