package com.example.eliteapartments.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eliteapartments.data.model.Property

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(navController: NavController, property: Property) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de la Propiedad") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (property.photos.isNotEmpty()) {
                AsyncImage(
                    model = property.photos.first(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay imágenes disponibles")
                }
            }

            Text(
                text = property.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Tipo: ${property.type}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Capacidad máxima: ${property.maxGuests} huéspedes",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Camas: ${property.beds}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Baños: ${property.bathrooms}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Descripción:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = property.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Ubicación:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Latitud: ${property.location.first}, Longitud: ${property.location.second}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (property.photos.size > 1) {
                Text(
                    text = "Galería de fotos:",
                    style = MaterialTheme.typography.titleMedium
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    property.photos.drop(1).forEach { photo ->
                        AsyncImage(
                            model = photo,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
