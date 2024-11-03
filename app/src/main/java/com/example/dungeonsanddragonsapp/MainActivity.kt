package com.example.dungeonsanddragonsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createCharacterButton = findViewById<Button>(R.id.create_character_button)
        val viewCharactersButton = findViewById<Button>(R.id.view_characters)

        createCharacterButton.setOnClickListener {
            val intent = Intent(this, CreateCharacterActivity::class.java)
            startActivity(intent)
        }

        viewCharactersButton.setOnClickListener {
            val intent = Intent(this, ViewCharactersActivity::class.java)
            startActivity(intent)
        }
    }
}
