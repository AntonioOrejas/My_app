package com.example.compose

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions

@Composable
fun CreateNewFishScreen(affirmationId: String, navController: NavController, datasource: Datasource) {
    var Species by remember { mutableStateOf("") }
    var Weight by remember { mutableStateOf("") }
    var Place by remember { mutableStateOf("") }
    var Bait by remember { mutableStateOf("") }
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
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
                    datasource.createFish(affirmationId,Species,"0",Place,Bait,image)
                    navController.navigateUp()

                }
                else{
                    if (Weight.toIntOrNull() is Int){
                        datasource.createFish(affirmationId,Species,Weight,Place,Bait,image)
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





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun takePhoto(modifier: Modifier): ImageBitmap? {
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current as Activity

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {

                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }

            } else {
                println("ImageCropping error: ${result.error}")
            }
        }
    Box(
        modifier = modifier
    ) {


        Image(painter = painterResource(R.drawable.add_photo),
            contentDescription = stringResource(R.string.update),
            modifier = modifier.size(24.dp).clickable {
                val cropOptions = CropImageContractOptions(
                    null,
                    CropImageOptions(
                        aspectRatioX = 1,
                        aspectRatioY = 1,
                        fixAspectRatio = true
                    )

                )
                imageCropLauncher.launch(cropOptions)
            }
        )



        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
    return bitmap?.asImageBitmap()
}



