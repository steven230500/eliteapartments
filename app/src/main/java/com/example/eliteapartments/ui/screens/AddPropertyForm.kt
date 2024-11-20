package com.example.eliteapartments.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eliteapartments.data.model.Property
import com.example.eliteapartments.data.saveImageToAppStorage
import com.example.eliteapartments.viewmodel.PropertyViewModel

@Composable
fun AddPropertyForm(navController: NavController, viewModel: PropertyViewModel) {
    val type by viewModel.type.collectAsState()
    val maxGuests by viewModel.maxGuests.collectAsState()
    val beds by viewModel.beds.collectAsState()
    val bathrooms by viewModel.bathrooms.collectAsState()
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val photos by viewModel.photos.collectAsState()
    val location by viewModel.location.collectAsState()

    var attemptedSave by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris != null) {
            val savedUris = uris.mapNotNull { uri ->
                context.saveImageToAppStorage(uri)
            }
            viewModel.setPhotos(photos + savedUris)
        }
    }

    val isFormValid = type.isNotEmpty() &&
            maxGuests.isNotEmpty() &&
            maxGuests.toIntOrNull() != null &&
            beds.isNotEmpty() &&
            beds.toIntOrNull() != null &&
            bathrooms.isNotEmpty() &&
            bathrooms.toIntOrNull() != null &&
            title.isNotEmpty() &&
            description.isNotEmpty() &&
            photos.size >= 5 &&
            location != null

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        ) {
            OutlinedTextField(
                value = type,
                onValueChange = {},
                label = { Text("Tipo de Propiedad") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedSave && type.isEmpty()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Casa", "Apartamento", "Estudio", "Cabaña").forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            viewModel.setType(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = maxGuests,
            onValueChange = viewModel::setMaxGuests,
            label = { Text("Máximo de Huéspedes") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = attemptedSave && (maxGuests.isEmpty() || maxGuests.toIntOrNull() == null)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = beds,
            onValueChange = viewModel::setBeds,
            label = { Text("Camas por Propiedad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = attemptedSave && (beds.isEmpty() || beds.toIntOrNull() == null)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = bathrooms,
            onValueChange = viewModel::setBathrooms,
            label = { Text("Cantidad de Baños") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = attemptedSave && (bathrooms.isEmpty() || bathrooms.toIntOrNull() == null)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = viewModel::setTitle,
            label = { Text("Título de la Propiedad") },
            modifier = Modifier.fillMaxWidth(),
            isError = attemptedSave && title.isEmpty()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = viewModel::setDescription,
            label = { Text("Descripción de la Propiedad") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            isError = attemptedSave && description.isEmpty()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.height(200.dp)) {
            itemsIndexed(photos) { index, photoUri ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                    Row {
                        Button(
                            onClick = {
                                if (index > 0) {
                                    val updatedPhotos = photos.toMutableList().apply {
                                        val temp = this[index - 1]
                                        this[index - 1] = this[index]
                                        this[index] = temp
                                    }
                                    viewModel.setPhotos(updatedPhotos)
                                }
                            },
                            enabled = index > 0
                        ) {
                            Text("Subir")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (index < photos.size - 1) {
                                    val updatedPhotos = photos.toMutableList().apply {
                                        val temp = this[index + 1]
                                        this[index + 1] = this[index]
                                        this[index] = temp
                                    }
                                    viewModel.setPhotos(updatedPhotos)
                                }
                            },
                            enabled = index < photos.size - 1
                        ) {
                            Text("Bajar")
                        }
                    }
                }
            }
            item {
                IconButton(onClick = { galleryLauncher.launch("image/*") }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Imagen")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Debes cargar al menos 5 fotos",
            color = if (photos.size >= 5) Color.Green else Color.Red,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Atrás")
            }

            Button(
                onClick = { navController.navigate("select_location") },
                enabled = true
            ) {
                Text("Seleccionar Ubicación")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                attemptedSave = true
                if (isFormValid) {
                    val property = Property(
                        type = type,
                        maxGuests = maxGuests.toInt(),
                        beds = beds.toInt(),
                        bathrooms = bathrooms.toInt(),
                        title = title,
                        description = description,
                        photos = photos,
                        location = location!!
                    )
                    viewModel.addProperty(property)
                    viewModel.resetForm()
                    navController.navigate("main")
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar Propiedad")
        }

        if (attemptedSave && !isFormValid) {
            Text(
                text = "Por favor, completa todos los campos obligatorios y asegúrate de que sean válidos.",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
