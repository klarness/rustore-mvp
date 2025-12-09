package com.example.rustore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rustore.ui.theme.RuStoreTheme

private const val ROUTE_ONBOARDING = "onboarding"
private const val ROUTE_LIST = "list"
private const val ROUTE_CATEGORIES = "categories"
private const val ROUTE_DETAIL = "detail"
private const val ROUTE_SCREENSHOTS = "screenshots"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = getSharedPreferences("rustore_prefs", Context.MODE_PRIVATE)
        val onboardingShown = prefs.getBoolean("onboarding_shown", false)

        setContent {
            var showOnboarding by rememberSaveable { mutableStateOf(!onboardingShown) }

            LaunchedEffect(showOnboarding) {
                if (!showOnboarding) {
                    prefs.edit().putBoolean("onboarding_shown", true).apply()
                }
            }

            RuStoreApp(
                showOnboarding = showOnboarding,
                onOnboardingFinished = { showOnboarding = false }
            )
        }
    }
}

@Composable
fun RuStoreApp(
    showOnboarding: Boolean,
    onOnboardingFinished: () -> Unit
) {
    RuStoreTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RuStoreNavHost(
                showOnboarding = showOnboarding,
                onOnboardingFinished = onOnboardingFinished
            )
        }
    }
}

@Composable
fun RuStoreNavHost(
    showOnboarding: Boolean,
    onOnboardingFinished: () -> Unit
) {
    val navController = rememberNavController()

    var selectedCategory by rememberSaveable { mutableStateOf<AppCategory?>(null) }
    var selectedAppId by rememberSaveable { mutableStateOf<Int?>(null) }
    var screenshotStartIndex by rememberSaveable { mutableStateOf(0) }

    val startDestination = if (showOnboarding) ROUTE_ONBOARDING else ROUTE_LIST

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(
                onContinue = {
                    onOnboardingFinished()
                    navController.navigate(ROUTE_LIST) {
                        popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTE_LIST) {
            val apps = FakeAppRepository.getApps(selectedCategory)
            AppListScreen(
                apps = apps,
                selectedCategory = selectedCategory,
                onAppClick = { app ->
                    selectedAppId = app.id
                    navController.navigate(ROUTE_DETAIL)
                },
                onOpenCategories = {
                    navController.navigate(ROUTE_CATEGORIES)
                },
                onResetFilter = {
                    selectedCategory = null
                }
            )
        }

        composable(ROUTE_CATEGORIES) {
            val categories = FakeAppRepository.getCategoriesWithCounts()
            CategoriesScreen(
                categories = categories,
                onAllClick = {
                    selectedCategory = null
                    navController.popBackStack()
                },
                onCategoryClick = { category ->
                    selectedCategory = category
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(ROUTE_DETAIL) {
            val appId = selectedAppId
            val app = if (appId != null) FakeAppRepository.getAppById(appId) else null
            if (app == null) {
                navController.popBackStack()
            } else {
                AppDetailScreen(
                    app = app,
                    onBack = { navController.popBackStack() },
                    onScreenshotClick = { index ->
                        screenshotStartIndex = index
                        navController.navigate(ROUTE_SCREENSHOTS)
                    }
                )
            }
        }

        composable(ROUTE_SCREENSHOTS) {
            val appId = selectedAppId
            val app = if (appId != null) FakeAppRepository.getAppById(appId) else null
            if (app == null) {
                navController.popBackStack()
            } else {
                ScreenshotGalleryScreen(
                    app = app,
                    startIndex = screenshotStartIndex,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
