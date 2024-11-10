package pvt.sanae.notepad.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;

public class ContentFragment extends Fragment {

    private MainActivity ac;
    private EditText textArea;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return textArea.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_content, container, false);
        ac = (MainActivity) requireActivity();
        textArea = view.findViewById(R.id.textarea);
        textArea.setOnClickListener(v -> modifyInfo());
        textArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modifyInfo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        textArea.setText(content);
    }

    @Override
    public void onPause() {
        super.onPause();
        content = textArea.getText().toString();
    }

    private void modifyInfo() {
        String content = getContent();
        int row = 1, colum = 1;
        for (int i = 0; i < textArea.getSelectionEnd(); i++) {
            if (content.charAt(i) == '\n') {
                row++;
                colum = 1;
            }
            else colum++;
        }
        ac.setInfo(row, colum, content.length());
    }
}