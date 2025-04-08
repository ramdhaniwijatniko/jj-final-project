@checkout
Feature: Checkout di Demoblaze

  @negative
  Scenario: User mencoba checkout tanpa mengisi data
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User mencoba melakukan checkout tanpa mengisi data
    Then Checkout gagal dan muncul pesan error "Please fill out all fields"

  @negative
  Scenario Outline: User mencoba checkout dengan data tidak valid
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User melakukan checkout dengan data berikut:
      | name   | country   | city    | card       | month | year  |
      | <name> | <country> | <city>  | <card>     | <month> | <year> |
    Then Checkout gagal dan muncul pesan error "<error_message>"

    Examples:
      | name  | country   | city    | card       | month | year  | error_message             |
      |       | Indonesia | Jakarta | 123456789  | 12    | 2025  | Name is required         |
      | Indah |          | Jakarta | 123456789  | 12    | 2025  | Country is required      |
      | Indah | Indonesia |        | 123456789  | 12    | 2025  | City is required        |
      | Indah | Indonesia | Jakarta |           | 12    | 2025  | Card number is required |
      | Indah | Indonesia | Jakarta | abcd1234  | 12    | 2025  | Invalid card number     |
      | Indah | Indonesia | Jakarta | 1234       | 12    | 2025  | Invalid card number     |
      | Indah | Indonesia | Jakarta | 123456789  | 15    | 2025  | Invalid month           |
      | Indah | Indonesia | Jakarta | 123456789  | 12    | 2020  | Card expired            |

  @positive
  Scenario: User berhasil melakukan checkout dengan data valid
    Given User membuka halaman Demoblaze
    When User menambahkan produk "Samsung galaxy s6" ke keranjang
    And User pergi ke halaman cart
    And User melakukan checkout dengan data berikut:
      | name  | country   | city    | card       | month | year  |
      | Indah | Indonesia | Jakarta | 123456789  | 12    | 2025  |
    Then Order berhasil diproses dan muncul pesan sukses "Thank you for your purchase!"
    And User menutup pop-up konfirmasi pembelian
