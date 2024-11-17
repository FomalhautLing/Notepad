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
    public void setFooter(int cursorRow, int cursorColum, int worldCnt) {
        footerItem_cursor.setText(String.format("行 %d，列 %d", cursorRow, cursorColum));
        footerItem_wordCnt.setText(worldCnt + " 个字符");
    }

    @Override
    protected void initView() {
        footerItem_cursor = ac.findViewById(R.id.footerItem_cursor);
        footerItem_wordCnt = ac.findViewById(R.id.footerItem_wordCnt);
    }
}
