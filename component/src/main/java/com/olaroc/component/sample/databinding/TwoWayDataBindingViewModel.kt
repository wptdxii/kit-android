package com.olaroc.component.sample.databinding

import androidx.lifecycle.viewModelScope
import com.olaroc.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.round

const val INITIAL_NUMBER_OF_SETS = 2
const val INITIAL_SECONDS_PER_WORK_SET = 5
const val INITIAL_SECONDS_PER_REST_SET = 5

data class NumberOfSetsWrapper(
    var numberOfSetsElapsed: Int = 0,
    var numberOfSetsTotal: Int = INITIAL_NUMBER_OF_SETS
) {

    /**
     * Resolve StateFlow distinctUntilChanged when focus changed
     */
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

@HiltViewModel
class TwoWayDataBindingViewModel @Inject constructor(
    private val timer: ITimer,
    private val repository: TwoWayBindingRepository,
) : BaseViewModel() {

    private var state = TimerState.STOPPED
    private var stage = StartedStages.WORKING

    private val _timePerWorkSet = MutableStateFlow(INITIAL_SECONDS_PER_WORK_SET * 10)
    private val _timePerRestSet = MutableStateFlow(INITIAL_SECONDS_PER_REST_SET * 10)
    private val _workTimeLeft = MutableStateFlow(_timePerWorkSet.value)
    private val _restTimeLeft = MutableStateFlow(_timePerRestSet.value)
    private val _inWorkingStage = MutableStateFlow(stage == StartedStages.WORKING)
    val timePerWorkSet = _timePerWorkSet.asStateFlow()
    val timePerRestSet = _timePerRestSet.asStateFlow()
    val workTimeLeft = _workTimeLeft.asStateFlow()
    val restTimeLeft = _restTimeLeft.asStateFlow()
    val inWorkingStage = _inWorkingStage.asStateFlow()

    var numberOfSetsWrapper = NumberOfSetsWrapper()
    var numberOfSets = MutableStateFlow(numberOfSetsWrapper)
    var timerRunning = MutableStateFlow(state == TimerState.STARTED)

    init {
        collectTimerRunning()
        savePrefs()
    }

    private fun savePrefs() {
        save(numberOfSets) { numberOfSetsWrapper ->
            repository.saveNumberOfSets(numberOfSetsWrapper.numberOfSetsTotal)
        }
        save(timePerWorkSet) { timePerWorkSet ->
            repository.saveTimePerWorkSet(timePerWorkSet)
        }
        save(timePerRestSet) { timePerRestSet ->
            repository.saveTimePerRestSet(timePerRestSet)
        }
    }

    private fun <T> save(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewModelScope.launch {
            flow.drop(1)
                .collect { element ->
                    action(element)
                }
        }
    }

    fun restorePrefs() {
        restore({ repository.getNumberOfSets() }) { numberOfSetsTotal ->
            numberOfSetsWrapper.numberOfSetsTotal = numberOfSetsTotal
            notifyNumberOfSets()
        }
        restore({ repository.getTimePerWorkSet() }) { timePerWorkSet ->
            _timePerWorkSet.value = timePerWorkSet
            _workTimeLeft.value = timePerWorkSet
        }

        restore({ repository.getTimePerRestSet() }) { timePerRestSet ->
            _timePerRestSet.value = timePerRestSet
            _restTimeLeft.value = timePerRestSet
        }
    }

    private fun <T> restore(flow: suspend () -> Flow<T>, action: (T) -> Unit) {
        viewModelScope.launch {
            flow().collect { element ->
                action(element)
                cancel()
            }
        }
    }

    private fun collectTimerRunning() {
        viewModelScope.launch {
            timerRunning
                .collect { running ->
                    if (running) startRunning() else pauseRunning()
                }
        }
    }

    private fun startRunning() {
        when (state) {
            TimerState.PAUSED -> pausedToStarted()
            TimerState.STOPPED -> stoppedToStarted()
            else -> {
                // Do nothing
            }
        }

        val task = object : TimerTask() {
            override fun run() {
                if (state == TimerState.STARTED) {
                    updateCountdowns()
                }
            }
        }
        timer.start(task)
    }

    private fun updateCountdowns() {
        if (state == TimerState.STOPPED) {
            resetTimers()
            return
        }

        val elapsed = if (state == TimerState.PAUSED) {
            timer.getPausedTime()
        } else {
            timer.getElapsedTime()
        }

        if (stage == StartedStages.RESTING) {
            updateRestCountdowns(elapsed)
        } else {
            updateWorkCountdowns(elapsed)
        }
    }

    private fun updateWorkCountdowns(elapsed: Long) {
        val newWorkTimeLeft = _timePerWorkSet.value - (elapsed / 100).toInt()
        if (newWorkTimeLeft <= 0) {
            workTimeFinished()
        }
        val coerceAtLeast = newWorkTimeLeft.coerceAtLeast(0)
        _workTimeLeft.value = coerceAtLeast
    }

    private fun workTimeFinished() {
        timer.resetStartTime()
        stage = StartedStages.RESTING
        notifyWorkingStage()
    }

    private fun updateRestCountdowns(elapsed: Long) {
        val newRestTimeLeft = _timePerRestSet.value - (elapsed / 100).toInt()
        _restTimeLeft.value = (newRestTimeLeft.coerceAtLeast(0))

        if (newRestTimeLeft <= 0) {
            numberOfSetsWrapper.numberOfSetsElapsed += 1
            notifyNumberOfSets()
            resetTimers()
            if (numberOfSetsWrapper.numberOfSetsElapsed >= numberOfSetsWrapper.numberOfSetsTotal) {
                timerFinished()
            } else {
                restTimeFinished()
            }
        }
        _restTimeLeft.value = (newRestTimeLeft.coerceAtLeast(0))
    }

    private fun restTimeFinished() {
        timer.resetStartTime()
        stage = StartedStages.WORKING
        notifyWorkingStage()

    }

    private fun timerFinished() {
        timer.reset()
        state = TimerState.STOPPED
        stage = StartedStages.WORKING
        numberOfSetsWrapper.numberOfSetsElapsed = 0

        notifyTimerRunning()
        notifyWorkingStage()
        notifyWorkingStage()
    }

    private fun resetTimers() {
        _workTimeLeft.value = _timePerWorkSet.value
        _restTimeLeft.value = _timePerRestSet.value
    }

    private fun stoppedToStarted() {
        timer.resetStartTime()
        state = TimerState.STARTED
        stage = StartedStages.WORKING

        notifyTimerRunning()
        notifyWorkingStage()
    }

    private fun pausedToStarted() {
        timer.updatePausedTime()
        state = TimerState.STARTED
        notifyTimerRunning()
    }

    private fun notifyWorkingStage() {
        _inWorkingStage.value = (stage == StartedStages.WORKING)
    }

    private fun notifyTimerRunning() {
        timerRunning.value = (state == TimerState.STARTED)
    }

    private fun pauseRunning() {
        if (state == TimerState.STARTED) {
            startedToPaused()
        }
    }

    private fun startedToPaused() {
        state = TimerState.PAUSED
        timer.resetPauseTime()
        notifyTimerRunning()
    }

    fun setsDecrease() {
        val numberOfSetsWrapper = numberOfSetsWrapper
        if (numberOfSetsWrapper.numberOfSetsTotal > numberOfSetsWrapper.numberOfSetsElapsed + 1) {
            numberOfSetsWrapper.numberOfSetsTotal -= 1
            notifyNumberOfSets()
        }
    }

    fun setsIncrease() {
        numberOfSetsWrapper.numberOfSetsTotal += 1
        notifyNumberOfSets()
    }

    private fun notifyNumberOfSets() {
        numberOfSets.value = numberOfSetsWrapper
    }

    fun timePerWorkSetChanged(newWorkTime: CharSequence) {
        try {
            _timePerWorkSet.value = Converters.cleanSecondsString(newWorkTime.toString())
        } catch (e: NumberFormatException) {
            return
        }
        if (!timerRunning.value) {
            _workTimeLeft.value = _timePerWorkSet.value
        }
    }

    fun timePerRestSetChanged(newRestTime: CharSequence) {
        try {
            _timePerRestSet.value = Converters.cleanSecondsString(newRestTime.toString())
        } catch (e: NumberFormatException) {
            return
        }
        if (!isRestTimeAndRunning()) {
            _restTimeLeft.value = _timePerRestSet.value
        }
    }

    private fun isRestTimeAndRunning(): Boolean {
        return (state == TimerState.PAUSED || state == TimerState.STARTED)
                && (_workTimeLeft.value == 0)
    }

    fun workTimeIncrease() = timePerSetIncrease(_timePerWorkSet, 1)


    fun workTimeDecrease() = timePerSetIncrease(_timePerWorkSet, -1, 10)


    fun restTimeIncrease() = timePerSetIncrease(_timePerRestSet, 1)


    fun restTimeDecrease() = timePerSetIncrease(_timePerRestSet, -1)

    fun reset() {
        resetTimers()
        numberOfSetsWrapper.numberOfSetsElapsed = 0
        state = TimerState.STOPPED
        stage = StartedStages.WORKING
        timer.reset()

        notifyNumberOfSets()
        notifyTimerRunning()
        notifyWorkingStage()
    }

    private fun timePerSetIncrease(timePerSet: MutableStateFlow<Int>, sign: Int = 1, min: Int = 0) {
        if (timePerSet.value < 10 && sign < 0) return
        roundTimeIncrease(timePerSet, sign, min)
        if (state == TimerState.STOPPED) {
            _workTimeLeft.value = _timePerWorkSet.value
            _restTimeLeft.value = _timePerRestSet.value
        } else {
            updateCountdowns()
        }
    }

    private fun roundTimeIncrease(timePerSet: MutableStateFlow<Int>, sign: Int, min: Int) {
        val currentValue = timePerSet.value
        val newValue =
            when {
                currentValue < 100 -> timePerSet.value + sign * 10
                currentValue < 600 -> (round(currentValue / 50.0) * 50 + (50 * sign)).toInt()
                else -> (round(currentValue / 100.0) * 100 + (100 * sign)).toInt()
            }
        timePerSet.value = newValue.coerceAtLeast(min)
    }
}

enum class TimerState {
    STOPPED, STARTED, PAUSED
}

enum class StartedStages {
    WORKING, RESTING
}