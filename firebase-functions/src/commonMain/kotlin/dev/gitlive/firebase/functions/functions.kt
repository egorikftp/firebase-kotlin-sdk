/*
 * Copyright (c) 2020 GitLive Ltd.  Use of this source code is governed by the Apache 2.0 license.
 */

package dev.gitlive.firebase.functions

import dev.gitlive.firebase.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

expect class FirebaseFunctions {
    fun httpsCallable(name: String, timeout: Long? = null): HttpsCallableReference
    fun useEmulator(host: String, port: Int)
}

abstract class BaseHttpsCallableReference {
    suspend inline operator fun <reified T> invoke(
        data: T,
        encodeDefaults: Boolean = true,
        serializersModule: SerializersModule = EmptySerializersModule()
    ) = invoke(encode(data, encodeDefaults, serializersModule)!!)

    suspend operator fun <T> invoke(
        strategy: SerializationStrategy<T>,
        data: T,
        encodeDefaults: Boolean = true,
        serializersModule: SerializersModule = EmptySerializersModule()
    ): HttpsCallableResult = invoke(strategy, data, encodeDefaults, serializersModule)
    abstract suspend fun invoke(encodedData: Any): HttpsCallableResult
}

expect class HttpsCallableReference : BaseHttpsCallableReference {
    suspend operator fun invoke(): HttpsCallableResult
}

expect class HttpsCallableResult {
    inline fun <reified T> data(): T
    fun <T> data(strategy: DeserializationStrategy<T>, serializersModule: SerializersModule = EmptySerializersModule()): T
}

/** Returns the [FirebaseFunctions] instance of the default [FirebaseApp]. */
expect val Firebase.functions: FirebaseFunctions

/** Returns the [FirebaseFunctions] instance of a given [region]. */
expect fun Firebase.functions(region: String): FirebaseFunctions

/** Returns the [FirebaseFunctions] instance of a given [FirebaseApp]. */
expect fun Firebase.functions(app: FirebaseApp): FirebaseFunctions

/** Returns the [FirebaseFunctions] instance of a given [FirebaseApp] and [region]. */
expect fun Firebase.functions(app: FirebaseApp, region: String): FirebaseFunctions

expect class FirebaseFunctionsException: FirebaseException

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
expect val FirebaseFunctionsException.code: FunctionsExceptionCode

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
expect val FirebaseFunctionsException.details: Any?

expect enum class FunctionsExceptionCode {
    OK,
    CANCELLED,
    UNKNOWN,
    INVALID_ARGUMENT,
    DEADLINE_EXCEEDED,
    NOT_FOUND,
    ALREADY_EXISTS,
    PERMISSION_DENIED,
    RESOURCE_EXHAUSTED,
    FAILED_PRECONDITION,
    ABORTED,
    OUT_OF_RANGE,
    UNIMPLEMENTED,
    INTERNAL,
    UNAVAILABLE,
    DATA_LOSS,
    UNAUTHENTICATED
}
