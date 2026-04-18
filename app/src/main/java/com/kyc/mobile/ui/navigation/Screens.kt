package com.kyc.mobile.ui.navigation

import com.kyc.mobile.domain.model.CustomerBill
import kotlinx.serialization.Serializable

@Serializable
object Permissions
@Serializable
object Intro

@Serializable
object Login

@Serializable
object Home

@Serializable
object Notifications

@Serializable
object Bills

@Serializable
data class BillDetail(val bill: CustomerBill)

@Serializable
object Offers