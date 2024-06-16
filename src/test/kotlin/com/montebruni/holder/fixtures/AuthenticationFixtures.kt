package com.montebruni.holder.fixtures

import com.montebruni.holder.account.presentation.rest.request.InitiatePasswordRecoverRequest

fun createInitiatePasswordRecoverRequest() = InitiatePasswordRecoverRequest(
    username = "john.snow@winterfell.north"
)
