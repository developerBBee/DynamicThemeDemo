package jp.developer.bbee.dynamicthemedemo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _colors = MutableStateFlow(mutableMapOf<ColorType, Color?>())
    val colorState: StateFlow<Map<ColorType, Color?>> = _colors.asStateFlow()

    fun setColor(type: ColorType, color: Color) {
        _colors.value = colorState.value.toMutableMap().apply { put(type, color) }
    }

    fun clearColor() {
        _colors.value = mutableMapOf()
    }

    private val _showDialog: MutableStateFlow<DialogType?> = MutableStateFlow(null)
    val showDialogState: StateFlow<DialogType?> = _showDialog.asStateFlow()
    fun setShowDialogType(dialogType: DialogType?) {
        _showDialog.value = dialogType
    }

    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val textState: StateFlow<String> = _text.asStateFlow()
    fun setShowDialogType(text: String) {
        _text.value = text
    }
}

enum class DialogType {
    ALERT_DIALOG,
    ALERT_DIALOG_2,
}

enum class ColorType {
    PRIMARY,
    ON_PRIMARY,
    PRIMARY_CONTAINER,
    ON_PRIMARY_CONTAINER,
    INVERSE_PRIMARY,
    SECONDARY,
    ON_SECONDARY,
    SECONDARY_CONTAINER,
    ON_SECONDARY_CONTAINER,
    TERTIARY,
    ON_TERTIARY,
    TERTIARY_CONTAINER,
    ON_TERTIARY_CONTAINER,
    BACKGROUND,
    ON_BACKGROUND,
    SURFACE,
    ON_SURFACE,
    SURFACE_VARIANT,
    ON_SURFACE_VARIANT,
    SURFACE_TINT,
    INVERSE_SURFACE,
    INVERSE_ON_SURFACE,
    ERROR,
    ON_ERROR,
    ERROR_CONTAINER,
    ON_ERROR_CONTAINER,
    OUTLINE,
    OUTLINE_VARIANT,
    SCRIM,
}