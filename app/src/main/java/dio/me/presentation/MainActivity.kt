package dio.me.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dagger.hilt.android.AndroidEntryPoint
import dio.me.presentation.theme.SantanderDevWeekTheme
import dio.me.data.model.Response
import dio.me.presentation.components.AppTopBar
import dio.me.presentation.components.BalanceCard
import dio.me.presentation.components.CreditCard
import dio.me.presentation.components.Header
import dio.me.presentation.components.MenuItems
import dio.me.presentation.theme.Spacing_2
import dio.me.presentation.theme.Spacing_3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SantanderDevWeekTheme() {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(response: Response? = null) =
    Surface(color = MaterialTheme.colorScheme.background) {
        Scaffold(
            topBar = { AppTopBar() },
            content = { innerPadding ->
                ConstraintLayout(modifier = Modifier.padding(innerPadding)) {
                     val (header, spacer, balanceCard, menuItems, creditCard) = createRefs()

                    Header(
                        modifier = Modifier.constrainAs(header) {
                            centerHorizontallyTo(parent)
                        },
                        name = response?.name ?: "",
                        agency = response?.account?.agency ?: "",
                        number = response?.account?.number ?: ""
                    )

                    Spacer(
                        modifier = Modifier
                            .height(40.dp)
                            .constrainAs(spacer) {
                                top.linkTo(header.bottom)
                                bottom.linkTo(header.bottom)
                            }
                    )

                    BalanceCard(
                        modifier = Modifier.constrainAs(balanceCard) {
                            top.linkTo(spacer.top)
                        },
                        balance = response?.account?.balance ?: 0.0,
                        limit = response?.account?.limit ?: 0.0,
                    )

                    MenuItems(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing_2,
                                vertical = Spacing_3
                            )
                            .constrainAs(menuItems) {
                                top.linkTo(balanceCard.bottom)
                            },
                        features = response?.features ?: emptyList()
                    )

                    CreditCard(
                        modifier = Modifier
                            .padding(horizontal = Spacing_2)
                            .constrainAs(creditCard) {
                                top.linkTo(menuItems.bottom)
                            },
                        number = response?.card?.number ?: "",
                    )

                }
            }
        )
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SantanderDevWeekTheme {
        MainApp()
    }
}
