package cn.wdsoft.flyapp.camera;

import android.content.Context;

import cn.wdsoft.flyapp.R;
import cn.wdsoft.flyapp.internal.controller.DJISampleApplication;
import cn.wdsoft.flyapp.internal.utils.ModuleVerificationUtil;
import cn.wdsoft.flyapp.internal.utils.ToastUtils;
import cn.wdsoft.flyapp.internal.view.BasePushDataView;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.sdk.camera.PlaybackManager;

/**
 * Class for getting
 */
public class PlaybackPushInfoView extends BasePushDataView {
    public PlaybackPushInfoView(Context context) {
        super(context);
    }

    /**
     * Before the playback commands are sent to the aircraft, the camera work mode should be set
     * to playback mode.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setPlaybackStateCallback();
        if (ModuleVerificationUtil.isCameraModuleAvailable()) {
            DJISampleApplication.getProductInstance()
                                .getCamera()
                                .setMode(SettingsDefinitions.CameraMode.PLAYBACK,
                                         new CommonCallbacks.CompletionCallback() {
                                             @Override
                                             public void onResult(DJIError djiError) {

                                             }
                                         });
        }
        if (!ModuleVerificationUtil.isPlaybackAvailable()) {
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append("This product does not support Playback function");
            showStringBufferResult();
            ToastUtils.setResultToToast("Not support");
        }
    }

    // Set back to the default work mode.
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (ModuleVerificationUtil.isPlaybackAvailable()) {
            DJISampleApplication.getProductInstance().getCamera().getPlaybackManager().setPlaybackStateCallback(null);

            DJISampleApplication.getProductInstance()
                                .getCamera()
                                .setMode(SettingsDefinitions.CameraMode.SHOOT_PHOTO,
                                         new CommonCallbacks.CompletionCallback() {
                                             @Override
                                             public void onResult(DJIError djiError) {

                                             }
                                         });
        }
    }

    private void setPlaybackStateCallback() {
        if (ModuleVerificationUtil.isPlaybackAvailable()) {
            DJISampleApplication.getProductInstance()
                                .getCamera()
                                .getPlaybackManager()
                                .setPlaybackStateCallback(new PlaybackManager.PlaybackState.CallBack() {
                                    @Override
                                    public void onUpdate(PlaybackManager.PlaybackState djiCameraPlaybackState) {
                                        stringBuffer.delete(0, stringBuffer.length());

                                        stringBuffer.append("CurrentSelectedFileIndex: ")
                                                    .append(djiCameraPlaybackState.getCurrentSelectedFileIndex())
                                                    .append("\n");
                                        stringBuffer.append("MediaFileType: ")
                                                    .append(djiCameraPlaybackState.getFileType())
                                                    .append("\n");
                                        stringBuffer.append("NumberOfMediaFiles").
                                            append(djiCameraPlaybackState.getNumberOfMediaFiles()).append("\n");
                                        stringBuffer.append("PlaybackMode: ")
                                                    .append(djiCameraPlaybackState.getPlaybackMode())
                                                    .append("\n");
                                        stringBuffer.append("NumbersOfSelected: ")
                                                    .append(djiCameraPlaybackState.getSelectedFileCount())
                                                    .append("\n");

                                        showStringBufferResult();
                                    }
                                });
        }
    }

    @Override
    public int getDescription() {
        return R.string.camera_listview_playback_push_info_description;
    }
}
