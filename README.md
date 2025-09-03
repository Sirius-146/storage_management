# Diagrama Entidade-Relacionamento - Sistema de Estoque/Vendas

```mermaid
erDiagram
    PRODUCT ||--o{ STORAGE : contains
    LOCATION ||--o{ STORAGE : stores
    PRODUCT ||--o{ ITEM_SOLD : sold_in
    SELL ||--o{ ITEM_SOLD : has
    SELL ||--o{ STOCK_MOVEMENT : related_to
    PRODUCT ||--o{ STOCK_MOVEMENT : moved
    LOCATION ||--o{ STOCK_MOVEMENT : moved_at
    SELL }o--|| WORKER : made_by
    SELL }o--|| RESERVATION : for
    RESERVATION ||--o{ SELL : includes
    RESERVATION ||--o{ GUEST : has
    RESERVATION ||--|| APARTMENT : assigned_to
    RESERVATION ||--|| CLIENT : booked_by
    RESERVATION }o--|| RESERVATION_GROUP : grouped_in
    RESERVATION_GROUP ||--o{ RESERVATION : contains
    WORKER ||--o{ SELL : made
    WORKER }o--|| ROLE : has
    APARTMENT ||--o{ RESERVATION : reserved
    PRODUCT ||--o{ STORAGE : stored_in
    ITEM_SOLD ||--|| PRODUCT : references
    ITEM_SOLD ||--|| SELL : references
    GUEST ||--|| RESERVATION : belongs_to

    PRODUCT {
        Integer id PK
        String name
        String brand
        Double price
        Double cost
        String barCode
    }
    LOCATION {
        Integer id PK
        String name
        String type
    }
    STORAGE {
        Integer id PK
        Integer quantity
    }
    STOCK_MOVEMENT {
        Integer id PK
        MovementType type
        String reason
        Integer quantity
        LocalDateTime movementDate
    }
    SELL {
        Integer id PK
    }
    ITEM_SOLD {
        Integer sell PK, FK
        Integer product PK, FK
        Integer quantity
        Double finalValue
    }
    WORKER {
        Integer id PK
        String name
        String username
        String password
        String department
        Role role
    }
    ROLE {
        ADMIN
        RECEPTIONIST
        WAITER
        MAITRE
        STORAGE_MANAGER
        HUMAN_RESOURCES
    }
    RESERVATION {
        Integer id PK
        LocalDate plannedCheckin
        LocalDateTime checkin
        LocalDate plannedCheckout
        LocalDateTime checkout
        ReservationStatus status
        BigDecimal discount
        BigDecimal dailyRate
    }
    RESERVATION_GROUP {
        Integer id PK
        String groupName
    }
    APARTMENT {
        Integer id PK
        Integer number
        String type
    }
    CLIENT {
        Integer id PK
        String name
        String cpf
        String phone
        String email
        String address
    }
    GUEST {
        Integer id PK
        String name
        Integer age
    }
```
