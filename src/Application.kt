package ca.etsmtl.applets.notre_dame

import ca.etsmtl.applets.notre_dame.config.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.jackson.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
fun main(args: Array<String>){
    val config = HoconApplicationConfig(ConfigFactory.load())
    val serverLogger: Logger = LoggerFactory.getLogger("Server")
    val port =config.property("ktor.deployment.port").getString().toInt()
    embeddedServer(Netty, port = port){
        kodeinApplication{
          module()
          import (common)
          import (whatsNewRepo)
          import (whatsNewService)
          import (whatsNewSController)
          import (usersRepo)
          import (usersService)
          import (usersController)
        }
    }.start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

fun Application.kodeinApplication(kodeinMapper : Kodein.MainBuilder.(Application) -> Unit = {}) {
    val app = this
    Kodein {
        bind<Application>() with singleton { app }
        kodeinMapper(this, app)
    }
}
