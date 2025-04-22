package com.example.worlddata

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import data.CountryDao
import data.DatabaseFactory
import data.quiz.QuestionDao
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Lifecycle callbacks of the entire application.
 *
 * @author Yishai Hezi
 */
@HiltAndroidApp
class WorldDataApplication : Application() {


    /**
     * The question data access object.
     */
    @Inject lateinit var questionDao: QuestionDao


    /**
     * The country data access object.
     */
    @Inject lateinit var countryDao: CountryDao


    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycleScope.launch {
            questionDao.deleteAll()
            DatabaseFactory.populateDB(this@WorldDataApplication, countryDao, questionDao, true)
        }
    }


}