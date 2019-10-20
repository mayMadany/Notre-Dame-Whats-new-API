package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class WhatsNewService (val repo : WhatsNewRepo) {

    fun getByVersion ( version : Float) : MutableList<WhatsNew>
    {
        return repo.getByVersion(version)
    }

    fun addNewWhatsNew ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNew(whatsNewOb)
    }

    fun getallWhatsNew () :MutableList<WhatsNew>
    {
        return repo.getAllWhatsNew()
    }
}