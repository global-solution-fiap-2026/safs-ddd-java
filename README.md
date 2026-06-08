# 🚀 SAFS — Surface Autonomous Fleet System

> Sistema de gerenciamento de frota autônoma para missões de mineração e exploração espacial, desenvolvido com **Java** e **Domain-Driven Design (DDD)**.

---

## 📋 Sobre o Projeto

O **SAFS** simula o coração operacional de uma missão espacial autônoma baseada no contexto da **Nova Economia Espacial** e do programa **ISRU (In-Situ Resource Utilization)**. 

Devido ao delay de comunicação de até 20 minutos entre a Terra e Marte, missões espaciais modernas dependem de sistemas autônomos para operação em campo. O SAFS é o sistema que permite ao **Comandante de Missão** gerenciar uma frota de sondas robóticas via terminal, delegando coordenadas de extração, supervisionando integridade da frota e garantindo o retorno seguro das unidades à base.

---

## 🏗️ Arquitetura

O projeto segue rigorosamente a **arquitetura em camadas do DDD**:

```
presentation/     → Interface com o usuário via console (Scanner)
application/      → Orquestração dos casos de uso (MissaoService)
domain/           → Coração do negócio (Entidades, Value Objects, Regras)
infrastructure/   → Repositório em memória (simulação de banco de dados)
```

### Princípios aplicados
- **Inversão de Dependência** — o domínio define contratos via interfaces; a infraestrutura os implementa
- **Encapsulamento** — regras de negócio protegidas dentro dos próprios objetos
- **Imutabilidade** — Value Objects nunca mudam de estado; operações retornam novos objetos
- **Separação de responsabilidades** — cada camada conhece apenas a camada imediatamente abaixo

---

## 🧩 Padrões de Projeto

| Padrão | Classe | Descrição |
|---|---|---|
| **Singleton** | `CentroDeComando` | Garante uma única instância controlando todas as sondas ativas |
| **Factory Method** | `SondaFactory` | Cria `SondaMineradora` ou `SondaExploradora` sem expor detalhes de construção |
| **Template Method** | `Sonda.executarRotinaAutonoma()` | Define o esqueleto da rotina autônoma; cada subclasse implementa sua ação específica |

---

## 🌍 Linguagem Ubíqua (Glossário do Domínio)

| Termo | Descrição |
|---|---|
| **Sonda** | Veículo autônomo. Pode ser Mineradora ou Exploradora |
| **Coordenada** | Ponto exato na malha do terreno (X, Y) |
| **NivelEnergia** | Capacidade de bateria da sonda. Toda ação consome energia |
| **Terreno** | Característica do solo que afeta o consumo de bateria |
| **Recurso** | Material sendo extraído (Gelo, Regolito, Titânio) |
| **CompartimentoCarga** | Armazém interno da sonda mineradora com capacidade máxima |
| **CentroDeComando** | Painel de controle único que registra todas as sondas ativas |

---

## ⚙️ Funcionalidades

### 🛸 Lançamento de Sondas
- Criação de **SondaMineradora** com recurso alvo, capacidade de bateria e capacidade de carga customizáveis
- Criação de **SondaExploradora** com capacidade de bateria e alcance de sensor customizáveis
- Geração automática de ID sequencial (`SND-001`, `SND-002`, ...)
- Registro automático no `CentroDeComando` e no repositório

### 🗺️ Missões Autônomas
- Envio de sonda para coordenadas específicas com tipo de terreno definido
- Execução da **rotina autônoma** em 4 etapas via Template Method:
  1. Validação de sistema (bateria e diagnóstico)
  2. Deslocamento até a coordenada
  3. Execução da ação local (mineração ou mapeamento)
  4. Envio de relatório ao Centro de Comando

### ⚡ Sistema de Energia
- Cálculo de custo de bateria baseado na **distância de Manhattan**
- Multiplicador de consumo por tipo de terreno (Planície 1.0x, Solo Rochoso 1.5x, Cratera 2.0x)
- **Proteção contra missões suicidas** — o sistema impede o envio de sondas a distâncias que não permitam o retorno à base

### 🔋 Recarga na Base
- Sondas podem recarregar bateria ao máximo conectando-se à base
- Restrição: a sonda deve estar na coordenada `(0, 0)` para recarregar

### 📊 Monitoramento da Frota
- Listagem de todas as sondas ativas com status completo
- **SondaMineradora** exibe: ID, bateria, posição, recurso alvo, carga atual/máxima
- **SondaExploradora** exibe: ID, bateria, posição, alcance do sensor

---

## 🛡️ Regras de Negócio e Exceções

| Exceção | Quando é lançada |
|---|---|
| `BateriaCriticaException` | Tentativa de mover/extrair sem energia suficiente |
| `CargaExcedidaException` | Tentativa de extrair além da capacidade do compartimento |
| `TerrenoInvalidoException` | Sonda com rodas tenta entrar em uma Cratera |
| `IllegalArgumentException` | ID inválido, tipo desconhecido, sonda não encontrada |

Todas as exceções são tratadas de forma amigável no console — nenhuma stacktrace é exposta ao Comandante de Missão.

---

## 🗂️ Estrutura do Projeto

```
src/
└── br/com/space/connect/
    ├── presentation/
    │   └── Main.java                      # Menu interativo via Scanner
    ├── application/
    │   └── MissaoService.java             # Orquestração dos casos de uso
    ├── domain/
    │   ├── entities/
    │   │   ├── Sonda.java                 # Entidade abstrata base
    │   │   ├── SondaMineradora.java       # Especialização: extração de recursos
    │   │   ├── SondaExploradora.java      # Especialização: mapeamento de terreno
    │   │   └── CentroDeComando.java       # Singleton: registro de sondas ativas
    │   ├── valueobjects/
    │   │   ├── Coordenada.java            # Ponto no terreno (imutável)
    │   │   ├── NivelEnergia.java          # Capacidade de bateria (imutável)
    │   │   ├── CompartimentoCarga.java    # Armazém de recursos (imutável)
    │   │   ├── Terreno.java               # Enum: tipo de solo
    │   │   └── Recurso.java               # Enum: material extraído
    │   ├── interfaces/
    │   │   ├── Recarregavel.java          # Contrato de recarga
    │   │   └── SondaRepository.java       # Contrato do repositório
    │   ├── factory/
    │   │   └── SondaFactory.java          # Criação de sondas
    │   └── exception/
    │       ├── BateriaCriticaException.java
    │       ├── CargaExcedidaException.java
    │       └── TerrenoInvalidoException.java
    └── infrastructure/
        └── SondaRepositoryEmMemoria.java  # Repositório em memória (HashMap)
```

---

## 🖥️ Como Executar

### Pré-requisitos
- Java 17+
- Maven ou sua IDE de preferência (IntelliJ IDEA recomendado)

### Rodando o projeto
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/safs-ddd.git

# Entre na pasta
cd safs-ddd

# Compile e execute
javac -r src/ && java br.com.space.connect.presentation.Main
```

### Menu do sistema
```
=== CENTRO DE COMANDO ===
1 - Lançar nova sonda
2 - Listar sondas
3 - Enviar sonda para coordenada e realizar ação
4 - Recarregar sonda
5 - Sair
```

---

## 🔬 Exemplos de Uso

### Lançando uma SondaMineradora
```
Tipo de sonda (MINERACAO / EXPLORACAO): MINERACAO
Capacidade máxima de bateria: 200
Tipo de recurso (GELO / REGOLITO / TITANIO): TITANIO
Capacidade máxima de carga: 100
Terreno inicial: PLANICIE
✅ Sonda lançada com sucesso: SND-001
```

### Enviando para uma missão
```
Digite o ID da sonda: SND-001
X: 5
Y: 3
Tipo de terreno (PLANICIE / SOLO_ROCHOSO / CRATERA): SOLO_ROCHOSO
Validando sistema... Bateria: 200.0/200.0
Mineração concluída! Extraídas 10.0 unidades de Titanio | Volume: 20.0 | Carga: 20.0/100.0
Relatório enviado. Sonda: SND-001 na coordenada (5, 3) | Terreno: Solo Rochoso
✅ Missão executada com sucesso!
```

### Proteção contra bateria insuficiente
```
X: 100
Y: 100
⚠ Alerta: Bateria Crítica! Energia insuficiente para ir ao destino e voltar à base.
```

---

## 🛠️ Tecnologias

- **Java 17**
- **Domain-Driven Design (DDD)**
- **Programação Orientada a Objetos**
- **Padrões de Projeto GoF** (Singleton, Factory Method, Template Method)

---

## 👨‍💻 Autores

Victor Simões Altieri
Rafael Falaguasta Ferraz
Gabriel Viana de Souza

Desenvolvido como projeto acadêmico para a disciplina de **Domain Driven Design - Java** na **FIAP — Engenharia de Software**.
Orientado pelo professor Eduardo Ramos.

---

> *"Arquitetura se constrói na base. Um código limpo vale mais que um código que apenas funciona."*
