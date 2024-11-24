package pvt.sanae.notepad.manager;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.widget.TextView;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.util.ToastUtil;

public class FooterManager extends ActivityManager<MainActivity> {

    private LinearLayout footer;
    private TextView footerItem_cursor;
    private TextView footerItem_wordCnt;

    public FooterManager(MainActivity ac) {
        super(ac);
    }

    public void setVisibility(int visibility) {
        footer.setVisibility(visibility);
    }

    public int getVisibility() {
        return footer.getVisibility();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void setFooter(int cursorRow, int cursorColum, int worldCnt, int selected) {
        footerItem_cursor.setText(String.format("行 %d，列 %d", cursorRow, cursorColum));
        if (selected == 0) {
            footerItem_wordCnt.setText(worldCnt + " 个字符");
        } else {
            footerItem_wordCnt.setText(selected + " 个字符，共 " + worldCnt + " 个");
        }
    }

    @Override
    protected void initView() {
        footer = ac.findViewById(R.id.footer);
        footerItem_cursor = ac.findViewById(R.id.footerItem_cursor);
        footerItem_wordCnt = ac.findViewById(R.id.footerItem_wordCnt);
    }

    @Override
    protected void bindListener() {
        ac.findViewById(R.id.footerItem_charset).setOnClickListener(v -> {
            // TODO
            ToastUtil.showShort(ac, "Not support yet.");
        });
    }
}
