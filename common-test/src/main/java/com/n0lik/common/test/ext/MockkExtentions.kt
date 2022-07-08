package com.n0lik.common.test.ext

import io.mockk.mockk
import kotlin.reflect.KClass

inline fun <reified T : Any> mockkRelaxed(
    name: String? = null,
    vararg moreInterfaces: KClass<*>,
    relaxUnitFun: Boolean = false,
    block: T.() -> Unit = {}
) = mockk(
    name = name,
    moreInterfaces = moreInterfaces,
    relaxed = true,
    relaxUnitFun = relaxUnitFun,
    block = block
)