package newjeans.bunnies.designsystem.theme


import androidx.compose.material3.Typography
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
    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = AuthTextMainColor
    ),

    titleMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        color = AuthTextMainColor
    ),

    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        color = AuthTextMainColor
    )
)