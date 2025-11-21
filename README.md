# Lichess Kotlin Multiplatform Client

[![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Clean, type-safe Kotlin SDK for the [Lichess API](https://lichess.org/api). Supports Android, iOS, JVM, Linux, and JS.

## Installation

```kotlin
dependencies {
    implementation("io.github.viplearner:lichess-kmp-client:0.1.0-alpha")
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
