package com.qu3dena.lawconnect.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.qu3dena.lawconnect.android.core.MainScreen
import com.qu3dena.lawconnect.android.shared.contracts.FeatureNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var featureNavGraphs: List<@JvmSuppressWildcards FeatureNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(featureNavGraphs = featureNavGraphs)
        }
    }
}