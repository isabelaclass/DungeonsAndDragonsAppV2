package com.example.dungeonsanddragonsapp

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import database.DatabaseHelper
import org.example.characters.Character

class UpdateCharacterActivity : AppCompatActivity() {

    private lateinit var strenghtValue: TextView
    private lateinit var dexterityValue: TextView
    private lateinit var intelligenceValue: TextView
    private lateinit var wisdomValue: TextView
    private lateinit var charismaValue: TextView
    private lateinit var constitutionValue: TextView
    private lateinit var lifePointsValue: TextView
    private lateinit var race: TextView
    private lateinit var name: EditText
    private lateinit var saveButton: Button

    private lateinit var dbHelper: DatabaseHelper

    lateinit var oldName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_character)

        dbHelper = DatabaseHelper(this)

        val characterName = intent.getStringExtra("character_name")

        if (characterName != null) {
            loadCharacterData(characterName)
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadCharacterData(characterName: String) {

        var character = dbHelper.getCharacterByName(characterName)

        strenghtValue = findViewById(R.id.strenght_value)
        dexterityValue = findViewById(R.id.dexterity_value)
        intelligenceValue = findViewById(R.id.intelligence_value)
        wisdomValue = findViewById(R.id.wisdom_value)
        charismaValue = findViewById(R.id.charisma_value)
        constitutionValue = findViewById(R.id.constitution_value)
        lifePointsValue = findViewById(R.id.life_points_value)
        race = findViewById(R.id.race_text)
        name = findViewById(R.id.name_input)
        saveButton = findViewById(R.id.save_button)

        if (character != null) {
            val editableName: Editable = Editable.Factory.getInstance().newEditable(character.name)

            strenghtValue.text = character.forca.toString()
            dexterityValue.text = character.destreza.toString()
            intelligenceValue.text = character.inteligencia.toString()
            wisdomValue.text = character.sabedoria.toString()
            charismaValue.text = character.carisma.toString()
            constitutionValue.text = character.constituicao.toString()
            lifePointsValue.text = character.pontosVida.toString()
            race.text = character.race!!::class.simpleName ?: ""
            oldName = character.name
            name.text = editableName

        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            finish()
        }

        saveButton.setOnClickListener {

            val newName = name.text.toString()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Please enter a character name", Toast.LENGTH_SHORT).show()
            } else if (dbHelper.characterExists(newName) && newName != character?.name) {
                Toast.makeText(this, "A character with this name already exists!", Toast.LENGTH_SHORT).show()
            } else {
                if (character != null) {
                    dbHelper.updateCharacter(character, newName)
                    Toast.makeText(this, "Character updated successfully!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}