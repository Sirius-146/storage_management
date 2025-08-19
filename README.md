# Diagrama Entidade-Relacionamento - Sistema de Estoque/Vendas

```mermaid
erDiagram

    Products {
        int id PK
        string name
        string brand
        decimal value
        string barCode
    }

    Storages {
        int id PK
        int id_product FK
        string name
        int quantity
    }

    Sells {
        int id PK
        datetime sell_date
        int id_location FK
        decimal total_value
    }

    ItemsSold {
        int id_sell PK, FK
        int id_product PK, FK
        int quantity
        decimal final_value
    }

    StockMovement {
        int id PK
        int id_product FK
        int id_location FK
        string type  // IN | OUT
        string reason // Venda, Transferência, Perda, Ajuste
        int quantity
        datetime movement_date
        int id_sell FK NULL
    }

    Locations {
        int id PK
        string name
        string tipo // Almoxarifado ou Ponto de Venda
    }

    Product ||--o{ Storage : "tem"
    Product ||--o{ ItemSold : "vendido"
    Sell ||--o{ ItemSold : "contém"
    Location ||--o{ Sell : "realizado_em"
    Product ||--o{ StockMovement : "movimentado"
    Location ||--o{ StockMovement : "ocorre_em"
    Sell ||--o{ StockMovement : "origina"

```