package ca.etsmtl.applets.notre_dame.config

import ca.etsmtl.applets.notre_dame.controllers.WhatsNewController
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import ca.etsmtl.applets.notre_dame.service.WhatsNewService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.KMongo

val common = Kodein.Module( name = "common"){
    bind("mongoClient") from singleton { KMongo.createClient() }
}

val whatsNewRepo = Kodein.Module(name = "whatsNewRepo") {
    bind(tag = "whatsNewRepo") from singleton { WhatsNewRepo(instance("mongoClient")) }
}

val whatsNewService = Kodein.Module(name = "whatsNewService") {
    bind(tag="whatsNewService") from singleton { WhatsNewService(instance("whatsNewRepo")) }
}

val whatsNewSController = Kodein.Module(name = "whatsNewSController") {
    bind(tag="whatsNewSController") from eagerSingleton { WhatsNewController(kodein) }
}