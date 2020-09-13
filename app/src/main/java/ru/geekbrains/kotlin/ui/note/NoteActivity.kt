package ru.geekbrains.kotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.ui.common.getResources
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    companion object {
        private val NOTE_KEY = "note"
        private const val DATE_FORMATE = "dd.MM.yy HH:mm"

        fun start(context: Context, note: Note? = null) =
            Intent(context, NoteActivity::class.java).apply {
                putExtra(NOTE_KEY, note)
                context.startActivity(this)
            }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    private val textWathcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(
            s: CharSequence?, start: Int, count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence?, start: Int, before: Int, count:
            Int
        ) {
        }
    }

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_note)

        note = intent.getParcelableExtra(NOTE_KEY)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMATE, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.new_note)
        initView()
    }

    private fun initView() {
        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)
            val color = note?.color?.getResources()
            color?.let {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, it))
            }
        }
        et_title.addTextChangedListener(textWathcher)
        et_body.addTextChangedListener(textWathcher)
    }

    private fun saveNote() {
        println("Такую штуку нашел" + arrayOf(Note.Color.values().toString()))
        et_title.text?.let {
            if (it.length < 3) return
        } ?: return
        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString(),
            Note.Color.values().toList().shuffled().first()
        )
        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}