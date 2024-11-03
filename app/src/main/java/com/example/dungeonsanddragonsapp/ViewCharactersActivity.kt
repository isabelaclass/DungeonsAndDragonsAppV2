package com.example.dungeonsanddragonsapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import database.DatabaseHelper

class ViewCharactersActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var searchView: SearchView
    private var selectedCharacterName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_characters)

        dbHelper = DatabaseHelper(this)
        val characterListView: ListView = findViewById(R.id.characterListView)
        searchView = findViewById(R.id.searchView)

        val characters = dbHelper.getAllCharacters()
        val trashIcon = findViewById<ImageView>(R.id.trashIcon)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            characters.map { it.name }
        )
        characterListView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        characterListView.setOnItemClickListener { _, _, position, _ ->
            selectedCharacterName = adapter.getItem(position)

            if (selectedCharacterName != null) {
                val intent = Intent(this, UpdateCharacterActivity::class.java).apply {
                    putExtra("character_name", selectedCharacterName)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Character not found!", Toast.LENGTH_SHORT).show()
            }
        }

        characterListView.setOnItemLongClickListener { _, _, position, _ ->
            selectedCharacterName = adapter.getItem(position)

            if (selectedCharacterName != null) {
                Toast.makeText(this, "Selected: $selectedCharacterName", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }

        trashIcon.setOnClickListener {
            if (selectedCharacterName != null) {
                dbHelper.deleteCharacter(selectedCharacterName!!)
                Toast.makeText(this, "Personagem excluído!", Toast.LENGTH_SHORT).show()
                selectedCharacterName = null

                finish()
                startActivity(intent)

            } else {
                Toast.makeText(this, "Nenhum personagem selecionado!", Toast.LENGTH_SHORT).show()
            }
        }

        val backArrow = findViewById<ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val characterListView: ListView = findViewById(R.id.characterListView)
        searchView = findViewById(R.id.searchView)

        val characters = dbHelper.getAllCharacters()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            characters.map { it.name }
        )

        characterListView.adapter = adapter
        val updatedCharacters = dbHelper.getAllCharacters()
        adapter.clear()
        adapter.addAll(updatedCharacters.map { it.name })
        adapter.notifyDataSetChanged()
    }
}