# 🧪 Automation Test Framework  

Framework pengujian otomatis untuk **Web UI (Demoblaze) & API (DummyAPI.io)** menggunakan **Java, Gradle, Selenium, RestAssured, Cucumber, dan GitHub Actions**.  

## 📌 Fitur  
✅ Pengujian **Web UI & API** dalam satu repository.  
✅ Menggunakan **Cucumber (Gherkin)** dan **Page Object Model (POM)**.  
✅ **Gradle Tasks** untuk menjalankan pengujian berdasarkan tag.  
✅ **GitHub Actions** untuk otomatisasi pengujian.  
✅ **Laporan HTML & JSON**.  

## 🛠 Tools & Library  

| Tool/Library | Versi |
|-------------|-------|
| Java        | 21    |
| Gradle      | Latest |
| Cucumber    | Latest |
| Selenium WebDriver | Latest |
| RestAssured | Latest |
| JUnit       | 5+    |

## 🚀 Cara Menjalankan Test  

1️⃣ **Clone Repository**  
```sh
git clone https://github.com/username/repository.git
cd repository
2️⃣ Menjalankan API Tests 
```sh
./gradlew test -Dcucumber.filter.tags="@api"
3️⃣ Menjalankan Web UI Tests 
```sh
./gradlew test -Dcucumber.filter.tags="@web"
4️⃣ Menjalankan Semua Test  
```sh
./gradlew test

🏗 Struktur Folder

📦 jj-final-tugas
 ┣ 📂 src
 ┃ ┣ 📂 main/java/web
 ┃ ┣ 📂 test/java
 ┃ ┃ ┣ 📂 api  # Pengujian API
 ┃ ┃ ┣ 📂 web  # Pengujian Web UI
 ┃ ┃ ┃ ┣ 📂 pages  # Page Object Model
 ┃ ┃ ┃ ┗ 📂 stepdefinitions  # Step Definition Cucumber
 ┃ ┃ ┣ 📂 resources/features  # File .feature untuk skenario pengujian
 ┣ 📂 .github/workflows  # Konfigurasi GitHub Actions
 ┣ 📜 README.md
 ┗ 📜 build.gradle

