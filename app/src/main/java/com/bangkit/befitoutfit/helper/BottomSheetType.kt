package com.bangkit.befitoutfit.helper

sealed class BottomSheetType(val title: String) {
    data object Profile : BottomSheetType(title = "Profile")
    data object DetailOutfit : BottomSheetType(title = "Detail Outfit")
    data object AddOutfit : BottomSheetType(title = "Add Outfit")
    data object SettingRecommend : BottomSheetType(title = "Setting Recommend")
}
