package com.kyc.mobile.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kyc.mobile.R
import com.kyc.mobile.data.remote.dto.MessageData
import com.kyc.mobile.ui.theme.algerianFontFamily

@Composable
fun KycAlertDialog(messageData: MessageData, dismissDialog: ()-> Unit){

    Dialog(onDismissRequest = {dismissDialog()}){
        Card(shape = RoundedCornerShape(16.dp)){
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.report_24px),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = messageData.type,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            fontFamily = algerianFontFamily,
                            textAlign = TextAlign.Center,
                            //modifier = Modifier.padding(16.dp),
                        )
                    }
                    Text(
                        text = messageData.message,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        fontFamily = algerianFontFamily,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
                    )
                    TextButton(
                        onClick = { dismissDialog() },
                        modifier = Modifier.padding(5.dp),
                        border = BorderStroke(1.dp,MaterialTheme.colorScheme.primary),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            text = stringResource(R.string.btn_accept),
                            fontWeight = FontWeight.Bold,
                            fontFamily = algerianFontFamily,
                            color = MaterialTheme.colorScheme.onPrimary
                            )
                    }
                    Text(
                        text = messageData.code,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        fontFamily = algerianFontFamily,
                        color = Color.Gray,
                        modifier = Modifier.padding(5.dp)
                    )
            }
        }
    }
}

@Preview
@Composable
fun KycAlertDialogPreview(){
    KycAlertDialog(messageData = MessageData(time= ""), dismissDialog = {})
}