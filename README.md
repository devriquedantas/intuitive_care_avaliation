# estagio-intuitive-care

Este projeto foi desenvolvido como parte de um **teste técnico para vaga de estágio**.  
O objetivo é realizar a **extração, processamento e consolidação de dados públicos** relacionados a **despesas com eventos e sinistros da ANS**.

A solução foi implementada em **Java**, priorizando **simplicidade, clareza** e decisões técnicas bem explicadas, sem complexidade desnecessária.

---

## Tecnologias Utilizadas

- **Java**
- **Maven**
- **jsoup** – raspagem de dados e extração de links
- Manipulação de arquivos CSV com `BufferedReader` e `BufferedWriter`
- Estruturas de dados em memória (`List`, `Map`)

---

## Uso do jsoup

Para automatizar a extração de dados, utilizei a biblioteca **jsoup** em vez de Java puro.

O jsoup permite:

- Acessar o site da ANS
- Extrair automaticamente os links dos arquivos ZIP trimestrais
- Realizar o download dos dados sem depender de URLs fixas

---

## Fluxo do Projeto

O processamento dos dados segue as etapas abaixo:

1. **Raspagem de Dados**
    - Acesso ao site da ANS
    - Extração dos links dos arquivos ZIP trimestrais
    - Download automático dos arquivos

2. **Consolidação**
    - Leitura dos arquivos CSV extraídos
    - Seleção dos dados relacionados a eventos e sinistros
    - Geração do arquivo `consolidado_despesas.csv`

---

## Trade-offs

Durante o processamento (1.2) optei por processar os arquivos linha a linha em vez de carregar tudo na memória para garantir um desempenho melhor.
Já na validação (2.1) optei marcar como inválido os CNPJ e valores negativos.

---

## Resultado Final

O resultado do projeto é o arquivo:

- `consolidado_despesas.csv`

Ele contém os dados **consolidados e validados**, referentes às despesas com eventos e sinistros.

---
## E o restante do projeto?
As etapas seguintes do desafio não foram implementadas por falta de conhecimento técnico e tempo para aprofundamento, principalmente nas partes de análise estatística, banco de dados e desenvolvimento de API.
Preferi focar em entregar bem essas etapas do que tentar avançar e entregar algo incompleto.

---
## Como Executar

1. **Clone o repositório**

```bash
git clone https://github.com/devriquedantas/estagio-intuitive-care.git
```


2. **Compile o projeto**

```bash
mvn clean install
```








