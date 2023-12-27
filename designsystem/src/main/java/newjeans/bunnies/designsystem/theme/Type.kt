package newjeans.bunnies.designsystem.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import newjeans.bunnies.designsystem.R


val pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_thin, FontWeight.Thin, FontStyle.Normal),
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
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = AuthTextMainColor
    ),

    headlineMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.Black
    ),

    labelLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.Black
    ),


    //약관 동의
    displayMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        color = Color.Black
    ),

    displaySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Black
    )





)