package com.example.kdemo.controller

import com.example.kdemo.aop.Foozy
import com.example.kdemo.entity.User
import com.example.kdemo.repository.UserRepository
import com.example.kdemo.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("user")
class UserController {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService


    @PostMapping("/add")
    @ResponseBody
    fun addNewUser(@RequestParam name: String, @RequestParam email: String): String {
        val u = User(name = name, address = email)
        userRepository.save(u)
        return "saved"
    }

    @DeleteMapping("/delete")
    @ResponseBody
    fun deleteUser(@RequestParam id: Int) {
        return userRepository.deleteById(id)
    }

    @PutMapping("/save")
    @ResponseBody
    fun saveUser(@RequestBody user: User): User {
        return userRepository.save(user)
    }

    @GetMapping("/all")
    @ResponseBody
    @Foozy
    fun getAllUsers(): MutableIterable<User> {
        logger.debug("find all users")
        return userRepository.findAll()
    }

    @GetMapping("/find")
    @ResponseBody
    fun getUserById(@RequestParam id: Int): Optional<User> {
        return userRepository.findById(id)
    }
}
