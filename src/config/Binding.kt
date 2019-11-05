package ca.etsmtl.applets.notre_dame.config

import ca.etsmtl.applets.notre_dame.controllers.UsersController
import ca.etsmtl.applets.notre_dame.controllers.WhatsNewController
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import ca.etsmtl.applets.notre_dame.service.UsersService
import ca.etsmtl.applets.notre_dame.service.WhatsNewService
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.KMongo

/*** MongoDb********************************/
val common = Kodein.Module(name = "common") {
    bind("mongoClient") from singleton { KMongo.createClient() }
}

/*** WhatsNew********************************/
@UseExperimental(KtorExperimentalLocationsAPI::class)
val whatsNewRepo = Kodein.Module(name = "whatsNewRepo") {
    bind(tag = "whatsNewRepo") from singleton { WhatsNewRepo(instance("mongoClient")) }
}

@UseExperimental(KtorExperimentalAPI::class)
val whatsNewService = Kodein.Module(name = "whatsNewService") {
    bind(tag = "whatsNewService") from singleton { WhatsNewService(instance("whatsNewRepo")) }
}

@UseExperimental(KtorExperimentalLocationsAPI::class)
val whatsNewSController = Kodein.Module(name = "whatsNewSController") {
    bind(tag = "whatsNewSController") from eagerSingleton { WhatsNewController(kodein) }
}
/*** Users********************************/
val usersRepo = Kodein.Module(name = "usersRepo") {
    bind(tag = "usersRepo") from singleton { UsersRepo(instance("mongoClient")) }
}

val usersService = Kodein.Module(name = "usersService") {
        bind(tag = "usersService") from singleton { UsersService(instance("usersRepo")) }
}

val usersController = Kodein.Module(name = "usersController") {
        bind(tag = "usersController") from eagerSingleton { UsersController(kodein) }
}

val serverModule = Kodein.Module (name = "serverModule"){
    bind(tag="serverModule") from eagerSingleton { ServerModule (kodein) }
}
