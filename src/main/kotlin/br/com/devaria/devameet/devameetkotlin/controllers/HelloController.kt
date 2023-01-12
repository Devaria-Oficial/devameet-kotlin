package br.com.devaria.devameet.devameetkotlin.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloController {

    @GetMapping
    fun hello() : String {
        return "Olã mundo, Devaria Kotlin"
    }
}