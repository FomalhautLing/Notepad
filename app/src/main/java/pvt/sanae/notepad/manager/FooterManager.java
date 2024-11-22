package pvt.sanae.notepad.manager;

import android.annotation.SuppressLint;
import android.widget.TextView;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;

public class FooterManager extends ActivityManager<MainActivity> {

    private TextView footerItem_cursor;
    private TextView footerItem_wordCnt;

    public FooterManager(MainActivity ac) {
        super(ac);
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
        footerItem_cursor = ac.findViewById(R.id.footerItem_cursor);
        footerItem_wordCnt = ac.findViewById(R.id.footerItem_wordCnt);
    }
}
