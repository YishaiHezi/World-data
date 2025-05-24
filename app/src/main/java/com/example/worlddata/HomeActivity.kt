package com.example.worlddata

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worlddata.country.ui.CountryRatingActivity
import com.example.worlddata.quiz.ui.QuizActivity
import com.example.worlddata.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * The home screen of the application.
 *
 * @author Yishai Hezi
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                HomeScreen()
            }
        }

    }


    @Composable
    fun HomeScreen() {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.home_headline),
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = ::onTakeQuizClick
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(R.string.quiz_button),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = ::onExploreClick
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = stringResource(R.string.explore_button),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun PreviewMyScreen() {
        AppTheme {
            HomeScreen()
        }
    }


    /**
     * Called when the "Take a Quiz" button is clicked.
     */
    fun onTakeQuizClick() {
        startActivity(Intent(this, QuizActivity::class.java))
    }


    /**
     * Called when the "Explore Countries" button is clicked.
     */
    fun onExploreClick() {
        startActivity(CountryRatingActivity.createStartIntent(this))
    }


}

