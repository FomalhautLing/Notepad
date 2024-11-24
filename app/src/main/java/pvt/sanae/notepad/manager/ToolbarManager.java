package pvt.sanae.notepad.manager;

import android.content.Intent;
import android.view.View;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.SettingActivity;
import pvt.sanae.notepad.menu.ToolbarMenu;
import pvt.sanae.notepad.util.StorageUtil;
import pvt.sanae.notepad.view.NavbarItem;

public class ToolbarManager extends ActivityManager<MainActivity> implements View.OnClickListener {

    private static final String[] fileMenu = new String[]{
            "新建标签页", "打开", "保存", "另存为", "全部保存", "退出"
    };

    private static final String[] editMenu = new String[]{
            "查找", "查找上一个", "查找下一个", "替换", "转到", "全选"
    };

    private static final String[] viewMenu = new String[]{
            "状态栏", "自动换行"
    };

    public ToolbarManager(MainActivity ac) {
        super(ac);
    }

    @Override
    protected void bindListener() {
        ac.findViewById(R.id.toolItem_file).setOnClickListener(this);
        ac.findViewById(R.id.toolItem_edit).setOnClickListener(this);
        ac.findViewById(R.id.toolItem_view).setOnClickListener(this);
        ac.findViewById(R.id.settingBtn).setOnClickListener(v -> {
            Intent intent = new Intent(ac, SettingActivity.class);
            ac.startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        ToolbarMenu menu = new ToolbarMenu(ac, v);
        if (v.getId() == R.id.toolItem_file) {
            menu.setData(fileMenu);
            menu.setOnItemClickListener((parent, view, position, id) -> {
                switch (position) {
                    case 0:
                        ac.mNavbar.addItem(new NavbarItem(ac));
                        break;
                    case 1:
                        break;
                    case 2:
                        int pos = ac.mPage.getCurrentPosition();
                        StorageUtil.saveContent(ac, pos, false);
                        break;
                }
                menu.dismiss();
            });
        } else if (v.getId() == R.id.toolItem_edit) {
            menu.setData(editMenu);
            menu.setOnItemClickListener((parent, view, position, id) -> {
                menu.dismiss();
            });
        } else if (v.getId() == R.id.toolItem_view) {
            menu.setData(viewMenu);
            menu.setOnItemClickListener((parent, view, position, id) -> {
                switch (position) {
                    case 0:
                        ac.mFooter.setVisibility(
                                ac.mFooter.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        break;
                    case 1:

                }
                menu.dismiss();
            });
        }
        menu.show();
    }
}
