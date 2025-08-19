# Diagrama Entidade-Relacionamento - Sistema de Estoque/Vendas

```mermaid
erDiagram
    Produto {
        int id PK
        string nome
        string marca
        decimal valor
        string codigo_barras
    }

    Estoque {
        int id PK
        int id_produto FK
        string local
        int quantidade
    }

    Cliente {
        int id PK
        string nome
        string apartamento
    }

    Funcionario {
        int id PK
        string setor
        string nome
    }

    Venda {
        int id PK
        datetime data
        int id_funcionario FK
        int id_cliente FK
        decimal valor_total
    }

    Item_venda {
        int id_venda PK,FK
        int id_produto PK,FK
        int quantidade
        decimal valor_final
    }

    Venda ||--o{ Item_venda : "tem"
    Produto ||--o{ Item_venda : "contem"
    Funcionario ||--o{ Venda : "realiza"
    Cliente ||--o{ Venda : "faz"
    Produto ||--o{ Estoque : "tem"
```