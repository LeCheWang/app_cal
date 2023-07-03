package com.android.demo1han;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvCal, tvResult;
    TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
    TextView tvC, tvSurplus, tvDi, tvMul, tvSub, tvPlus, tvCompute, tvDot;

    public String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refView();

        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "0";
                tvCal.setText(str);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "1";
                tvCal.setText(str);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "2";
                tvCal.setText(str);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "3";
                tvCal.setText(str);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "4";
                tvCal.setText(str);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "5";
                tvCal.setText(str);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "6";
                tvCal.setText(str);
            }
        });
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "7";
                tvCal.setText(str);
            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "8";
                tvCal.setText(str);
            }
        });
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "9";
                tvCal.setText(str);
            }
        });

        tvC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "";
                tvCal.setText(str);
                tvResult.setText(str);
            }
        });

        tvSurplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "%";
                tvCal.setText(str);
            }
        });
        tvDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "/";
                tvCal.setText(str);
            }
        });
        tvMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "*";
                tvCal.setText(str);
            }
        });
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "-";
                tvCal.setText(str);
            }
        });
        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += "+";
                tvCal.setText(str);
            }
        });
        tvDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str += ".";
                tvCal.setText(str);
            }
        });

        tvCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double result = eval(str);
                tvResult.setText(result+"");
            }
        });

    }

    private void refView() {
        tvCal = findViewById(R.id.tvCal);
        tvResult = findViewById(R.id.tvResult);

        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);

        tvC = findViewById(R.id.tvC);
        tvSurplus = findViewById(R.id.tvSurplus);
        tvDi = findViewById(R.id.tvDi);
        tvMul = findViewById(R.id.tvMul);
        tvSub = findViewById(R.id.tvSub);
        tvPlus = findViewById(R.id.tvPlus);
        tvCompute = findViewById(R.id.tvCompute);
        tvDot = findViewById(R.id.tvDot);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm(); // addition
                    } else if (eat('-')) {
                        x -= parseTerm(); // subtraction
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) {
                        x *= parseFactor(); // multiplication
                    } else if (eat('/')) {
                        x /= parseFactor(); // division
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor(); // unary plus
                }
                if (eat('-')) {
                    return -parseFactor(); // unary minus
                }
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) {
                        x = Math.sqrt(x);
                    } else if (func.equals("sin")) {
                        x = Math.sin(Math.toRadians(x));
                    } else if (func.equals("cos")) {
                        x = Math.cos(Math.toRadians(x));
                    } else if (func.equals("tan")) {
                        x = Math.tan(Math.toRadians(x));
                    } else {
                        throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) {
                    x = Math.pow(x, parseFactor()); // exponentiation
                }
                return x;
            }
        }.parse();
    }
}