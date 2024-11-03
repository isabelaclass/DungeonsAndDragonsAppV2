package database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import org.example.characters.Character
import org.example.racas.iRace
import org.example.races.AltoElfo
import org.example.races.Anao
import org.example.races.AnaoDaColina
import org.example.races.AnaoDaMontanha
import org.example.races.Draconato
import org.example.races.Drow
import org.example.races.Elfo
import org.example.races.ElfoDaForesta
import org.example.races.Gnomo
import org.example.races.GnomoDaFloresta
import org.example.races.GnomoDasRochas
import org.example.races.HalfingPesLeves
import org.example.races.Halfling
import org.example.races.HalflingRobusto
import org.example.races.Humano
import org.example.races.MeioElfo
import org.example.races.MeioOrc
import org.example.races.Tiefling

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "character_database.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "characters"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_RACE = "race"
        const val COLUMN_STRENGTH = "strength"
        const val COLUMN_DEXTERITY = "dexterity"
        const val COLUMN_INTELLIGENCE = "intelligence"
        const val COLUMN_WISDOM = "wisdom"
        const val COLUMN_CHARISMA = "charisma"
        const val COLUMN_CONSTITUTION = "constitution"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_RACE TEXT NOT NULL,
                $COLUMN_STRENGTH INTEGER NOT NULL,
                $COLUMN_DEXTERITY INTEGER NOT NULL,
                $COLUMN_INTELLIGENCE INTEGER NOT NULL,
                $COLUMN_WISDOM INTEGER NOT NULL,
                $COLUMN_CHARISMA INTEGER NOT NULL,
                $COLUMN_CONSTITUTION INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createCharacter(character: Character): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, character.name ?: "")
            put(COLUMN_RACE, character.race!!::class.simpleName ?: "")
            put(COLUMN_STRENGTH, character.forca ?: 0)
            put(COLUMN_DEXTERITY, character.destreza ?: 0)
            put(COLUMN_INTELLIGENCE, character.inteligencia ?: 0)
            put(COLUMN_WISDOM, character.sabedoria ?: 0)
            put(COLUMN_CHARISMA, character.carisma ?: 0)
            put(COLUMN_CONSTITUTION, character.constituicao ?: 0)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun updateCharacter(character: Character, newName: String) {
        val db = writableDatabase

        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, newName)
        }

        db.update(TABLE_NAME, contentValues, "$COLUMN_NAME = ?", arrayOf(character.name))

        db.close()
    }

    fun deleteCharacter(name: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(name.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllCharacters(): List<Character> {
        val characterList = mutableListOf<Character>()
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val race = cursor.getString(cursor.getColumnIndex(COLUMN_RACE))
                    val strength = cursor.getInt(cursor.getColumnIndex(COLUMN_STRENGTH))
                    val dexterity = cursor.getInt(cursor.getColumnIndex(COLUMN_DEXTERITY))
                    val intelligence = cursor.getInt(cursor.getColumnIndex(COLUMN_INTELLIGENCE))
                    val wisdom = cursor.getInt(cursor.getColumnIndex(COLUMN_WISDOM))
                    val charisma = cursor.getInt(cursor.getColumnIndex(COLUMN_CHARISMA))
                    val constitution = cursor.getInt(cursor.getColumnIndex(COLUMN_CONSTITUTION))

                    val character = Character().apply {
                        this.init(name, race)
                        this.forca = strength
                        this.destreza = dexterity
                        this.inteligencia = intelligence
                        this.sabedoria = wisdom
                        this.carisma = charisma
                        this.constituicao = constitution
                    }

                    characterList.add(character)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor?.close()
            db.close()
        }

        return characterList
    }

    @SuppressLint("Range")
    fun getCharacterByName(name: String): Character? {
        val db = readableDatabase
        val cursor = db.query(
            "characters",
            null,
            "name = ?",
            arrayOf(name),
            null, null, null
        )

        var character: Character? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val characterName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE))
            val strength = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRENGTH))
            val dexterity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEXTERITY))
            val intelligence = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELLIGENCE))
            val wisdom = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WISDOM))
            val charisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHARISMA))
            val constitution = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUTION))

            character = Character().apply {
                init(characterName, race)
                this.forca = strength
                this.destreza = dexterity
                this.inteligencia = intelligence
                this.sabedoria = wisdom
                this.carisma = charisma
                this.constituicao = constitution
            }
        }

        cursor.close()
        db.close()
        return character
    }

    fun characterExists(name: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return exists
    }

}