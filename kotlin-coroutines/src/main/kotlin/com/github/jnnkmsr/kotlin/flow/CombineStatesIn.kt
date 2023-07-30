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

package com.github.jnnkmsr.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param scope The [CoroutineScope] in which sharing is started.
 * @param started The strategy that controls when sharing is started and stopped.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, R> combineStatesIn(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    scope: CoroutineScope,
    started: SharingStarted,
    transform: (T1, T2) -> R,
): StateFlow<R> = combine(flow1, flow2, transform).stateIn(
    scope = scope,
    initialValue = transform(flow1.value, flow2.value),
    started = started,
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param flow3 A [StateFlow] to be combined.
 * @param scope The [CoroutineScope] in which sharing is started.
 * @param started The strategy that controls when sharing is started and stopped.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, R> combineStatesIn(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    scope: CoroutineScope,
    started: SharingStarted,
    transform: (T1, T2, T3) -> R,
): StateFlow<R> = combine(flow1, flow2, flow3, transform).stateIn(
    scope = scope,
    initialValue = transform(flow1.value, flow2.value, flow3.value),
    started = started,
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param flow3 A [StateFlow] to be combined.
 * @param scope The [CoroutineScope] in which sharing is started.
 * @param started The strategy that controls when sharing is started and stopped.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, T4, R> combineStatesIn(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    scope: CoroutineScope,
    started: SharingStarted,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> = combine(flow1, flow2, flow3, flow4, transform).stateIn(
    scope = scope,
    initialValue = transform(flow1.value, flow2.value, flow3.value, flow4.value),
    started = started,
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param flow3 A [StateFlow] to be combined.
 * @param flow4 A [StateFlow] to be combined.
 * @param flow5 A [StateFlow] to be combined.
 * @param scope The [CoroutineScope] in which sharing is started.
 * @param started The strategy that controls when sharing is started and stopped.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, T4, T5, R> combineStatesIn(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    scope: CoroutineScope,
    started: SharingStarted,
    transform: (T1, T2, T3, T4, T5) -> R,
): StateFlow<R> = combine(flow1, flow2, flow3, flow4, flow5, transform).stateIn(
    scope = scope,
    initialValue = transform(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value),
    started = started,
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [flows].
 *
 * @param flows The [StateFlow]s to be combined.
 * @param scope The [CoroutineScope] in which sharing is started.
 * @param started The strategy that controls when sharing is started and stopped.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows].
 */
public inline fun <reified T, R> combineStatesIn(
    vararg flows: StateFlow<T>,
    scope: CoroutineScope,
    started: SharingStarted,
    crossinline transform: (Array<T>) -> R,
): StateFlow<R> = combine(*flows) { values -> transform(values) }.stateIn(
    scope = scope,
    initialValue = transform(flows.map { flow -> flow.value }.toTypedArray()),
    started = started,
)
