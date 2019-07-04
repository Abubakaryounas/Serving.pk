package com.serving.servingpk.Loginpackage;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

public class ModifyNumber {
    public EditText rearrange(final EditText number)
    {

        Selection.setSelection(number.getText(), number.getText().length());


        number.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("92")){
                    number.setText("92");
                    Selection.setSelection(number.getText(), number.getText().length());

                }

            }
        });
        return number;
    }
}
