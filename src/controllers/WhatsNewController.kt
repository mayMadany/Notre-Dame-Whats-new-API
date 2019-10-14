package ca.etsmtl.applets.notre_dame.controllers
import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.service.WhatsNewService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import io.ktor.routing.get
import io.ktor.routing.post

class WhatsNewController (override val kodein: Kodein) : KodeinAware{
    private val app : Application by instance()
    private val service: WhatsNewService by instance("whatsNewService")
    init {
        app.routing {
            get("/whatsNew/{version}")  {
                val version : Float = call.parameters["version"]?.toFloat() ?: 0.0f
                call.respond(service.getByVersion(version))
            }
            post ("/whatsNew"){
                val newWhatsNew = call.receive<WhatsNew>()
                call.respond(service.addNewWhatNew(newWhatsNew))
            }
        }
    }
}