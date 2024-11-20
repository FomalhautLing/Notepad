package pvt.sanae.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import pvt.sanae.notepad.R;

public class SettingOption extends FrameLayout {

    private SwitchCompat switchBtn;

    public SettingOption(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_setting_option, this);

        if (attrs != null) {
            try (TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SettingOption)) {
                int rid = t.getResourceId(R.styleable.SettingOption_src, R.mipmap.setting_option_palette);
                ((ImageView) view.findViewById(R.id.icon)).setImageResource(rid);

                String title = t.getString(R.styleable.SettingOption_title);
                ((TextView) view.findViewById(R.id.title)).setText(title);

                String desc = t.getString(R.styleable.SettingOption_desc);
                ((TextView) view.findViewById(R.id.desc)).setText(desc);

                if (desc == null) {
                    var params = new GridLayout.LayoutParams();
                    params.rowSpec = GridLayout.spec(0, 2, 1);
                    params.columnSpec = GridLayout.spec(1, 1, 1);
                    params.setGravity(Gravity.CENTER_VERTICAL);
                    view.findViewById(R.id.title).setLayoutParams(params);
                }

                if (!t.getBoolean(R.styleable.SettingOption_with_switch_btn, false)) {
                    view.findViewById(R.id.switchBtn).setVisibility(GONE);
                    view.findViewById(R.id.switchBtnState).setVisibility(GONE);
                } else {
                    switchBtn = view.findViewById(R.id.switchBtn);
                    TextView state = view.findViewById(R.id.switchBtnState);
                    switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        state.setText(isChecked ? switchBtn.getTextOn() : switchBtn.getTextOff());
                    });
                }
            }
        }
    }

    public boolean isChecked() {
        return switchBtn != null && switchBtn.isChecked();

    }

    public void setChecked(boolean checked) {
        if (switchBtn == null) return;
        switchBtn.setChecked(checked);
    }
}
