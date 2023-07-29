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

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/*
 * Implementation based on:
 * https://blog.shreyaspatil.dev/combining-stateflows-and-transforming-it-into-a-stateflow
 */

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    transform: (T1, T2) -> R,
): StateFlow<R> = CombinedStateFlow(
    flow = kotlinx.coroutines.flow.combine(flow1, flow2, transform),
    getValue = { transform(flow1.value, flow2.value) },
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param flow3 A [StateFlow] to be combined.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    transform: (T1, T2, T3) -> R,
): StateFlow<R> = CombinedStateFlow(
    flow = kotlinx.coroutines.flow.combine(flow1, flow2, flow3, transform),
    getValue = { transform(flow1.value, flow2.value, flow3.value) },
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [StateFlow]s.
 *
 * @param flow1 A [StateFlow] to be combined.
 * @param flow2 A [StateFlow] to be combined.
 * @param flow3 A [StateFlow] to be combined.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, T4, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    transform: (T1, T2, T3, T4) -> R,
): StateFlow<R> = CombinedStateFlow(
    flow = kotlinx.coroutines.flow.combine(flow1, flow2, flow3, flow4, transform),
    getValue = { transform(flow1.value, flow2.value, flow3.value, flow4.value) },
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
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows][flow1].
 */
public fun <T1, T2, T3, T4, T5, R> combineState(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    flow3: StateFlow<T3>,
    flow4: StateFlow<T4>,
    flow5: StateFlow<T5>,
    transform: (T1, T2, T3, T4, T5) -> R,
): StateFlow<R> = CombinedStateFlow(
    flow = kotlinx.coroutines.flow.combine(flow1, flow2, flow3, flow4, flow5, transform),
    getValue = { transform(flow1.value, flow2.value, flow3.value, flow4.value, flow5.value) },
)

/**
 * Returns a [StateFlow] whose values are generated with [transform] function
 * by combining the most recently emitted values by each of the given
 * [flows].
 *
 * @param flows The [StateFlow]s to be combined.
 * @param transform The transform to apply to combine the most recent values of
 *   the original [flows].
 */
public inline fun <reified T, R> combineState(
    vararg flows: StateFlow<T>,
    crossinline transform: (Array<T>) -> R,
): StateFlow<R> = CombinedStateFlow(
    flow = kotlinx.coroutines.flow.combine(flows.toList(), transform),
    getValue = { transform(flows.map(StateFlow<T>::value).toTypedArray()) },
)

/**
 * A [StateFlow] implementation that separates flow collection and reading
 * of [StateFlow.value] for [combining][combineState] multiple [StateFlow]s without
 * calling [stateIn].
 *
 * See: [Combining StateFlows and transforming it into a StateFlow](https://blog.shreyaspatil.dev/combining-stateflows-and-transforming-it-into-a-stateflow)
 */
@PublishedApi
internal class CombinedStateFlow<T>(
    private val flow: Flow<T>,
    private val getValue: () -> T,
) : StateFlow<T> {

    override val value: T
        get() = getValue()

    override val replayCache: List<T>
        get() = listOf(value)

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        coroutineScope { flow.stateIn(this).collect(collector) }
}
