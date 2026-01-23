package br.com.dev360.globalwalletexplorer.corenetwork.di

import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CurrenciesQualifier
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.http.LoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single(CurrenciesQualifier) {
        ApolloClient.Builder()
            .serverUrl("https://swop.cx/graphql")
            .addHttpHeader("Authorization", "your_api_key_here")
            .addHttpInterceptor(
                LoggingInterceptor(
                    level = LoggingInterceptor.Level.BODY,
                    log = { println(it) }
                )
            )
            .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
            .build()
    }
}