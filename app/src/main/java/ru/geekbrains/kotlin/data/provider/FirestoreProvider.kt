package ru.geekbrains.kotlin.data.provider


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.kotlin.data.entity.Note
import ru.geekbrains.kotlin.data.entity.User
import ru.geekbrains.kotlin.data.errors.NoAuthException
import ru.geekbrains.kotlin.data.model.NoteResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreProvider(val firebaseAuth: FirebaseAuth, val store: FirebaseFirestore) : DataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

    private val notesReference
        get() = currentUser?.let {
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()


    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        continuation.resume(currentUser?.let { User(it.displayName ?: "", it.email ?: "") })
    }

    override fun subscribeToAllNotes(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null

        try {
            registration = notesReference.addSnapshotListener { snapshot, e ->
                val value = e?.let {
                    NoteResult.Error(it)
                } ?: snapshot?.let {
                    val notes = snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }

                value?.let { offer(it) }
            }
        } catch (t: Throwable) {
            offer(NoteResult.Error(t))
        }

        invokeOnClose { registration?.remove() }
    }

    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            notesReference.document(note.id).set(note)
                .addOnSuccessListener {
                    continuation.resume(note)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

    override suspend fun getNoteById(id: String): Note? = suspendCoroutine { continuation ->
        try {
            notesReference.document(id).get()
                .addOnSuccessListener { snapshot ->
                    val note = snapshot.toObject(Note::class.java)
                    continuation.resume(note)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

    override suspend fun deleteNote(id: String): Unit = suspendCoroutine { continuation ->
        try {
            notesReference.document(id).delete()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (t: Throwable) {
            continuation.resumeWithException(t)
        }
    }

}
