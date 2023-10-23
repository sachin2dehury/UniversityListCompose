package github.sachin2dehury.universitylistcompose

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UniversityResponseItem(
    @Json(name = "alpha_two_code")
    val alphaTwoCode: String? = null,
    val country: String? = null,
    val domains: List<String?>? = null,
    val name: String? = null,
    @Json(name = "state-province")
    val stateProvince: String? = null,
    @Json(name = "web_pages")
    val webPages: List<String?>? = null,
)
