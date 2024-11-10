package com.example.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.data.Datasource


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@Composable
fun EditFishScreen(fishiDF: String,cardID:String, navController: NavController, datasource: Datasource) {
    val fish = datasource.getFish(fishiDF,cardID)
    var Species by remember { mutableStateOf(fish?.species?:"") }
    var Weight by remember { mutableStateOf(fish?.weight?:"") }
    var Place by remember { mutableStateOf(fish?.place?:"") }
    var Bait by remember { mutableStateOf(fish?.bait?:"") }
    var image by remember { mutableStateOf(fish?.photo) }
    var Error by remember { mutableStateOf(false) }
    if (Weight==""){
        Error=false
    }
    else {
        if (Weight.toIntOrNull() is Int) {
            Error = false
        } else {
            Error = true
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        image=takePhoto(Modifier.size(150.dp).clip(RoundedCornerShape(8.dp)))
        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = Species,
            onValueChange = { Species = it },
            label = { Text("Species") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = Weight,
            onValueChange = { Weight = it},
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth(),
            isError = Weight!="" && Weight.toIntOrNull()==null
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = Place,
            onValueChange = { Place = it },
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = Bait,
            onValueChange = { Bait = it },
            label = { Text("Bait") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (Weight==""){
                    datasource.updateFish(fishiDF,cardID,Species,"0",Place,Bait,image)
                    navController.navigateUp()

                }
                else{
                    if (Weight.toIntOrNull() is Int){
                        datasource.updateFish(fishiDF,cardID,Species,Weight,Place,Bait,image)
                        navController.navigateUp()

                    }
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
        if (Error) {
            Text(text="Weight has to be a numerical value",
                color = Color.Red)
        }

    }
}
