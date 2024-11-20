# Property Management App

App diseñada con Jetpack Compose y una arquitectura basada en MVVM. Permite a los usuarios agregar propiedades y ver detalles de cada propiedad.

## Funcionalidades

1. **Gestión de Propiedades**:
   - Añadir nuevas propiedades con detalles como tipo, capacidad, camas, baños, ubicación y fotos.
   - Reordenar las fotos de las propiedades.
   - Validación de campos obligatorios antes de guardar.

2. **Navegación**:
   - Navegación entre pantallas con `NavController`.
   - Pantallas de formulario, mapa y detalles.

3. **Temas**:
   - Soporte para tema claro y oscuro con un toggle de configuración.

4. **Persistencia de Datos**:
   - Almacenamiento local de propiedades utilizando Room.

## Arquitectura

La aplicación utiliza la arquitectura MVVM (Model-View-ViewModel):

- **Model**:
  - Representa los datos de la aplicación.
  - Uso de Room para persistencia de datos.
  - Modelos definidos en `data.model`.

- **ViewModel**:
  - Gestiona la lógica de negocio y estados de la interfaz.
  - Uso de `StateFlow` para la reactividad.
  - Ubicado en el paquete `viewmodel`.

- **View**:
  - Interfaces gráficas construidas con Jetpack Compose.
  - Implementación en el paquete `ui.screens`.

## Estructura del Proyecto

├── data │ 
  ├── database # Configuración de Room │
  ├── model # Modelos de datos │ 
  ├── repository # Repositorio de acceso a datos 
├── ui │ 
  ├── navigation # Configuración de rutas de navegación │ 
  ├── screens # Pantallas de la aplicación │ 
  ├── theme # Temas claros y oscuros 
├── viewmodel # Lógica de negocio y estados 
├── res # Recursos gráficos y textos


## Pasos para Construir y Ejecutar la Aplicación

### Requisitos

- Android Studio Flamingo o superior.
- Gradle 8.1.0 o superior.
- Compile SDK: 34
- Min SDK: 24

