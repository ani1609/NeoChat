# NeoChat - Team AsyncAwait
>A modern chat app that puts inclusivity first and allows seamless conversation between the disabled and others.

## Mobile app📱

### Installation ⬇️
<a href="https://github.com/ani1609/NeoChat/releases/download/v1.0.0/neo-chat.apk"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=80px />

### Building 🏗️

1. Clone the project
2. cd `NeoChat`
3. cd `mobile-app`
4. Open this project in Andorid Studio
5. Grab your ```YOUR_API_KEY``` from https://assemblyai.com
6. Go to Firebase console and create a new project.
7. Setup and add this android app to the project.
8. Enable authentication, cloud firestore and storage.
9. Download and paste `google-services.json` file inside `/app`
10. Now, in your local.properties add the block
```
ASSEMBLY_AI_KEY = YOUR-API-KEY
FIREBASE_STORAGE_PATH = YOUR_FIRBASE_STORAGE_PATH
FIREBASE_ATORAGE_URL = YOUR_FIREBASE_ATORAGE_URL
```
8. Build and run

### Tech Stack 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous calls and tasks to utilize threads.
- [Jetpack Compose UI Toolkit](https://developer.android.com/jetpack/compose) - Modern UI development toolkit.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Dagger-Hilt](https://dagger.dev/hilt/) - A standard way to incorporate Koin dependency injection into an Android application.
    - [Hilt-ViewModel](https://dagger.dev/hilt/view-model) - DI for injecting ```ViewModel```. 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

### Architecture 👷‍♂️
This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch)  and Clean architecture.


## High level working of the app

1. The user authenticates and provides information.

2. The user then is able to send text, image and voice messages to others.

3. On the receiver’s side, the processing happens. If the receiver is
- Deaf: All audio files sent to the receiver is transcribed to text
which shows up a as a regular text message.
- Color-blind: All image files go through a color filter to assist
them with visibility. It aims to cater 7 different conditions:
deuteranopia, protanopia, tritanopia, tritanomaly,
deuteranomaly, cone monochromacy and rod
monochromacy.
- Blind: All text messages sent to the receiver is synthesized to
speech which shows up a voice message.

![BridgeTogether DFD](https://github.com/ani1609/NeoChat/assets/124783808/84ab96b1-841d-4efc-b87e-5dc5c20e535b)
