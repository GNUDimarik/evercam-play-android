package io.evercam.androidapp.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import io.evercam.androidapp.CamerasActivity;
import io.evercam.androidapp.R;
import io.evercam.androidapp.sharing.SharingActivity;
import io.evercam.androidapp.sharing.SharingStatus;
import io.evercam.androidapp.tasks.CreatePresetTask;
import io.evercam.androidapp.video.VideoActivity;

public class CustomedDialog
{
    public final static String TAG = "CustomedDialog";
    /**
     * Helper method to show unexpected error dialog.
     */
    public static void showUnexpectedErrorDialog(Activity activity)
    {
        getStandardStyledDialog(activity, R.string.msg_error_occurred, R.string.msg_exception,
                new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }, null, R.string.ok, 0).show();
    }

    /**
     * The dialog that prompt to connect Internet, with listener.
     */
    public static AlertDialog getNoInternetDialog(final Activity activity,
                                                  DialogInterface.OnClickListener negativeistener)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setTitle(R.string.msg_network_not_connected)
                .setMessage(R.string.msg_try_network_again)
                .setCancelable(false).setPositiveButton(R.string.settings_capital,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton(R.string.notNow, negativeistener);
        AlertDialog alertDialog = dialogBuilder.create();
        return alertDialog;
    }

    /**
     * The single message dialog that contains title, a message, two buttons(Yes
     * & No) and two listeners.
     * <p/>
     * If int negativeButton == 0, it will be a dialog without negative button
     */
    private static AlertDialog getStandardStyledDialog(final Activity activity, int title,
                                                       int message,
                                                       DialogInterface.OnClickListener
                                                               positiveListener,
                                                       DialogInterface.OnClickListener
                                                               negativeListener,
                                                       int positiveButton, int negativeButton)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false).setPositiveButton(positiveButton, positiveListener);
        if(negativeButton != 0)
        {
            dialogBuilder.setNegativeButton(negativeButton, negativeListener);
        }
        return dialogBuilder.create();
    }

    public static AlertDialog getCanNotPlayDialog(final Activity activity,
                                                  DialogInterface.OnClickListener positiveListener)
    {
        return getStandardStyledDialog(activity, R.string.msg_unable_to_play, R.string
                .msg_please_check_camera, positiveListener, null, R.string.ok, 0);
    }

    /**
     * Return the styled dialog with title and message to ask for confirmation
     * to create camera.
     */
    public static AlertDialog getConfirmCreateDialog(Activity activity,
                                                     DialogInterface.OnClickListener
                                                             positiveListener,
                                                     DialogInterface.OnClickListener
                                                             negativeListener)
    {
        AlertDialog confirmCreateDialog = getStandardStyledDialog(activity,
                R.string.dialog_title_warning, R.string.msg_confirm_create, positiveListener,
                negativeListener, R.string.yes, R.string.no);
        return confirmCreateDialog;
    }

    public static AlertDialog getConfirmQuitFeedbackDialog(Activity activity,
                                                           DialogInterface.OnClickListener
                                                                   positiveListener)
    {
        AlertDialog confirmFeedbackDialog = getStandardStyledDialog(activity,
                R.string.dialog_title_warning, R.string.msg_confirm_quit_feedback,
                positiveListener, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }, R.string.yes, R.string.cancel);
        return confirmFeedbackDialog;
    }

    /**
     * The helper method to show Internet alert dialog and finish the activity.
     */
    public static void showInternetNotConnectDialog(final Activity activity)
    {
        CustomedDialog.getNoInternetDialog(activity, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                activity.finish();
            }
        }).show();
    }

    /**
     * The alert dialog with no title, but with a cancel button Used in account management.
     */
    public static AlertDialog getAlertDialogNoTitle(Context ctx, View view)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ctx);

        view.setPadding(14, 10, 5, 21);

        dialogBuilder.setView(view);
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static AlertDialog getConfirmLogoutDialog(Activity activity,
                                                     DialogInterface.OnClickListener listener)
    {
        AlertDialog confirmLogoutDialog = new AlertDialog.Builder(activity)

                .setMessage(R.string.msg_confirm_sign_out).setPositiveButton(R.string.yes,
                        listener).setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return confirmLogoutDialog;
    }

    public static AlertDialog getConfirmCancelScanDialog(Activity activity,
                                                         DialogInterface.OnClickListener listener)
    {
        AlertDialog confirmCancelDialog = new AlertDialog.Builder(activity)

                .setMessage(R.string.msg_confirm_cancel_scan).setPositiveButton(R.string.yes,
                        listener).setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return confirmCancelDialog;
    }

    public static AlertDialog getConfirmCancelAddCameraDialog(final Activity activity)
    {
        AlertDialog confirmCancelDialog = new AlertDialog.Builder(activity)

                .setMessage(R.string.msg_confirm_cancel_add_camera).setPositiveButton(R.string
                        .yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        activity.finish();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                }).create();
        return confirmCancelDialog;
    }

    public static AlertDialog getConfirmRemoveDialog(Activity activity,
                                                     DialogInterface.OnClickListener listener,
                                                     int message)
    {
        AlertDialog confirmLogoutDialog = new AlertDialog.Builder(activity)

                .setMessage(message).setPositiveButton(R.string.remove,
                        listener).setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return confirmLogoutDialog;
    }

    public static AlertDialog getConfirmDeleteDialog(Activity activity,
                                                     DialogInterface.OnClickListener listener,
                                                     int message)
    {
        AlertDialog confirmLogoutDialog = new AlertDialog.Builder(activity)

                .setMessage(message).setPositiveButton(R.string.delete,
                        listener).setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return confirmLogoutDialog;
    }

    /**
     * Return a pop up dialog that shows camera snapshot.
     *
     * @param drawable the image drawable returned to show in pop up dialog
     */
    public static AlertDialog getSnapshotDialog(Activity activity, Drawable drawable)
    {
        AlertDialog snapshotDialog = new AlertDialog.Builder(activity).create();
        LayoutInflater mInflater = LayoutInflater.from(activity);
        final View snapshotView = mInflater.inflate(R.layout.test_snapshot_dialog, null);
        ImageView snapshotImageView = (ImageView) snapshotView.findViewById(R.id
                .test_snapshot_image);
        snapshotImageView.setImageDrawable(drawable);
        snapshotDialog.setView(snapshotView);

        Window window = snapshotDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.y = -CamerasActivity.readScreenHeight(activity) / 9;
        window.setAttributes(layoutParams);
        return snapshotDialog;
    }

    /**
     * Return a pop up dialog that ask the user whether or not to save the snapshot
     */
    public static AlertDialog getConfirmSnapshotDialog(Activity activity, Bitmap bitmap,
                                                       DialogInterface.OnClickListener listener)
    {
        Builder snapshotDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater mInflater = LayoutInflater.from(activity);
        final View snapshotView = mInflater.inflate(R.layout.confirm_snapshot_dialog, null);
        ImageView snapshotImageView = (ImageView) snapshotView.findViewById(R.id
                .confirm_snapshot_image);
        snapshotImageView.setImageBitmap(bitmap);
        snapshotDialogBuilder.setView(snapshotView);
        snapshotDialogBuilder.setPositiveButton(activity.getString(R.string.save), listener);
        snapshotDialogBuilder.setNegativeButton(activity.getString(R.string.cancel),
                new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
        AlertDialog snapshotDialog = snapshotDialogBuilder.create();
        snapshotDialog.setCanceledOnTouchOutside(false);

        return snapshotDialog;
    }

    /**
     * A dialog without title, with a message and an 'OK' button
     *
     * @param message Message to show in the dialog
     */
    public static AlertDialog getMessageDialog(Activity activity, int message)
    {
        AlertDialog messageDialog = new AlertDialog.Builder(activity)

                .setMessage(message).setNegativeButton(R.string.ok,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return messageDialog;
    }

    /**
     * The standard alert dialog that ask the user for a yes or no choice
     * @param listener The callback that perform a option when user choose yes
     * @param message The message resource to show in the dialog
     * @return
     */
    public static AlertDialog getStandardAlertDialog(Activity activity,
                                                     DialogInterface.OnClickListener listener,
                                                     int message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)

                .setMessage(message).setPositiveButton(R.string.yes, listener).setNegativeButton
                        (R.string.no,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).create();
        return alertDialog;
    }

    /**
     * The prompt dialog that ask for a preset name
     */
    public static AlertDialog getCreatePresetDialog(final VideoActivity videoActivity, final String cameraId)
    {
        Builder dialogBuilder = new AlertDialog.Builder(videoActivity);
        LayoutInflater inflater = LayoutInflater.from(videoActivity);
        final View view = inflater.inflate(R.layout.create_preset_dialog_layout, null);
        final EditText editText = (EditText) view.findViewById(R.id.create_preset_edit_text);
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String presetName = editText.getText().toString();
                if(!presetName.isEmpty())
                {
                    new CreatePresetTask(videoActivity, cameraId, presetName)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, null);
        return dialogBuilder.create();
    }

    public static AlertDialog getShareStatusDialog(final SharingActivity.SharingListFragment fragment)
    {
        final Activity activity = fragment.getActivity();
        final CharSequence[] shareStatusItems = {activity.getString(R.string.sharing_status_public),
                                                activity.getString(R.string.sharing_status_link),
                                                activity.getString(R.string.sharing_status_specific_user)};
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setSingleChoiceItems(shareStatusItems, 0, null)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ListView listView = ((AlertDialog)dialog).getListView();
                        Object checkedItem = listView.getAdapter().getItem(listView
                                .getCheckedItemPosition());

                        SharingStatus status = new SharingStatus(checkedItem.toString(),
                                activity);

                        fragment.patchSharingStatusAndUpdateUi(status);
                    }
                })
                .setNegativeButton(R.string.cancel, null).create();
        return  alertDialog;
    }
}
