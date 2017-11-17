package cn.wdsoft.flyapp.gimbal;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.wdsoft.flyapp.R;
import cn.wdsoft.flyapp.internal.controller.DJISampleApplication;
import cn.wdsoft.flyapp.internal.utils.ModuleVerificationUtil;
import cn.wdsoft.flyapp.internal.view.BasePushDataView;
import dji.common.gimbal.GimbalState;

/**
 * Class for getting gimbal information.
 */
public class PushGimbalDataView extends BasePushDataView {

    public PushGimbalDataView(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (ModuleVerificationUtil.isGimbalModuleAvailable()) {
            DJISampleApplication.getProductInstance().getGimbal().setStateCallback(new GimbalState.Callback() {
                @Override
                public void onUpdate(@NonNull GimbalState gimbalState) {
                    stringBuffer.delete(0, stringBuffer.length());

                    stringBuffer.append("PitchInDegrees: ").
                        append(gimbalState.getAttitudeInDegrees().getPitch()).append("\n");
                    stringBuffer.append("RollInDegrees: ").
                        append(gimbalState.getAttitudeInDegrees().getRoll()).append("\n");
                    stringBuffer.append("YawInDegrees: ").
                        append(gimbalState.getAttitudeInDegrees().getYaw()).append("\n");

                    showStringBufferResult();
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (ModuleVerificationUtil.isGimbalModuleAvailable()) {
            DJISampleApplication.getProductInstance().getGimbal().setStateCallback(null);
        }
    }

    @Override
    public int getDescription() {
        return R.string.gimbal_listview_push_info;
    }
}
