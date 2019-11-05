package ca.etsmtl.applets.notre_dame

import ca.etsmtl.applets.notre_dame.config.*
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.JwtConfig
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.typesafe.config.ConfigFactory
import io.ktor.auth.jwt.jwt
import io.ktor.config.HoconApplicationConfig
import io.ktor.jackson.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.kodein.di.generic.instance

@UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
fun main(args: Array<String>){
    val config = HoconApplicationConfig(ConfigFactory.load())
    val serverLogger: Logger = LoggerFactory.getLogger("Server")
    val port =config.property("ktor.deployment.port").getString().toInt()
    embeddedServer(Netty, port = port){
        kodeinApplication{
          import (common)
          import (whatsNewRepo)
          import (whatsNewService)
          import (whatsNewSController)
          import (usersRepo)
          import (usersService)
          import (usersController)
          import (serverModule)
        }
    }.start(wait = true)
}


fun Application.kodeinApplication(kodeinMapper : Kodein.MainBuilder.(Application) -> Unit = {}) {
    val app = this
    Kodein {
        bind<Application>() with singleton { app }
        kodeinMapper(this, app)
    }
}
