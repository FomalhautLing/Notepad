package pvt.sanae.notepad.menu;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import pvt.sanae.notepad.R;
import pvt.sanae.notepad.util.ToastUtil;

public class ToolbarMenu extends ListPopupWindow {

    private final Context context;

    public ToolbarMenu(@NonNull Context context, View anchor) {
        super(context);
        this.context = context;

        setBackgroundDrawable(AppCompatResources.getDrawable(context, R.color.menuBackground));
        setWidth(context.getResources().getDimensionPixelSize(R.dimen.menuWidth));
        setHeight(ListPopupWindow.WRAP_CONTENT);
        setAnchorView(anchor);
        setModal(true);
    }

    public void setData(String[] data) {
        setAdapter(new ArrayAdapter<>(context, R.layout.menu_item, data));
        setOnItemClickListener((parent, view, position, id) -> {
            ToastUtil.showShort(context, data[position]);
        });
    }
}
