@purchase
Feature: Checkout Produk di Demoblaze

  Scenario: User berhasil melakukan checkout di Demoblaze
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User melakukan checkout dengan data berikut:
      | name | country | city | card          | month | year |
      | John | USA     | NY   | 1234567890123 | 12    | 2025 |
    Then Order berhasil diproses dan muncul pesan sukses "Thank you for your purchase!"
    And User menutup pop-up konfirmasi pembelian

  Scenario: User gagal melakukan checkout tanpa mengisi data
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User mencoba melakukan checkout tanpa mengisi data
    Then Checkout gagal dan muncul pesan error "Please fill out Name and Creditcard."
