package com.morteza.myapplication

import android.R
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.relocationRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.morteza.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Morteza", "Akbari")
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Greeting(name: String, lastname: String) {
    Box() {
        Column(Modifier.fillMaxSize()) {
            topGreeting(name)
            chipSection(chips = listOf("Sweet sleep", "Insomnia", "Depression"))
            CurrentMeditation()
            FeatureSection(
                listOf(
                    Feature(
                        title = "Compare to the world",
                        R.drawable.stat_sys_headset,
                        OrangeYellow1,
                        OrangeYellow2,
                        OrangeYellow3,
                    ),
                    Feature(
                        title = "Sleep meditation",
                        R.drawable.stat_notify_call_mute,
                        BlueViolet1,
                        BlueViolet2,
                        BlueViolet3
                    ),
                    Feature(
                        title = "Concentrate deep",
                        R.drawable.ic_menu_recent_history,
                        LightGreen1,
                        LightGreen2,
                        LightGreen3,
                    ),
                    Feature(
                        title = "Deep work",
                        R.drawable.stat_sys_speakerphone,
                        Beige1,
                        Beige2,
                        Beige3,
                    ),
                )
            )
            post(name, lastname)
        }
        BottomMenu(items = listOf(
            BottomMenuContent("Home", R.drawable.ic_menu_recent_history),
            BottomMenuContent("Meditate", R.drawable.ic_menu_agenda),
            BottomMenuContent("Sleep", R.drawable.ic_menu_edit),
            BottomMenuContent("Music", R.drawable.ic_menu_myplaces),
            BottomMenuContent("Profile", R.drawable.ic_menu_more),
        ), modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun topGreeting(name: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        userPhoto(
            url = "https://img.freepik.com/free-photo/pleasant-looking-serious-man-stands-profile-has-confident-expression-wears-casual-white-t-shirt_273609-16959.jpg?size=626&ext=jpg",
            20
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Good morning, $name", style = MaterialTheme.typography.h6
            )
            Text(text = "We wish you have a good day!", style = MaterialTheme.typography.body1)
        }
        Box(Modifier.padding(10.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_search),
                contentDescription = "Search for shit",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun chipSection(chips: List<String>) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    LazyRow() {
        items(chips.size) {
            Box(modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                .clickable {
                    selectedIndex = it
                }
                .clip(RoundedCornerShape(percent = 20))
                .background(color = if (selectedIndex == it) DarkerButtonBlue else ButtonBlue)
                .padding(10.dp)
            ) {
                Text(text = chips[it])
            }
        }
    }
}

@Composable
fun CurrentMeditation(color: Color = LightRed) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Daily thought", style = MaterialTheme.typography.h6
            )
            Text(text = "Meditation 3-10 min", style = MaterialTheme.typography.body1)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(ButtonBlue)
                .clickable { }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_media_play),
                contentDescription = "Search for shit",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FeatureSection(features: List<Feature>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(15.dp)
        )
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
        ) {
            items(features.size) {
                FeatureItem(features[it])
            }

        }
    }

}

@Composable
fun FeatureItem(feature: Feature) {
    BoxWithConstraints(
        Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkerColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        //medium colored path:
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100)
            lineTo(-100f, height.toFloat() + 100)
            close()
        }
        //Light colored path:
        val lightColoredPoint1 = Offset(0f, height * 0.35f)
        val lightColoredPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightColoredPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightColoredPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightColoredPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightColoredPoint1.x, lightColoredPoint1.y)
            standardQuadFromTo(lightColoredPoint1, lightColoredPoint2)
            standardQuadFromTo(lightColoredPoint2, lightColoredPoint3)
            standardQuadFromTo(lightColoredPoint3, lightColoredPoint4)
            standardQuadFromTo(lightColoredPoint4, lightColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100)
            lineTo(-100f, height.toFloat() + 100)
            close()
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPath(path = mediumColoredPath, color = feature.mediumColor)
            drawPath(path = lightColoredPath, color = feature.lightColor)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.h5,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = feature.iconID),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Text(
                text = "Start",
                color = TextWhite,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable { }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(5.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}
@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if(isSelected) activeTextColor else inactiveTextColor
        )
    }
}

@Composable
private fun post(name: String, lastname: String) {
    Row {
        userPhoto("https://data.whicdn.com/images/322027365/original.jpg?t=1541703413")
        Column {
            Row() {
                Text(text = "$name $lastname", color = MaterialTheme.colors.onBackground)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "12:53", color = Color.LightGray, style = TextStyle(fontSize = 12.sp))
                PaddingValues(10.dp)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Surface {
                Text(text = "Hello $name! Jetpack Compose is the new next generation UI toolkit. It uses a declarative component based paradigm for building UIs easily and quickly. It’s written entirely in Kotlin and embraces the style and ergonomics of the Kotlin language. ")
            }
            var count by remember { mutableStateOf(0) }

            Button(onClick = { count++ }) {
                Text(text = "$count ", color = Color.Black)
            }

            var text by remember { mutableStateOf("") }
            TextField(value = text, onValueChange = {
                text = it
            })
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "$text ", color = Color.Black)
            LazyRow() {
                items(
                    listOf(
                        "https://i0.wp.com/newdoorfiji.com/wp-content/uploads/2018/03/profile-img-1.jpg?ssl=1",
                        "https://img.freepik.com/free-photo/pleasant-looking-serious-man-stands-profile-has-confident-expression-wears-casual-white-t-shirt_273609-16959.jpg?size=626&ext=jpg",
                        "https://mk0ziglar4ifu5ixq7cx.kinstacdn.com/wp-content/uploads/2016/09/michelle-prince-profile-img.png",
                        "https://www.adobe.com/express/create/media_1bcd514348a568faed99e65f5249895e38b06c947.jpeg?width=2000&format=webply&optimize=medium",
                        "https://static.wikia.nocookie.net/sonic-underground/images/0/00/Guy.jpeg/revision/latest?cb=20201121200403"
                    )
                ) {
                    userPhoto(url = it, 50)
                }
            }
        }

    }
}

@Composable
fun userPhoto(url: String, size: Int = 10) {
//    val ringColor = remember { randomColor() }
    var isSelected by remember { mutableStateOf(false) }
    val ringColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else randomColor(),
        animationSpec = tween(durationMillis = 1000)
    )
    Image(
        painter = rememberImagePainter(data = url, builder = {
            transformations(if (isSelected) GrayscaleTransformation() else CircleCropTransformation())
        }),
        contentDescription = null,
        modifier = Modifier
            .clickable { }
            .size((50 + size).dp)
            .padding(5.dp)
            .border(1.dp, ringColor, CircleShape)
            .padding(5.dp)
    )
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Jafar", "Akbari")
    }
}

fun randomColor() = Color(
    blue = Random.nextInt(0, 255),
    red = Random.nextInt(0, 255),
    green = Random.nextInt(0, 255)
)