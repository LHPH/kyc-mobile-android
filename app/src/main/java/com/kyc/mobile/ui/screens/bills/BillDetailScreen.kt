package com.kyc.mobile.ui.screens.bills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyc.mobile.R
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.ui.theme.algerianFontFamily

@Composable
fun BillDetailScreen(
    bill: CustomerBill,
    onClickBack: () -> Unit
){

    Box(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.intro_kyc),
                contentScale = ContentScale.Crop)
            .padding(top = 50.dp)

    ){

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(start = 30.dp)
                .clickable(enabled = true, onClick = onClickBack)
        )
        Text(
            text = "Detail of bill",
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = algerianFontFamily,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier
                .padding(
                    top = 40.dp,
                    start = 35.dp,
                    end = 35.dp
                )
        )
        OutlinedCard(
            modifier = Modifier.padding(top = 60.dp, start = 35.dp),
            ) {
            Column(
                modifier = Modifier.width(320.dp).padding(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ){
                BillDetailRowText("Folio:", "${bill.id}")
                BillDetailRowText("Status:", bill.status)
                BillDetailRowText("Subtotal:", "${bill.subtotal}")
                BillDetailRowText("Taxes:", "${bill.taxes}")
                BillDetailRowText("Total:", "${bill.total}")
                BillDetailRowText("Issue:", bill.issueDate)
                BillDetailRowText("Payment due:", bill.paymentDueDate)
                BillDetailRowText("Billing start:", bill.billingStartDate)
                BillDetailRowText("Billing end:", bill.billingFinishDate)
                if(bill.settled){
                    BillDetailRowText("Settled:", bill.settlementDate ?: "NA")
                }
            }
        }

    }
}

@Composable
fun BillDetailRowText(
    label: String,
    text: String,
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = algerianFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = algerianFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview
@Composable
fun BillDetailScreenPreview(){

    val customerBill = CustomerBill(
        id = 1, taxes = 10.2, subtotal = 20.0,total = 300.0, settled = true,
        status = "PAID", issueDate = "2026-10-20", billingStartDate = "2026-10-20", billingFinishDate = "2026-10-20",
        paymentDueDate =  "2026-10-20", settlementDate = "2026-10-20"
    )
    BillDetailScreen(customerBill, {})
}