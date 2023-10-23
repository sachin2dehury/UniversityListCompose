package github.sachin2dehury.universitylistcompose

class UniversityRepository(private val service: UniversityService) {

    suspend fun getUniversityList(country: String) = service.getUniversityList(country)
}
