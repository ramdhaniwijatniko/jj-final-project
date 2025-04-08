@login
Feature: Login ke Demoblaze

Scenario: User berhasil login ke aplikasi
  Given user membuka halaman utama Demoblaze
  When user mengklik menu Log in
  And user memasukkan username "jamkrida" dan password "jamkrida123"
  And user menekan tombol Log in
  Then sistem menampilkan halaman utama


  Scenario: Gagal login dengan password salah
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Log in
    And user memasukkan username "user123" dan password "passwordsalah"
    And user menekan tombol Log in
    Then sistem menampilkan pesan "Wrong password."

  Scenario: Gagal login dengan username yang belum terdaftar
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Log in
    And user memasukkan username "tidakadauser" dan password "password123"
    And user menekan tombol Log in
    Then sistem menampilkan pesan "User does not exist."
