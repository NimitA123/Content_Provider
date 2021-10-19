package com.example.contain_provider_unit4
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.provider.ContactsContract
import java.util.*


class ContactFetcher(private val context: Context) {

    fun fetchAll(): ArrayList<Contact> {
//Select id, display_name from contatcs
        val projectionFields = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )


        //select _ID, DISPLAY_NAME

        val listContacts: ArrayList<Contact> = ArrayList<Contact>()


        val cursorLoader = CursorLoader(
            context,
            ContactsContract.Contacts.CONTENT_URI,
            projectionFields,  // the columns to retrieve
            null,  // the selection criteria (none)
            null,  // the selection args (none)
            null // the sort order (default)
        )
        val cursor: Cursor = cursorLoader.loadInBackground()

        val contactsMap: MutableMap<String, Contact> = HashMap<String, Contact>(cursor.count)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            do {
                val contactId = cursor.getString(idIndex)
                var contactDisplayName = ""
                if (cursor.getString(nameIndex) != null){
                    contactDisplayName = cursor.getString(nameIndex)
                }
                val contact = Contact(contactId, contactDisplayName)
                contactsMap[contactId] = contact
                listContacts.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        //matchContactNumbers(contactsMap)
        //matchContactEmails(contactsMap)
        return listContacts
    }
}