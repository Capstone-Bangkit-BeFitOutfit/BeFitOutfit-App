package com.bangkit.befitoutfit.helper

sealed class BottomSheetType(val title: String) {
    data object Profile : BottomSheetType("Profile")
    data object SettingRecommend : BottomSheetType("Setting Recommend")
}
