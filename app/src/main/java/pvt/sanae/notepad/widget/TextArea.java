package pvt.sanae.notepad.widget;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class TextArea extends AppCompatEditText {

    private Runnable mOnSelectionChanged;

    public TextArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int getSelectionLength() {
        return getSelectionEnd() - getSelectionStart();
    }

    public void setOnSelectionChangedListener(Runnable r) {
        mOnSelectionChanged = r;
    }

    @NonNull
    @Override
    public String toString() {
        if (getText() == null) return "";
        return getText().toString();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (mOnSelectionChanged != null) {
            mOnSelectionChanged.run();
        }
    }
}
