package ru.geekbrains.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import ru.geekbrains.kotlin.R
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.provider.FirestoreProvider
import ru.geekbrains.kotlin.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrains.kotlin.ui.note.NoteActivity
import ru.geekbrains.kotlin.ui.splash.SplashActivity


class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    val firestoreProvider: FirestoreProvider by inject()

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_main
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter {
            NoteActivity.start(this, it.id)
        }

        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
            else -> false
        }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }


}

