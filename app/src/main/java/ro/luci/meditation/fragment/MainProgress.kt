package ro.luci.meditation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.luci.meditation.MainActivity
import ro.luci.meditation.MainActivityVM
import ro.luci.meditation.Utils
import ro.luci.meditation.adapters.SessionsAdapter
import ro.luci.meditation.data.model.Session
import ro.luci.meditation.databinding.CompSessionUpdateBinding
import ro.luci.meditation.databinding.FragMainProgressBinding

class MainProgress: Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var vm: MainActivityVM
    private var _binding: FragMainProgressBinding? = null
    private val binding get() = _binding!!

    private val sessionsAdapter = SessionsAdapter(
        {
            attemptDelete(it)
        },
        {
            showUpdateModal(it)
        },
    )

    private var dialog: AlertDialog? = null
    private var modalBinding: CompSessionUpdateBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = requireActivity() as MainActivity
        val injectedViewModel by sharedViewModel<MainActivityVM>()
        vm = injectedViewModel
        _binding = FragMainProgressBinding.inflate(inflater, container, false)

        binding.sessionsList.apply {
            adapter = sessionsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        sessionsAdapter.addSessions(vm.getSessions())

        initModal(inflater)

        return binding.root
    }

    private fun initModal(inflater: LayoutInflater) {
        modalBinding = CompSessionUpdateBinding.inflate(inflater)
        dialog = AlertDialog.Builder(activity).setView(modalBinding!!.root).create()
    }

    private fun showUpdateModal(session: Session) {
        if (dialog == null) return

        Utils.fillModal(modalBinding!!, session) { it, location ->
            vm.updateLocation(it.id!!, location)
            sessionsAdapter.clearSessions()
            sessionsAdapter.addSessions(vm.getSessions())

            modalBinding!!.location.clearFocus()
            dialog!!.dismiss()
        }

        dialog!!.apply {
            setOnDismissListener {
                modalBinding!!.location.clearFocus()
            }
            dismiss()
            show()
        }
    }

    private fun attemptDelete(session: Session) {
        vm.removeSession(session.id!!)
        sessionsAdapter.clearSessions()
        sessionsAdapter.addSessions(vm.getSessions())
    }

}
