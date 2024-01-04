package com.example.myapplication

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var exitConfirmation = false
    private lateinit var adapter: RecyclerContactAdapter
    private lateinit var contactList: ArrayList<ContactModel>
    private val delayHandler = Handler(Looper.getMainLooper())
    private val delay: Long = 300 // Delay time in milliseconds
    private var isClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val closeButton: ImageButton = findViewById(R.id.close_button)
        closeButton.setOnClickListener {
            showExitConfirmation()
        }
        supportActionBar?.title = "Contact App"

        // Toggle button click method
        val button =
            findViewById<Button>(R.id.toggle_button) // Use findViewById with the correct type

        var isDarkMode = false // Assuming the initial mode is light

        button.setOnClickListener {
            isDarkMode = !isDarkMode
            AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }


        contactList = arrayListOf(
            ContactModel(R.drawable.image1, "John Doe", "123-456-7890"),
            ContactModel(R.drawable.image2, "Alice Smith", "987-654-3210"),
            ContactModel(R.drawable.image3, "Bob Johnson", "555-123-4567"),
            // Adding more ContactModel instances
            ContactModel(R.drawable.image4, "Emily Davis", "111-222-3333"),
            ContactModel(R.drawable.image5, "James Wilson", "444-555-6666"),
            ContactModel(R.drawable.image6, "Sophia Brown", "777-888-9999"),
            ContactModel(R.drawable.image7, "Oliver Miller", "333-222-1111"),
            ContactModel(R.drawable.image8, "Charlotte Garcia", "999-888-7777"),
            ContactModel(R.drawable.image9, "Daniel Martinez", "444-333-2222"),
            ContactModel(R.drawable.image10, "Ava Johnson", "666-777-8888"),

            // Adding more ContactModel instances
            ContactModel(R.drawable.image4, "Emily Davis", "111-222-3333"),
            ContactModel(R.drawable.image5, "James Wilson", "444-555-6666"),
            ContactModel(R.drawable.image6, "Sophia Brown", "777-888-9999"),
            ContactModel(R.drawable.image7, "Oliver Miller", "333-222-1111"),
            ContactModel(R.drawable.image8, "Charlotte Garcia", "999-888-7777"),
            ContactModel(R.drawable.image9, "Daniel Martinez", "444-333-2222"),
            ContactModel(R.drawable.image10, "Ava Johnson", "666-777-8888"),

            // Adding more ContactModel instances
            ContactModel(R.drawable.image4, "Emily Davis", "111-222-3333"),
            ContactModel(R.drawable.image5, "James Wilson", "444-555-6666"),
            ContactModel(R.drawable.image6, "Sophia Brown", "777-888-9999"),
            ContactModel(R.drawable.image7, "Oliver Miller", "333-222-1111"),
            ContactModel(R.drawable.image8, "Charlotte Garcia", "999-888-7777"),
            ContactModel(R.drawable.image9, "Daniel Martinez", "444-333-2222"),
            ContactModel(R.drawable.image10, "Ava Johnson", "666-777-8888"),
        )

// Inside your onCreate method after setting the LinearLayoutManager
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerContactAdapter(this, contactList)
        recyclerView.adapter = adapter

        setupInsertContact()
        setupUpdateContact()
    }

    private fun setupInsertContact() {
        val fabAddContact = findViewById<FloatingActionButton>(R.id.fabAddContact)
        fabAddContact.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_update, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()

            val buttonSave = dialogView.findViewById<Button>(R.id.buttonSave)
            val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)

            buttonSave.setOnClickListener {
                val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
                val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)
                val name = editTextName.text.toString()
                val phone = editTextPhone.text.toString()

                contactList.add(ContactModel(R.drawable.ic_launcher_foreground, name, phone))
                adapter.notifyItemInserted(contactList.size - 1)
                alertDialog.dismiss()
            }

            buttonCancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

    private fun setupUpdateContact() {
        adapter.setOnItemClickListener { position ->
            if (isClicked) {
                isClicked = false
                showDeleteConfirmationDialog(position)
            } else {
                isClicked = true
                val clickedContact = contactList[position]
                showEditDialog(clickedContact, position)

                delayHandler.postDelayed({
                    isClicked = false
                }, delay)
            }
        }
    }

    private fun showEditDialog(contact: ContactModel, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_update, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)
        val buttonDelete = dialogView.findViewById<Button>(R.id.buttonDelete) // New delete button

        editTextName.setText(contact.name)
        editTextPhone.setText(contact.phone)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()

        val buttonSave = dialogView.findViewById<Button>(R.id.buttonSave)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)

        buttonSave.setOnClickListener {
            val updatedName = editTextName.text.toString()
            val updatedPhone = editTextPhone.text.toString()

            contactList[position] = ContactModel(contact.image, updatedName, updatedPhone)
            adapter.notifyItemChanged(position)
            alertDialog.dismiss()
        }

        buttonDelete.setOnClickListener {
            // Handle deletion here
            contactList.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, contactList.size)
            alertDialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }


    private fun showDeleteConfirmationDialog(position: Int) {
        val clickedContact = contactList[position]

        AlertDialog.Builder(this)
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete ${clickedContact.name}?")
            .setPositiveButton("Yes") { _, _ ->
                contactList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, contactList.size)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onBackPressed() {
        if (exitConfirmation) {
            super.onBackPressed()
        } else {
            showExitConfirmation()
        }
    }

    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                exitConfirmation = true
                onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}