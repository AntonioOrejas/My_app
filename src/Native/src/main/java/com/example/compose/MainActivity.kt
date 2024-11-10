package com.example.compose
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.data.Datasource
import com.example.compose.model.Affirmation
import com.example.compose.ui.theme.AffirmationsTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xff495076)
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun ButtonDay(onCreate: () -> Unit,modifier: Modifier) {
        Button(onClick = onCreate, modifier = modifier, colors = ButtonDefaults.buttonColors(Color(0xff727aa8))) {
            Text(text = stringResource(R.string.añadir))

    }
}

@SuppressLint("ResourceType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AffirmationsApp() {
    val datasource = remember { Datasource() }
    val affirmationList = datasource.loadAffirmations()
    var showDialog by remember { mutableStateOf(false) }
    var cardToDelete by remember { mutableStateOf<Affirmation>(Affirmation())}
    var cardIn by remember { mutableStateOf<Affirmation>(Affirmation())}
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            Box(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "CatchTrack",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xff343a5e))
                            .padding(16.dp),
                        color = Color(0xff727aa8),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    AffirmationList(
                        onCardClick = {affirmation ->  navController.navigate("detail/${affirmation.iD}")
                            cardIn=affirmation},
                        affirmationList = affirmationList,
                        modifier=Modifier.fillMaxSize(),
                        onDelete = {affirmation->cardToDelete=affirmation
                            showDialog=true

                        }

                    )

                }

                ButtonDay(
                    onCreate = { datasource.addCard() }
                    , modifier=Modifier.align(Alignment.TopEnd).padding(12.dp)
                )

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest ={showDialog=false},
                        title = { Text(text = "Confirmation") },
                        text = { Text("Are you sure you want to delete this day?") },
                        confirmButton = {
                            Button(onClick =  {datasource.deleteCard(cardToDelete)

                                showDialog=false}) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {showDialog=false}) {
                                Text("No")
                            }
                        }
                    )
                }
            }
        }
        composable("detail/{affirmationId}") { lastCard ->
            val affirmationId = lastCard.arguments?.getString("affirmationId")
            val card= affirmationId?.let { datasource.getCard(it) }
            FishListScreen(card,navController,datasource)

        }
        composable("create_fish/{affirmationId}") {lastCard ->
            val affirmationId = lastCard.arguments?.getString("affirmationId")
            if (affirmationId != null) {
                CreateNewFishScreen(affirmationId, navController,datasource)
            }
        }
        composable("update_fish/{affirmationId}/{fishId}") {lastCard ->
            val fishId = lastCard.arguments?.getString("fishId")
            val affirmationId = lastCard.arguments?.getString("affirmationId")
            if (fishId != null) {
                if (affirmationId != null) {
                    EditFishScreen(fishId,affirmationId, navController,datasource)
                }
            }
        }

    }
}




@Composable
fun AffirmationList(onCardClick: (Affirmation) -> Unit,affirmationList: List<Affirmation>, modifier: Modifier = Modifier
,onDelete: (Affirmation) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                openCard = {onCardClick(affirmation)},
                affirmation = affirmation,
                modifier = Modifier.padding(10.dp).fillMaxSize(),
                onDelete = { onDelete(affirmation) }
            )

        }
    }
}

@Composable
fun AffirmationCard(openCard: () -> Unit,affirmation: Affirmation, modifier: Modifier = Modifier,onDelete:()->Unit) {
    Card(onClick = openCard,modifier = modifier, colors = CardDefaults.cardColors(Color(0xff32383b))) {
        Column {
            Text(
                text = affirmation.stringResourceId,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium,
                color=Color.White
            )
            Text(
                text = "   Total catches: ${affirmation.total}",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.bodyLarge,
                color=Color(0xffa3a9ca)
            )
            Text(
                text = "   Biggest catch: ${affirmation.big} kg",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.bodyLarge,
                color=Color(0xffa3a9ca)
            )
            ButtonDeleteDay(
                delDay = onDelete
                ,
                modifier=modifier
            )

        }
    }
}

@Composable
fun ButtonDeleteDay(delDay: () -> Unit,modifier: Modifier) {
    Button(onClick = delDay, modifier = modifier, colors = ButtonDefaults.buttonColors(Color(0xff727aa8))) {
        Text(text = stringResource(R.string.delete))

    }
}




