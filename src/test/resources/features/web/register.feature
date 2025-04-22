@web @priority2
Feature: Registrasi Akun di Demoblaze
  Sebagai user baru
  Saya ingin mendaftar akun di Demoblaze
  Agar saya bisa login dan berbelanja di situs ini

  Scenario Outline: Mendaftar akun di Demoblaze
    Given user membuka halaman utama Demoblaze
    When user mengklik menu Sign up
    And user memasukkan username "<username>"
    And user memasukkan password "<password>"
    And user menekan tombol Sign up
    Then sistem menampilkan pesan "<expectedMessage>"

    Examples:
      | username | password    | expectedMessage              |
      | random   | amir123     | Sign up successful.          |
      | user123  | password123 | This user already exist.     |
