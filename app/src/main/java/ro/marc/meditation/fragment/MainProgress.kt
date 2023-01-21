package ro.marc.meditation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.meditation.*
import ro.marc.meditation.adapters.SessionsAdapter
import ro.marc.meditation.data.api.CallResponse
import ro.marc.meditation.data.dto.ChangeLocationDTO
import ro.marc.meditation.data.dto.SessionDTO
import ro.marc.meditation.data.model.Session
import ro.marc.meditation.databinding.CompSessionUpdateBinding
import ro.marc.meditation.databinding.FragMainProgressBinding

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

        loadSessions()

        initModal(inflater)

        attachNetworkStateListener()

        return binding.root
    }

    private fun initModal(inflater: LayoutInflater) {
        modalBinding = CompSessionUpdateBinding.inflate(inflater)
        dialog = AlertDialog.Builder(activity).setView(modalBinding!!.root).create()
    }

    private fun showUpdateModal(session: Session) {
        if (dialog == null) return

        Utils.fillModal(modalBinding!!, session) { it, location ->
            if (NetworkUtils.hasNetwork == false) {
                Utils.toast(activity, R.string.main_list_no_internet)
                return@fillModal
            }

            vm.updateLocation(session.id!!, ChangeLocationDTO(location)).observe(viewLifecycleOwner) {
                if (it is CallResponse.Success) {
                    vm.updateLocalLocation(session.localId!!, location)
                    sessionsAdapter.clearSessions()
                    sessionsAdapter.addSessions(vm.getSessions())
                    modalBinding!!.location.clearFocus()
                    dialog!!.dismiss()
                }
            }
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
        if (NetworkUtils.hasNetwork == false) {
            Utils.toast(activity, R.string.main_list_no_internet)
            return
        }

        vm.remove(session.id!!).observe(viewLifecycleOwner) {
            if (it is CallResponse.Success) {
                vm.removeLocalSession(session.localId!!)
                sessionsAdapter.clearSessions()
                sessionsAdapter.addSessions(vm.getSessions())
            }
        }

    }

    private fun attachNetworkStateListener() {
        NetworkUtils.reconnectedLiveData.observe(viewLifecycleOwner) {
            if (!it) return@observe

            Utils.toast(activity, R.string.main_list_reconnected)
            vm.getUncommitted().forEach { session ->
                tryToPost(session)
            }
        }
    }

    private fun loadSessions() {
        when (NetworkUtils.hasNetwork) {
            true -> {
                vm.fetchAll().observe(viewLifecycleOwner) {
                    if (it is CallResponse.Success) {
                        vm.clearLocal()

                        it.genericResponseDTO!!.payload!!.map(Session::from).forEach {
                            vm.addLocalSession(it)
                        }

                        sessionsAdapter.addSessions(vm.getSessions())
                        vm.getUncommitted().forEach {
                            tryToPost(it)
                        }
                    }
                }
            }
            false -> {
                sessionsAdapter.addSessions(vm.getSessions())
            }
        }
    }

    private fun tryToPost(session: Session) {
        val dto = SessionDTO(
            null,
            location = session.location,
            duration = session.durationInSeconds,
        )
        vm.postSession(dto).observe(viewLifecycleOwner) {
            if (it is CallResponse.Success) {
                vm.setCommitted(session.localId!!)
                vm.setId(session.localId, it.genericResponseDTO!!.payload!!.id!!)

                sessionsAdapter.clearSessions()
                sessionsAdapter.addSessions(vm.getSessions())
            }
        }
    }

}
