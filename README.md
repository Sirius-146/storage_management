# Diagrama Entidade-Relacionamento - Sistema de Estoque/Vendas

```mermaid
erDiagram

    PRODUCT {
        int id PK
        string name
        string brand
        decimal value
        string barcode
    }

    LOCATION {
        int id PK
        string name
    }

    STORAGE {
        int id PK
        string local
        int quantity
        int id_product FK
    }

    CLIENT {
        int id PK
        string name
        string document
    }

    RESERVATION {
        int id PK
        date checkin
        date checkout
        string apartment
        int id_client FK
    }

    SELL {
        int id PK
        date sell_date
        int id_reservation FK
    }

    ITEM_SELL {
        int id_sell PK, FK
        int id_product PK, FK
        int quantity
        decimal value
    }

    STOCK_MOVEMENT {
        int id PK
        string type
        int quantity
        date movement_date
        int id_product FK
        int id_location FK
        int id_sell FK NULL
    }

    PRODUCT ||--o{ STORAGE : "tem"
    PRODUCT ||--o{ ITEM_SELL : "compõe"
    LOCATION ||--o{ STORAGE : "guarda"
    SELL ||--o{ ITEM_SELL : "contém"
    CLIENT ||--o{ RESERVATION : "faz"
    RESERVATION ||--o{ SELL : "origina"
    PRODUCT ||--o{ STOCK_MOVEMENT : "move"
    LOCATION ||--o{ STOCK_MOVEMENT : "ocorre em"
    SELL ||--o{ STOCK_MOVEMENT : "origina"


```
