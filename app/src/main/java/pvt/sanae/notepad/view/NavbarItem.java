package pvt.sanae.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.util.StorageUtil;

public class NavbarItem extends FrameLayout {

    private final MainActivity ac;
    private final TextView text;
    private final TextView close;

    public NavbarItem(@NonNull Context context) {
        this(context, null);
    }

    public NavbarItem(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_navbar_item, this);
        text = view.findViewById(R.id.text);
        ac = (MainActivity) context;

        if (attrs != null) {
            try (TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.NavbarItem)) {
                String s = t.getString(R.styleable.NavbarItem_text);
                setText(s);
            }
        }

        close = view.findViewById(R.id.close);

        close.setOnClickListener(v ->
                StorageUtil.saveContent(ac, ac.mNavbar.indexOfItem(this), true));
        setOnClickListener(v -> ac.mPage.setCurrentPosition(ac.mNavbar.indexOfItem(this)));
    }

    public void setHintUnsaved() {
        close.setText(ac.getResources().getText(R.string.oplus));
    }

    public void setHintSaved() {
        close.setText(ac.getResources().getText(R.string.times));
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
