package ru.geekbrains.kotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.model.NoteResult

class FirestoreProvider : DataProvider {
    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { snapshot, e ->
            e?.let {
                result.value = NoteResult.Error(it)
            } ?: snapshot?.let {
                val notes = snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                result.value = NoteResult.Succes(notes)
            }
        }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(note.id).set(note)
            .addOnSuccessListener { snapshot ->
                result.value = NoteResult.Succes(note)

            }.addOnFailureListener {
                result.value = NoteResult.Error(it)
            }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {

        val result = MutableLiveData<NoteResult>()

        notesReference.document(id).get().addOnSuccessListener { snapshot ->
            val note: Note? = snapshot.toObject(Note::class.java)
            result.value = NoteResult.Succes(note)

        }.addOnFailureListener {
            result.value = NoteResult.Error(it)
        }
        return result
    }
}