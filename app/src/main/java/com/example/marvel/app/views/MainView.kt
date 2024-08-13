package com.example.marvel.app.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.marvel.app.components.AppCard
import com.example.marvel.app.components.FavoriteButtonPlaceholder
import com.example.marvel.app.components.SearchPlaceholder
import com.example.marvel.app.components.SingleRow
import com.example.marvel.app.components.Slideshow
import com.example.marvel.app.viewmodels.MainViewModel

class MainView(
    private val mainViewModel: MainViewModel,
    private val onSearchClick: () -> Unit = {},
    private val onCharactersClick: () -> Unit = {},
    private val onComicsClick: () -> Unit = {},
    private val onSeriesClick: () -> Unit = {},
    private val onStoriesClick: () -> Unit = {},
    private val onEventsClick: () -> Unit = {},
    private val onFavoritesClick: () -> Unit = {}
) : AbstractView() {
    @Composable
    fun LogoImage() {
        val logoImageUrl by mainViewModel.getLogoImageUrl().observeAsState()

        logoImageUrl?.let {
            SubcomposeAsyncImage(
                model = logoImageUrl,
                contentDescription = "Character",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }

    @Composable
    fun Search() {
        SearchPlaceholder(onClick = onSearchClick)
    }

    @Composable
    fun Main() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Slideshow(mainViewModel.getSlideshowImages())
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Search()
                }
                FavoriteButtonPlaceholder(onClick = onFavoritesClick)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Content()
        }
    }

    @Composable
    fun Content() {
        val height = 110
        val width = 180
        Column {
            SingleRow {
                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(height.dp)
                        .width(width.dp),
                    imageModifier = Modifier.height((height - 30).dp),
                    item = mainViewModel.getCharacters(),
                    onClick = onCharactersClick
                )
                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(height.dp)
                        .width(width.dp),
                    imageModifier = Modifier.height((height - 30).dp),
                    item = mainViewModel.getComics(),
                    onClick = onComicsClick
                )
            }
            SingleRow {
                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(height.dp)
                        .width(width.dp),
                    imageModifier = Modifier.height((height - 30).dp),
                    item = mainViewModel.getEvents(),
                    onClick = onEventsClick
                )
                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(height.dp)
                        .width(width.dp),
                    imageModifier = Modifier.height((height - 30).dp),
                    item = mainViewModel.getSeries(),
                    onClick = onSeriesClick
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppCard(
                    modifier = Modifier
                        .height(height.dp)
                        .width(width.dp),
                    imageModifier = Modifier.height((height - 30).dp),
                    item = mainViewModel.getStories(),
                    onClick = onStoriesClick
                )
            }
        }
    }

    @Composable
    override fun Render() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(40.dp))
            Main()
        }
    }
}