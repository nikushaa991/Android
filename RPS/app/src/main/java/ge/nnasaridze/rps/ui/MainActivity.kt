package ge.nnasaridze.rps.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ge.nnasaridze.rps.R.drawable.*
import ge.nnasaridze.rps.databinding.ActivityMainBinding
import ge.nnasaridze.rps.enums.Action
import ge.nnasaridze.rps.enums.GameResult
import ge.nnasaridze.rps.R.color.score_default_color as black
import ge.nnasaridze.rps.R.color.score_tie_color as yellow
import ge.nnasaridze.rps.R.color.score_win_color as green

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerScore = 0
    private var computerScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonRock.setOnClickListener { onPlayerAction(Action.ROCK) }
            buttonPaper.setOnClickListener { onPlayerAction(Action.PAPER) }
            buttonScissors.setOnClickListener { onPlayerAction(Action.SCISSORS) }
        }
    }

    private fun onPlayerAction(playerAction: Action) {

        val computerAction = generateRandomAction()
        val gameResult = getGameResultFromActions(playerAction, computerAction)

        var playerColor = black
        var computerColor = black

        when (gameResult) {
            GameResult.WIN -> {
                playerColor = green
                playerScore += 1
            }
            GameResult.LOSE -> {
                computerColor = green
                computerScore += 1
            }
            GameResult.TIE -> {
                playerColor = yellow
                computerColor = yellow
            }
        }

        binding.apply {
            textScorePlayer.text = playerScore.toString()
            textScorePlayer.setTextColor(resolveColor(playerColor))
            imagePlayer.setImageResource(getImageIdFromAction(playerAction))

            textScoreComputer.text = computerScore.toString()
            textScoreComputer.setTextColor(resolveColor(computerColor))
            imageComputer.setImageResource(getImageIdFromAction(computerAction))

            textStart.visibility = View.INVISIBLE
        }
    }

    private fun getGameResultFromActions(playerAction: Action, computerAction: Action): GameResult {
        if (playerAction == computerAction) return GameResult.TIE
        return if (
            (playerAction == Action.ROCK && computerAction == Action.SCISSORS) ||
            (playerAction == Action.PAPER && computerAction == Action.ROCK) ||
            (playerAction == Action.SCISSORS && computerAction == Action.PAPER)
        ) GameResult.WIN else GameResult.LOSE
    }

    private fun generateRandomAction(): Action {
        return Action.values().toList().random()
    }

    private fun getImageIdFromAction(action: Action): Int {
        return when (action) {
            Action.ROCK -> rock
            Action.PAPER -> paper
            Action.SCISSORS -> scissors
        }
    }

    private fun resolveColor(color: Int): Int {
        return ContextCompat.getColor(applicationContext, color)
    }
}