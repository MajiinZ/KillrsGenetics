🌱 KillrsGenetics — Kotlin Multiplatform E-Commerce App

KillrsGenetics is a secure, modern e-commerce platform built using Kotlin Multiplatform (KMP). Designed for the sale of premium genetics—including specialized seeds and branded merchandise—this cross-platform project emphasizes performance, privacy, and a seamless shopping experience.

Built with Kotlin's robust cross-platform capabilities, KillrsGenetics targets:

✅ Android (Jetpack Compose)

✅ Desktop (Compose Multiplatform)

✅ Web (WASM, in progress)

✅ Server (Ktor backend with Firebase integration)


## 📽️ Demo

Check out the app in action on YouTube Shorts:  

[![Watch the demo](https://img.youtube.com/vi/cbXkYSfZJDo/0.jpg)](https://youtube.com/shorts/cbXkYSfZJDo)




---

📦 Project Structure

composeApp/

UI and business logic for Android and Desktop.

Built with JetBrains Compose Multiplatform.

Uses shared view models and design components.


shared/

Core logic, models, networking, and utilities.

Most development takes place in commonMain/.

Platform-specific extensions (e.g., androidMain/, desktopMain/) override shared behavior when necessary.


server/

Ktor-based backend with integration for:

Firebase Authentication

Secure order handling

Email-based order notifications



> 🔒 Note: This folder contains sensitive backend logic and is not publicly available.




---

🔐 Key Features

Secure Firebase Authentication
Supports Google Sign-In for streamlined user access.

Real-Time Inventory Management
Admins can adjust product stock via Firebase Console.

Automated Order Notifications
Purchases trigger custom order emails to admin with detailed order data.

Shared Codebase
UI and logic are shared across Android and Desktop for consistency and maintainability.

Modular Architecture
Project structure follows clean architecture principles, separating concerns across modules like feature/, data/, navigation/, and di/.



---

🚀 Vision

KillrsGenetics is committed to delivering a trusted, discreet, and cross-platform shopping experience tailored for customers in the premium genetics market. The app is built with security-first practices and optimized for reliability on both mobile and desktop environments.


---

🛠️ Tech Stack

Kotlin Multiplatform (KMP)

JetBrains Compose Multiplatform

Ktor (Backend APIs)

Firebase (Authentication, Realtime DB, Storage)

Google Sign-In

Modular, multi-layer architecture (inspired by clean architecture practices)



---

📚 Based On

This project is influenced by Stefan Jovanovic’s Kotlin Multiplatform eCommerce course, but has been significantly customized to support the unique branding, operational needs, and privacy considerations of KillrsGenetics.


---

🧪 In Progress

Development is active. Planned upcoming features include:

🔄 Payment Gateway Integration (Stripe, Coinbase Commerce)

📦 Order Tracking System

📱 iOS Support using Swift + Kotlin Multiplatform

🧠 Advanced Filtering & Search

📊 Admin Analytics Dashboard

🧾 Printable Invoices & Receipts



---

🙌 Contributions

This is a private, branded project and not accepting outside contributions at this time.
However, you are welcome to explore or fork the project for educational purposes only.


---

📄 License

Private Use Only
All rights reserved to KillrsGenetics.
Commercial redistribution or unauthorized resale is strictly prohibited.
