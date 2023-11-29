package project.application.myapplication;





import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;

    private FirebaseAuth mAuth;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    Button button;

    Button miwa;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.login);
        miwa=findViewById(R.id.button_without_login);
        button = findViewById(R.id.button_login);
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setFilterByAuthorizedAccounts(false)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.miwa))
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK) {
                            try {
                                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                String idToken = credential.getGoogleIdToken();
                                String username = credential.getId();
                                String password = credential.getPassword();
                                if (idToken != null) {
                                    String email = credential.getId();

                                    // Got an ID token from Google. Use it to authenticate
                                    // with Firebase.
                                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                                    mAuth.signInWithCredential(firebaseCredential)
                                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d("tag", "signInWithCredential:success");
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        Intent click = new Intent(LoginActivity.this, Activity_3.class);
                                                        startActivity(click);
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Log.w("TAG", "signInWithCredential:failure", task.getException());

                                                    }
                                                }
                                            });

                                } else if (password != null) {

                                }

                            } catch (ApiException e) {
                                e.printStackTrace();

                            }
                        }else{
                            Log.d("tag","miwamiwamiwa");
                        }

                    }
                });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(
                                        result.getPendingIntent().getIntentSender()).build();
                                activityResultLauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.d("TAG", e.getLocalizedMessage());
                            }
                        });
            }
        });

        miwa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                Intent click = new Intent(LoginActivity.this, Activity_3.class);
                startActivity(click);
            }

        });
    }


}
