# Curso de Java — de cero a experto

Mentor: ingeniero senior Java (APIs, 15+ años).
Alumno: Jorge — 5 años programando, pero en GeneXus (4GL declarativo). Poca experiencia en Java.
Objetivo: dominio sólido de Java moderno (21 LTS), patrones de diseño, principios SOLID y Git.

## Entorno

| Herramienta | Versión |
|---|---|
| JDK | 21.0.9 LTS |
| Maven | 3.9.16 |
| Git | 2.53 |
| IDE | IntelliJ IDEA |

Proyecto Maven: `pe.forjix:java-learning`. Código en `src/main/java/pe/forjix/leccionNN/`.

## Roadmap

### Fase A — Fundamentos del lenguaje
- [ ] **01** — Qué es Java: JVM, JDK, bytecode. Anatomía de un programa. Compilar y ejecutar. Git básico.
- [ ] **02** — Tipos, variables, primitivos vs objetos, `var`, operadores, casting.
- [ ] **03** — Control de flujo: `if`, `switch` expressions, bucles, arrays.
- [ ] **04** — Métodos: firma, parámetros, `return`, sobrecarga, `static` vs instancia, scope.
- [ ] **05** — Strings: inmutabilidad, `String` vs `StringBuilder`, text blocks, formateo.

### Fase B — Programación orientada a objetos
- [ ] **06** — Clases y objetos: campos, constructores, `this`, encapsulamiento, `final`.
- [ ] **07** — Herencia y polimorfismo: `extends`, `@Override`, `abstract`, `sealed`.
- [ ] **08** — Interfaces: contratos, `default`, `static`, interfaces funcionales.
- [ ] **09** — `record`, `enum`, clases anidadas, `Object` (`equals`, `hashCode`, `toString`).
- [ ] **10** — Pattern matching e `instanceof` moderno, `switch` sobre tipos.

### Fase C — Bibliotecas esenciales
- [ ] **11** — Colecciones: `List`, `Set`, `Map`, `Deque`; cuándo usar cada una.
- [ ] **12** — Genéricos: `<T>`, wildcards, borrado de tipos.
- [ ] **13** — Excepciones: checked vs unchecked, `try-with-resources`, diseño de errores.
- [ ] **14** — Lambdas y `Stream`: `map`, `filter`, `reduce`, `Collectors`, `Optional`.
- [ ] **15** — Fechas (`java.time`), I/O y archivos (`Path`, `Files`).

### Fase D — Ingeniería
- [ ] **16** — Testing con JUnit 5, AAA, mocks, TDD.
- [ ] **17** — Principios SOLID, uno por uno, con refactors reales.
- [ ] **18** — Patrones de diseño: creacionales, estructurales, de comportamiento.
- [ ] **19** — Git en serio: ramas, merge vs rebase, PRs, conventional commits.
- [ ] **20** — Concurrencia: hilos, `ExecutorService`, virtual threads.
- [ ] **21** — JVM por dentro: memoria, GC, rendimiento.
- [ ] **22** — Proyecto final: una API REST bien diseñada.

## Reglas de trabajo

1. Cada lección: teoría breve → ejemplo comentado → ejercicio tuyo → corrección del mentor.
2. Tú escribes el código del ejercicio. El mentor lo lee del proyecto y lo corrige (no se pega código en el chat).
3. Cada lección terminada se cierra con un commit de Git.
