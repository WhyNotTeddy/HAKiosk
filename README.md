# 🏠 HAKiosk (Android 8 / API 26 Edition)

**HAKiosk** is a dedicated, ultra-lightweight WebView wrapper designed specifically to transform legacy **Android 8 (Oreo)** tablets into permanent Home Assistant wall dashboards.

While modern browsers like Chrome or Edge have become too resource-heavy for older hardware, **HAKiosk** uses a streamlined, single-process engine to provide a smooth and stable experience on tablets from the 2017-2018 era.

---

## 🚀 Key Features

* **Independent WebView Engine**: Unlike other "kiosk" apps that simply launch Chrome, HAKiosk uses an internal WebView component. This ensures the app remains lightweight and isn't affected by heavy external browser updates.
* **Optimized for Legacy Hardware**: Targetted specifically at **API 26 (Android 8.0/8.1)** to ensure maximum CPU and RAM efficiency.
* **True Immersive Mode**: Automatically hides the Android status bar and navigation buttons for a 100% fullscreen dashboard experience.
* **Low Resource Footprint**: Designed to run 24/7 without memory leaks, keeping the tablet cool and responsive.
* **Cleartext Support**: Enabled by default to allow seamless HTTP connections to local Home Assistant instances without requiring complex SSL setups.

---

## 🛠️ Technical Specifications

| Feature | Specification |
| :--- | :--- |
| **Minimum OS** | Android 8.0 (Oreo) |
| **Target SDK** | API 26 |
| **Rendering Engine** | Android System WebView |
| **Network** | Supported HTTP/HTTPS (Cleartext enabled) |
| **RAM Usage** | ~60MB - 120MB |

---

## ⚙️ Configuration & Setup

1. **Dashboard URL**: Set your Home Assistant local or remote URL (e.g., `http://192.168.0.37:8123/lovelace/0`).
2. **Kiosk Mode**: We recommend appending `?kiosk` to your Home Assistant URL to hide the sidebar and header within the HA interface itself.
3. **Permissions**: The app only requires **Internet Access** and **Keep Screen On** permissions.

---

## 📥 Building & Installation

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Build the APK using the `release` variant.
4. Sideload the APK onto your Android 8 tablet.

---

## 📄 License
This project is available under the MIT License.
