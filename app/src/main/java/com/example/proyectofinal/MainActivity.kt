package com.example.proyectofinal

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.style.ClickableSpan
import androidx.compose.foundation.Image
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.proyectofinal.model.User
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProyectoFinal1()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProyectoFinal1(
    viewModel: UserViewModel = hiltViewModel()
){
    val users by viewModel.users.observeAsState(arrayListOf())
    val isLoading by viewModel.isLoading.observeAsState(false)
   ProyectoFinal2(onAddClick = {
       viewModel.addUser()
   },
       onDeleteClick={
           viewModel.deleteUser(it)
       },
       users = users,
       isLoading = isLoading
   )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProyectoFinal2(
    onAddClick: (() -> Unit)? = null,
    onDeleteClick: ((toDelete: User)->Unit)?=null,
    users:List<User>,
    isLoading: Boolean
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Proyecto Final")},
                actions = {
                    IconButton(onClick = {
                        onAddClick?.invoke()
                    }) {
                        Icon(Icons.Filled.Add, "Agregar")
                    }
                }
            )
        }
    ){
        LazyColumn{
            var itemCount = users.size
            if (isLoading) itemCount++
            items(count=itemCount){
                index ->
                var auxIndex = index
                if (isLoading){
                    if (auxIndex == 0)
                        return@items LoadingCard()
                    auxIndex--
                }
                val user = users[auxIndex]
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth(),

                ){
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            modifier= Modifier.size(50.dp),
                            painter = rememberImagePainter(
                                data = user.thumbnail,
                                builder = {

                                }
                        ),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight
                        )
                        Spacer()
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text("${user.name} ${user.lastName}")
                            Text(user.city)
                        }
                        Spacer()
                        IconButton(onClick =  {
                            onDeleteClick?.invoke(user)

                        }){
                            Icon(Icons.Filled.Delete, "Eliminar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingCard(){
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .testTag("Cargando...")
    ){
        Row(modifier = Modifier.padding(8.dp)) {
            ImageLoading()
            Spacer()
            Column{
                Spacer()
                Box(modifier = Modifier.shimmer()){
                    Column{
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        )
                        Spacer()
                        Box(
                            modifier = Modifier
                                .height(15.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageLoading()
{
    Box(modifier=Modifier.shimmer()){
       Box(
           modifier = Modifier
               .size(50.dp)
               .background(Color.Gray)
       )
    }
}


@Composable
fun Spacer(size: Int = 8) = Spacer(modifier = Modifier.size(size.dp))

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ProyectoFinalTheme {
        ProyectoFinal2(users = listOf(), isLoading = false)
    }
}