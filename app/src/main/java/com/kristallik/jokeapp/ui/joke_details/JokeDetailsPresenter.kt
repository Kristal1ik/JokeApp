package com.kristallik.jokeapp.ui.joke_details

import android.content.Context
import com.kristallik.jokeapp.db.JokeDatabase
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsView.Companion.VALUE_IF_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Должен знать о view
class JokeDetailsPresenter(private val view: JokeDetailsView) {

    fun loadJokeDetails(jokeId: Int, context: Context) {
        if (jokeId == VALUE_IF_ERROR) {
            view.showErrorAndCloseScreen("Invalid joke position!")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val jokeDao = JokeDatabase.getDatabase(context).jokeDao()
                val joke = jokeDao.getJokeById(jokeId) // Получаем шутку по ID

                withContext(Dispatchers.Main) {
                    if (joke != null) {
                        view.showJokeInfo(joke) // Отображаем информацию о шутке
                    } else {
                        view.showErrorAndCloseScreen("Invalid joke data!")
                    }
                }
            }
        }
    }
}
