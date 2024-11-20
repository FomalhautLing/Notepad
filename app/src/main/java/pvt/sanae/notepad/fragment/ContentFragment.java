package pvt.sanae.notepad.fragment;

import static android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import lombok.Getter;
import pvt.sanae.notepad.MainActivity;
import pvt.sanae.notepad.R;
import pvt.sanae.notepad.util.ConfigUtil;

public class ContentFragment extends Fragment {

    private MainActivity ac;
    private EditText textarea;
    private String initialText;
    @Getter private Uri uri;

    public static ContentFragment newInstance(Uri uri, String initialText) {
        Bundle args = new Bundle();
        args.putParcelable("uri", uri);
        args.putString("initialText", initialText);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            uri = args.getParcelable("uri");
            initialText = args.getString("initialText");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ac = (MainActivity) requireActivity();
        textarea = view.findViewById(R.id.textarea);
        textarea.setText(initialText);
        bindListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int type = textarea.getInputType();
        // 没用
        if (ConfigUtil.isSpellCheck()) {
            textarea.setInputType(type & ~TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        } else {
            textarea.setInputType(type | TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
    }

    public boolean contentNotChanged() {
        if (initialText == null) initialText = "";
        return initialText.equals(getContent());
    }

    public String getContent() {
        return textarea.getText().toString();
    }

    public String getFirstLine(int limit) {
        String content = getContent();
        int b = content.indexOf('\n');
        if (b == -1) return content;
        else if (limit == -1) return content.substring(0, b);
        else return content.substring(0, Math.min(limit, b));
    }

    private void bindListener() {
        textarea.setOnClickListener(v -> updateFooter());  // 第一次获取焦点时不触发点击事件
        textarea.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) updateFooter();
        });
        textarea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateFooter();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (uri == null) {
                    String l = getFirstLine(-1);
                    if (l.isEmpty()) ac.mNavbar.setCurrentText("无标题");
                    else ac.mNavbar.setCurrentText(l);
                }
            }
        });
    }

    private void updateFooter() {
        String content = getContent();
        int row = 1, colum = 1;
        for (int i = 0; i < textarea.getSelectionEnd(); i++) {
            if (content.charAt(i) == '\n') {
                row++;
                colum = 1;
            } else colum++;
        }
        ac.mFooter.setFooter(row, colum, content.length());
    }
}