package ru.shanin.madarareddit.networking

import ru.shanin.madarareddit.networking.models.ChildTopModel

class UserRepository {

    suspend fun getTop(): List<ChildTopModel> {
        return Networking.githubApi.getTop().data.children
    }

    suspend fun getTopWithIndex(after: String): List<ChildTopModel> {
        return Networking.githubApi.getTopWithIndex(after,limit = 25).data.children
    }

    suspend fun vote(id: String, dir: String) {
        return Networking.githubApi.vote(id, dir)
    }
}
