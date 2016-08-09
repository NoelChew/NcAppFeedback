//package com.noelchew.ncappfeedback.library;
//
//import android.content.Context;
//import android.content.DialogInterface;
//
//import com.afollestad.materialdialogs.AlertDialogWrapper;
//import com.afollestad.materialdialogs.MaterialDialog;
//
//import java.util.ArrayList;
//
//public class AlertDialogUtil {
//
//    public static void showAlertDialogMessage(Context context, String message) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setMessage(message)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogMessage(Context context, int message) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setMessage(message)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogMessage(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(R.string.ncutils_ok, onClickListener)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogMessage(Context context, int title, int message) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogMessage(Context context, int title, int message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(R.string.ncutils_ok, onClickListener)
//                .create()
//                .show();
//    }
//
//
//    public static void showAlertDialogMessage(Context context, int message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setMessage(message)
//                .setPositiveButton(R.string.ncutils_ok, onClickListener)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogMessage(Context context, String message, DialogInterface.OnClickListener onClickListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setMessage(message)
//                .setPositiveButton(R.string.ncutils_ok, onClickListener)
//                .create()
//                .show();
//    }
//
//    public static void showAlertDialogWithInput(Context context, String title, String message, String hint, String prefill, int inputType, MaterialDialog.InputCallback inputCallback, String positiveButtonText) {
//        new MaterialDialog.Builder(context)
//                .title(title)
//                .content(message)
//                .inputType(inputType)
////                .inputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE)
//                .input(hint, prefill, inputCallback)
//                .positiveText(positiveButtonText)
//                .show();
//    }
//
//    public static void showAlertDialogWithInput(Context context, String title, String message, String hint, String prefill, int inputType, MaterialDialog.InputCallback inputCallback, String positiveButtonText, String negativeButtonText, MaterialDialog.SingleButtonCallback negativeButtonCallback) {
//        new MaterialDialog.Builder(context)
//                .title(title)
//                .content(message)
//                .inputType(inputType)
//                .input(hint, prefill, inputCallback)
//                .cancelable(false)
//                .positiveText(positiveButtonText)
//                .negativeText(negativeButtonText)
//                .onNegative(negativeButtonCallback)
//                .show();
//    }
//
//    public static void showAlertDialogWithSelections(Context context, int title, ArrayList<String> selectionArrayList, DialogInterface.OnClickListener onClickListener) {
//        CharSequence[] tmpSelections = new CharSequence[selectionArrayList.size()];
//        for (int i = 0; i < selectionArrayList.size(); i++) {
//            tmpSelections[i] = selectionArrayList.get(i);
//        }
//
//        final CharSequence[] selections = tmpSelections;
//
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setItems(selections, onClickListener)
//                .create()
//                .show();
//    }
//
//
//    public static void showAlertDialogWithSelections(Context context, ArrayList<String> selectionArrayList, DialogInterface.OnClickListener onClickListener) {
//        CharSequence[] tmpSelections = new CharSequence[selectionArrayList.size()];
//        for (int i = 0; i < selectionArrayList.size(); i++) {
//            tmpSelections[i] = selectionArrayList.get(i);
//        }
//
//        final CharSequence[] selections = tmpSelections;
//
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setItems(selections, onClickListener)
//                .create()
//                .show();
//    }
//
//    public static void showYesNoDialog(Context context, int title, int message, int positiveButtonText, int negativeButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(positiveButtonText, positiveListener)
//                .setNegativeButton(negativeButtonText, negativeListener)
//                .create()
//                .show();
//    }
//
//    public static void showYesNoDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(positiveButtonText, positiveListener)
//                .setNegativeButton(negativeButtonText, negativeListener)
//                .create()
//                .show();
//    }
//
//
//    public static void showYesNoDialog(Context context, int title, int positiveButtonText, int negativeButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setPositiveButton(positiveButtonText, positiveListener)
//                .setNegativeButton(negativeButtonText, negativeListener)
//                .create()
//                .show();
//    }
//
//    public static void showYesNoDialog(Context context, String title, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setPositiveButton(positiveButtonText, positiveListener)
//                .setNegativeButton(negativeButtonText, negativeListener)
//                .create()
//                .show();
//    }
//
//    public static void showYesNoNeutralDialog(Context context, String title, String positiveButtonText, String negativeButtonText, String neutralButtonText, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, DialogInterface.OnClickListener neutralListener) {
//        AlertDialogWrapper.Builder builder = new AlertDialogWrapper.Builder(context);
//        builder.setTitle(title)
//                .setPositiveButton(positiveButtonText, positiveListener)
//                // this swap between negative and neutral button is intentional
//                .setNegativeButton(neutralButtonText, neutralListener)
//                .setNeutralButton(negativeButtonText, negativeListener)
//                .create()
//                .show();
//    }
//}
//
