package com.example.worlddata

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                modifier = Modifier.padding(bottom = 64.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Button(
                    onClick = ::onTakeQuizClick,
                    modifier = Modifier.size(150.dp)) {
                    Text(
                        text = stringResource(R.string.quiz_button),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = ::onExploreClick,
                    modifier = Modifier.size(150.dp)
                ) {
                    Text(
                        text = stringResource(R.string.explore_button),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
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

    }


    /**
     * Called when the "Explore Countries" button is clicked.
     */
    fun onExploreClick() {
        startActivity(CountryRatingActivity.createStartIntent(this))
    }


}

