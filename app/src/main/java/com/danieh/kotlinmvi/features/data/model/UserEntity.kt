package com.danieh.kotlinmvi.features.data.model

import com.danieh.kotlinmvi.features.domain.model.User

data class UserEntity(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address?,
    val phone: String,
    val website: String,
    val company: Company?
) {
    companion object {
        fun empty() = UserEntity(0, "", "", "", null, "", "", null)
    }

    fun toUser() = User(id, name, username, email)

    data class Address(
        val city: String,
        val geo: Geo?,
        val street: String,
        val suite: String,
        val zipcode: String
    )

    data class Company(
        val bs: String,
        val catchPhrase: String,
        val name: String
    )

    data class Geo(
        val lat: String,
        val lng: String
    )
}
