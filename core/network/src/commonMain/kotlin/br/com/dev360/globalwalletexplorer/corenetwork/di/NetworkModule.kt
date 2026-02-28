package br.com.dev360.globalwalletexplorer.corenetwork.di

import br.com.dev360.globalwalletexplorer.corenetwork.BuildKonfig
import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CurrenciesQualifier
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.api.CacheKey
import com.apollographql.apollo.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.TypePolicyCacheKeyGenerator
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.network.http.LoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single <SqlNormalizedCacheFactory> {
        SqlNormalizedCacheFactory(name = "global_wallet.db")
    }

    single(CurrenciesQualifier) {
        val memoryCacheFactory = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024, expireAfterMillis = 24 * 60 * 60 * 1000)
        val sqlCacheFactory = get<SqlNormalizedCacheFactory>()
        val chainedCacheFactory = memoryCacheFactory.chain(sqlCacheFactory)

        ApolloClient.Builder()
            .serverUrl("https://swop.cx/graphql")
            .addHttpHeader("Authorization", "ApiKey ${BuildKonfig.API_KEY}")
            .addHttpInterceptor(
                LoggingInterceptor(
                    level = LoggingInterceptor.Level.BODY,
                    log = { println(it) }
                )
            )
            .normalizedCache(
                normalizedCacheFactory = chainedCacheFactory,
                cacheKeyGenerator = object : CacheKeyGenerator {
                    override fun cacheKeyForObject(
                        obj: Map<String, Any?>,
                        context: CacheKeyGeneratorContext
                    ): CacheKey? {
                        val code = obj["code"] as? String
                        return if (code != null) CacheKey(code) else TypePolicyCacheKeyGenerator.cacheKeyForObject(obj, context)
                    }

                }
            )
            .fetchPolicy(FetchPolicy.CacheFirst)
            .build()
    }
}