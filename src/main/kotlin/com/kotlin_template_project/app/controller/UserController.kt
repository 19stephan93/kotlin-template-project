package com.kotlin_template_project.app.controller

import com.kotlin_template_project.app.dto.UserDto
import com.kotlin_template_project.app.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController {
    @Autowired
    lateinit var userService: IUserService<UserDto>

    @GetMapping("/")
    fun findAll() = userService.findAll()

    @GetMapping("/{username}")
    fun findByUsername(@PathVariable username: String): UserDto {
        val resultOpt : Optional<UserDto> = userService.findByUsername(username)
        if (resultOpt.isPresent) {
            return resultOpt.get()
        }
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
    }
}