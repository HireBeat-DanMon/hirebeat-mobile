# HireBeat Mobile

HireBeat es una aplicación móvil diseñada para revolucionar la forma en que los músicos profesionales se conectan con reclutadores y entusiastas de la música. Esta aplicación permite a los músicos gestionar su perfil profesional, disponibilidad y recibir solicitudes de contratación en tiempo real.

## 🚀 Tecnologías y Herramientas

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Arquitectura declarativa moderna)
* **Inyección de Dependencias:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* **Navegación:** Compose Navigation
* **Red:** [Retrofit](https://square.github.io/retrofit/) & OkHttp para consumo de APIs REST
* **Base de Datos Local:** [Room Service](https://developer.android.com/training/data-storage/room) (Caché y persistencia local)
* **Gestión de Estado:** ViewModel + StateFlow
* **Arquitectura:** MVVM (Model-View-ViewModel) con principios de Clean Architecture.
* **Gestión de Sesión:** Tokens JWT con persistencia segura local para mantener la sesión activa.

## 🏗️ Estructura del Proyecto

El proyecto está organizado siguiendo una estructura de **Feature-based modules** para facilitar la escalabilidad:

* **`core/`**: Componentes transversales a toda la aplicación.
    * `data/`: Manejo de sesiones (SessionManager) y preferencias.
    * `database/`: Configuración central de Room (`AppDatabase`).
    * `di/`: Módulos de inyección de dependencias (Network, Database, Hardware, Location).
    * `hardware/`: Gestión de sensores y actuadores físicos del dispositivo (Vibración, Flash, Localización).
    * `navigation/`: Gestión de rutas y grafos de navegación de Compose.
    * `permission/`: Verificación y solicitud de permisos en tiempo de ejecución.
    * `presentation/`: Componentes UI reutilizables (Botones, TextFields, TopBars).
    * `theme/`: Sistema de diseño (Color, Typography, Shapes).
* **`feature/`**: Módulos específicos de funcionalidades de negocio.
    * `auth/`: Flujo de inicio de sesión y registro con selección de roles.
    * `profile/`: Visualización de perfiles, feeds de músicos y edición de información profesional.
    * `gig_requests/`: Gestión de solicitudes de servicios, formularios de contratación y listas de estados.
    * `review/`: Sistema de valoración y comentarios entre usuarios.

## Características Principales

* **Feed de Músicos:** Visualizar los perfiles de los músicos activos.
* **Gestión de Perfil:** Los músicos pueden subir su foto de perfil, enlazar redes sociales y definir sus instrumentos/niveles.
* **Contratación de Músicos:** Formulario integrado para solicitar servicios musicales con selección de fecha y lugar.
* **Sistema de Reseñas:** Feedback bidireccional para garantizar la calidad en la comunidad.
* **Interacción de Hardware:** Notificaciones utilizando la vibración del dispositivo y el flash de la cámara.
## Configuración e Instalación

### Prerrequisitos
* Android Studio Ladybug o superior.
* JDK 17.
* Un dispositivo Android o emulador con API 24 (Android 7.0) o superior.

### Instalación
1.  Clona el repositorio:
    ```bash
    git clone [https://github.com/tu-usuario/hirebeat-mobile.git](https://github.com/tu-usuario/hirebeat-mobile.git)
    ```
2.  Importa el proyecto en Android Studio.
3.  Sincroniza los archivos de Gradle.
4.  Configura la URL base de la API en el archivo `NetworkModule.kt` o mediante una variable de entorno en `local.properties`.

#### Permisos de Dispositivo
* `INTERNET`: Comunicación con el backend de HireBeat.
* `VIBRATE`: Retroalimentación táctil en acciones.
* `ACCESS_FINE_LOCATION` & `ACCESS_COARSE_LOCATION`: Localización precisa para la gestión de configurar tu perfil.
* `READ/WRITE_EXTERNAL_STORAGE`: Gestión de archivos y caché.

#### Interacciones con Aplicaciones Externas (Queries)
La aplicación está configurada para interactuar de forma segura con herramientas externas mediante *Intents*:
* **Llamadas:** Permite abrir el marcador telefónico (`DIAL`) para contactar a músicos o reclutadores directamente.
* **Navegación Web:** Capacidad para abrir enlaces externos (redes sociales, portafolios) en el navegador.
* **Correo Electrónico:** Permite enviar mensajes directos (`mailto`) a través de aplicaciones de e-mail instaladas.
* 
## Contribuciones

Este proyecto ha sido desarrollado como parte del ecosistema HireBeat para la **Universidad Politécnica de Chiapas**.
