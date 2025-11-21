package com.viplearner.api.client

import com.viplearner.api.client.auth.NoAuthProvider
import com.viplearner.api.client.auth.OAuthAuthProvider
import com.viplearner.api.client.auth.OAuthManager
import com.viplearner.api.client.auth.StaticTokenAuthProvider
import com.viplearner.api.client.clientid.ClientIdProvider
import com.viplearner.api.client.clientid.InMemoryClientIdProvider
import com.viplearner.api.services.AccountService
import com.viplearner.api.services.AnalysisService
import com.viplearner.api.services.ArenaTournamentsService
import com.viplearner.api.services.BoardService
import com.viplearner.api.services.BotService
import com.viplearner.api.services.BroadcastsService
import com.viplearner.api.services.BulkPairingsService
import com.viplearner.api.services.ChallengesService
import com.viplearner.api.services.ExternalEngineService
import com.viplearner.api.services.FideService
import com.viplearner.api.services.GamesService
import com.viplearner.api.services.MessagingService
import com.viplearner.api.services.OauthService
import com.viplearner.api.services.OpeningExplorerService
import com.viplearner.api.services.PuzzlesService
import com.viplearner.api.services.RelationsService
import com.viplearner.api.services.SimulsService
import com.viplearner.api.services.StudiesService
import com.viplearner.api.services.SwissTournamentsService
import com.viplearner.api.services.TablebaseService
import com.viplearner.api.services.TeamsService
import com.viplearner.api.services.TvService
import com.viplearner.api.services.UsersService
import io.ktor.client.HttpClient
import io.ktor.utils.io.core.Closeable

class LichessClient private constructor(
    private val ctx: ApiContext,
) : Closeable {
    private val apiClient by lazy { BaseApiClient(ctx) }

    val games: GamesService by lazy { GamesService(apiClient) }
    val users: UsersService by lazy { UsersService(apiClient) }
    val account: AccountService by lazy { AccountService(apiClient) }
    val board: BoardService by lazy { BoardService(apiClient) }
    val bot: BotService by lazy { BotService(apiClient) }
    val challenges: ChallengesService by lazy { ChallengesService(apiClient) }
    val analysis: AnalysisService by lazy { AnalysisService(apiClient) }
    val arena: ArenaTournamentsService by lazy { ArenaTournamentsService(apiClient) }
    val broadcasts: BroadcastsService by lazy { BroadcastsService(apiClient) }
    val bulkPairings: BulkPairingsService by lazy { BulkPairingsService(apiClient) }
    val externalEngine: ExternalEngineService by lazy { ExternalEngineService(apiClient) }
    val fide: FideService by lazy { FideService(apiClient) }
    val messaging: MessagingService by lazy { MessagingService(apiClient) }
    val oauth: OauthService by lazy { OauthService(apiClient) }
    val openingExplorer: OpeningExplorerService by lazy { OpeningExplorerService(apiClient) }
    val puzzles: PuzzlesService by lazy { PuzzlesService(apiClient) }
    val relations: RelationsService by lazy { RelationsService(apiClient) }
    val simuls: SimulsService by lazy { SimulsService(apiClient) }
    val studies: StudiesService by lazy { StudiesService(apiClient) }
    val swiss: SwissTournamentsService by lazy { SwissTournamentsService(apiClient) }
    val tablebase: TablebaseService by lazy { TablebaseService(apiClient) }
    val teams: TeamsService by lazy { TeamsService(apiClient) }
    val tv: TvService by lazy { TvService(apiClient) }

    override fun close() {
        ctx.authProvider.close()
        ctx.httpClient.close()
    }

    companion object {
        fun anonymous(
            baseUrl: String = LICHESS_API_URL,
            httpClient: HttpClient? = null,
        ): LichessClient {
            val client = httpClient ?: BaseApiClient.createDefaultHttpClient()
            val context =
                ApiContext(
                    baseUrl = baseUrl,
                    httpClient = client,
                    authProvider = NoAuthProvider(),
                    clientIdProvider = null,
                )
            return LichessClient(context)
        }

        fun withToken(
            token: String,
            baseUrl: String = LICHESS_API_URL,
            httpClient: HttpClient? = null,
            clientIdProvider: ClientIdProvider? = null,
        ): LichessClient {
            val client = httpClient ?: BaseApiClient.createDefaultHttpClient()
            val context =
                ApiContext(
                    baseUrl = baseUrl,
                    httpClient = client,
                    authProvider = StaticTokenAuthProvider(token),
                    clientIdProvider = clientIdProvider,
                )
            return LichessClient(context)
        }

        fun withClientId(
            clientId: String? = null,
            baseUrl: String = LICHESS_API_URL,
            httpClient: HttpClient? = null,
        ): LichessClient {
            val client = httpClient ?: BaseApiClient.createDefaultHttpClient()
            val provider =
                clientId?.let {
                    object : ClientIdProvider {
                        override fun getClientId() = it
                    }
                } ?: InMemoryClientIdProvider()

            val context =
                ApiContext(
                    baseUrl = baseUrl,
                    httpClient = client,
                    authProvider = NoAuthProvider(),
                    clientIdProvider = provider,
                )
            return LichessClient(context)
        }

        fun withClientIdAndOAuth(
            oauthManager: OAuthManager,
            clientId: String? = null,
            baseUrl: String = LICHESS_API_URL,
            httpClient: HttpClient? = null,
        ): LichessClient {
            val client = httpClient ?: BaseApiClient.createDefaultHttpClient()
            val provider =
                clientId?.let {
                    object : ClientIdProvider {
                        override fun getClientId() = it
                    }
                } ?: InMemoryClientIdProvider()

            val actualClientId = clientId ?: provider.getClientId()

            val context =
                ApiContext(
                    baseUrl = baseUrl,
                    httpClient = client,
                    authProvider = OAuthAuthProvider(actualClientId, oauthManager),
                    clientIdProvider = provider,
                )
            return LichessClient(context)
        }
    }
}
