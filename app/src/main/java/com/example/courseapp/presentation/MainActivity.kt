package com.example.courseapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.courseapp.common.AppDataStoreManager
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.navigation.NavGraph
import com.example.courseapp.presentation.ui.theme.CourseAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: AppDataStoreManager
    private var currentToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userVerificationModel: UserVerificationModel by viewModels()

        lifecycleScope.launch(Dispatchers.Main) {

            currentToken = userVerificationModel.tokenFlow.firstOrNull()
            Log.d(
                "Collected",
                " Collected token in MAINaCTIVITY :${currentToken} "
            )



            setContent {
                var starDestination by remember {
                    mutableStateOf(Constants.TEACHER_SCREEN_ROUTE)
                }
                if (currentToken.isNullOrEmpty()) starDestination =
                    Constants.LOGIN_SIGNUP_SCREEN_ROUTE


                val navController = rememberNavController()
                CourseAppTheme {
                    NavGraph(
                        navController = navController,
                        starDestination = starDestination
                    )
                }
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content))
        { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom) //complete on the manifests (Resize)
            insets
        }
    }
}
