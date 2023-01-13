package ro.luci.meditation

import android.content.Context
import android.widget.Toast
import ro.luci.meditation.data.model.Session
import ro.luci.meditation.databinding.CompSessionItemBinding
import ro.luci.meditation.databinding.CompSessionUpdateBinding


object Utils {

    fun toast(ctx: Context, stringResId: Int) {
        Toast.makeText(ctx, ctx.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    fun formatSeconds(seconds: Int) = String.format(
        "%02d:%02d",
        (seconds % 3600) / 60,
        seconds % 60,
    )

    fun fillSessionsCard(item: CompSessionItemBinding, session: Session, onDelete: (Session) -> Unit, onUpdate: (Session) -> Unit) {
        item.time.text = formatSeconds(session.durationInSeconds)
        item.location.text = session.location

        item.delete.setOnClickListener {
            onDelete(session)
        }

        item.update.setOnClickListener {
            onUpdate(session)
        }
    }

    fun fillModal(item: CompSessionUpdateBinding, session: Session, onClick: (Session, String) -> Unit) {
        item.apply {
            location.setText(session.location)
            update.setOnClickListener {
                val newLocation = item.location.text.toString()
                if (newLocation.trim().isEmpty()) {
                    toast(item.root.context, R.string.main_timer_locationEmpty)
                    return@setOnClickListener
                }

                onClick(session, item.location.text.toString())
            }
        }
    }

}
