package sk.msvvas.sofia.fam.offline.ui.views.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sk.msvvas.sofia.fam.offline.data.database.FamOfflineDatabase
import sk.msvvas.sofia.fam.offline.data.repository.InventoryRepository
import sk.msvvas.sofia.fam.offline.data.repository.PropertyRepository
import sk.msvvas.sofia.fam.offline.data.repository.codebook.*
import sk.msvvas.sofia.fam.offline.ui.views.inventory.detail.InventoryDetailView
import sk.msvvas.sofia.fam.offline.ui.views.inventory.detail.InventoryDetailViewModel
import sk.msvvas.sofia.fam.offline.ui.views.inventory.list.InventoryListView
import sk.msvvas.sofia.fam.offline.ui.views.inventory.list.InventoryListViewModel
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginView
import sk.msvvas.sofia.fam.offline.ui.views.login.LoginViewModel

@Composable
fun Navigation(
    database: FamOfflineDatabase
) {

    val inventoryRepository = InventoryRepository(database.inventoryDao())
    val propertyRepository = PropertyRepository(database.propertyDao())

    val allCodebookRepository = AllCodebooksRepository(
        localityCodebookRepository = LocalityCodebookRepository(database.localityCodebookDao()),
        roomCodebookRepository = RoomCodebookRepository(database.roomCodebookDao()),
        placesCodebookRepository = PlacesCodebookRepository(database.placesCodebookDao()),
        userCodebookRepository = UserCodebookRepository(database.userCodebookDao()),
        noteCodebookRepository = NoteCodebookRepository(database.noteCodebookDao())
    )

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN_VIEW.value) {
        composable(route = Routes.LOGIN_VIEW.value) {
            LoginView(loginViewModel = LoginViewModel(navController))
        }
        composable(route = Routes.INVENTORY_LIST.value) {
            InventoryListView(
                inventoryListViewModel = InventoryListViewModel(
                    inventoryRepository = inventoryRepository,
                    navController = navController
                )
            )
        }
        composable(
            route = Routes.INVENTORY_DETAIL.value + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
            )
        ) {
            InventoryDetailView(
                inventoryDetailViewModel = InventoryDetailViewModel(
                    propertyRepository = propertyRepository,
                    allCodebooksRepository = allCodebookRepository,
                    navController = navController,
                    inventoryIdParameter = it.arguments?.getString("id")!!
                )
            )
        }
    }
}