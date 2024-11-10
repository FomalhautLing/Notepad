package pvt.sanae.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;

public class TabItem extends FrameLayout {

    private final TextView title;

    public TabItem(@NonNull Context context) {
        this(context, null);
    }

    public TabItem(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_tab_item, this);
        title = view.findViewById(R.id.title);

        if (attrs != null) {
            try (TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.TabItem)) {
                String title = t.getString(R.styleable.TabItem_text);
                setTitle(title);
            }
        }

        view.findViewById(R.id.close).setOnClickListener(v -> {
            LinearLayout parent = (LinearLayout) getParent();
            ((MainActivity) context).removeTab(parent.indexOfChild(this));
        });
        setOnClickListener(v -> {
            LinearLayout parent = (LinearLayout) getParent();
            ((MainActivity) context).setCurrentPosition(parent.indexOfChild(this));
        });
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

}
