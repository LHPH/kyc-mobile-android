package com.kyc.mobile.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kyc.mobile.domain.model.MessageData

@Composable
fun KycAlertDialog(messageData: MessageData, dismissDialog: ()-> Unit){

    Dialog(onDismissRequest = {dismissDialog()}){
        Card(shape = RoundedCornerShape(16.dp)){
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "New Update Available",
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                    )
                    Text(
                        text = "This is an example of the description of a very beautiful dialog which you may like.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
                    )
                    TextButton(
                        onClick = { dismissDialog() },
                        modifier = Modifier.padding(5.dp),
                        border = BorderStroke(1.dp,MaterialTheme.colorScheme.primary)
                    ) {
                        Text(style = MaterialTheme.typography.bodySmall, text = "Accept")
                    }
                    Text(
                        text = messageData.code,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(5.dp)
                    )
            }
        }
    }
}