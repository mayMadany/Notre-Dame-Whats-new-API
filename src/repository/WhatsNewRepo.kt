package ca.etsmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import com.mongodb.MongoClient
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import org.litote.kmongo.*

class WhatsNewRepo (val client: MongoClient){
      val config = HoconApplicationConfig(ConfigFactory.load())
      val databaseName  = config.property("database.name").getString()
      val whatsNewCollName = config.property("database.whatsNewCollection").getString()
      val whatsNewCollection = client.getDatabase(databaseName).getCollection<WhatsNew>(whatsNewCollName)

    fun getByVersion( version : Float) : MutableList<WhatsNew>
    {
        return whatsNewCollection.find(WhatsNew::version eq version).toMutableList()
    }

    fun addWhatNew ( whatnewOb : WhatsNew) :Unit
    {
        return whatsNewCollection.insertOne(whatnewOb)
    }
}