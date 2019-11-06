package ca.etsmtl.applets.notre_dame

import ca.etsmtl.applets.notre_dame.config.*
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.JwtConfig
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import com.typesafe.config.ConfigFactory
import io.ktor.server.engine.embeddedServer
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.event.Level

fun main(args: Array<String>) {

    val kodein = Kodein {
        bind<App>() with  eagerSingleton { App(kodein) }
    }
     var app : App = kodein.direct.instance()
    app.startApp()
}


class App (override val kodein: Kodein) : KodeinAware
{
    fun startApp(){
        val config = HoconApplicationConfig(ConfigFactory.load())
        val port = config.property("ktor.deployment.port").getString().toInt()
        embeddedServer(Netty, port = port) {
            kodeinApplication {
                module()
                import(common)
                import(whatsNewRepo)
                import(whatsNewService)
                import(whatsNewSController)
                import(usersRepo)
                import(usersService)
                import(usersController)
            }
        }.start(wait = true)
    }

    fun Application.module() {
         val repo: UsersRepo by instance("usersRepo")
        install(CallLogging) {
            level = Level.INFO
        }
        install(DefaultHeaders)

        install(Authentication) {
            jwt {
                authSchemes("Token")
                verifier(JwtConfig.verifier)
                validate {
                    it.payload.claims.forEach(::println)
                    val userName = it.payload.getClaim("userName")?.asString() ?: return@validate null
                    println("Required: $userName")
                    repo.findByUserName(userName)?.let { user ->
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

    fun Application.kodeinApplication(kodeinMapper: Kodein.MainBuilder.(Application) -> Unit = {}) {
        val app = this
        Kodein {
            bind<Application>() with singleton { app }
            kodeinMapper(this, app)
        }
    }
}
