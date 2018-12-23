package com.lab.greenpremium.data.entity

import java.io.Serializable

data class PortfolioData(val sections: List<PortfolioSection>?) {
    val time: Long = System.currentTimeMillis()
}

data class PortfolioSection(
        val id: String,
        val name: String,
        val photos: List<Photo>,
        val sort: String) : Serializable

data class Photo(
        val height: String,
        val id: String,
        val url: String,
        val width: String
) : Serializable