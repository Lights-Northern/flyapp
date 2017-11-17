package cn.wdsoft.flyapp.internal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import com.squareup.otto.Subscribe;

import cn.wdsoft.flyapp.R;
import cn.wdsoft.flyapp.airlink.RebootWiFiAirlinkView;
import cn.wdsoft.flyapp.airlink.SetGetWiFiLinkSSIDView;
import cn.wdsoft.flyapp.appactivation.AppActivationView;
import cn.wdsoft.flyapp.battery.PushBatteryDataView;
import cn.wdsoft.flyapp.battery.SetGetDischargeDayView;
import cn.wdsoft.flyapp.camera.FetchMediaView;
import cn.wdsoft.flyapp.camera.MediaPlaybackView;
import cn.wdsoft.flyapp.camera.PlaybackCommandsView;
import cn.wdsoft.flyapp.camera.PlaybackDownloadView;
import cn.wdsoft.flyapp.camera.PlaybackPushInfoView;
import cn.wdsoft.flyapp.camera.PushCameraDataView;
import cn.wdsoft.flyapp.camera.RecordVideoView;
import cn.wdsoft.flyapp.camera.SetGetISOView;
import cn.wdsoft.flyapp.camera.ShootSinglePhotoView;
import cn.wdsoft.flyapp.camera.VideoFeederView;
import cn.wdsoft.flyapp.flightcontroller.CompassCalibrationView;
import cn.wdsoft.flyapp.flightcontroller.FlightAssistantPushDataView;
import cn.wdsoft.flyapp.flightcontroller.FlightLimitationView;
import cn.wdsoft.flyapp.flightcontroller.OrientationModeView;
import cn.wdsoft.flyapp.flightcontroller.VirtualStickView;
import cn.wdsoft.flyapp.gimbal.GimbalCapabilityView;
import cn.wdsoft.flyapp.gimbal.MoveGimbalWithSpeedView;
import cn.wdsoft.flyapp.gimbal.PushGimbalDataView;
import cn.wdsoft.flyapp.internal.controller.DJISampleApplication;
import cn.wdsoft.flyapp.internal.controller.ExpandableListAdapter;
import cn.wdsoft.flyapp.internal.controller.MainActivity;
import cn.wdsoft.flyapp.internal.model.GroupHeader;
import cn.wdsoft.flyapp.internal.model.GroupItem;
import cn.wdsoft.flyapp.internal.model.ListItem;
import cn.wdsoft.flyapp.key.KeyedInterfaceView;
import cn.wdsoft.flyapp.missionoperator.WaypointMissionOperatorView;
import cn.wdsoft.flyapp.mobileremotecontroller.MobileRemoteControllerView;
import cn.wdsoft.flyapp.remotecontroller.PushRemoteControllerDataView;
import cn.wdsoft.flyapp.timeline.TimelineMissionControlView;

/**
 * This view is in charge of showing all the demos in a list.
 */

public class DemoListView extends FrameLayout {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expandableListView;

    public DemoListView(Context context) {
        this(context, null, 0);
    }

    public DemoListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.demo_list_view, this);

        // Build model for ListView
        ListItem.ListBuilder builder = new ListItem.ListBuilder();

        builder.addGroup(R.string.component_listview_sdk_4_1,
                         false,
                         new GroupItem(R.string.component_listview_app_activation,
                                       AppActivationView.class));


        builder.addGroup(R.string.component_listview_sdk_4_0,
                         false,
                         new GroupItem(R.string.component_listview_waypoint_mission_operator,
                                       WaypointMissionOperatorView.class),
                         new GroupItem(R.string.component_listview_keyed_interface, KeyedInterfaceView.class),
                         new GroupItem(R.string.component_listview_timeline_mission_control,
                     TimelineMissionControlView.class));

        builder.addGroup(R.string.component_listview_camera,
                         false,
                         new GroupItem(R.string.camera_listview_push_info, PushCameraDataView.class),
                         new GroupItem(R.string.camera_listview_iso, SetGetISOView.class),
                         new GroupItem(R.string.camera_listview_shoot_single_photo, ShootSinglePhotoView.class),
                         new GroupItem(R.string.camera_listview_record_video, RecordVideoView.class),
                         new GroupItem(R.string.camera_listview_playback_push_info, PlaybackPushInfoView.class),
                         new GroupItem(R.string.camera_listview_playback_command, PlaybackCommandsView.class),
                         new GroupItem(R.string.camera_listview_playback_download, PlaybackDownloadView.class),
                         new GroupItem(R.string.camera_listview_download_media, FetchMediaView.class),
                         new GroupItem(R.string.camera_listview_media_playback, MediaPlaybackView.class),
                         new GroupItem(R.string.component_listview_video_feeder, VideoFeederView.class));

        builder.addGroup(R.string.component_listview_gimbal,
                         false,
                         new GroupItem(R.string.gimbal_listview_push_info, PushGimbalDataView.class),
                         new GroupItem(R.string.gimbal_listview_rotate_gimbal, MoveGimbalWithSpeedView.class),
                         new GroupItem(R.string.gimbal_listview_gimbal_capability, GimbalCapabilityView.class));

        builder.addGroup(R.string.component_listview_battery,
                         false,
                         new GroupItem(R.string.battery_listview_push_info, PushBatteryDataView.class),
                         new GroupItem(R.string.battery_listview_set_get_discharge_day, SetGetDischargeDayView.class));

        builder.addGroup(R.string.component_listview_airlink,
                         false,
                         new GroupItem(R.string.airlink_listview_wifi_set_get_ssid, SetGetWiFiLinkSSIDView.class),
                         new GroupItem(R.string.airlink_listview_wifi_reboot_wifi, RebootWiFiAirlinkView.class),
                         new GroupItem(R.string.airlink_listview_lb_set_get_channel, SetGetWiFiLinkSSIDView.class));

        builder.addGroup(R.string.component_listview_flight_controller,
                         false,
                         new GroupItem(R.string.flight_controller_listview_compass_calibration,
                                       CompassCalibrationView.class),
                         new GroupItem(R.string.flight_controller_listview_flight_limitation,
                                       FlightLimitationView.class),
                         new GroupItem(R.string.flight_controller_listview_orientation_mode, OrientationModeView.class),
                         new GroupItem(R.string.flight_controller_listview_virtual_stick, VirtualStickView.class),
                         new GroupItem(R.string.flight_controller_listview_intelligent_flight_assistant,
                                       FlightAssistantPushDataView.class));

        builder.addGroup(R.string.component_listview_remote_controller,
                         false,
                         new GroupItem(R.string.remote_controller_listview_push_info,
                                       PushRemoteControllerDataView.class),
                         new GroupItem(R.string.component_listview_mobile_remote_controller,
                                       MobileRemoteControllerView.class));

        // Set-up ExpandableListView
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        listAdapter = new ExpandableListAdapter(context, builder.build());
        expandableListView.setAdapter(listAdapter);
        DJISampleApplication.getEventBus().register(this);
        expandAllGroupIfNeeded();
    }

    @Subscribe
    public void onSearchQueryEvent(MainActivity.SearchQueryEvent event) {
        listAdapter.filterData(event.getQuery());
        expandAllGroup();
    }

    /**
     * Expands all the group that has children
     */
    private void expandAllGroup() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

    /**
     * Expands all the group that has children
     */
    private void expandAllGroupIfNeeded() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            if (listAdapter.getGroup(i) instanceof GroupHeader
                && ((GroupHeader) listAdapter.getGroup(i)).shouldCollapseByDefault()) {
                expandableListView.collapseGroup(i);
            } else {
                expandableListView.expandGroup(i);
            }
        }
    }
}
