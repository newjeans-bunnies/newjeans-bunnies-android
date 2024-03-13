package newjeans.bunnies.auth.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import newjeans.bunnies.designsystem.R
import newjeans.bunnies.designsystem.theme.CustomTextStyle
import newjeans.bunnies.designsystem.theme.CustomColor

@Composable
fun StatusMessageText(message: String, errorStatus: Boolean) {
    if (errorStatus) Text(text = message, style = CustomTextStyle.Title1, color = CustomColor.Error)
    else Text(text = message, style = CustomTextStyle.Title1, color = CustomColor.Check)
}

@Composable
fun StatusMessageIcon(errorStatus: Boolean?) {
    Box(){
        if (errorStatus == true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "사용가능",
                    style = CustomTextStyle.Title8,
                    color = CustomColor.Check,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Image(painterResource(id = R.drawable.ic_accept), "ic_refuse", modifier = Modifier.size(16.dp))
            }
        } else if(errorStatus == false){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "사용불가",
                    style = CustomTextStyle.Title8,
                    color = CustomColor.Error,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Image(painterResource(id = R.drawable.ic_refuse), "ic_accept", modifier = Modifier.size(16.dp))
            }
        }
    }

}