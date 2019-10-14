package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo

class WhatsNewService ( val repo : WhatsNewRepo) {

    fun getByVersion ( version : Float) : MutableList<WhatsNew>
    {
        return repo.getByVersion(version)
    }

    fun addNewWhatNew ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNew(whatsNewOb)
    }
}