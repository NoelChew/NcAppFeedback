package com.noelchew.ncappfeedback.library;

/**
 * Created by noelchew on 7/27/16.
 */
public interface NcAppFeedbackListener {
    void onFeedbackAnonymouslySuccess();
    void onFeedbackNonAnonymouslySuccess(String senderEmail);
    void onFeedbackAnonymouslyError(String error);
    void onFeedbackViaPhoneEmailClient();
    void onError(String error);
}
