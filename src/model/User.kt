package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles
import java.util.*

data class User ( val id : UUID= UUID.randomUUID(), var userName : String, var role : Roles, var password : String ){

}