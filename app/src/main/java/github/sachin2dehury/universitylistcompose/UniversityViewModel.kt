package github.sachin2dehury.universitylistcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class UniversityViewModel @Inject constructor(private val repository: UniversityRepository) :
    ViewModel() {

    private val viewModelIOScope = viewModelScope + Dispatchers.IO

    private val _data = MutableStateFlow<ResultState>(ResultState.Empty)
    val data = _data.asStateFlow()

    fun getUniversityList(country: String) = viewModelIOScope.launch {
        _data.value = ResultState.Loading
        if (country.isEmpty()) {
            _data.value = ResultState.Error("Enter a Country Name")
            return@launch
        }
        val response = repository.getUniversityList(country)
        _data.value = if (response.isSuccessful && response.code() == 200) {
            ResultState.Success(response.body())
        } else {
            ResultState.Error(response.message())
        }
    }
}
