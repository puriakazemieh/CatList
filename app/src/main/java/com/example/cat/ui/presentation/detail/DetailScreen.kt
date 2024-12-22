package com.example.cat.ui.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cat.ui.component.ProgressScreen
import com.example.cat.ui.component.imageOption
import com.example.cat.ui.component.shimmerPluginImage
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewmodel = koinViewModel(),
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                when {
                    state.cat != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .background(Color.White)
                                .clip(RoundedCornerShape(20.dp))
                        ) {
                            Column {
                                CoilImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    imageModel = { state.cat.imageUrl },
                                    imageOptions = imageOption(),
                                    component = shimmerPluginImage(),
                                )
                                LazyColumn{
                                    state.cat.breeds?.firstOrNull()?.let {breeds->
                                        breeds.name?.let { item{Detail("name",it)} }
                                        breeds.description?.let { item{Detail("description",it)} }
                                        state.cat.width?.let { item{Detail("width",it.toString())} }
                                        state.cat.height?.let { item{Detail("height",it.toString())} }
                                        breeds.origin?.let { item{Detail("origin",it)} }
                                        breeds.lifeSpan?.let { item{Detail("lifeSpan",it)} }
                                        breeds.temperament?.let { item{Detail("temperament",it)} }
                                        breeds.countryCodes?.let { item{Detail("countryCodes",it)} }
                                        breeds.indoor?.let { item{Detail("indoor",it.toString())} }
                                        breeds.lap?.let { item{Detail("lap",it.toString())} }
                                        breeds.altNames?.let { item{Detail("altNames",it)} }
                                        breeds.adaptability?.let { item{Detail("adaptability",it.toString())} }
                                        breeds.affectionLevel?.let { item{Detail("affectionLevel",it.toString())} }
                                        breeds.childFriendly?.let { item{Detail("childFriendly",it.toString())} }
                                        breeds.vcahospitalsUrl?.let { item{Detail("vcahospitalsUrl",it)} }
                                        breeds.temperament?.let { item{Detail("temperament",it)} }
                                        breeds.name?.let { item{Detail("name",it)} }
                                        breeds.vetstreetUrl?.let { item{Detail("vetstreetUrl",it)} }
                                        breeds.grooming?.let { item{Detail("grooming",it.toString())} }
                                        breeds.hypoallergenic?.let { item{Detail("hypoallergenic",it.toString())} }
                                        breeds.indoor?.let { item{Detail("indoor",it.toString())} }
                                        breeds.energyLevel?.let { item{Detail("energyLevel",it.toString())} }
                                        breeds.strangerFriendly?.let { item{Detail("strangerFriendly",it.toString())} }
                                        breeds.referenceImageId?.let { item{Detail("referenceImageId",it)} }
                                        breeds.affectionLevel?.let { item{Detail("affectionLevel",it.toString())} }
                                        breeds.catFriendly?.let { item{Detail("catFriendly",it.toString())} }
                                    }
                                }


                            }

                        }
                    }

                    state.isLoading -> ProgressScreen()
                }
            }
        }
    }
}



@Composable
fun Detail(key: String, value: String) {
    Row {
        Column(
            modifier = Modifier
                .weight(0.35f)
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        ) {
            Text(
                text = key,
                fontWeight = FontWeight.Medium,
            )
        }

        Column(
            modifier = Modifier
                .weight(0.65f)
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        ) {
            Text(
                fontWeight = FontWeight.SemiBold,
                text = value,
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }
    }
}