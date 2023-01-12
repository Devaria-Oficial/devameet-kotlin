package br.com.devaria.devameet.devameetkotlin.repositories

import br.com.devaria.devameet.devameetkotlin.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email:String) : User?
}