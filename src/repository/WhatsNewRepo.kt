package ca.etsmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.utils.Property
import com.mongodb.MongoClient
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.litote.kmongo.*

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class WhatsNewRepo (val client: MongoClient){
      val databaseName  = Property["db.name"]
      val whatsNewCollName =Property["db.whatsNewCollection"]
      val whatsNewCollection = client.getDatabase(databaseName).getCollection<WhatsNew>(whatsNewCollName)

    fun getByVersion( version : Float) : MutableList<WhatsNew>
    {
        return whatsNewCollection.find(WhatsNew::version eq version).toMutableList()
    }

    fun addWhatNew ( whatnewOb : WhatsNew) :Unit
    {
        return whatsNewCollection.insertOne(whatnewOb)
    }

    fun getAllWhatsNew() : MutableList<WhatsNew>
    {
        return whatsNewCollection.find().toMutableList();
    }
}