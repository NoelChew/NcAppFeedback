package com.noelchew.ncappfeedback;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.noelchew.ncappfeedback.library.NcAppFeedback;
import com.noelchew.ncappfeedback.library.NcAppFeedbackListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SENDER_NAME = "NcAppFeedback Demo User";

    private Context context;

    private EditText etSparkPostApiKey, etSenderEmail, etSenderName, etRecipientEmail;
    private Button btnSend;
    private ProgressDialog progressDialog;

    // set this to true if you want to use normal email client as backup if SparkPost fails
    private static final boolean ENABLE_NORMAL_EMAIL_AS_BACKUP = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSparkPostApiKey = (EditText) findViewById(R.id.edit_text_sparkpost_api_key);
        etSenderEmail = (EditText) findViewById(R.id.edit_text_sender_email);
        etSenderName = (EditText) findViewById(R.id.edit_text_sender_name);
        etRecipientEmail = (EditText) findViewById(R.id.edit_text_recipient_email);
        btnSend = (Button) findViewById(R.id.button_send);

        etSparkPostApiKey.setText(getString(R.string.sparkpost_api_key));
        etSenderEmail.setText(getString(R.string.sparkpost_sender_email));
        etSenderName.setText(SENDER_NAME);
        etRecipientEmail.setText(getString(R.string.sparkpost_recipient_email));

        btnSend.setOnClickListener(btnSendOnClickListener);

        progressDialog = new ProgressDialog(MainActivity.this);

        context = MainActivity.this;
    }

    private View.OnClickListener btnSendOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (progressDialog != null && !progressDialog.isShowing()) {
//                progressDialog.show();
//            }

            NcAppFeedback.feedback(context, etSparkPostApiKey.getText().toString(), etSenderEmail.getText().toString(), etSenderName.getText().toString(), etRecipientEmail.getText().toString(), R.string.nc_utils_feedback, new NcAppFeedbackListener() {
                @Override
                public void onFeedbackAnonymouslySuccess() {
                    Toast.makeText(context, "onFeedbackAnonymouslySuccess()", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFeedbackNonAnonymouslySuccess(String senderEmail) {
                    Toast.makeText(context, "onFeedbackNonAnonymouslySuccess() senderEmail: " + senderEmail, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFeedbackAnonymouslyError(Throwable e) {
                    Toast.makeText(context, "onFeedbackAnonymouslyError() error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(context, "onError() error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFeedbackViaPhoneEmailClient() {
                    Toast.makeText(context, "onFeedbackViaPhoneEmailClient()", Toast.LENGTH_SHORT).show();
                }

            }, progressDialog, ENABLE_NORMAL_EMAIL_AS_BACKUP);
        }
    };
}

