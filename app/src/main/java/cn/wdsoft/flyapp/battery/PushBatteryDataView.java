package cn.wdsoft.flyapp.battery;

import android.content.Context;

import cn.wdsoft.flyapp.R;
import cn.wdsoft.flyapp.internal.controller.DJISampleApplication;
import cn.wdsoft.flyapp.internal.view.BasePushDataView;
import dji.common.battery.BatteryState;

/**
 * Class for getting the battery information.
 */
public class PushBatteryDataView extends BasePushDataView {
    public PushBatteryDataView(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        try {
            DJISampleApplication.getProductInstance().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState djiBatteryState) {
                    stringBuffer.delete(0, stringBuffer.length());

                    stringBuffer.append("BatteryEnergyRemainingPercent: ").
                        append(djiBatteryState.getChargeRemainingInPercent()).
                                    append("%\n");
                    stringBuffer.append("CurrentVoltage: ").
                        append(djiBatteryState.getVoltage()).append("mV\n");
                    stringBuffer.append("CurrentCurrent: ").
                        append(djiBatteryState.getCurrent()).append("mA\n");

                    showStringBufferResult();
                }
            });
        } catch (Exception ignored) {

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        try {
            DJISampleApplication.getProductInstance().getBattery().setStateCallback(null);
        } catch (Exception ignored) {

        }
    }

    @Override
    public int getDescription() {
        return R.string.battery_listview_push_info;
    }
}
