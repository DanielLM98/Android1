package com.iluminaapp.micalculadora;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etInput)
    EditText etInput;
    @BindView(R.id.contentMain)
    RelativeLayout contentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configEditText();
    }

    private void configEditText() {
        etInput.setOnClickListener(view -> {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (input != null) {
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    @OnClick({R.id.btnSeven, R.id.btnFour, R.id.btnOne, R.id.btnEight, R.id.btnFive, R.id.btnTwo, R.id.btnNine, R.id.btnSix, R.id.btnThree, R.id.btnPoint, R.id.btnCero})
    public void onViewClicked(View view) {
        final String valStr = ((Button) view).getText().toString();
        switch (view.getId()) {

            case R.id.btnCero:
            case R.id.btnOne:
            case R.id.btnTwo:
            case R.id.btnThree:
            case R.id.btnFour:
            case R.id.btnFive:
            case R.id.btnSix:
            case R.id.btnSeven:
            case R.id.btnEight:
            case R.id.btnNine:
                etInput.getText().append(valStr);
                break;
            case R.id.btnPoint:
                final String operacion = etInput.getText().toString();
                final String operador = Metodos.getOperator(operacion);
                final int count = operacion.length() - operacion.replace(".", "").length();

                if (!operacion.contains(Constantes.POINT) || (count < 2 && (!operador.equals(Constantes.OPERATOR_NULL)))) {
                    etInput.getText().append(valStr);
                }
                break;
        }
    }

    @OnClick({R.id.btnClear, R.id.btnDiv, R.id.btnMulti, R.id.btnSum, R.id.btnRes, R.id.bntResult})
    public void onClickControls(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                etInput.setText("");
                break;
            case R.id.btnDiv:
            case R.id.btnMulti:
            case R.id.btnSum:
            case R.id.btnRes:
                resolve(false);
                final String operador = ((Button)view).getText().toString();
                final String operacion = etInput.getText().toString();
                final String ultimoCaracter =  operacion.isEmpty()? "": operacion.substring(operacion.length() -1);
                if(operador.equals(Constantes.OPERATOR_SUB)){
                    if(operacion.isEmpty() ||
                            (!(ultimoCaracter).equals(Constantes.OPERATOR_SUB)) &&
                            !(ultimoCaracter.equals(Constantes.POINT))){
                        etInput.getText().append(operador);
                    }
                } else {
                    if(!operacion.isEmpty() &&
                            !(ultimoCaracter.equals(Constantes.OPERATOR_SUB)) &&
                            !(ultimoCaracter.equals(Constantes.POINT))){
                        etInput.getText().append(operador);
                    }
                }
                break;
            case R.id.bntResult:
                resolve(true);
                break;
        }
    }

    private void resolve(boolean fromResult) {

    }
}
