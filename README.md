# Lichess Kotlin Multiplatform Client

[![Maven Central](https://img.shields.io/maven-central/v/io.github.viplearner/lichess-kmp-client.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.viplearner/lichess-kmp-client)

Clean, type-safe Kotlin SDK for the [Lichess API](https://lichess.org/api). Supports Android, iOS, JVM, Linux, and JS.

## Installation

```kotlin
dependencies {
    implementation("io.github.viplearner:lichess-kmp-client:0.1.1-alpha")
}
```

## Usage

### Anonymous Access
```kotlin
val client = LichessClient.anonymous()
val tvGames = client.tv.getCurrentTvGames()
client.close()
```

### With Authentication
```kotlin
val client = LichessClient.withToken("lip_yourAccessToken")

client.games.listUserGames("username").onSuccess { games ->
    games.collect { game -> println(game.id) }
}

client.close()
```

### Streaming
```kotlin
client.bot.streamEvent().onSuccess { flow ->
    flow.collect { event -> 
        println("Event: $event") 
    }
}
```

### OAuth with Auto-Refresh
```kotlin
val oauthManager = DefaultOAuthManager(
    redirectUri = "https://myapp.com/callback",
    scopes = listOf("board:play", "puzzle:read"),
    onAuthorizationRequired = { authUrl, _ ->
        // Direct user to authUrl, return authorization code
        getCodeFromCallback()
    }
)

val client = LichessClient.withClientIdAndOAuth(oauthManager)
```

## Available Services

All Lichess API endpoints are exposed through typed services:

`account`, `analysis`, `arena`, `board`, `bot`, `broadcasts`, `challenges`, `games`, `messaging`, `oauth`, `puzzles`, `relations`, `studies`, `teams`, `tv`, `users`, and more.

## Documentation

- [Lichess API Docs](https://lichess.org/api) - Official API reference
- [OpenAPI Spec](openapi.json) - Complete API specification

## License

Apache 2.0 License - See [LICENSE](LICENSE) for details
