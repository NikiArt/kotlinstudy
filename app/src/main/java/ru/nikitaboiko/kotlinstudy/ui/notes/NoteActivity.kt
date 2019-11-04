package ru.nikitaboiko.kotlinstudy.ui.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note.*
import ru.nikitaboiko.kotlinstudy.R
import ru.nikitaboiko.kotlinstudy.data.entities.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbarNote)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(
                    DATE_TIME_FORMAT,
                    Locale.getDefault()
            ).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView()
    }


    private fun initView() {
        note_activity_title.removeTextChangedListener(textChangeListener)
        note_activity_text.removeTextChangedListener(textChangeListener)

        if (note != null) {
            note_activity_title.setText(note?.title ?: "")
            note_activity_text.setText(note?.text ?: "")
            val color = when (note!!.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
            }

            toolbarNote.setBackgroundColor(resources.getColor(color))
        }

        note_activity_title.addTextChangedListener(textChangeListener)
        note_activity_text.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (note_activity_title.text == null || note_activity_title.text!!.length < 3) return

        note = note?.copy(
                title = note_activity_title.text.toString(),
                text = note_activity_text.text.toString(),
                lastChanged = Date()
        )
                ?: Note(UUID.randomUUID().toString(), note_activity_title.text.toString(), note_activity_title.text.toString())

        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
}
