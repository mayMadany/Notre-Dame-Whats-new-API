package ca.etsmtl.applets.notre_dame.config

import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.JwtConfig
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ServerModule (override val kodein: Kodein) : KodeinAware {

    private val repo :UsersRepo  by instance("usersRepo")

    fun Application.module() {
        install(Authentication) {
            jwt {
                authSchemes("Token")
                verifier(JwtConfig.verifier)
                validate {
                    it.payload.claims.forEach(::println)
                    val email = it.payload.getClaim("email")?.asString() ?: return@validate null
                    println("Required: $email")
                    repo.findByUserName(email)?.let { user ->
                        val token = JwtConfig.makeToken(user)
                        user.copy(token = token)
                    }
                }
            }
        }

        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }
}

