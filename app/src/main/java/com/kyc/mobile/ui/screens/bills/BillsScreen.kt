package com.kyc.mobile.ui.screens.bills

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyc.mobile.R
import com.kyc.mobile.domain.model.CustomerBill
import com.kyc.mobile.ui.shared.DisplayState
import com.kyc.mobile.ui.shared.KycAlertDialog
import com.kyc.mobile.ui.theme.algerianFontFamily
import com.kyc.mobile.ui.viewmodel.BillViewModel

@Composable
fun BillScreen(
    viewModel: BillViewModel,
    onClickBack: () -> Unit,
    onClickDetail: (bill: CustomerBill) -> Unit){

    val billState by viewModel.billState.collectAsStateWithLifecycle()
    BillView(billState, onClickBack,onClickDetail, viewModel::onAction)
}

@Composable
fun BillView(
    billState: BillsState,
    onClickBack: ()-> Unit={},
    onClickDetail:(bill: CustomerBill)-> Unit={},
    onAction: (action: BillAction)-> Unit = {}
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
            text = "Bills",
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

        when(billState.state){
            DisplayState.Idle,
            DisplayState.Success -> {

                Row(
                    modifier = Modifier.padding(top = 40.dp,start = 20.dp),
                ) {
                    IconButton(
                        onClick = {
                            onAction(BillAction.OnClickDropdown(true))
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = ""
                        )
                    }
                    DropdownMenu(
                        expanded = billState.expandedDropdown,
                        onDismissRequest = {onAction(BillAction.OnClickDropdown(false))},
                        offset = DpOffset(10.dp, 0.dp)
                    ) {
                        DropdownMenuItem(
                            text ={
                                Text(
                                    text = stringResource(R.string.option_title_all)
                                )
                            },
                            onClick = { onAction(BillAction.OnClickDropdownItem(""))}
                        )
                        DropdownMenuItem(
                            text ={
                                Text(
                                    text = stringResource(R.string.option_title_paid)
                                )
                            },
                            onClick = { onAction(BillAction.OnClickDropdownItem("PAID")) }
                        )
                        DropdownMenuItem(
                            text ={
                                Text(
                                    text = stringResource(R.string.option_title_valid)
                                )
                            },
                            onClick = { onAction(BillAction.OnClickDropdownItem("VALID")) }
                        )
                        DropdownMenuItem(
                            text ={
                                Text(
                                    text = stringResource(R.string.option_title_canceled)
                                )
                            },
                            onClick = { onAction(BillAction.OnClickDropdownItem("CANCELED")) }
                        )
                    }
                }


                val sizeBills = billState.displayedBills.size
                if(sizeBills > 0){

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 70.dp),
                        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 40.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(sizeBills) { item ->
                            val bill: CustomerBill = billState.displayedBills[item]
                            BillCard(bill,onClickDetail)
                        }
                    }
                }
                else {

                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                        ),
                        border = BorderStroke(width = 1.dp, color = Color.White),
                        shape = RoundedCornerShape(size = 16.dp),
                        modifier = Modifier
                            .padding(start= 50.dp, top=85.dp)
                            .size(
                                width = 300.dp,
                                height = 100.dp
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.title_no_bills),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = algerianFontFamily,
                            fontSize = 28.sp,
                            fontWeight =FontWeight.Normal,
                            modifier = Modifier.padding(
                                start = 25.dp,
                                top= 30.dp
                            )
                        )
                    }
                }
            }
            DisplayState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            DisplayState.Exit -> {}
            is DisplayState.Error ->{
                val error = billState.state.messageData!!
                val action = BillAction.fromAttempts(billState.attempts,onClickBack)
                KycAlertDialog(
                    messageData = error,
                    dismissDialog = {onAction(action)})
            }
        }
    }
}

@Preview
@Composable
fun BillViewPreview(){

    val customerBills = ArrayList<CustomerBill>()
    val customerBill = CustomerBill(
        id = 1, taxes = 10.2, subtotal = 20.0,total = 300.0, settled = false,
        status = "PAID", issueDate = "", billingStartDate = "", billingFinishDate = "",
        paymentDueDate =  "", settlementDate = ""
    )
    customerBills.add(customerBill)
    customerBills.add(customerBill)
    customerBills.add(customerBill)
    customerBills.add(customerBill)
    customerBills.add(customerBill)

    BillView(billState = BillsState(bills = customerBills, displayedBills = customerBills,
        state = DisplayState.Idle))
}