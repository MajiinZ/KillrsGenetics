🌱 KillrsGenetics — Kotlin Multiplatform E-Commerce App
This is a Kotlin Multiplatform (KMP) project designed for KillrsGenetics, a secure and modern e-commerce solution tailored for selling premium genetics (specialized seeds and merchandise). Built using Kotlin's cross-platform capabilities, this project targets:

✅ Android (via Jetpack Compose)

✅ Desktop (Compose Multiplatform)

✅ Server (Ktor Backend with Firebase integration)

📦 Project Structure
composeApp/
Contains shared UI and business logic for Android and Desktop clients using JetBrains Compose Multiplatform.

commonMain/ – Shared Kotlin code across all targets.

Platform-specific folders (androidMain/, desktopMain/, etc.) for target-dependent implementations.

shared/
The primary shared module between all platforms. You can place core domain models, network logic, and utilities here.

Most development occurs in commonMain/.

Extend with androidMain/, iosMain/, or other folders for target-specific code when needed.

server/
This module houses the Ktor backend, which manages authentication, email orders, and integrates with Firebase.

🔒 This folder contains sensitive backend logic and will not be made public.

🔐 Key Features
Secure User Auth – Supports sign-in with Google using Firebase Authentication.

Real-Time Inventory – Admins can update product stock directly via the Firebase Console.

Custom Order Emails – On purchase, admins receive detailed emails with order content and preferred shipping.

Shared UI & Logic – Built for efficiency with a shared Kotlin codebase across Android and Desktop clients.

Modular Architecture – Inspired by multi-module clean architecture principles.

🚀 Goals & Vision
KillrsGenetics aims to provide a trustworthy and discreet shopping experience for customers interested in premium genetics. This application prioritizes security, reliability, and cross-platform reach, making it accessible on both mobile and desktop platforms with a consistent experience.

🛠️ Tech Stack
Kotlin Multiplatform

JetBrains Compose Multiplatform

Ktor for backend APIs

Firebase for authentication and data handling

Google Sign-In

Modular Architecture following clean coding practices

📚 Based On
While the app draws architectural guidance from Stefan Jovanovic’s Kotlin Multiplatform eCommerce course, it has been customized extensively to meet the unique branding, security, and operational needs of KillrsGenetics.

Check out the Kotlin Multiplatform Docs

🧪 In Progress
This project is still under active development. The following features are planned:

🔄 Payment gateway integration (Stripe, Coinbase Commerce)

📦 Order tracking system

📱 iOS version using Swift + Kotlin Multiplatform

🧠 Advanced filtering and search experience

🧾 Admin analytics dashboard

🙌 Contributions
This is a private-use, branded project and not accepting public contributions at this time. However, feel free to fork or explore for learning purposes.

📄 License
Private Use Only — All rights reserved to KillrsGenetics. Not for commercial redistribution or unauthorized resale.
