package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.ApiExceptions.UserAlreadyExists
import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher

class UsersService ( val repo : UsersRepo) {

    fun addNewUser ( user : User) : Unit{
        val checkUserExists=findByUsername(user.userName) != null
        if (checkUserExists)
        {
            throw UserAlreadyExists;
        }
        val hashedPass = BcryptHasher.hashPassword(user.password)
        val user =user.copy( password = hashedPass)
        return repo.addUser(user)
    }

    fun getAllUsers () :MutableList<User>
    {
        return repo.getAllUsers()
    }

    fun findByUsername( userName : String) : User?
    {
        return repo.findByUserName(userName)
    }
}