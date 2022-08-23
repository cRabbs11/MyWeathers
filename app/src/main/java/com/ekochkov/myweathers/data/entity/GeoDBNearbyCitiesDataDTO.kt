package com.ekochkov.myweathers.data.entity

data class GeoDBNearbyCitiesDataDTO(
    val `data`: List<DataDTO>,
    val links: List<LinkDTO>,
    val metadata: MetadataDTO
)

fun GeoDBNearbyCitiesDataDTO.toCityList(): List<Point> {
    var result = arrayListOf<Point>()
    data.forEach {
        result.add(Point(
            id = it.id,
            name = it.name,
            latitude = it.latitude,
            longitude = it.longitude))
    }
    return result
}