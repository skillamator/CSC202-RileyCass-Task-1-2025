package com.task3.csc202_task1_2025_absol_final

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.task3.csc202_task1_2025_absol_final.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                quizViewModel.isCheater =
                    result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false

            }
        }

    private var score = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate(Bundle?) called")


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            if (!quizViewModel.checkCurrentQuestionStatus){
                checkAnswer(true)
            }else{
                Snackbar.make(binding.root, R.string.already_answered, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.falseButton.setOnClickListener { view: View ->
            if (!quizViewModel.checkCurrentQuestionStatus) {
            checkAnswer(false)
            }else{
                Snackbar.make(binding.root, R.string.already_answered, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
//            val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            if (quizViewModel.questionBankRef[quizViewModel.currentIndex].isAnswered == false){
                binding.cheatButton.isEnabled = true
            }else{
                binding.cheatButton.isEnabled = false
            }
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            if (quizViewModel.questionBankRef[quizViewModel.currentIndex].isAnswered == false){
                binding.cheatButton.isEnabled = true
            }else{
                binding.cheatButton.isEnabled = false
            }
            updateQuestion()
        }

        updateQuestion()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {

        quizViewModel.questionBankRef[quizViewModel.currentIndex].isAnswered = true


        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when{
            quizViewModel.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> {
                score++
                R.string.correct_toast
            }
            else -> R.string.incorrect_toast
        }
        quizViewModel.isCheater = false
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_SHORT).show()
        binding.cheatButton.isEnabled = false


        if (quizViewModel.questionBankRef.all { it.isAnswered == true }){
            Toast.makeText(this, R.string.quiz_complete, Toast.LENGTH_SHORT).show()
            Snackbar.make(binding.root, "Your final score is $score out of ${quizViewModel.questionBankRef.size}", Snackbar.LENGTH_SHORT).show()
            binding.nextButton.isEnabled = false
            binding.previousButton.isEnabled = false
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            binding.cheatButton.isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}