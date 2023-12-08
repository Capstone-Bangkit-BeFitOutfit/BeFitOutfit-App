package com.bangkit.befitoutfit.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.bangkit.befitoutfit.helper.TextFieldType
import com.bangkit.befitoutfit.ui.screen.Screen
import com.bangkit.befitoutfit.ui.theme.BeFitOutfitTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinContext

@OptIn(
    ExperimentalMaterial3Api::class,
)
class BeFitOutfitAppTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        rule.setContent {
            navController = TestNavHostController(
                context = LocalContext.current,
            ).apply {
                navigatorProvider.addNavigator(
                    navigator = ComposeNavigator(),
                )
            }

            KoinContext {
                BeFitOutfitTheme {
                    BeFitOutfitApp(
                        context = LocalContext.current,
                        navController = navController,
                    )
                }
            }
        }
    }

    @Test
    fun test_MyOutfitScreenDisplayedAsStartDestination_Success() {
        navController.assertCurrentRouteIs(
            expected = Screen.MyOutfit.route,
        )
    }

    @Test
    fun test_MyOutfitScreenDisplayedAsStartDestination_Failure() {
        navController.assertCurrentRouteIsNot(
            Screen.Login.route,
            Screen.Register.route,
            Screen.Recommend.route,
        )
    }

    @Test
    fun test_RecommendScreenDisplayedWhenClick_Success() {
        rule.onNodeWithContentDescription(
            label = Screen.Recommend.route,
            useUnmergedTree = true,
        ).performClick()

        navController.assertCurrentRouteIs(
            expected = Screen.Recommend.route,
        )
    }

    @Test
    fun test_RecommendScreenDisplayedWhenClick_Failure() {
        rule.onNodeWithContentDescription(
            label = Screen.Recommend.route,
            useUnmergedTree = true,
        ).performClick()

        navController.assertCurrentRouteIsNot(
            Screen.Login.route,
            Screen.Register.route,
            Screen.MyOutfit.route,
        )
    }

    @Test
    fun test_LoginScreenDisplayedWhenClick_Success() {
        rule.logout()

        navController.assertCurrentRouteIs(
            expected = Screen.Login.route,
        )
    }

    @Test
    fun test_LoginScreenDisplayedWhenClick_Failure() {
        rule.logout()

        navController.assertCurrentRouteIsNot(
            Screen.Register.route,
            Screen.MyOutfit.route,
            Screen.Recommend.route,
        )
    }

    @Test
    fun test_RegisterScreenDisplayedWhenClick_Success() {
        rule.apply {
            logout()

            onNodeWithText(
                text = "Register",
            ).performClick()
        }

        navController.assertCurrentRouteIs(
            expected = Screen.Register.route,
        )
    }

    @Test
    fun test_RegisterScreenDisplayedWhenClick_Failure() {
        rule.apply {
            logout()

            onNodeWithText(
                text = "Register",
            ).performClick()
        }

        navController.assertCurrentRouteIsNot(
            Screen.Login.route,
            Screen.MyOutfit.route,
            Screen.Recommend.route,
        )
    }

    @Test
    fun test_ContentProfileDisplayedWhenClick_Success() {
        rule.apply {
            onNodeWithContentDescription(
                label = "Profile",
            ).performClick()

            onNodeWithText(
                text = TextFieldType.Name.type,
            ).assertExists()

            onNodeWithText(
                text = TextFieldType.Email.type,
            ).assertExists()

            onNodeWithText(
                text = "Update",
            ).assertExists()
        }
    }

    @Test
    fun test_ContentProfileDisplayedWhenClick_Failure() {
        rule.apply {
            onNodeWithContentDescription(
                label = "Profile",
            ).performClick()

            onNodeWithText(
                text = TextFieldType.Password.type,
            ).assertDoesNotExist()

            onNodeWithText(
                text = TextFieldType.OutfitName.type,
            ).assertDoesNotExist()

            onNodeWithText(
                text = "Add",
            ).assertDoesNotExist()
        }
    }

    @Test
    fun test_ContentAddOutfitDisplayedWhenClick_Success() {
        rule.apply {
            onNodeWithContentDescription(
                label = Screen.MyOutfit.fabTitle,
            ).performClick()

            onNodeWithText(
                text = TextFieldType.OutfitName.type,
            ).assertExists()

            onNodeWithText(
                text = "Outfit type",
            ).assertExists()

            onNodeWithText(
                text = "Include this outfit in recommendation",
            ).assertExists()

            onNodeWithText(
                text = "Add",
            ).assertExists()
        }
    }

    @Test
    fun test_ContentAddOutfitDisplayedWhenClick_Failure() {
        rule.apply {
            onNodeWithContentDescription(
                label = Screen.MyOutfit.fabTitle,
            ).performClick()

            onNodeWithText(
                text = "Update",
            ).assertDoesNotExist()
        }
    }

    @Test
    fun test_ContentSettingRecommendDisplayedWhenClick_Success() {
        rule.apply {
            onNodeWithContentDescription(
                label = Screen.Recommend.route,
                useUnmergedTree = true,
            ).performClick()

            onNodeWithContentDescription(
                label = Screen.Recommend.fabTitle,
            ).performClick()

            onNodeWithText(
                text = "Event",
            ).assertExists()
        }
    }

    @Test
    fun test_ContentSettingRecommendDisplayedWhenClick_Failure() {
        rule.apply {
            onNodeWithContentDescription(
                label = Screen.Recommend.route,
                useUnmergedTree = true,
            ).performClick()

            onNodeWithContentDescription(
                label = Screen.Recommend.fabTitle,
            ).performClick()

            onNodeWithText(
                text = "Add",
            ).assertDoesNotExist()

            onNodeWithText(
                text = "Update",
            ).assertDoesNotExist()
        }
    }

    companion object {
        private fun NavController.assertCurrentRouteIs(
            expected: String,
        ) {
            assertEquals(expected, currentBackStackEntry?.destination?.route)
        }

        private fun NavController.assertCurrentRouteIsNot(
            vararg expected: String,
        ) {
            expected.forEach {
                assertNotEquals(it, currentBackStackEntry?.destination?.route)
            }
        }

        private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.logout() {
            onNodeWithContentDescription(
                label = "Logout",
                useUnmergedTree = true,
            ).performClick()
        }
    }
}
