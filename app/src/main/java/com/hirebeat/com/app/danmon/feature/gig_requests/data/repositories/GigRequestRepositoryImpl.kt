package com.hirebeat.com.app.danmon.feature.gig_requests.data.repositories

import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.api.GigRequestApi
import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.CreateGigRequestDto
import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.UpdateStatusDto
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.entities.GigRequestItem
import com.hirebeat.com.app.danmon.feature.gig_requests.domain.repositories.GigRequestRepository
import javax.inject.Inject

class GigRequestRepositoryImpl @Inject constructor(
    private val api: GigRequestApi
) : GigRequestRepository {

    override suspend fun createRequest(
        musicianProfileId: String,
        startTime: String,
        endTime: String,
        location: String,
        paymentOffered: Double,
        messageDetails: String?
    ): Result<Unit> {
        return try {
            val dto = CreateGigRequestDto(
                musicianProfileId = musicianProfileId,
                startTime = startTime,
                endTime = endTime,
                location = location,
                paymentOffered = paymentOffered,
                messageDetails = messageDetails
            )

            val response = api.createGigRequest(dto)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecruiterRequests(): Result<List<GigRequestItem>> {
        return try {
            val response = api.getRecruiterRequests()
            if (response.isSuccessful) {
                val requests = response.body()?.map { it.toDomain() } ?: emptyList()
                Result.success(requests)
            } else {
                Result.failure(Exception("Error al cargar historial"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMusicianRequests(): Result<List<GigRequestItem>> {
        return try {
            val response = api.getMusicianRequests()
            if (response.isSuccessful) {
                val requests = response.body()?.map { it.toDomain() } ?: emptyList()
                Result.success(requests)
            } else {
                Result.failure(Exception("Error al cargar bandeja"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateRequestStatus(id: String, status: String): Result<Unit> {
        return try {
            val response = api.updateRequestStatus(id, UpdateStatusDto(status))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("No se pudo actualizar el estado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}