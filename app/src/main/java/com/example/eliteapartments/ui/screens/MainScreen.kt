package com.example.eliteapartments.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eliteapartments.R
import com.example.eliteapartments.data.model.Property
import com.example.eliteapartments.viewmodel.PropertyViewModel
import com.example.eliteapartments.viewmodel.ThemeViewModel


@Composable
fun MainScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    propertyViewModel: PropertyViewModel
) {
    val isDarkTheme = themeViewModel.isDarkTheme.collectAsState(initial = false).value
    val properties by propertyViewModel.properties.collectAsState()

    LaunchedEffect(Unit) {
        propertyViewModel.loadProperties()
        Log.d("MainScreen", "Properties loaded: $properties")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add_property")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_property)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lista de Propiedades",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.NightsStay else Icons.Default.LightMode,
                        contentDescription = if (isDarkTheme) "Modo Oscuro" else "Modo Claro"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { themeViewModel.toggleTheme() }
                    )
                }
            }

            if (properties.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(properties) { property ->
                        PropertyItem(navController = navController, property = property)
                    }
                }
            }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No hay propiedades guardadas")
                }
            }
        }
    }
}

@Composable
fun PropertyItem(navController: NavController, property: Property) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("property_detail/${property.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val firstPhotoUri = property.photos.firstOrNull()

            if (!firstPhotoUri.isNullOrEmpty()) {
                AsyncImage(
                    model = firstPhotoUri,
                    contentDescription = property.title,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sin Imagen")
                }
            }
            Text(
                text = property.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}




