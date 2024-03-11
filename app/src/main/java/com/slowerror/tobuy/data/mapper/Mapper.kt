package com.slowerror.tobuy.data.mapper


interface Mapper<I, O> {
    fun mapToData(input: I): O
    fun mapToDomain(input: O): I
}



