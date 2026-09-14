# gradle-conventions

Org-wide Gradle conventions for [KolektivComputer](https://github.com/KolektivComputer).

See epic: [KolektivComputer/.github#2](https://github.com/KolektivComputer/.github/issues/2)

## `computer.kolektiv.publishing`

Dual Maven publish: **GitHub Packages** + optional Yuri Capital when env credentials exist.

```kotlin
plugins {
    `maven-publish`
    id("computer.kolektiv.publishing") version "0.1.0"
}
```

Publish job needs `permissions: packages: write` and `GITHUB_TOKEN`.
