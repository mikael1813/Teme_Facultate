package com.google.firebase.quickstart.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.quickstart.auth.databinding.ActivityPhoneAuthBinding
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityPhoneAuthBinding

  private lateinit var auth: FirebaseAuth

  private var verificationInProgress = false
  private var storedVerificationId: String? = ""
  private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
  private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // Restore instance state
    if (savedInstanceState != null) {
      onRestoreInstanceState(savedInstanceState)
    }

    // Assign click listeners
    binding.buttonStartVerification.setOnClickListener(this)
    binding.buttonVerifyPhone.setOnClickListener(this)
    binding.buttonResend.setOnClickListener(this)
    binding.signOutButton.setOnClickListener(this)

    // Initialize Firebase Auth
    auth = FirebaseAuth.getInstance()

    // Initialize phone auth callbacks
    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

      override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        // This callback will be invoked in two situations:
        // 1 - Instant verification. In some cases the phone number can be instantly
        //     verified without needing to send or enter a verification code.
        // 2 - Auto-retrieval. On some devices Google Play services can automatically
        //     detect the incoming verification SMS and perform verification without
        //     user action.
        logd("onVerificationCompleted:$credential")
        verificationInProgress = false

        // Update the UI and attempt sign in with the phone credential
        updateUI(credential)
        signInWithPhoneAuthCredential(credential)
      }

      override fun onVerificationFailed(e: FirebaseException) {
        // This callback is invoked in an invalid request for verification is made,
        // for instance if the the phone number format is not valid.
        logw("onVerificationFailed", e)
        verificationInProgress = false

        if (e is FirebaseAuthInvalidCredentialsException) {
          // Invalid request
          binding.fieldPhoneNumber.error = "Invalid phone number."
        } else if (e is FirebaseTooManyRequestsException) {
          // The SMS quota for the project has been exceeded
          Snackbar.make(
            findViewById(android.R.id.content), "Quota exceeded.",
            Snackbar.LENGTH_SHORT
          ).show()
        }

        // Show a message and update the UI
        updateUI(STATE_VERIFY_FAILED)
      }

      override fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
      ) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
        logd("onCodeSent: $verificationId")

        // Save verification ID and resending token so we can use them later
        storedVerificationId = verificationId
        resendToken = token

        // Update UI
        updateUI(STATE_CODE_SENT)
      }
    }
  }

  public override fun onStart() {
    super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
    val currentUser = auth.currentUser
    updateUI(currentUser)

    if (verificationInProgress && validatePhoneNumber()) {
      startPhoneNumberVerification(binding.fieldPhoneNumber.text.toString())
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(KEY_VERIFY_IN_PROGRESS, verificationInProgress)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    verificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS)
  }

  private fun startPhoneNumberVerification(phoneNumber: String) {
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
      phoneNumber,      // Phone number to verify
      60,               // Timeout duration
      TimeUnit.SECONDS, // Unit of timeout
      this,             // Activity (for callback binding)
      callbacks
    ) // OnVerificationStateChangedCallbacks

    verificationInProgress = true
  }

  private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
    val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
    signInWithPhoneAuthCredential(credential)
  }

  private fun resendVerificationCode(
    phoneNumber: String,
    token: PhoneAuthProvider.ForceResendingToken?
  ) {
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
      phoneNumber, // Phone number to verify
      60, // Timeout duration
      TimeUnit.SECONDS, // Unit of timeout
      this, // Activity (for callback binding)
      callbacks, // OnVerificationStateChangedCallbacks
      token
    ) // ForceResendingToken from callbacks
  }

  private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
    auth.signInWithCredential(credential)
      .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
          // Sign in success, update UI with the signed-in user's information
          logd("signInWithCredential:success")

          val user = task.result?.user
          updateUI(STATE_SIGNIN_SUCCESS, user)
        } else {
          // Sign in failed, display a message and update the UI
          logw("signInWithCredential:failure", task.exception)
          if (task.exception is FirebaseAuthInvalidCredentialsException) {
            // The verification code entered was invalid
            binding.fieldVerificationCode.error = "Invalid code."
          }
          // Update UI
          updateUI(STATE_SIGNIN_FAILED)
        }
      }
  }

  private fun signOut() {
    auth.signOut()
    updateUI(STATE_INITIALIZED)
  }

  private fun updateUI(user: FirebaseUser?) {
    if (user != null) {
      updateUI(STATE_SIGNIN_SUCCESS, user)
    } else {
      updateUI(STATE_INITIALIZED)
    }
  }

  private fun updateUI(cred: PhoneAuthCredential) {
    updateUI(STATE_VERIFY_SUCCESS, null, cred)
  }

  private fun updateUI(
    uiState: Int,
    user: FirebaseUser? = auth.currentUser,
    cred: PhoneAuthCredential? = null
  ) {
    when (uiState) {
      STATE_INITIALIZED -> {
        // Initialized state, show only the phone number field and start button
        enableViews(binding.buttonStartVerification, binding.fieldPhoneNumber)
        disableViews(binding.buttonVerifyPhone, binding.buttonResend, binding.fieldVerificationCode)
        binding.detail.text = null
      }
      STATE_CODE_SENT -> {
        // Code sent state, show the verification field, the
        enableViews(
          binding.buttonVerifyPhone,
          binding.buttonResend,
          binding.fieldPhoneNumber,
          binding.fieldVerificationCode
        )
        disableViews(binding.buttonStartVerification)
        binding.detail.setText(R.string.status_code_sent)
      }
      STATE_VERIFY_FAILED -> {
        // Verification has failed, show all options
        enableViews(
          binding.buttonStartVerification,
          binding.buttonVerifyPhone,
          binding.buttonResend,
          binding.fieldPhoneNumber,
          binding.fieldVerificationCode
        )
        binding.detail.setText(R.string.status_verification_failed)
      }
      STATE_VERIFY_SUCCESS -> {
        // Verification has succeeded, proceed to firebase sign in
        disableViews(
          binding.buttonStartVerification,
          binding.buttonVerifyPhone,
          binding.buttonResend,
          binding.fieldPhoneNumber,
          binding.fieldVerificationCode
        )
        binding.detail.setText(R.string.status_verification_succeeded)

        // Set the verification text based on the credential
        if (cred != null) {
          if (cred.smsCode != null) {
            binding.fieldVerificationCode.setText(cred.smsCode)
          } else {
            binding.fieldVerificationCode.setText(R.string.instant_validation)
          }
        }
      }
      STATE_SIGNIN_FAILED ->
        // No-op, handled by sign-in check
        binding.detail.setText(R.string.status_sign_in_failed)
      STATE_SIGNIN_SUCCESS -> {
      }
    } // Np-op, handled by sign-in check

    if (user == null) {
      // Signed out
      binding.phoneAuthFields.visibility = View.VISIBLE
      binding.signedInButtons.visibility = View.GONE

      binding.status.setText(R.string.signed_out)
    } else {
      // Signed in
      binding.phoneAuthFields.visibility = View.GONE
      binding.signedInButtons.visibility = View.VISIBLE

      enableViews(
        binding.fieldPhoneNumber,
        binding.fieldVerificationCode
      )
      binding.fieldPhoneNumber.text = null
      binding.fieldVerificationCode.text = null

      binding.status.setText(R.string.signed_in)
      binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)
    }
  }

  private fun validatePhoneNumber(): Boolean {
    val phoneNumber = binding.fieldPhoneNumber.text.toString()
    if (TextUtils.isEmpty(phoneNumber)) {
      binding.fieldPhoneNumber.error = "Invalid phone number."
      return false
    }

    return true
  }

  private fun enableViews(vararg views: View) {
    for (v in views) {
      v.isEnabled = true
    }
  }

  private fun disableViews(vararg views: View) {
    for (v in views) {
      v.isEnabled = false
    }
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.buttonStartVerification -> {
        if (!validatePhoneNumber()) {
          return
        }

        startPhoneNumberVerification(binding.fieldPhoneNumber.text.toString())
      }
      R.id.buttonVerifyPhone -> {
        val code = binding.fieldVerificationCode.text.toString()
        if (TextUtils.isEmpty(code)) {
          binding.fieldVerificationCode.error = "Cannot be empty."
          return
        }

        verifyPhoneNumberWithCode(storedVerificationId, code)
      }
      R.id.buttonResend -> resendVerificationCode(
        binding.fieldPhoneNumber.text.toString(),
        resendToken
      )
      R.id.signOutButton -> signOut()
    }
  }

  companion object {
    private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
    private const val STATE_INITIALIZED = 1
    private const val STATE_VERIFY_FAILED = 3
    private const val STATE_VERIFY_SUCCESS = 4
    private const val STATE_CODE_SENT = 2
    private const val STATE_SIGNIN_FAILED = 5
    private const val STATE_SIGNIN_SUCCESS = 6
  }
}
