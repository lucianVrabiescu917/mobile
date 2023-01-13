package ro.luci.meditation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.luci.meditation.Utils
import ro.luci.meditation.data.model.Session
import ro.luci.meditation.databinding.CompSessionItemBinding


class SessionsAdapter(
    private val onDeleteClick: (Session) -> Unit,
    private val onUpdateClick: (Session) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _sessions: MutableList<Session> = mutableListOf()

    val sessions: List<Session>
        get() = _sessions

    @SuppressLint("NotifyDataSetChanged")
    fun addSessions(sessions: List<Session>) {
        this._sessions.addAll(sessions)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSessions() {
        this._sessions.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = RequestViewHolder(
            CompSessionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RequestViewHolder).bind(_sessions[position], onDeleteClick, onUpdateClick)
    }

    override fun getItemCount(): Int = sessions.size

    class RequestViewHolder(private val sessionItem: CompSessionItemBinding) : RecyclerView.ViewHolder(sessionItem.root) {

        fun bind(session: Session, onDeleteClick: (Session) -> Unit, onUpdateClick: (Session) -> Unit) {
            Utils.fillSessionsCard(sessionItem, session, onDeleteClick, onUpdateClick)
        }
    }
    
}
