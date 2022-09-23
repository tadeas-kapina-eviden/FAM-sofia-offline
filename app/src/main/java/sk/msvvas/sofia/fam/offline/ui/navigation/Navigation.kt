package sk.msvvas.sofia.fam.offline.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sk.msvvas.sofia.fam.offline.data.application.database.FamOfflineDatabase
import sk.msvvas.sofia.fam.offline.data.application.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.application.repository.codebook.*
import sk.msvvas.sofia.fam.offline.ui.views.inventory.detail.InventoryDetailView
import sk.msvvas.sofia.fam.offline.ui.views.inventory.detail.InventoryDetailViewModel
import sk.msvvas.sofia.fam.offline.ui.views.inventory.list.InventoryListView
import sk.msvvas.sofia.fam.offline.ui.views.inventory.list.InventoryListViewModel
import sk.msvvas.sofia.fam.offline.ui.views.loading.LoadingScreenView
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginView
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginViewModel
import sk.msvvas.sofia.fam.offline.ui.views.property.detail.PropertyDetailView
import sk.msvvas.sofia.fam.offline.ui.views.property.detail.PropertyDetailViewModel


/**
 * Component for handling navigation in Application
 * Holds instances for nav controller and all repositories
 * @param database Local database of application
 */
@Composable
fun Navigation(
    database: FamOfflineDatabase
) {

    val inventoryRepository = InventoryRepository(database.inventoryDao())
    val propertyRepository = PropertyRepository(database.propertyDao())

    val allCodebooksRepository = AllCodebooksRepository(
        localityCodebookRepository = LocalityCodebookRepository(database.localityCodebookDao()),
        roomCodebookRepository = RoomCodebookRepository(database.roomCodebookDao()),
        placeCodebookRepository = PlaceCodebookRepository(database.placeCodebookDao()),
        userCodebookRepository = UserCodebookRepository(database.userCodebookDao()),
        noteCodebookRepository = NoteCodebookRepository(database.noteCodebookDao())
    )

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOADING_SCREEN.value) {
        composable(route = Routes.LOADING_SCREEN.value) {
            LoadingScreenView(
                propertyRepository = propertyRepository,
                navController = navController
            )
        }
        composable(route = Routes.addOptionalParametersToRoute(Routes.LOGIN_VIEW.value, "id"),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )) {
            LoginView(
                loginViewModel = LoginViewModel(
                    navController,
                    inventoryIDParameter = it.arguments?.getString("id")!!,
                    inventoryRepository = inventoryRepository
                )
            )
        }
        composable(route = Routes.INVENTORY_LIST.value) {
            InventoryListView(
                inventoryListViewModel = InventoryListViewModel(
                    inventoryRepository = inventoryRepository,
                    propertyRepository = propertyRepository,
                    allCodebooksRepository = allCodebooksRepository,
                    navController = navController
                )
            )
        }
        composable(
            route = Routes.addOptionalParametersToRoute(
                Routes.INVENTORY_DETAIL.defineRoute("id"),
                "locality",
                "room",
                "user",
                "statusFilter"
            ),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("locality") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("room") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("user") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("statusFilter") {
                    type = NavType.StringType
                    defaultValue = "U"
                    nullable = false
                },
            )
        ) {
            InventoryDetailView(
                inventoryDetailViewModel = InventoryDetailViewModel(
                    propertyRepository = propertyRepository,
                    inventoryRepository = inventoryRepository,
                    allCodebooksRepository = allCodebooksRepository,
                    navController = navController,
                    inventoryIdParameter = it.arguments?.getString("id")!!,
                    localityFilterParameter = it.arguments?.getString("locality")!!,
                    roomFilterParameter = it.arguments?.getString("room")!!,
                    userFilterParameter = it.arguments?.getString("user")!!,
                    statusFilterParameter = it.arguments?.getString("statusFilter")!![0],
                )
            )
        }
        composable(
            route = Routes.addOptionalParametersToRoute(
                Routes.PROPERTY_DETAIL.defineRoute("id"),
                "locality",
                "room",
                "user",
                "inventoryId",
                "statusFilter",
                "isManual"
            ),
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                },
                navArgument("locality") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("room") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("user") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("inventoryId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("statusFilter") {
                    type = NavType.StringType
                    defaultValue = "U"
                    nullable = false
                },
                navArgument("isManual") {
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                }
            )
        ) {
            PropertyDetailView(
                propertyDetailViewModel = PropertyDetailViewModel(
                    propertyRepository = propertyRepository,
                    allCodebooksRepository = allCodebooksRepository,
                    id = it.arguments?.getLong("id")!!,
                    navController = navController,
                    localityFilter = it.arguments?.getString("locality")!!,
                    roomFilter = it.arguments?.getString("room")!!,
                    userFilter = it.arguments?.getString("user")!!,
                    inventoryId = it.arguments?.getString("inventoryId")!!,
                    statusFilter = it.arguments?.getString("statusFilter")!![0],
                    isManual = it.arguments?.getBoolean("isManual")!!,
                )
            )
        }
    }
}