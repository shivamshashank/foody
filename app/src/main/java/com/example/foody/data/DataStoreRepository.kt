package com.example.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foody.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

    private val dataStore: DataStore<Preferences> = context._dataStore

    suspend fun saveMealAndDietType(mealAndDietType: MealAndDietType) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.selectedMealType] = mealAndDietType.selectedMealType
            preference[PreferenceKeys.selectedMealTypeId] = mealAndDietType.selectedMealTypeId
            preference[PreferenceKeys.selectedDietType] = mealAndDietType.selectedDietType
            preference[PreferenceKeys.selectedDietTypeId] = mealAndDietType.selectedDietTypeId
        }

    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            MealAndDietType(
                preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE,
                preferences[PreferenceKeys.selectedMealTypeId] ?: 0,
                preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE,
                preferences[PreferenceKeys.selectedDietTypeId] ?: 0,
            )
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int,
)
