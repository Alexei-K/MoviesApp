package com.kolis.test_catalog_app.data

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.kolis.test_catalog_app.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DressModel(
    val id: Int,
    val name: String,
    val oldPrice: Float,
    val newPrice: Float,
    var isLiked: Boolean = false,
    val overallRating: Long,
    val numberOfVotes: Long,
    val timeTill: Long = 0L,
    val sizes: List<String>,
    val colors: List<Pair<String, String>>,
    val description: String,
    val productCode: String,
    val category: String,
    val material: String,
    val country: String
) : Parcelable {

    companion object {
        const val sampleDesription: String =
            "The Karissa V-Neck Tee features a semi-fitted shape that's " +
                    "flattering for every figure. You can hit the gym with confidence while it hugs curves and " +
                    "hides common \"problem\" areas. Find stunning women's cocktail dresses and party dresses."
        private val sampleColors = listOf(
            Pair("Grey", "676767"),
            Pair("Black", "000000"),
            Pair("Red", "EB5757"),
            Pair("Green", "5ECE7B")
        )

        /**
         * Sample data used to upload it on server or local testing
         */
        val sampleList = arrayListOf(
            DressModel(
                0, "Scaridian dress", 100.00f, 50.00f, false, 80, 83, 1602798596177,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                1, "Wool dress", 200.00f, 180.00f, false, 100, 12, 1602885996177,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "China"
            ),
            DressModel(
                2, "Cream cotton dress", 150.00f, 100.00f, true, 120, 28, 1602962396177,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Ukraine"
            ),
            DressModel(
                3, "Black dress", 120.00f, 120.00f, true, 2, 1, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                4, "Scaridian dress", 120.00f, 50.00f, false, 40, 24, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                5, "Black dress", 1000.00f, 250.00f, true, 45, 12, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "China"
            ),
            DressModel(
                6, "Scaridian dress", 20.00f, 20.00f, false, 54, 40, 1609796996177,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                7, "Wool dress", 160.00f, 16.00f, true, 80, 58, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "China"
            ),
            DressModel(
                8, "Scaridian dress", 120.00f, 120.00f, false, 100, 26, 1607890006177,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                9, "Wool dress", 120.00f, 50.00f, true, 120, 40, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                10, "Scaridian dress", 160.00f, 50.00f, true, 80, 16, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            ),
            DressModel(
                1, "Black dress", 200.00f, 50.00f, false, 40, 40, 7,
                listOf("small", "medium", "Large", "tiny", "XL", "XXL"), sampleColors,
                sampleDesription, "578902-00", "Sweater", "Cotton", "Spain"
            )
        )

        const val ID_CODE: String = "id"
        val NAME_CODE: String = "name"
        val OLD_PRICE_CODE: String = "oldPrice"
        val NEW_PRICE_CODE: String = "newPrice"
        var IS_LIKED_CODE: String = "isLiked"
        val OVERALL_RATING_CODE: String = "overallRating"
        val NUMBER_OF_VOTES_CODE: String = "numberOfVotes"
        val TIME_TILL_CODE: String = "timeTill"
        val SIZE_CODE: String = "sizes"
        val COLOR_NAME_CODE: String = "colors"
        val DESCRIPTION_CODE: String = "description"
        val PRODUCT_CODE_CODE: String = "productCode"
        val CATEGORY_CODE: String = "category"
        val MATERIAL_CODE: String = "material"
        val COUNTRY_CODE: String = "country"

        fun fromFirebaseDocument(document: QueryDocumentSnapshot): DressModel {
            return DressModel(
                (document[ID_CODE] as Long).toInt(),
                document[NAME_CODE] as String,
                (document[OLD_PRICE_CODE] as Double).toFloat(),
                (document[NEW_PRICE_CODE] as Double).toFloat(),
                document[IS_LIKED_CODE] as Boolean,
                document[OVERALL_RATING_CODE] as Long,
                document[NUMBER_OF_VOTES_CODE] as Long,
                document[TIME_TILL_CODE] as Long,
                document[SIZE_CODE] as ArrayList<String>,
                getColorsFromFirebaseDocument(document),
                document[DESCRIPTION_CODE] as String,
                document[PRODUCT_CODE_CODE] as String,
                document[CATEGORY_CODE] as String,
                document[MATERIAL_CODE] as String,
                document[COUNTRY_CODE] as String
            )
        }

        private fun getColorsFromFirebaseDocument(document: QueryDocumentSnapshot): MutableList<Pair<String, String>> {
            val colorsArray = document[COLOR_NAME_CODE] as ArrayList<HashMap<String, String>>
            val colors = mutableListOf<Pair<String, String>>()
            for (map in colorsArray) {
                colors.add(Pair(map["first"] as String, map["second"] as String))
            }
            return colors
        }
    }

    fun getAvgMark(): Float = overallRating.toFloat() / numberOfVotes.toFloat()

    // For testing purpose
    fun getTestImageResource(): Int {
        return when (id % 4) {
            0 -> R.drawable.image_test
            1 -> R.drawable.image_test2
            2 -> R.drawable.image_test3
            3 -> R.drawable.image_test4
            else -> R.drawable.image_test4
        }

    }

    /**
     *Mapper for Firebase Cloud Firestore
     */
    fun toMap(): Map<String, Any> {
        return hashMapOf(
            ID_CODE to id,
            NAME_CODE to name,
            OLD_PRICE_CODE to oldPrice,
            NEW_PRICE_CODE to newPrice,
            IS_LIKED_CODE to isLiked,
            OVERALL_RATING_CODE to overallRating,
            NUMBER_OF_VOTES_CODE to numberOfVotes,
            TIME_TILL_CODE to timeTill,
            SIZE_CODE to sizes,
            COLOR_NAME_CODE to colors,
            DESCRIPTION_CODE to description,
            PRODUCT_CODE_CODE to productCode,
            CATEGORY_CODE to category,
            MATERIAL_CODE to material,
            COUNTRY_CODE to country
        )
    }
}