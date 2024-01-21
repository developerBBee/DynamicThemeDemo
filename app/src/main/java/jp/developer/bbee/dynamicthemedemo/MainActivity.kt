package jp.developer.bbee.dynamicthemedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.dynamicthemedemo.ui.theme.DynamicThemeDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicThemeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Contents()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contents(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(
            title = { Box {
                Text(text = "ColorPicker")
            }},
            actions = {
                IconButton(onClick = { viewModel.clearColor() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
                }
            }
        ) },
        floatingActionButton = { FloatingActionButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Email, contentDescription = "")
        }},
        bottomBar = { BottomAppBar {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                val filledModifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                BottomNavIcon(
                    modifier = filledModifier,
                    icon = Icons.Default.DateRange,
                    iconSize = 32.dp
                )
                BottomNavIcon(
                    modifier = filledModifier,
                    icon = Icons.Default.Search,
                    iconSize = 32.dp
                )
                Box(modifier = Modifier.weight(2f)) // dummy
                BottomNavIcon(
                    modifier = filledModifier,
                    icon = Icons.Default.List,
                    iconSize = 32.dp
                )
                BottomNavIcon(
                    modifier = filledModifier,
                    icon = Icons.Default.Settings,
                    iconSize = 32.dp
                )
            }
        }}
    ) { paddingValues ->
        // カラーピッカーを表示する
        ColorPicker(modifier = Modifier.padding(paddingValues = paddingValues))
    }
        // 中央のアイコンを強調
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = BottomCenter) {
        Surface(
            shape = CircleShape, // 丸い形状を設定
            color = MaterialTheme.colorScheme.primary, // 背景色を設定
            modifier = Modifier
                .size(120.dp) // サイズを設定
                .offset(y = (20).dp) // 上に40.dp移動
        ) {
            BottomNavIcon(icon = Icons.Default.ShoppingCart, iconSize = 50.dp)
        }
    }

    // ダイアログを表示する
    val dialogType by viewModel.showDialogState.collectAsStateWithLifecycle()
    val closeDialog = { viewModel.setShowDialogType(null) }

    dialogType?.let {
        when (it) {
            DialogType.ALERT_DIALOG -> {
                AlertDialog(
                    onDismissRequest = closeDialog,
                    title = { Text(text = "Title") },
                    confirmButton = {
                        Button(onClick = closeDialog) { Text(text = "Button") }
                    },
                )
            }
            DialogType.ALERT_DIALOG_2 -> {
                AlertDialog(
                    onDismissRequest = closeDialog,
                ) {
                    Text(text = "This is Custom AlertDialog on Text")
                }
            }
        }
    }
}

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    colors: List<Color> = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta, Color.Gray, Color.Black, Color.White)
) {
    val showDialog = { type: DialogType -> viewModel.setShowDialogType(type) }
    val text by viewModel.textState.collectAsStateWithLifecycle()
    val setText = { newText: String -> viewModel.setShowDialogType(newText) }

    val colorState by viewModel.colorState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(5.dp)
            .shadow(1.dp)) {
            items(ColorType.entries) { colorType ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), horizontalAlignment = CenterHorizontally) {
                    Text(text = colorType.name, textAlign = TextAlign.Center, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        colors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(4.dp)
                                    .background(color)
                                    .border(width = if (colorState[colorType] == color) 2.dp else 0.dp, color = color.reverse())
                                    .clickable {
                                        viewModel.setColor(
                                            type = colorType,
                                            color = color
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
            Button(onClick = { showDialog(DialogType.ALERT_DIALOG) }) { Text(text = "Button") }
            OutlinedButton(onClick = { showDialog(DialogType.ALERT_DIALOG_2) }) { Text(text = "OutlinedButton") }
            OutlinedTextField(value = text, onValueChange = { setText(it) })
            Card(modifier = Modifier.padding(10.dp)) {
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Center) { Text(text = "Card") }
            }
            ElevatedCard(modifier = Modifier.padding(10.dp)) {
                Box(modifier = Modifier.padding(10.dp), contentAlignment = Center) { Text(text = "ElevatedCard") }
            }
        }
    }
}

@Composable
fun BottomNavIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp? = null,
) {
    IconButton(modifier = modifier, onClick = {}) {
        val iconModifier = Modifier.run { iconSize?.let { size(it) } ?: this }
        Icon(imageVector = icon , contentDescription = icon.name, modifier = iconModifier)
    }
}

@Preview(showBackground = true)
@Composable
fun DynamicThemeDemoPreview() {
    DynamicThemeDemoTheme {
        Contents()
    }
}

fun Color.reverse(): Color = Color(1.0f - red, 1.0f - green, 1.0f - blue)
