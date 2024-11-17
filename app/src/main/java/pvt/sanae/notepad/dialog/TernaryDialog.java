package pvt.sanae.notepad.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pvt.sanae.notepad.R;

public class TernaryDialog extends FullScreenDialog {

    private String title;
    private String desc;
    private String positiveButtonText;
    private String negativeButtonText;
    private String neutralButtonText;
    private Runnable positiveRunnable;
    private Runnable negativeRunnable;
    private Runnable neutralRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ternary, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.desc)).setText(desc);

        TextView positiveButton = view.findViewById(R.id.positive_btn);
        positiveButton.setText(positiveButtonText);
        positiveButton.setOnClickListener(v -> {
            if (positiveRunnable != null) positiveRunnable.run();
            dismiss();
        });

        TextView neutralButton = view.findViewById(R.id.neutral_btn);
        neutralButton.setText(neutralButtonText);
        neutralButton.setOnClickListener(v -> {
            if (neutralRunnable != null) neutralRunnable.run();
            dismiss();
        });

        TextView negativeButton = view.findViewById(R.id.negative_btn);
        negativeButton.setText(negativeButtonText);
        negativeButton.setOnClickListener(v -> {
            if (negativeRunnable != null) negativeRunnable.run();
            dismiss();
        });

        return view;
    }

    public TernaryDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TernaryDialog setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public TernaryDialog setPositiveButton(String text, Runnable runnable) {
        this.positiveButtonText = text;
        this.positiveRunnable = runnable;
        return this;
    }

    public TernaryDialog setNegativeButton(String text, Runnable runnable) {
        this.negativeButtonText = text;
        this.negativeRunnable = runnable;
        return this;
    }

    public TernaryDialog setNeutralButton(String text, Runnable runnable) {
        this.neutralButtonText = text;
        this.neutralRunnable = runnable;
        return this;
    }
}
