package com.example.dungeonsanddragonsapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import database.DatabaseHelper
import org.example.characters.Character


class CreateCharacterActivity : AppCompatActivity() {

    private lateinit var strenghtSeekBar: SeekBar
    private lateinit var strenghtValue: TextView

    private lateinit var dexteritySeekBar: SeekBar
    private lateinit var dexterityValue: TextView

    private lateinit var intelligenceSeekBar: SeekBar
    private lateinit var intelligenceValue: TextView

    private lateinit var wisdomSeekBar: SeekBar
    private lateinit var wisdomValue: TextView

    private lateinit var charismaSeekBar: SeekBar
    private lateinit var charismaValue: TextView

    private lateinit var constitutionSeekBar: SeekBar
    private lateinit var constitutionValue: TextView

    private lateinit var yourPointsValue: TextView
    private lateinit var lifePointsValue: TextView

    var totalPoints: Int = 27
    private var previousStrenghtPoints: Int = 8
    private var previousDexterityPoints: Int = 8
    private var previousIntelligencePoints: Int = 8
    private var previousWisdomPoints: Int = 8
    private var previousCharismaPoints: Int = 8
    private var previousConstitutionPoints: Int = 8
    private var previousLifePoints: Int = 8

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_character)

        dbHelper = DatabaseHelper(this)

        val character = Character()

        yourPointsValue = findViewById(R.id.your_points_value)
        lifePointsValue = findViewById(R.id.life_points_value)

        strenghtSeekBar = findViewById(R.id.strenght_slider)
        strenghtValue = findViewById(R.id.strenght_value)

        strenghtSeekBar.max = 7

        strenghtSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentStrenghtPoints = progress + 8
                val pointsUsed = numberOfPoints(currentStrenghtPoints, previousStrenghtPoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                strenghtValue.text = currentStrenghtPoints.toString()
                character.forca = currentStrenghtPoints

                previousStrenghtPoints = currentStrenghtPoints
                updateSeekBarsState()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        dexteritySeekBar = findViewById(R.id.dexterity_slider)
        dexterityValue = findViewById(R.id.dexterity_value)

        dexteritySeekBar.max = 7

        dexteritySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentDexterityPoints = progress + 8
                val pointsUsed = numberOfPoints(currentDexterityPoints, previousDexterityPoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                dexterityValue.text = currentDexterityPoints.toString()
                character.destreza = currentDexterityPoints

                previousDexterityPoints = currentDexterityPoints
                updateSeekBarsState()
            }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        intelligenceSeekBar = findViewById(R.id.intelligence_slider)
        intelligenceValue = findViewById(R.id.intelligence_value)

        intelligenceSeekBar.max = 7

        intelligenceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentIntelligencePoints = progress + 8
                val pointsUsed = numberOfPoints(currentIntelligencePoints, previousIntelligencePoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                intelligenceValue.text = currentIntelligencePoints.toString()
                character.inteligencia = currentIntelligencePoints

                previousIntelligencePoints = currentIntelligencePoints
                updateSeekBarsState()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        wisdomSeekBar = findViewById(R.id.wisdom_slider)
        wisdomValue = findViewById(R.id.wisdom_value)

        wisdomSeekBar.max = 7

        wisdomSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentWisdomPoints = progress + 8
                val pointsUsed = numberOfPoints(currentWisdomPoints, previousWisdomPoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                wisdomValue.text = currentWisdomPoints.toString()
                character.sabedoria = currentWisdomPoints

                previousWisdomPoints = currentWisdomPoints
                updateSeekBarsState()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        charismaSeekBar = findViewById(R.id.charisma_slider)
        charismaValue = findViewById(R.id.charisma_value)

        charismaSeekBar.max = 7

        charismaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentCharismaPoints = progress + 8
                val pointsUsed = numberOfPoints(currentCharismaPoints, previousCharismaPoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                charismaValue.text = currentCharismaPoints.toString()
                character.carisma = currentCharismaPoints

                previousCharismaPoints = currentCharismaPoints
                updateSeekBarsState()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        constitutionSeekBar = findViewById(R.id.constitution_slider)
        constitutionValue = findViewById(R.id.constitution_value)

        constitutionSeekBar.max = 7

        constitutionSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentConstitutionPoints = progress + 8
                val pointsUsed = numberOfPoints(currentConstitutionPoints, previousConstitutionPoints)

                updateSeekBarsState()
                totalPoints = totalPoints - pointsUsed
                yourPointsValue.text = totalPoints.toString()
                constitutionValue.text = currentConstitutionPoints.toString()
                character.constituicao = currentConstitutionPoints
                
                previousConstitutionPoints = currentConstitutionPoints
                updateSeekBarsState()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        val races = arrayOf("AnaoDaMontanha", "Humano", "Draconato", "MeioOrc", "Elfo", "Halfling", "GnomoDaFloresta", "Anao", "HalflingRobusto", "GnomoDasRochas", "AltoElfo", "Gnomo", "Tiefling", "AnaoDaColina", "ElfoDaFloresta", "MeioElfo", "Drow", "HalflingPesLeves")

        val raceSpinner: Spinner = findViewById(R.id.race_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, races)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        raceSpinner.adapter = adapter

        val createButton: Button = findViewById(R.id.create_button)

        createButton.setOnClickListener {
            val selectedRace = raceSpinner.selectedItem.toString()
            val name = findViewById<EditText>(R.id.name_input).text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please, write your character's name", Toast.LENGTH_SHORT).show()
            } else if (dbHelper.characterExists(name)) {
                Toast.makeText(this, "Character with this name already exists!", Toast.LENGTH_SHORT).show()
            } else {
                character.init(name, selectedRace)
                dbHelper.createCharacter(character)
                showCharacterInfoDialog(character, selectedRace)
            }
        }
    }

    private fun numberOfPoints(attributePoints: Int, previousAttributePoints: Int): Int {
        val valuesMap = mapOf(
            8 to 0, 9 to 1, 10 to 2, 11 to 3,
            12 to 4, 13 to 5, 14 to 7, 15 to 9
        )
        return valuesMap[attributePoints]!! - valuesMap[previousAttributePoints]!!
    }

    private fun updateSeekBarsState() {
        val isEnabled = totalPoints > 0

        if (totalPoints <= 0) {
            totalPoints = 0
            yourPointsValue.text = totalPoints.toString()
        }

        strenghtSeekBar.isEnabled = isEnabled
        dexteritySeekBar.isEnabled = isEnabled
        intelligenceSeekBar.isEnabled = isEnabled
        wisdomSeekBar.isEnabled = isEnabled
        charismaSeekBar.isEnabled = isEnabled
        constitutionSeekBar.isEnabled = isEnabled
    }

    private fun showCharacterInfoDialog(character: Character, race: String) {

        val characterInfo = """
        Name: ${character.name}
        Race: ${race}
        Strength: ${character.forca}
        Dexterity: ${character.destreza}
        Intelligence: ${character.inteligencia}
        Wisdom: ${character.sabedoria}
        Charisma: ${character.carisma}
        Constitution: ${character.constituicao}
        Life Points: ${character.pontosVida}
    """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Character Info")
            .setMessage(characterInfo)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }


}