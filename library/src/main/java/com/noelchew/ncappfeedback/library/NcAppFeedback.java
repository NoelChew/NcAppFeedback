package com.noelchew.ncappfeedback.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.noelchew.sparkpostutil.library.EmailListener;
import com.noelchew.sparkpostutil.library.SparkPostEmailUtil;
import com.noelchew.sparkpostutil.library.SparkPostRecipient;
import com.noelchew.sparkpostutil.library.SparkPostSender;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by noelchew on 7/27/16.
 */
public class NcAppFeedback {
    private static final String TAG = "NcAppFeedback";

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, listener, progressDialog, false);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, boolean enableNormalEmailAsBackup) {

        String appName = "NcAppFeedback App";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appName = context.getString(R.string.nc_utils_feedback) + "-" + packageInfo.packageName + ":" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
            e.printStackTrace();
            listener.onError(e.getMessage());
        }
        final String subject = appName;

        feedback(context, sparkPostApiKey, subject, senderEmailAddress, senderName, recipientEmailAddress, listener, progressDialog, enableNormalEmailAsBackup);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String subject, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, final boolean enableNormalEmailAsBackup) {
        progressDialog.setTitle(R.string.ncutils_loading);
        progressDialog.setMessage(context.getString(R.string.ncutils_please_wait));

        ArrayList<String> selections = new ArrayList<>();
        selections.add(context.getString(R.string.nc_utils_feedback_anonymously));
        selections.add(context.getString(R.string.nc_utils_feedback_by_email));
        AlertDialogUtil.showAlertDialogWithSelections(context, selections, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        sendFeedbackAnonymously(context, sparkPostApiKey, subject, senderEmailAddress, senderName, recipientEmailAddress, listener, progressDialog, enableNormalEmailAsBackup);
                        break;

                    case 1:
                        sendFeedbackByEmail(context, subject, recipientEmailAddress, listener);
                        break;
                }
            }
        });
    }

    public static void sendFeedbackAnonymously(final Context context, final String sparkPostApiKey, final String subject, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, final boolean enableNormalEmailAsBackup) {
        AlertDialogUtil.showAlertDialogWithInput(context,
                context.getString(R.string.nc_utils_feedback),
                context.getString(R.string.nc_utils_feedback_message),
                context.getString(R.string.nc_utils_insert_feedback),
                "",
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE,
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence feedback) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        final String feedbackContent = feedback.toString();
                        if (!TextUtils.isEmpty(feedbackContent)) {
                            AlertDialogUtil.showAlertDialogWithInput(context,
                                    context.getString(R.string.nc_utils_feedback_input_email_address_title),
                                    context.getString(R.string.nc_utils_feedback_input_email_address_message),
                                    context.getString(R.string.nc_utils_feedback_hint_email_address),
                                    "",
                                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT,
                                    new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(@NonNull MaterialDialog dialog, CharSequence email) {
                                            if (progressDialog != null && !progressDialog.isShowing()) {
                                                progressDialog.show();
                                            }

                                            final String userEmail = email.toString().trim();
                                            final boolean isSenderEmailValid = isValidEmail(userEmail);

                                            EmailListener emailListener = new EmailListener() {
                                                @Override
                                                public void onSuccess() {
                                                    if (progressDialog != null && progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    AlertDialogUtil.showAlertDialogMessage(context, R.string.nc_utils_feedback, R.string.nc_utils_feedback_send_success);
                                                    if (isSenderEmailValid) {
                                                        listener.onFeedbackNonAnonymouslySuccess(userEmail);
                                                    } else {
                                                        listener.onFeedbackAnonymouslySuccess();
                                                    }
                                                }

                                                @Override
                                                public void onError(String errorMessage) {
                                                    if (progressDialog != null && progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    if (!TextUtils.isEmpty(errorMessage)) {
                                                        listener.onFeedbackAnonymouslyError(errorMessage);
                                                    } else {
                                                        listener.onFeedbackAnonymouslyError("");
                                                    }

                                                    Log.e(TAG, "Error occurred when sending email using SparkPost. Error: " + errorMessage);
                                                    if (enableNormalEmailAsBackup) {
                                                        Log.d(TAG, "Use normal email as backup is ENABLED.");
                                                        sendFeedbackByEmail(context, subject, feedbackContent, recipientEmailAddress, null);
                                                    } else {
                                                        Log.d(TAG, "Use normal email as backup is NOT ENABLED.");
                                                    }
                                                }
                                            };

                                            SparkPostEmailUtil.sendEmail(context,
                                                    sparkPostApiKey,
                                                    subject,
                                                    feedbackContent + "\nUser Email: " + userEmail.trim(),
                                                    new SparkPostSender(senderEmailAddress, senderName),
                                                    new SparkPostRecipient(recipientEmailAddress),
                                                    emailListener);
                                        }
                                    },
                                    context.getString(R.string.nc_utils_feedback_send));
                        } else {
                            Toast.makeText(context, R.string.nc_utils_feedback_invalid_feedback, Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                context.getString(R.string.ncutils_ok),
                context.getString(R.string.nc_utils_feedback_cancel),
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }
        );
    }

    public static void sendFeedbackByEmail(Context context, String subject, String recipientEmailAddress, @Nullable NcAppFeedbackListener listener) {
        EmailUtil.sendEmailByIntent(context, subject, context.getString(R.string.nc_utils_feedback_email_message), recipientEmailAddress);
        if (listener != null) {
            listener.onFeedbackViaPhoneEmailClient();
        }
    }


    public static void sendFeedbackByEmail(Context context, String subject, String message, String recipientEmailAddress, @Nullable NcAppFeedbackListener listener) {
        EmailUtil.sendEmailByIntent(context, subject, message, recipientEmailAddress);
        if (listener != null) {
            listener.onFeedbackViaPhoneEmailClient();
        }
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
