package com.bangkit.befitoutfit.ui.screen.myOutfit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Outfits
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.helper.StringExtensions.errorMessageHandler
import com.bangkit.befitoutfit.ui.component.ColumnOutfit

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun MyOutfitScreen(
    state: State<Outfits>,
    outfits: List<Outfit>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    onError: (String) -> Unit = {},
    onClickDetailOutfit: (Outfit) -> Unit = {},
    pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = state is State.Loading,
        onRefresh = onRefresh,
    ),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(
                state = pullRefreshState,
            ),
    ) {
        when (state) {
            is State.Error -> onError(state.message.errorMessageHandler())
            else -> {}
        }

        ColumnOutfit(
            outfits = outfits,
            onClick = onClickDetailOutfit,
        )

        PullRefreshIndicator(
            refreshing = state is State.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(
                alignment = Alignment.TopCenter,
            ),
        )
    }
}
