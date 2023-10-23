package github.sachin2dehury.universitylistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import github.sachin2dehury.universitylistcompose.ui.theme.UniversityListComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UniversityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversityListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        UserCountryInput(callback = viewModel::getUniversityList)
                        UniversityList(data = viewModel.data.collectAsState().value)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun UserCountryInput(modifier: Modifier = Modifier, callback: (String) -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        var userText by remember {
            mutableStateOf("")
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = userText,
            onValueChange = {
                userText = it
            },
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp),
            onClick = { callback.invoke(userText) },
        ) {
            Text(text = "Check Universities")
        }
    }
}

@Preview
@Composable
fun UniversityList(
    modifier: Modifier = Modifier,
    data: ResultState = ResultState.Empty,
) {
    when (data) {
        ResultState.Empty -> {
            // empty
        }

        is ResultState.Error -> {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Text(text = data.msg ?: "Something went wrong!")
            }
        }

        ResultState.Loading -> {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        }

        is ResultState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .padding(12.dp),
            ) {
                items(data.data.orEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color.LightGray)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Text(text = it.name.orEmpty())
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(4.dp),
                        )
                        Text(text = it.stateProvince.orEmpty())
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(4.dp),
                        )
                        Text(text = it.domains.toString())
                    }
                }
            }
        }
    }
}
