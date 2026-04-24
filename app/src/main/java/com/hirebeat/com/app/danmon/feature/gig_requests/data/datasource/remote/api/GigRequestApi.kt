package com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.api

import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.CreateGigRequestDto
import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.CreateGigRequestResponse
import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.GigRequestResponseDto
import com.hirebeat.com.app.danmon.feature.gig_requests.data.datasource.remote.model.UpdateStatusDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GigRequestApi {
    @POST("/requests")
    suspend fun createGigRequest(@Body request: CreateGigRequestDto): Response<CreateGigRequestResponse>

    @GET("/requests/recruiter")
    suspend fun getRecruiterRequests(): Response<List<GigRequestResponseDto>>

    @GET("/requests/musician")
    suspend fun getMusicianRequests(): Response<List<GigRequestResponseDto>>

    @PUT("/requests/{id}/status")
    suspend fun updateRequestStatus(
        @Path("id") id: String,
        @Body status: UpdateStatusDto
    ): Response<String>
}