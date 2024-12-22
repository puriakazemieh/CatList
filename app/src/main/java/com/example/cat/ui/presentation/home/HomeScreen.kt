package com.example.cat.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cat.ui.component.ProgressScreen
import com.example.cat.ui.component.imageOption
import com.example.cat.ui.component.shimmerPluginImage
import com.skydoves.landscapist.coil3.CoilImage
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewmodel = koinViewModel(),
    gotoDetail: (String) -> Unit
) {


    LaunchedEffect(true) {
        viewModel.effect.collect {
            when (it) {
                is HomeContract.Effect.GoToDetail -> gotoDetail(it.id)
            }
        }
    }


    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
        viewModel.setEvent(HomeContract.Event.GetCats)
    }
    val catList = viewModel.catResult.collectAsLazyPagingItems()
    viewModel.setEvent(HomeContract.Event.OnCollectCatList(catList))

    Scaffold { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                when {
                    state.catList?.itemCount != 0 -> {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            columns = GridCells.Fixed(2),
                        ) {
                            items(
                                state.catList?.itemCount ?: 0
                            ) { index ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .background(Color.White)
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable {
                                            viewModel.setEvent(HomeContract.Event.OnGoToDetail(state.catList?.itemSnapshotList[index]?.id.toString()))
                                        }) {
                                    Column {
                                        CoilImage(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(RoundedCornerShape(10.dp)),
                                            imageModel = { state.catList?.get(index)?.imageUrl },
                                            imageOptions = imageOption(),
                                            component = shimmerPluginImage(),
                                        )

                                        state.catList?.get(index)?.breeds?.firstOrNull()?.name?.let {
                                            Text(it)
                                        }

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
