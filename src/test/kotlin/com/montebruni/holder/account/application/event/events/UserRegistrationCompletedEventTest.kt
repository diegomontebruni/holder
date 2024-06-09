package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createCustomer
import com.montebruni.holder.fixtures.createUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserRegistrationCompletedEventTest {

    @Test
    fun `should get data from UserRegistrationCompletedEvent successfully`() {
        val user = createUser()
        val customer = createCustomer()

        val eventData = UserRegistrationCompletedEvent(user, customer).getData()

        assertThat(eventData.userId).isEqualTo(user.id)
        assertThat(eventData.username).isEqualTo(user.username)
        assertThat(eventData.status).isEqualTo(user.status)
        assertThat(eventData.name).isEqualTo(customer.name)
        assertThat(eventData.email).isEqualTo(customer.email)
    }
}
