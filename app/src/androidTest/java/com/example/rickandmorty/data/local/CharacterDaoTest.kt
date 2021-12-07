package com.example.rickandmorty.data.local
import com.google.common.truth.Truth.assertThat
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class CharacterDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var albumDao: CharacterDao

    @Before
    fun setup() {
        hiltRule.inject()
        albumDao = database.characterDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCharacter() = runBlockingTest {
        val character = Character(
            "345","f",212,
            "image","name","species","status","type","url"
        )
        albumDao.insert(character)
        val allUsers = albumDao.getAllCharacters().getOrAwaitValue()
        assertThat(allUsers).contains(character)
    }
}