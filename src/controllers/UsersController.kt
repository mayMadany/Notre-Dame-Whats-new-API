package ca.etsmtl.applets.notre_dame.controllers

import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.service.UsersService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.generic.instance
import io.ktor.routing.get
import io.ktor.routing.post

class UsersController (override val kodein: Kodein) : KodeinAware {
    private val app : Application by instance()
    private val service: UsersService by instance("usersService")
    init {
        app.routing {
            get("/users"){
                call.respond(service.getAllUsers())
            }
            post ("/users"){
                val newUser = call.receive<User>()
                call.respond(service.addNewUser(newUser))
            }
        }
    }
}