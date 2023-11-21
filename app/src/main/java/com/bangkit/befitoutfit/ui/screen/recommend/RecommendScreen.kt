package com.bangkit.befitoutfit.ui.screen.recommend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bangkit.befitoutfit.data.model.Outfit
import com.bangkit.befitoutfit.data.model.Recommend
import com.bangkit.befitoutfit.helper.State
import com.bangkit.befitoutfit.ui.component.ColumnRecommend

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendScreen(
    state: State<Recommend>,
    modifier: Modifier = Modifier,
    getRecommend: () -> Unit = {},
    detailRecommend: (Outfit) -> Unit = {},
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = state is State.Loading, onRefresh = getRecommend)

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        when (state) {
            is State.Idle -> getRecommend()
            is State.Loading -> {}
            is State.Success -> ColumnRecommend(
                recommend = state.data, modifier = Modifier, onClick = detailRecommend
            )

            is State.Error -> {}
        }

        PullRefreshIndicator(
            refreshing = state is State.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
