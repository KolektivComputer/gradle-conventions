# gradle-conventions

Org-wide Gradle conventions for KolektivComputer.

Epic: https://github.com/KolektivComputer/.github/issues/2

JSR / npm scope: `@kolektiv` (owned).

Maven groupId family for consuming libraries: `computer.kolektiv.*` (not `dev.kolektiv.*`). This plugin registers publish *repositories*; each repo still sets its own `group`.

## `computer.kolektiv.publishing`

Registers GitHub Packages (+ optional Yuri Capital) when `maven-publish` is applied.

```kotlin
plugins {
    `maven-publish`
    id("computer.kolektiv.publishing") version "0.1.0"
}
```
