# 🎸 Touring Musician

An Android app that visualizes the Travelling Salesman Problem (TSP) using a circular linked list. Tap on a map to place concert stops and watch the app build a tour using different insertion heuristics.

## What It Does

Users tap on a US map to place tour stops for a travelling musician. The app connects the stops into a circular tour and displays the total distance. Three insertion strategies are available:

- **Beginning** — inserts each new stop at the start of the tour
- **Closest** — inserts next to the nearest existing stop
- **Smallest** — inserts at the edge that causes the smallest increase in total tour distance (cheapest insertion heuristic)

## Architecture

The core data structure is a **circular doubly-linked list** (`CircularLinkedList.java`) that implements `Iterable<Point>`. Each node stores an `android.graphics.Point` and links to its predecessor and successor.

| Class | Responsibility |
|---|---|
| `CircularLinkedList` | Circular doubly-linked list with three insertion strategies |
| `TourMap` | Custom `View` that renders the map, tour lines, and handles touch input |
| `MainActivity` | Hosts the `TourMap` and popup menu for mode selection |

## Screenshots

<p align="center">
  <img src="screenshots/Screenshot_1563784499.png" width="250" />
  <img src="screenshots/Screenshot_1563784504.png" width="250" />
  <img src="screenshots/Screenshot_1563784508.png" width="250" />
</p>

## 🛠 Tech Stack

| | Technology | Version |
|---|---|---|
| 📱 | Android SDK | 34 |
| ☕ | Java | 8 |
| 🏗️ | Gradle | 7.5.1 |
| 🎨 | AndroidX AppCompat | 1.6.1 |
| 🧩 | Material Components | 1.11.0 |

## Getting Started

1. Clone the repository
   ```bash
   git clone https://github.com/stabgan/Touring-Musician-Android-App.git
   ```
2. Open in Android Studio (Arctic Fox or later)
3. Sync Gradle and run on an emulator or device (API 23+)

## ⚠️ Known Issues

- The map image is a fixed-size bitmap — it does not scale to different screen densities or orientations
- No persistence: tour stops are lost on rotation or app restart
- The app targets a single activity with no navigation

## License

Apache License 2.0 — see [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) for details.
