package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ads.AdMobManager
import com.example.ads.BannerAdView
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MotivationTheme
import com.example.ui.viewmodel.MotivationViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Preload Interstitial and Rewarded Ads quietly in background
        // STRICT AD RULE: Never show when the app opens.
        AdMobManager.loadInterstitialAd(this)
        AdMobManager.loadRewardedInterstitialAd(this)

        setContent {
            val app = application as MotivationApp
            val viewModel: MotivationViewModel = viewModel(
                factory = MotivationViewModel.Factory(app.repository)
            )

            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val isDarkTheme = when (themeMode) {
                "DARK" -> true
                "LIGHT" -> false
                else -> isSystemInDarkTheme()
            }

            MotivationTheme(darkTheme = isDarkTheme) {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: MotivationViewModel) {
    val context = LocalContext.current
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Request POST_NOTIFICATIONS permission on Android 13+
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.toggleNotification(context, true)
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Observe snackbar messages
    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                // STRICT AD RULE:
                // Banner Ad: Show ONLY at the bottom of the Home/Quote screen.
                // Do NOT show on Splash, Settings, or other screens. Never cover content.
                AnimatedVisibility(
                    visible = selectedTab == 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    BannerAdView()
                }

                // M3 Navigation Bar
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("main_navigation_bar"),
                    tonalElevation = 6.dp
                ) {
                    // Tab 0: Home / Daily Quotes
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { viewModel.selectTab(0) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == 0) Icons.Default.AutoAwesome else Icons.Outlined.AutoAwesome,
                                contentDescription = "Daily Rise"
                            )
                        },
                        label = { Text("Daily Rise") },
                        modifier = Modifier.testTag("nav_item_home")
                    )

                    // Tab 1: Categories
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { viewModel.selectTab(1) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == 1) Icons.Default.Category else Icons.Outlined.Category,
                                contentDescription = "Categories"
                            )
                        },
                        label = { Text("Categories") },
                        modifier = Modifier.testTag("nav_item_categories")
                    )

                    // Tab 2: Favorites
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { viewModel.selectTab(2) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == 2) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorites"
                            )
                        },
                        label = { Text("Favorites") },
                        modifier = Modifier.testTag("nav_item_favorites")
                    )

                    // Tab 3: Settings
                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { viewModel.selectTab(3) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == 3) Icons.Default.Settings else Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                        },
                        label = { Text("Settings") },
                        modifier = Modifier.testTag("nav_item_settings")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(viewModel = viewModel)
                1 -> CategoriesScreen(
                    viewModel = viewModel,
                    onCategorySelected = { category ->
                        viewModel.setCategory(category)
                        viewModel.selectTab(0)
                    }
                )
                2 -> FavoritesScreen(viewModel = viewModel)
                3 -> SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
