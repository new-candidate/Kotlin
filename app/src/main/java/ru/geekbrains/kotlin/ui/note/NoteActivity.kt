package ru.geekbrains.kotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.ui.base.BaseActivity
import ru.geekbrains.kotlin.ui.common.getResources
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {
    companion object {
        private const val NOTE_KEY = "note"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).apply {
            putExtra(NOTE_KEY, noteId)
            context.startActivity(this)
        }
    }

    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    override val viewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(NOTE_KEY)

        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title =  getString(R.string.new_note)
            initView()
        }
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.new_note)
        initView()
    }

    private fun initView() {
        et_title.removeTextChangedListener(textWatcher)
        et_body.removeTextChangedListener(textWatcher)

        note?.let {
            et_title.setTextKeepState(it.title)
            et_body.setTextKeepState(it.text)
            val color = note?.color?.getResources()
            color?.let {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, it))
            }
        }
        et_title.addTextChangedListener(textWatcher)
        et_body.addTextChangedListener(textWatcher)
    }

    private fun saveNote() {
        et_title.text?.let {
            if (it.length < 3) return
        } ?: return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString(), Note.Color.values().toList().shuffled().first())

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


