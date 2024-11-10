package com.example.compose


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.compose.model.Affirmation
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.data.Datasource
import com.example.compose.model.Fish

import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip



@Composable
fun FishListScreen(card: Affirmation?, navController: NavController, datasource: Datasource) {
    val fishList by remember { derivedStateOf { card?.fishList ?: emptyList() } }

    var showDialog by remember { mutableStateOf(false) }
    var fishToDelete by remember { mutableStateOf<Fish>(Fish())}


    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp),horizontalArrangement = Arrangement.SpaceBetween) {

            Button(
                onClick = {navController.navigateUp()}, modifier = Modifier.padding(5.dp),

            ) { Text(text = stringResource(R.string.regresar))}

            ButtonCreateFish(
                onCreate = {
                    if (card != null) {
                        navController.navigate("create_fish/${card.iD}")

                    }
                }, modifier = Modifier.padding(5.dp)
            )

        }
        FishList(
            fishList = fishList,
            modifier=Modifier.fillMaxSize(),
            onDelete = {fish->fishToDelete=fish
                showDialog=true
            },
            onUpdate = {fish->
                if (card != null) {
                    navController.navigate("update_fish/${card.iD}/${fish.iDF}")
                }
            }

        )


        if (showDialog) {
            AlertDialog(
                onDismissRequest ={showDialog=false},
                title = { Text(text = "Confirmation") },
                text = { Text("Are you sure you want to delete this catch?") },
                confirmButton = {
                    Button(onClick =  {
                        if (card != null) {
                            datasource.deleteFish(card,fishToDelete)
                        }

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

@Composable
fun ButtonCreateFish(onCreate: () -> Unit,modifier: Modifier) {
    Button(onClick = onCreate, modifier = modifier, colors = ButtonDefaults.buttonColors(Color(
        0xFF4E5E69
    )
    )) {
        Text(text = stringResource(R.string.añadir_pez))

    }
}



@Composable
fun FishList(fishList: List<Fish>, modifier: Modifier = Modifier
                    ,onDelete: (Fish) -> Unit,onUpdate: (Fish) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(fishList) { fish ->
            FishCard(
                fish = fish,
                modifier = Modifier.padding(10.dp).fillMaxSize(),
                onDelete = { onDelete(fish) },
                onUpdate = { onUpdate(fish) }
            )

        }
    }
}



@Composable
fun FishCard(fish: Fish, modifier: Modifier = Modifier,onDelete:()->Unit,onUpdate:()->Unit) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(Color(0xFF5A6164))) {
        Column {
            var specie:String="?????"
            if (fish.species!=""){
                specie=fish.species
            }


            Row {
                Column {
                    if (fish.photo!=null){
                        Image(bitmap = fish.photo!!, contentDescription = null,
                            modifier = Modifier.size(150.dp).clip(RoundedCornerShape(8.dp)))
                    }else{
                        Image(painter = painterResource(R.drawable.add_photo), contentDescription = null,
                            modifier = Modifier.size(150.dp).clip(RoundedCornerShape(8.dp)))
                    }
                    Text(
                        text = "Found in: ${fish.place}",
                        modifier = Modifier.padding(3.dp).align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xffa3a9ca)
                    )

                    Text(
                        text = "Using ${fish.bait} as bait",
                        modifier = Modifier.padding(6.dp).align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xffa3a9ca)
                    )


                }


                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = specie,
                        modifier = Modifier.padding(1.dp).align(Alignment.Start),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xff495076)
                    )

                    Text(
                        text = "${fish.weight} kg",
                        modifier = Modifier.padding(1.dp).align(Alignment.Start),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xffa3a9ca)
                    )

                    ButtonDeleteFish(
                        delFish = onDelete,
                        modifier=Modifier.align(Alignment.End).padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    ButtonUpdateFish(
                        upFish = onUpdate,
                        modifier=Modifier.align(Alignment.End).padding(5.dp)
                    )

                }


            }







        }
    }
}

@Composable
fun ButtonDeleteFish(delFish: () -> Unit,modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.delete),
        contentDescription = null,
        modifier = modifier.size(24.dp).clickable { delFish() }
    )
}

@Composable
fun ButtonUpdateFish(upFish: () -> Unit,modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.edit_image),
        contentDescription = stringResource(R.string.update),
        modifier=modifier.size(24.dp).clickable { upFish() }
    )

}


