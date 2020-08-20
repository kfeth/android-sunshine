package com.kfeth.sunshine.data

class SearchResponse(val list: List<LocationResponse>)

class LocationResponse(val id: Int, val name: String)