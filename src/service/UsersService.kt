package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.repository.UsersRepo

class UsersService ( val repo : UsersRepo) {

    fun addNewUser ( whatsNewOb : User) : Unit{
        return repo.addUser(whatsNewOb)
    }

    fun getAllUsers () :MutableList<User>
    {
        return repo.getAllUsers()
    }
}