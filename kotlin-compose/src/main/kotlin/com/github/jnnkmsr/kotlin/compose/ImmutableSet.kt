/*
 * Copyright 2023 Jannik Möser
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jnnkmsr.kotlin.compose

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.Collections

/**
 * Immutable [Set] implementation that can be used for
 * [stable](https://medium.com/androiddevelopers/jetpack-compose-stability-explained-79c10db270c8)
 * input to composables.
 */
@Immutable
@Keep
@Parcelize
public class ImmutableSet<E> private constructor(
    private val delegate: @RawValue Set<E>,
) : Set<E> by delegate,
    Parcelable {

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is ImmutableSet<*> -> false
        else -> delegate == other.delegate
    }

    override fun hashCode(): Int = delegate.hashCode()

    override fun toString(): String = delegate.toString()

    public companion object {

        /**
         * Returns an [ImmutableSet] with the elements of the given [collection].
         */
        public operator fun <E> invoke(collection: Collection<E>): ImmutableSet<E> =
            ImmutableSet(Collections.unmodifiableSet(HashSet(collection)))
    }
}

/**
 * Converts `this` collection to an [ImmutableSet].
 */
public fun <E> Collection<E>.toImmutableSet(): ImmutableSet<E> = ImmutableSet(this)

/**
 * Returns an empty [ImmutableSet].
 */
public fun <E> emptyImmutableSet(): ImmutableSet<E> = ImmutableSet(emptySet())

/**
 * Returns an [ImmutableSet] with the given [elements].
 */
public fun <E> immutableSetOf(vararg elements: E): ImmutableSet<E> =
    ImmutableSet(elements.toSet())
