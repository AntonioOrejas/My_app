/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.compose.model

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.ImageBitmap


/**
 * [Affirmation] is the data class to represent the Affirmation text and imageResourceId
 */
@SuppressLint("SupportAnnotationUsage")
data class Affirmation(
    val iD: String="",
    val stringResourceId: String="",
    var total: Int=0,
    var big: Int=0,
    var fishList: MutableList<Fish> = mutableListOf()
)

@SuppressLint("SupportAnnotationUsage")
data class Fish(
    val iDF: String="",
    val iD: String="",
    var species: String="",
    var weight: String="",
    var place: String="",
    var bait: String="",
    var photo: ImageBitmap?=null
)
