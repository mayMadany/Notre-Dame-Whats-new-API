package ca.etsmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.utils.Property
import com.mongodb.MongoClient
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.set

class UsersRepo (val client: MongoClient) {
    val databaseName  = Property["db.name"]
    val usersCollName = Property["db.usersCollection"]
    val usersCollection = client.getDatabase(databaseName).getCollection<User>(usersCollName)

    fun addUser ( userOb : User) :Unit
    {
        return usersCollection.insertOne(userOb)
    }

    fun getAllUsers() : MutableList<User>
    {
        return usersCollection.find().toMutableList();
    }

    fun findByUserName ( userName : String) : User?
    {
        return usersCollection.findOne(User::userName eq userName)
    }

    fun updateUserToken( user :User): UpdateResult
    {
        return usersCollection.updateOne( User::id eq user.id, Updates.set("token", user.token))
    }
}