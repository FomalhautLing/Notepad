package pvt.sanae.notepad.manager;

import android.content.Intent;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.SettingActivity;

public class ToolbarManager extends ActivityManager<MainActivity> {

    public ToolbarManager(MainActivity ac) {
        super(ac);
    }

    @Override
    protected void bindListener() {
        ac.findViewById(R.id.toolItem_file);
        ac.findViewById(R.id.toolItem_edit);
        ac.findViewById(R.id.toolItem_view);
        ac.findViewById(R.id.settingBtn).setOnClickListener(v -> {
            Intent intent = new Intent(ac, SettingActivity.class);
            ac.startActivity(intent);
        });
    }
}
