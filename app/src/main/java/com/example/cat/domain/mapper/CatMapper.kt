package com.example.cat.domain.mapper

import com.example.cat.data.remote.dto.BreedsItemDto
import com.example.cat.data.remote.dto.CatDto
import com.example.cat.data.remote.dto.WeightDto
import com.example.cat.domain.model.BreedsItem
import com.example.cat.domain.model.Cat
import com.example.cat.domain.model.Weight

fun CatDto.toCat() =
    Cat(
        width = width,
        id = id,
        imageUrl = url,
        breeds = breeds?.map { it.toBreedsItem() },
        height = height
    )


fun BreedsItemDto.toBreedsItem() =
    BreedsItem(
        suppressedTail = suppressedTail,
        wikipediaUrl = wikipediaUrl,
        origin = origin,
        description = description,
        experimental = experimental,
        lifeSpan = lifeSpan,
        cfaUrl = cfaUrl,
        rare = rare,
        countryCodes = countryCodes,
        lap = lap,
        id = id,
        shortLegs = shortLegs,
        sheddingLevel = sheddingLevel,
        dogFriendly = dogFriendly,
        natural = natural,
        rex = rex,
        healthIssues = healthIssues,
        hairless = hairless,
        weight = weightDto?.toWeight(),
        altNames = altNames,
        adaptability = adaptability,
        vocalisation = vocalisation,
        intelligence = intelligence,
        socialNeeds = socialNeeds,
        countryCode = countryCode,
        childFriendly = childFriendly,
        vcahospitalsUrl = vcahospitalsUrl,
        temperament = temperament,
        name = name,
        vetstreetUrl = vetstreetUrl,
        grooming = grooming,
        hypoallergenic = hypoallergenic,
        indoor = indoor,
        energyLevel = energyLevel,
        strangerFriendly = strangerFriendly,
        referenceImageId = referenceImageId,
        affectionLevel = affectionLevel,
        catFriendly = catFriendly
    )


fun WeightDto.toWeight() =
    Weight(
        metric = metric,
        imperial = imperial
    )