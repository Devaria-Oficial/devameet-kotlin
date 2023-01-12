package br.com.devaria.devameet.devameetkotlin.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val email: String = "",
    var name: String = "",
    var password: String = "",
    val avatar: String = ""
)
