package com.example.gamesessionapp.data.local.factory

import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.local.dao.NewsStatusDao
import com.example.gamesessionapp.data.repository.FakeNewsRepository
import com.example.gamesessionapp.data.repository.NewsRepository
import com.example.gamesessionapp.data.repository.room.RoomNewsRepository
import com.example.gamesessionapp.features.user.news.NewsComponent

//fun provideNewsFactory(componentContext: ComponentContext, repository: NewsRepository = FakeNewsRepository) : NewsComponent {
//    return NewsComponent(componentContext, repository)
//}

fun provideNewsFactory(newsStatusDao: NewsStatusDao) : NewsRepository {
    return RoomNewsRepository(
        newsStatusDao = newsStatusDao,
        fakeNewsRepository = FakeNewsRepository
    )
}

