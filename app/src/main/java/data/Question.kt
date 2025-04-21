package data

import data.CountriesCodes.countryFlagMap


/**
 * A quiz question.
 */
data class Question(
    val id: Int,
    val imageRes: Int?,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val chosenAnswer: String?
) {


    companion object {


        /**
         * Converts [QuestionEntity] to [Question].
         */
        fun toQuestion(questionEntity: QuestionEntity) =
            Question(
                questionEntity.id,
                convertImage(questionEntity.imageName),
                questionEntity.questionText,
                questionEntity.options,
                questionEntity.correctAnswer,
                questionEntity.chosenAnswer
            )


        /**
         * Converts an image name to an image resource.
         */
        private fun convertImage(imageName: String): Int? {
            return if (imageName.startsWith("flag")) {
                val countryCode = imageName.substringAfter("flag_")
                countryFlagMap[countryCode]
            } else {
                null
            }
        }


    }


}
