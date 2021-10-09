package ru.shanin.madarareddit.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.shanin.madarareddit.networking.models.TopData

class UserRepository {

    fun getTopWithIndex(before: String?, after: String?, count: Int?): Flow<TopData> = flow {
        emit(Networking.githubApi.getTopWithIndex(before, after, count).data)
    }


    suspend fun vote(id: String, dir: String) {
        return Networking.githubApi.vote(id, dir)
    }
}
