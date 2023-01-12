package br.com.devaria.devameet.devameetkotlin.controllers

import br.com.devaria.devameet.devameetkotlin.dtos.DefaultResponseMsgDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class BaseController(c:String) {

    protected val log = LoggerFactory.getLogger(c)

    fun formatErrorResponse(statusCode: HttpStatus, message: Array<String>): ResponseEntity<Any>
        = ResponseEntity(DefaultResponseMsgDto(statusCode.value(), message, statusCode.toString()), statusCode)
}