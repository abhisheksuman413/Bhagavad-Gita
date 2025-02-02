# Bhagavad Gita Mobile Application

The **Bhagavad Gita Mobile Application** is an Android app designed to bring the timeless wisdom of the **Bhagavad Gita** to your fingertips. The Bhagavad Gita, often referred to as the *“Song of God,”* is a 700-verse spiritual and philosophical scripture from the ancient Indian epic, *Mahabharata*. It explores profound themes such as duty (*dharma*), devotion (*bhakti*), knowledge (*jnana*), and self-realization.

This app provides users with a seamless and intuitive platform to explore all **18 chapters** and **700 verses** of the Bhagavad Gita, complete with **accurate translations**, **detailed commentaries**, and **word meanings** to deepen understanding. Whether you're a spiritual seeker, student of philosophy, or someone looking for daily inspiration, this app serves as a personal guide for reflection and growth.


### 🎯 **Key Highlights:**
- **Comprehensive Content:** Access the complete Bhagavad Gita, including Sanskrit verses, English translations, transliterations, and commentaries from renowned scholars.
- **Personalized Experience:** Save your favorite chapters and verses to revisit them anytime, even offline.
- **Easy Navigation:** The app’s simple and clean interface makes it easy to move between chapters and verses with just a few taps.
- **Offline Access:** Saved content is available without the need for an internet connection.
- **Spiritual Growth:** Perfect for daily reflections, meditation, and study sessions.

This app is not just a reading tool; it's a **spiritual companion** designed to inspire, motivate, and guide you on your personal journey, offering timeless wisdom relevant to modern life. 🌟




## Features

- **Displays chapters and verses with translations and commentaries:** The app fetches data from an API using Retrofit and displays Bhagavad Gita chapters and verses along with their translations and commentaries for deeper understanding.

- **Save favorite chapters and verses locally using RoomDB:** Users can save their favorite chapters and verses for offline access. This data is stored using Room Database, ensuring persistence even after app restarts.

- **Manage saved content with SharedPreferences:** The app uses SharedPreferences to quickly store and retrieve lightweight user preferences, such as bookmarked chapters or last-read positions.

- **Check network connectivity for seamless data loading:** Before fetching data from the API, the app checks for network connectivity to avoid unnecessary requests. If the device is offline, it gracefully falls back to locally saved data.

- **Dark mode compatibility for better readability:** The app supports Dark Mode, improving user experience in low-light environments by automatically adjusting UI themes.

- **Single Activity Architecture with multiple Fragments for smooth navigation:** The app follows a Single Activity Architecture using the Navigation Component, ensuring seamless transitions between different fragments without performance overhead.

- **Animations for better user experience:** Smooth fragment transitions and UI animations enhance the usability of the app, providing an engaging and modern user experience.

## Screenshots
**𝐂𝐨𝐦𝐢𝐧𝐠 𝐒𝐨𝐨𝐧**

## Technologies Used

- **Kotlin** for Android app development.
- **XML** for designing intuitive user interfaces.
- **MVVM Architecture** with Repository Pattern for clean code structure.
- **Retrofit** for API calls to fetch Bhagavad Gita content.
- **RoomDB** for local data storage.
- **SharedPreferences** for lightweight data persistence.
- **Kotlin Coroutines** for asynchronous operations.
- **Network Connectivity Manager** for real-time connection status.
- **Fragments** for modular and reusable UI components.
- **Navigation Graph** for managing screen transitions.
- **Animations** in Navigation Graph for smooth and visually appealing transitions between screens.

## Prerequisites

- Android Studio installed
- Basic knowledge of Kotlin, XML and Android components like ViewModel, RoomDB, Retrofit, and Coroutines.

## Architecture

### MVVM (Model-View-ViewModel)
This app follows the MVVM pattern for better code maintainability and separation of concerns.

- **Model:** Handles data retrieval (API, RoomDB, SharedPreferences)

- **ViewModel:** Acts as a bridge between View and Model, exposing LiveData and Flow for UI updates.

- **View (Fragments):** Observes LiveData from ViewModel and updates UI accordingly.

### Repository Pattern
The repository acts as a single source of truth, managing data from both local (RoomDB) and remote (API) sources. It ensures a clean separation of concerns and promotes code reusability
It abstracts the data sources such as API calls, Room Database, and SharedPreferences into a single class..



## Learn More

For more insights and a step-by-step guide on building this app, check out:
- **Medium Article:** [Link](https://medium.com/@abhisheksuman413)

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/abhisheksuman413/Bhagavad-Gita.git
