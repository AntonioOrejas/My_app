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
package com.example.compose.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.compose.model.Affirmation
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import com.example.compose.model.Fish
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
/**
 * [Datasource] generates a list of [Affirmation]
 */


class Datasource() {
    private val affirmationslist = mutableStateListOf<Affirmation>()


    fun loadAffirmations(): List<Affirmation> {
        return affirmationslist

    }
    fun deleteCard(affirmation: Affirmation){
        affirmationslist.remove(affirmation)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addCard(){
        val date = LocalDate.now()
        val change = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val today = date.format(change)
        val iD = UUID.randomUUID().toString()
        affirmationslist.add(Affirmation(iD, today, 0, 0))
    }

    fun getCard(id: String): Affirmation? {
        return affirmationslist.find { it.iD == id }
    }


    fun createFish(iD: String, species: String, weight: String, place: String, bait: String, photo: ImageBitmap?) {
        val card = getCard(iD)
        val iDF = UUID.randomUUID().toString()
        card?.fishList?.add(Fish(iDF, iD, species, weight, place, bait, photo))
        if (card != null) {
            card.total += 1
            if (card.big<weight.toInt()) {
                card.big=weight.toInt()

            }
        }
    }

    fun getFish(iD:String,iDcard: String): Fish? {
        val card = getCard(iDcard)
        return card?.fishList?.find { it.iDF == iD }

    }


    fun updateFish(iD: String, iDCard: String, species: String, weight: String, place: String, bait: String, photo: ImageBitmap? ) {
        val fish = getFish(iD,iDCard)
        val card = getCard(iDCard)
        if (fish != null) {
            fish.species = species
            fish.weight = weight
            fish.place = place
            fish.bait = bait
            fish.photo=photo
        }

        if (card != null) {
            card.big=getMaxWeight(card.fishList)
        }


    }


    private fun getMaxWeight(fishList:MutableList<Fish>):Int {
        var max:Int=0
        for (n in fishList){
            if (n.weight.toInt()>max){
                max=n.weight.toInt()
            }
        }
        return max
    }

    fun deleteFish(card: Affirmation,fish: Fish){
        card.fishList.remove(fish)
        card.total -= 1

        if (fish.weight.toInt()==card.big){
            card.big=getMaxWeight(card.fishList)
        }
    }

}







