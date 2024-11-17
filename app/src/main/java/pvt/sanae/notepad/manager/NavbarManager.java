package pvt.sanae.notepad.manager;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.fragment.ContentFragment;
import pvt.sanae.notepad.view.NavbarItem;

public class NavbarManager extends ActivityManager<MainActivity> {

    private LinearLayout navbar;

    public NavbarManager(@NonNull MainActivity ac) {
        super(ac);
    }

    /**
     * 该方法由 PageManager 负责，一般不需要直接调用
     */
    void select(int position) {
        if (position < 0 || position >= getSize()) return;
        if (navbar.getChildAt(position).isSelected()) return;
        clearSelectedState();
        navbar.getChildAt(position).setSelected(true);
    }

    /**
     * 该方法由 PageManager 负责，一般不需要直接调用
     */
    void addItem(NavbarItem item) {
        navbar.addView(item);
    }

    /**
     * 该方法由 PageManager 负责，一般不需要直接调用
     */
    void removeItem(int position) {
        navbar.removeViewAt(position);
        if (position < getSize()) {
            select(position);
        }
    }

    public int indexOfItem(NavbarItem item) {
        return navbar.indexOfChild(item);
    }

    public void setCurrentText(String text) {
        NavbarItem item = (NavbarItem) navbar.getChildAt(ac.mPage.getCurrentPosition());
        item.setText(text);
    }

    public int getSize() {
        return navbar.getChildCount();
    }

    private void clearSelectedState() {
        for (int i = 0; i < getSize(); i++) {
            navbar.getChildAt(i).setSelected(false);
        }
    }

    @Override
    protected void initView() {
        navbar = ac.findViewById(R.id.navbar);
    }

    @Override
    protected void bindListener() {
        ac.findViewById(R.id.navbarPlusBtn).setOnClickListener(v -> {
            ac.mPage.addPage(new ContentFragment());
            ac.mPage.setCurrentPosition(getSize() - 1);
        });
    }
}
