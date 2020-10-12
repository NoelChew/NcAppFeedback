package com.noelchew.ncappfeedback.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.noelchew.sparkpostutil.library.EmailListener;
import com.noelchew.sparkpostutil.library.SparkPostEmailUtil;
import com.noelchew.sparkpostutil.library.SparkPostRecipient;
import com.noelchew.sparkpostutil.library.SparkPostSender;

import java.util.regex.Pattern;

/**
 * Created by noelchew on 7/27/16.
 */
public class NcAppFeedback {
    private static final String TAG = "NcAppFeedback";

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, "", R.string.nc_utils_feedback, listener, progressDialog, false);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, boolean enableNormalEmailAsBackup) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, "", R.string.nc_utils_feedback, listener, progressDialog, enableNormalEmailAsBackup);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, int selectionDialogTitleResourceId, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, boolean enableNormalEmailAsBackup) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, "", selectionDialogTitleResourceId, listener, progressDialog, enableNormalEmailAsBackup);
    }

    // this is used when user has submitted a bad rating
    public static void feedbackWithBadRating(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, final int rating, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, "Rated " + rating + "/5", R.string.nc_utils_feedback_for_bad_rating, listener, progressDialog, false);
    }

    // this is used when user has submitted a bad rating
    public static void feedbackWithBadRating(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, final int rating, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, boolean enableNormalEmailAsBackup) {
        feedback(context, sparkPostApiKey, senderEmailAddress, senderName, recipientEmailAddress, "Rated " + rating + "/5", R.string.nc_utils_feedback_for_bad_rating, listener, progressDialog, enableNormalEmailAsBackup);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, final String additionalDetails, int selectionDialogTitleResourceId, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, boolean enableNormalEmailAsBackup) {
        String appName = "NcAppFeedback App";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appName = context.getString(R.string.nc_utils_feedback) + "-" + packageInfo.packageName + ":" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
            e.printStackTrace();
            listener.onError(e);
        }
        final String subject = appName;

        feedback(context, sparkPostApiKey, subject, senderEmailAddress, senderName, recipientEmailAddress, additionalDetails, selectionDialogTitleResourceId, listener, progressDialog, enableNormalEmailAsBackup);
    }

    public static void feedback(final Context context, final String sparkPostApiKey, final String subject, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, final String additionalDetails, int selectionDialogTitleResourceId, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, final boolean enableNormalEmailAsBackup) {
        if (!checkContext(context, listener)) {
            return;
        }
        progressDialog.setTitle(R.string.ncutils_loading);
        progressDialog.setMessage(context.getString(R.string.ncutils_please_wait));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(selectionDialogTitleResourceId)
                .setItems(new CharSequence[]{context.getString(R.string.nc_utils_feedback_anonymously), context.getString(R.string.nc_utils_feedback_by_email)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                sendFeedbackAnonymously(context, sparkPostApiKey, subject, senderEmailAddress, senderName, recipientEmailAddress, additionalDetails, listener, progressDialog, enableNormalEmailAsBackup);
                                break;

                            case 1:
                                sendFeedbackByEmail(context, subject, recipientEmailAddress, listener);
                                break;
                        }
                    }
                })
                .show();
    }

    public static void sendFeedbackAnonymously(final Context context, final String sparkPostApiKey, final String subject, final String senderEmailAddress, final String senderName, final String recipientEmailAddress, final String additionalDetails, @Nullable final NcAppFeedbackListener listener, final ProgressDialog progressDialog, final boolean enableNormalEmailAsBackup) {
        if (!checkContext(context, listener)) {
            return;
        }

        final View dialogView = View.inflate(context, R.layout.dialog_input, null);
        ((EditText) dialogView.findViewById(R.id.edit_text)).setHint(R.string.nc_utils_insert_feedback);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.setTitle(R.string.nc_utils_feedback)
                .setMessage(R.string.nc_utils_feedback_message)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.nc_utils_feedback_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!checkContext(context, listener)) {
                            return;
                        }
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        dialog.dismiss();

                        final String feedbackContent = ((EditText) dialogView.findViewById(R.id.edit_text)).getText().toString().trim();
                        if (!TextUtils.isEmpty(feedbackContent)) {
                            final View dialogView1 = View.inflate(context, R.layout.dialog_input, null);
                            ((EditText) dialogView1.findViewById(R.id.edit_text)).setHint(R.string.nc_utils_feedback_hint_email_address);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            AlertDialog alertDialog1 = builder.setTitle(R.string.nc_utils_feedback_input_email_address_title)
                                    .setMessage(R.string.nc_utils_feedback_input_email_address_message)
                                    .setView(dialogView1)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.nc_utils_feedback_ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!checkContext(context, listener)) {
                                                return;
                                            }
                                            if (progressDialog != null && !progressDialog.isShowing()) {
                                                progressDialog.show();
                                            }

                                            final String userEmail = ((EditText) dialogView1.findViewById(R.id.edit_text)).getText().toString().trim();

                                            final boolean isSenderEmailValid = isValidEmail(userEmail);

                                            EmailListener emailListener = new EmailListener() {
                                                @Override
                                                public void onSuccess() {
                                                    if (!checkContext(context, listener)) {
                                                        return;
                                                    }
                                                    if (progressDialog != null && progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                                    builder1.setTitle(R.string.nc_utils_feedback)
                                                            .setMessage(R.string.nc_utils_feedback_send_success)
                                                            .show();
                                                    if (isSenderEmailValid) {
                                                        if (listener != null) {
                                                            listener.onFeedbackNonAnonymouslySuccess(userEmail);
                                                        }
                                                    } else {
                                                        if (listener != null) {
                                                            listener.onFeedbackAnonymouslySuccess();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    if (!checkContext(context, listener)) {
                                                        return;
                                                    }
                                                    if (progressDialog != null && progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    if (listener != null) {
                                                        listener.onFeedbackAnonymouslyError(e);
                                                    }

                                                    Log.e(TAG, "Error occurred when sending email using SparkPost. Error: " + e);
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
                                                    feedbackContent +
                                                            "\n\nUser Email: " + userEmail.trim() +
                                                            "\n\n" + additionalDetails.trim(),
                                                    new SparkPostSender(senderEmailAddress, senderName),
                                                    new SparkPostRecipient(recipientEmailAddress),
                                                    emailListener);
                                        }
                                    })
                                    .setNegativeButton(R.string.nc_utils_feedback_cancel, null)
                                    .show();

                            Button btnCancel = alertDialog1.getButton(DialogInterface.BUTTON_NEGATIVE);
                            btnCancel.setTextColor(fetchAccentColor(context));
                            Button btnOk = alertDialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                            btnOk.setTextColor(fetchAccentColor(context));
                        } else {
                            Toast.makeText(context, R.string.nc_utils_feedback_invalid_feedback, Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton(R.string.nc_utils_feedback_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        Button btnCancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnCancel.setTextColor(fetchAccentColor(context));
        Button btnOk = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnOk.setTextColor(fetchAccentColor(context));
    }

    public static void sendFeedbackByEmail(Context context, String subject, String recipientEmailAddress, @Nullable NcAppFeedbackListener listener) {
        if (!checkContext(context, listener)) {
            return;
        }
        EmailUtil.sendEmailByIntent(context, subject, context.getString(R.string.nc_utils_feedback_email_message), recipientEmailAddress);
        if (listener != null) {
            listener.onFeedbackViaPhoneEmailClient();
        }
    }


    public static void sendFeedbackByEmail(Context context, String subject, String message, String recipientEmailAddress, @Nullable NcAppFeedbackListener listener) {
        if (!checkContext(context, listener)) {
            return;
        }
        EmailUtil.sendEmailByIntent(context, subject, message, recipientEmailAddress);
        if (listener != null) {
            listener.onFeedbackViaPhoneEmailClient();
        }
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private static int fetchAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private static boolean checkContext(Context context, @Nullable NcAppFeedbackListener listener) {
        if (context == null) {
            if (listener != null) {
                listener.onFeedbackAnonymouslyError(new Throwable("NULL context."));
            }
            return false;
        } else if (context instanceof Activity && isActivityFinishingOrDestroyed((Activity) context)) {
            if (listener != null) {
                listener.onFeedbackAnonymouslyError(new Throwable("Activity is not alive."));
            }
            return false;
        }
        return true;
    }

    private static boolean isActivityFinishingOrDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed() || activity.isFinishing();
        } else {
            return activity.isFinishing();
        }
    }
}
