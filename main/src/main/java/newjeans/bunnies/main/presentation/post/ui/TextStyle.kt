package newjeans.bunnies.main.presentation.post.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import newjeans.bunnies.designsystem.theme.pretendard

object TextStyle {
    val userId = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = Color.Black
    )

    val createDate = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        color = Color.Black
    )
}