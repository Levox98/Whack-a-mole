package com.levox.whackamole.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.levox.whackamole.data.AppPreferences
import com.levox.whackamole.data.GameRepositoryImpl
import com.levox.whackamole.domain.usecases.GenerateMolePositionUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val appPreferences = AppPreferences(application)
    private val repository = GameRepositoryImpl()

    private val generateMolePositionUseCase = GenerateMolePositionUseCase(repository)

    private var timer: CountDownTimer? = null
    private var moleTimer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _molePosition = MutableLiveData<Int>()
    val molePosition: LiveData<Int>
        get() = _molePosition

    private fun startGame() {
        startTimer()
        startMoleTimer()
        updateCurrentScore()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            PLAY_TIME_IN_SECONDS * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun startMoleTimer() {
        moleTimer = object : CountDownTimer(
            PLAY_TIME_IN_SECONDS * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS / 2
        ) {
            override fun onTick(millisUntilFinished: Long) {
                generateMolePosition()
            }

            override fun onFinish() {
                return
            }
        }
        moleTimer?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        return String.format("%02d", millisUntilFinished / MILLIS_IN_SECONDS)
    }

    private fun updateCurrentScore() {
        _score.value = 0
    }

    private fun saveHighScore(score: Int) {
        appPreferences.saveHighScore(score)
    }

    private fun finishGame() {
        if (_score.value!! > appPreferences.getHighScore()) {
            saveHighScore(_score.value!!)
        }
    }

    fun increaseScore() {
        _score.value = _score.value?.plus(1)
    }

    private fun startRandomMoles() {

    }

    private fun generateMolePosition() {
        _molePosition.value = generateMolePositionUseCase()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        moleTimer?.cancel()
    }

    private companion object {
        private const val PLAY_TIME_IN_SECONDS = 30
        private const val MILLIS_IN_SECONDS = 1000L
    }
}