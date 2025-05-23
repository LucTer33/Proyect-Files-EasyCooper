import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pmdm.EasyCooper.ui.features.login.LoginViewModel
import com.pmdm.EasyCooper.ui.features.newuser.NewUserViewModel
import com.pmdm.EasyCooper.ui.features.pedidos.PedidosViewModel
import com.pmdm.EasyCooper.ui.features.easycooper.TiendaViewModel
import com.pmdm.EasyCooper.ui.navigation.LoginRoute
import com.pmdm.EasyCooper.ui.navigation.NewUserRoute
import com.pmdm.EasyCooper.ui.navigation.PedidoRoute
import com.pmdm.EasyCooper.ui.navigation.TiendaRoute
import com.pmdm.EasyCooper.ui.navigation.loginDestination
import com.pmdm.EasyCooper.ui.navigation.newUserDestination
import com.pmdm.EasyCooper.ui.navigation.pedidosDestination
import com.pmdm.EasyCooper.ui.navigation.tiendaDestination


@Composable
fun TiendaNavHost() {
    val navController = rememberNavController()
    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val tiendaViewModel: TiendaViewModel = hiltViewModel()
    val pedidosViewModel: PedidosViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = LoginRoute("")
    )
    {
        loginDestination(
            loginViewModel = loginViewModel,
            onNavigateToNewUser = {
                newUserViewModel.clearCliente()
                newUserViewModel.esNuevoCliente=true
                navController.navigate(NewUserRoute)
                loginViewModel.clearLogin()
            },
            onNavigateToTienda = { correo ->
                tiendaViewModel.clearTienda()
                navController.navigate(TiendaRoute(correo))
                loginViewModel.clearLogin()
            },
        )
        newUserDestination(
            newUserViewModel = newUserViewModel,
            onNavigateToLogin = { correo, _ ->
                loginViewModel.iniciaUsuario(correo)
                navController.navigate(LoginRoute(correo))
                newUserViewModel.clearCliente()
            })
        tiendaDestination(
            tiendaViewModel = tiendaViewModel,
            onNavigateToPedido = { dni ->
                navController.navigate(PedidoRoute(dni))
            },
            onNavigateToNewUser = {
                newUserViewModel.esNuevoCliente=false
                newUserViewModel.inicializarCliente(tiendaViewModel.clienteState, )
                navController.navigate(NewUserRoute)

            },
            onNavigateToLogin = {
               navController.popBackStack()
            },
        )
        pedidosDestination(pedidosViewModel=pedidosViewModel)
    }
}