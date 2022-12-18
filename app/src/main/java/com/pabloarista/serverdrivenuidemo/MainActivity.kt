package com.pabloarista.serverdrivenuidemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pabloarista.serverdrivenuidemo.ui.Build
import com.pabloarista.serverdrivenuidemo.ui.theme.ServerDrivenUIDemoTheme
import com.pabloarista.serverdrivenuidemo.ui.toColor
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import com.pabloarista.serverdrivenuidemo.shared.data.models.Component

class MainActivity : ComponentActivity() {
    val viewModel = ServerDrivenUIApplication.serverDrivernUIViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serializer: Serializer = Persister()
        val dataFetch = serializer.read(XmlTest::class.java, xmlTest)
        println(dataFetch)
        refreshUi(viewModel.rooApiPath)
    }

    @Composable
    fun ServerContent(component: Component) {
        component.Build()
    }

    fun serverContentCallback(component: Component?) {
        setContent {
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth()
                    , horizontalArrangement = Arrangement.Center) {
                    RefreshButton(title = "Main", viewModel.rooApiPath)
                    RefreshButton(title = "Test", viewModel.testApiPath)
                    RefreshButton(title = "Sample", viewModel.sampleApiPath)
                }
                if(component != null) {
                    ServerContent(component = component)
                }
            }
        }
    }

    @Composable
    fun RefreshButton(title: String, apiPath: String) {
        Button(onClick = { refreshUi(apiPath) }) {
            Text(title)
        }
    }

    fun refreshUi(apiPath: String) {
        viewModel.callback = ::serverContentCallback
        viewModel.getView(apiPath)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MessageCard(msg: Message, spaceSize: Double) {
    Column {
        Text(text = msg.author, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(spaceSize.dp))
        Surface(shape = MaterialTheme.shapeScheme.medium
            , shadowElevation = 1.dp
            , tonalElevation = 1.dp) {
            Text(text = msg.body
                //, color = Color(red = 0x0, green = 0x0, blue = 0x0)
                , modifier = Modifier.padding(all = 4.dp)
                , style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PersonCard(person: Person, spaceSizes: Array<Double>) {
    var index = 0
    val imageId = when(person.gender) {
        Gender.MALE -> R.drawable.mavatar
        Gender.FEMALE -> R.drawable.favatar
    }

    Row(modifier = Modifier.padding(all = 9.dp)) {
        Spacer(modifier = Modifier.width(0.dp))
        Image(painter = painterResource(imageId)
            , contentDescription = "Contact Profile"
            , modifier = Modifier
                .size(40.dp)
                .clip(RectangleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape))
        Spacer(modifier = Modifier.width(spaceSizes[index++].dp))

        var isExpanded by remember { mutableStateOf(false) }

        var expandedColor = if(isExpanded) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface
        val expandedAnimatedColor by animateColorAsState(expandedColor)

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(text = person.name, color = MaterialTheme.colorScheme.secondary)
//            BasicTextField()
            Spacer(modifier = Modifier.height(spaceSizes[index++].dp))
            Surface(
                shape = MaterialTheme.shapeScheme.medium,
                shadowElevation = 1.dp,
                tonalElevation = 1.dp,
                color = expandedAnimatedColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = person.details
                    //, color = Color(red = 0x0, green = 0x0, blue = 0x0)
                    , modifier = Modifier.padding(all = 4.dp)
                    , style = TextStyle(
                        color = Color.White
                        , fontSize = 23.sp
                        , fontWeight = FontWeight(weight = 23)
                        , fontStyle = FontStyle.Italic
                        , textAlign = TextAlign.Center
                        , shadow = Shadow(color = Color.Blue, offset = Offset(x = 1F, y = 1F))
                        , fontFamily = FontFamily.Default
                        , background = Color.Green
                        , textDecoration = TextDecoration.LineThrough
                        , textIndent = TextIndent(firstLine = 0.sp, restLine = 0.sp)
                    )// MaterialTheme.typography.bodyMedium
                    , maxLines = if(isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Composable
fun Conversation(people: List<Person>) {
    LazyColumn {
        people.map { item { PersonCard(person = it, spaceSizes = arrayOf(8.0, 4.2))}}
    }
}

@Preview(
    name = "Light Mode"
    , showBackground = true
)
@Preview(
    name = "Dark Mode"
    , uiMode = Configuration.UI_MODE_NIGHT_YES
    , showBackground = true
)
@Composable
fun MainContent() {
    ServerDrivenUIDemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = "FFFFFF00".toColor()//MaterialTheme.colorScheme.background
        ) {
            if(1==0) {
                Column {
                    Text("1==1")
                }
            }
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                    , horizontalAlignment = Alignment.CenterHorizontally
//                    , verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { /*TODO: call API*/ }) {
                        Text("Refresh")
                    }
                }
                Column {
                    Button(onClick = { }
                        , colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.Yellow)) {
                        Image(
                            painter = painterResource(R.drawable.mavatar)
                            , contentDescription = ""
                            , modifier = Modifier.size(23.dp)
                        )
                        Text("Test")
                    }
                }
                Text("blah", color = Color.Unspecified, modifier = Modifier
                    .padding(0.dp)
                    .padding(bottom = 10.dp))
                Text("blegh", color = Color.Blue, style = TextStyle(color = Color.Green, background = Color.Red))
                Spacer(modifier = Modifier.height(15.dp))
                Conversation(people = testItems())
            }
        }
    }
}


//fun Color.hexStringToColor(hexString: String, alpha: Double): Color {
//    val components = hexString.toCharArray().map { ("" + it).toLong() }
////    return Color(red = components[0], green = components[1], blue = components[2], alpha = alpha)
//}

fun testItems(): List<Person> {
    val people = mutableListOf<Person>()
    for(i in 0..100) {
        val gender = if(i % 2 == 0) {
            Gender.MALE
        } else {
            Gender.FEMALE
        }
        val person = Person("Android $i", "This is the details of Android $i. We have a lot of details to write about Android $1. Android $1 has more details written here about them.", gender)
        people.add(person)
    }
    return people
}

data class Message(val author: String, val body: String)
enum class Gender { MALE, FEMALE }
data class Person(val name: String, val details: String, val gender: Gender)
//material design 3 shapes
//https://stackoverflow.com/questions/70668348/how-to-handle-shape-when-creating-a-theme-for-material-design-3-for-compose
data class Shape(
    val default: RoundedCornerShape = RoundedCornerShape(0.dp),
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp)
)

val LocalShape = compositionLocalOf { Shape() }

val MaterialTheme.shapeScheme: Shape
    @Composable
    @ReadOnlyComposable
    get() = LocalShape.current
val xmlTest = "" +
        "<XmlTest>" +
            "<child>blah</child>" +
        "</XmlTest>"

class XmlTest {
    lateinit var child: String
}

