package github.sachin2dehury.universitylistcompose

sealed interface ResultState {
    data class Success(val data: List<UniversityResponseItem>? = null) : ResultState
    data class Error(val msg: String? = null) : ResultState
    data object Loading : ResultState
    data object Empty : ResultState
}
