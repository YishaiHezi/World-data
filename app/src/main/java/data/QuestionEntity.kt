package data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * This class represents a question in the DB.
 */
@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageName: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val chosenAnswer: String?
)