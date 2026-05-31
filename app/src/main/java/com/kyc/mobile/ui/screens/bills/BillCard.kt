package com.kyc.mobile.ui.screens.bills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyc.mobile.R
import com.kyc.mobile.domain.model.CustomerBill

@Composable
fun BillCard(
    bill: CustomerBill,
    onClickDetail: (bill: CustomerBill)-> Unit = {}){

    var painterIconEvent: Painter = painterResource(id = R.drawable.info_24px)

    ListItem(
        headlineContent = {
            Text("${stringResource(R.string.label_bill_id)} ${bill.id}")
        },
        leadingContent = {
            Icon(
                painter = painterIconEvent,
                contentDescription = "",
            )
        },
        supportingContent = {
            Text("${stringResource(R.string.label_total)} ${bill.total}")
        },
        trailingContent = {
            Text(text = bill.status)
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(size = 16.dp))
            .clickable(true, onClick = {onClickDetail(bill)})

    )
}