package br.com.devaria.devameet.devameetkotlin.controllers

import br.com.devaria.devameet.devameetkotlin.dtos.LoginRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/login")
class LoginController : BaseController(LoginController::class.java.toString()) {

    @PostMapping
    fun doLogin(@RequestBody dto: LoginRequestDto) : ResponseEntity<Any> {
        var messages = mutableListOf<String>()
        try{
            if(dto.login.isNullOrBlank() || dto.login.isNullOrEmpty() ||
                dto.password.isNullOrBlank() || dto.password.isNullOrEmpty()){
                messages.add("Favor preencher os campos")
                return formatErrorResponse(HttpStatus.BAD_REQUEST, messages.toTypedArray())
            }

            if(dto.login == "teste@teste.com" && dto.password == "Teste@123"){
                return ResponseEntity(dto, HttpStatus.OK)
            }

            messages.add("Usuario e senha não encontrados")
            return formatErrorResponse(HttpStatus.BAD_REQUEST, messages.toTypedArray())
        }catch (e: Exception){
            messages.add("Ocorreu erro ao efetuaro login")
            return formatErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, messages.toTypedArray())
        }
    }
}