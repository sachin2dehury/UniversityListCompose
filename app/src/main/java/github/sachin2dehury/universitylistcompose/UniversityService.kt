package github.sachin2dehury.universitylistcompose

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityService {

    @GET("search")
    suspend fun getUniversityList(@Query("country") country: String = "india"): Response<List<UniversityResponseItem>>

    companion object {
        const val BASE_URL = "http://universities.hipolabs.com/"
    }
}
