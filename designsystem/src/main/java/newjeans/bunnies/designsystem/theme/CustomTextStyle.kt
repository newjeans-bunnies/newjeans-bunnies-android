package newjeans.bunnies.designsystem.theme


import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import newjeans.bunnies.designsystem.theme.TextFont.pretendard

object CustomTextStyle {
    val TitleLarge = TextStyle(
        fontFamily = pretendard, fontWeight = FontWeight.Bold, fontSize = 20.sp
    )
    val TitleMedium = TextStyle(
        fontFamily = pretendard, fontWeight = FontWeight.Bold, fontSize = 16.sp
    )
    val TitleSmall = TextStyle(
        fontFamily = pretendard, fontWeight = FontWeight.SemiBold, fontSize = 14.sp
    )
    val buttonText = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = CustomColor.DarkGray
    )
    val Title1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
    val Title2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    )
    val Title3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    val Title4 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
    val Title5 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    )
    val Title6 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )
    val Title7 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
    val Title8 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    )
}