package com.rocbillow.component.sample.databinding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rocbillow.component.R
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFS_NAME = "prefs_component"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

@Module
@InstallIn(SingletonComponent::class)
abstract class PrefsModule {

    @Binds
    @Singleton
    abstract fun providePrefsStore(prefsStore: PrefsStoreImpl): PrefsStore
}

interface PrefsStore {

    suspend fun saveNumberOfSets(numberOfSetsTotal: Int)

    suspend fun saveTimePerWorkSet(timePerWorkSet: Int)

    suspend fun saveTimePerRestSet(timePerRestSet: Int)

    suspend fun getNumberOfSets(): Flow<Int>

    suspend fun getTimePerWorkSet(): Flow<Int>

    suspend fun getTimePerRestSet(): Flow<Int>
}

@Singleton
class PrefsStoreImpl @Inject constructor(@ApplicationContext context: Context) : PrefsStore {

    private val prefsNumberOfSets by lazy {
        intPreferencesKey(context.getString(R.string.prefs_numberOfSets))
    }
    private val prefsTimePerWorkSet by lazy {
        intPreferencesKey(context.getString(R.string.prefs_timePerWorkSet))
    }
    private val prefsTimePerRestSet by lazy {
        intPreferencesKey(context.getString(R.string.prefs_timePerRestSet))
    }
    private val dataStore by lazy {
        context.dataStore
    }

    override suspend fun saveNumberOfSets(numberOfSetsTotal: Int) {
        saveToDataStore(prefsNumberOfSets, numberOfSetsTotal)
    }

    override suspend fun saveTimePerWorkSet(timePerWorkSet: Int) {
        saveToDataStore(prefsTimePerWorkSet, timePerWorkSet)
    }

    override suspend fun saveTimePerRestSet(timePerRestSet: Int) {
        saveToDataStore(prefsTimePerRestSet, timePerRestSet)
    }

    private suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override suspend fun getNumberOfSets(): Flow<Int> =
        getFromDataStore(prefsNumberOfSets, INITIAL_NUMBER_OF_SETS)

    override suspend fun getTimePerWorkSet(): Flow<Int> =
        getFromDataStore(prefsTimePerWorkSet, INITIAL_SECONDS_PER_WORK_SET)

    override suspend fun getTimePerRestSet(): Flow<Int> =
        getFromDataStore(prefsTimePerRestSet, INITIAL_SECONDS_PER_REST_SET)

    private suspend fun <T> getFromDataStore(key: Preferences.Key<T>, default: T): Flow<T> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                }
                throw exception
            }.map { prefs ->
                prefs[key] ?: default
            }

}