package ru.shanin.madarareddit.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.shanin.madarareddit.networking.models.ChildTopModel

class UserRepository {

    fun getTop(): Flow<List<ChildTopModel>> = flow { emit(Networking.githubApi.getTop().data.children) }

    fun getTopWithIndex(after: String): Flow<List<ChildTopModel>> = flow {
        emit(Networking.githubApi.getTopWithIndex(after).data.children)
    }


    suspend fun vote(id: String, dir: String) {
        return Networking.githubApi.vote(id, dir)
    }
}
