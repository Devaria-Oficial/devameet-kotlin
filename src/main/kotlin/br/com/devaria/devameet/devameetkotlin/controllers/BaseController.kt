package br.com.devaria.devameet.devameetkotlin.controllers

import br.com.devaria.devameet.devameetkotlin.dtos.DefaultResponseMsgDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class BaseController {

    fun formatErrorResponse(statusCode: HttpStatus, message: Array<String>): ResponseEntity<Any>
        = ResponseEntity(DefaultResponseMsgDto(statusCode.value(), message, statusCode.toString()), statusCode)
}