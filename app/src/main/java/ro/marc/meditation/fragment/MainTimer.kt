package ro.marc.meditation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.meditation.*
import ro.marc.meditation.data.api.CallResponse
import ro.marc.meditation.data.dto.SessionDTO
import ro.marc.meditation.data.model.Session
import ro.marc.meditation.databinding.FragMainTimerBinding


class MainTimer: Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var vm: MainActivityVM
    private var _binding: FragMainTimerBinding? = null
    private val binding get() = _binding!!

    enum class StopwatchState {
        NOT_STARTED,
        ONGOING,
        FINISHED,
    }

    private var stopwatchState: StopwatchState = StopwatchState.NOT_STARTED

    private var location: String = ""
    private var seconds: Int = 0

    private var stopwatchHandler: Handler? = null

    private var stopwatch = object : Runnable {
        override fun run() {
            try {
                seconds += 1
                binding.insertLocation.text = Utils.formatSeconds(seconds)
            } finally {
                stopwatchHandler!!.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = requireActivity() as MainActivity
        val injectedViewModel by sharedViewModel<MainActivityVM>()
        vm = injectedViewModel
        _binding = FragMainTimerBinding.inflate(inflater, container, false)

        configureLayoutFor()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (stopwatchState == StopwatchState.ONGOING) {
            stopwatchHandler = Handler(Looper.getMainLooper())
            stopwatch.run()
        }
    }

    override fun onPause() {
        super.onPause()

        stopwatchHandler?.removeCallbacks(stopwatch)
        stopwatchHandler = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun startTimer() {
        if (binding.input.text.toString().trim().isEmpty()) {
            Utils.toast(activity, R.string.main_timer_locationEmpty)
            return
        }

        binding.button.apply {
            location = binding.input.text.toString()
            text = activity.getString(R.string.main_timer_stop)
            setOnClickListener {

                this.text = activity.getString(R.string.main_timer_start)
                this.setOnClickListener {
                    startTimer()
                }
            }
        }

        stopwatchHandler = Handler(Looper.getMainLooper())
        stopwatch.run()

        stopwatchState = StopwatchState.ONGOING
    }

    private fun stopTimer() {
        stopwatchHandler?.removeCallbacks(stopwatch)
        stopwatchHandler = null

        val session = Session(
            null,
            null,
            location,
            seconds
        )
        if (NetworkUtils.hasNetwork == false) {
            vm.addLocalSession(session)

            location = ""
            seconds = 0

            stopwatchState = StopwatchState.NOT_STARTED
            configureLayoutFor()
            return
        }

        vm.postSession(SessionDTO.from(session)).observe(viewLifecycleOwner) {
            if (it is CallResponse.Success) {
                vm.addLocalSession(Session.from(it.genericResponseDTO!!.payload!!))
                binding.button.isClickable = true
                location = ""
                seconds = 0

                stopwatchState = StopwatchState.NOT_STARTED
                configureLayoutFor()
            } else {
                binding.button.isClickable = false
            }
        }

    }

    private fun configureLayoutFor() {
        when (stopwatchState) {
            StopwatchState.NOT_STARTED -> {
                binding.button.apply {
                    text = activity.getString(R.string.main_timer_start)
                    setOnClickListener {
                        startTimer()
                        configureLayoutFor()
                    }
                }

                binding.insertLocation.text = getString(R.string.main_timer_insertLocation)

                binding.input.apply {
                    visibility = View.VISIBLE
                    setText("")
                    clearFocus()
                }
            }
            StopwatchState.ONGOING -> {
                binding.button.apply {
                    text = activity.getString(R.string.main_timer_stop)
                    setOnClickListener {
                        stopTimer()
                    }
                }

                binding.input.visibility = View.INVISIBLE
            }
        }
    }

}
