package com.bindlesoft.calculatorsimulator;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static int ADDITION = 1;
    private static int SUBTRACTION = 2;
    private static int MULTIPLICATION = 3;
    private static int DIVISION = 4;
    private static int XROOTY = 1;
    private static int NONE = 0;
    private static String STARTUPDISPLAY = "0";
    private String currentDisplay = STARTUPDISPLAY;
    private int operation = NONE;
    private int prevOperation = NONE;
    private int twoVarOp = NONE;
    private String parPrevX = "";
    private String parPrevY = "";
    private int parPrevOperation = NONE;
    private String prevY = "";
    private String x = "";
    private String y = "";
    private String stored = "";
    private boolean result = false;
    private boolean parenthesis = false;
    private boolean audio = true;
    private boolean radians = false;
    private boolean alt = false;
    private boolean twoVars = false;

    private int height = 0;
    private int width = 0;

    private boolean numberInput = false;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceSettings = getPreferences(0);
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putBoolean("radOrDeg",true);
        preferenceEditor.commit();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void factorial(View view) {

        double display = Double.parseDouble(currentDisplay);

        for (int i = (int) (display - 1); i > 1; i--) {
            display = display * i;
        }
        currentDisplay = String.valueOf(display);
        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }

    public void SetPOV(View view) {
        Intent intent = new Intent(this,pov.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Calculator Main",

                Uri.parse("http://host/path"),

                Uri.parse("android-app://com.bindlesoft.calculatorsimulator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        //updateDisplay();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Calculator Main",

                Uri.parse("http://host/path"),

                Uri.parse("android-app://com.bindlesoft.calculatorsimulator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void inverse(View view) {
        double display = Double.parseDouble(currentDisplay);

        currentDisplay = String.valueOf(1 / display);
        updateDisplay(view);
        result = true;
        prevOperation = NONE;
        operation = NONE;
    }

    public void toggleSound(View view) {
        audio = !audio;
    }

    public void parenthesis(View view) {
        if(parenthesis) {
            findViewById(R.id.butParenthesis).setBackgroundResource(R.drawable.calc_sim_main_parenthesis);
            if(x.equals("")) {
                x = "0";
            }
            equals(view);
            parenthesis = false;
            x = parPrevX;
            y = parPrevY;
            operation = parPrevOperation;
        } else {
            findViewById(R.id.butParenthesis).setBackgroundResource(R.drawable.calc_sim_main_parenthesis_enabled);
            parenthesis = true;
            if(x.equals("")) {
                parPrevX = "0";
            } else {
                parPrevX = x;
                parPrevOperation = operation;
                parPrevY = y;
                x = "";
                y = "";
                operation = NONE;
                result = true;
            }
        }
    }

    public void backspace(View view) {
        if (currentDisplay.length() != 1 && !(currentDisplay.contains("-") && currentDisplay.length() == 2)) {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
        } else {
            currentDisplay = STARTUPDISPLAY;
        }
        updateDisplay(view);

    }

    public void squareRoot(View view) {
        double display = Double.parseDouble(currentDisplay);
        if(alt) {
            if(twoVars) {

            } else {
                twoVars = true;
                x = currentDisplay;
                prevOperation = NONE;
                operation = NONE;
                result = true;
                twoVarOp = XROOTY;
            }

        } else {
            currentDisplay = String.valueOf(Math.sqrt(display));
            result = true;
            prevOperation = NONE;
            operation = NONE;
        }

        updateDisplay(view);

    }



    public void log(View view) {
        double display = Double.parseDouble(currentDisplay);

        currentDisplay = String.valueOf(Math.log10(display));
        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }

    public void clearDisplay(View view) {
        currentDisplay = STARTUPDISPLAY;
        updateDisplay(view);
        result = false;
        operation = NONE;
        prevOperation = NONE;
    }

    public void equals(View view) {
        double xvalue, yvalue;
        if(twoVars) {
            if(twoVarOp == XROOTY) {
                xvalue = Double.parseDouble(x);
                double display = Double.parseDouble(currentDisplay);
                currentDisplay = String.valueOf(Math.pow(xvalue,1/display));
                prevOperation = NONE;
                operation = NONE;
                result = true;
                twoVars = false;
                updateDisplay(view);
            }
        } else {
            if (!x.equals("")) {
                xvalue = Double.parseDouble(x);
                if (!y.equals("")) {
                    yvalue = Double.parseDouble(y);
                } else {
                    yvalue = Double.parseDouble(currentDisplay);
                    y = currentDisplay;
                }

                if (operation == ADDITION) {
                    currentDisplay = String.valueOf(xvalue + yvalue);
                    prevOperation = ADDITION;
                    prevY = y;
                } else if (operation == SUBTRACTION) {
                    currentDisplay = String.valueOf(xvalue - yvalue);
                    prevOperation = SUBTRACTION;
                    prevY = y;
                } else if (operation == MULTIPLICATION) {
                    currentDisplay = String.valueOf(xvalue * yvalue);
                    prevOperation = MULTIPLICATION;
                    prevY = y;
                } else if (operation == DIVISION) {
                    currentDisplay = String.valueOf(xvalue / yvalue);
                    prevOperation = DIVISION;
                    prevY = y;
                } else if (prevOperation == ADDITION) {
                    yvalue = Double.parseDouble(prevY);
                    currentDisplay = String.valueOf(xvalue + yvalue);

                } else if (prevOperation == SUBTRACTION) {
                    yvalue = Double.parseDouble(prevY);
                    currentDisplay = String.valueOf(xvalue - yvalue);

                } else if (prevOperation == MULTIPLICATION) {
                    yvalue = Double.parseDouble(prevY);
                    currentDisplay = String.valueOf(xvalue * yvalue);

                } else if (prevOperation == DIVISION) {
                    yvalue = Double.parseDouble(prevY);
                    currentDisplay = String.valueOf(xvalue / yvalue);

                }
                result = true;

                x = currentDisplay;
                y = "";
                operation = NONE;
                updateDisplay(view);
            }
        }


    }

    public void decimal(View view) {
        if (currentDisplay.equals(STARTUPDISPLAY)) {
            currentDisplay = "0.";
        } else {
            if (result) {
                currentDisplay = "0.";
                result = false;
            } else if (!currentDisplay.contains(".")) {
                currentDisplay = currentDisplay + ".";
            }
        }
        updateDisplay(view);
    }



    public void opposite(View view) {
        if (!currentDisplay.equals(STARTUPDISPLAY)) {
            if (currentDisplay.contains("-")) {
                currentDisplay = currentDisplay.replace('-', ' ');
                currentDisplay = currentDisplay.trim();
            } else {
                currentDisplay = "-" + currentDisplay;
            }
        }
        result = true;
        operation = NONE;
        updateDisplay(view);

    }

    public void alt(View view) {

        if(alt) {
            findViewById(R.id.butTan).setBackgroundResource(R.drawable.calc_sim_main_tan);
            findViewById(R.id.butCos).setBackgroundResource(R.drawable.calc_sim_main_cos);
            findViewById(R.id.butSin).setBackgroundResource(R.drawable.calc_sim_main_sin);
            findViewById(R.id.butExponential).setBackgroundResource(R.drawable.calc_sim_main_exponential);
            findViewById(R.id.butSqrRoot).setBackgroundResource(R.drawable.calc_sim_main_squareroot);
            alt = false;
        } else {
            findViewById(R.id.butTan).setBackgroundResource(R.drawable.calc_sim_main_inverse_tan);
            findViewById(R.id.butCos).setBackgroundResource(R.drawable.calc_sim_main_inverse_cosin);
            findViewById(R.id.butSin).setBackgroundResource(R.drawable.calc_sim_main_inverse_sin);
            findViewById(R.id.butExponential).setBackgroundResource(R.drawable.calc_sim_main_ln);
            findViewById(R.id.butSqrRoot).setBackgroundResource(R.drawable.calc_sim_main_root_x);

            alt = true;
        }

    }

    public void plus(View view) {
        processOperation(view);
        operation = ADDITION;

    }
    public void checkRadians() {
        radians = preferenceSettings.getBoolean("radOrDeg", true);

    }
    public void processOperation(View view) {
        if (operation == NONE) {
            x = currentDisplay;
            result = true;
        } else if (!x.equals("") && !result) {
            equals(view);
        } else {
            x = currentDisplay;
            result = true;
        }
    }

    public void addNumber(String number) {
        if (currentDisplay.equals(STARTUPDISPLAY)) {
            currentDisplay = number;
        } else {
            if (result) {
                currentDisplay = number;
                result = false;
            } else {
                currentDisplay = currentDisplay + number;
            }
        }

    }


    private void updateDisplay(View view) {

        TextView display = ((TextView) findViewById(R.id.mainDisplay));
        if (currentDisplay.equals("0.0")) {
            currentDisplay = STARTUPDISPLAY + " ";
            display.setText(currentDisplay);
        } else if (currentDisplay.length() > 2 && currentDisplay.substring(currentDisplay.length() - 2, currentDisplay.length()).equals(".0")) {

            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 2);
            display.setText(currentDisplay);
        } else if(currentDisplay.contains("E")) {
            int edisplay = currentDisplay.lastIndexOf("E");
            int eNeeded = currentDisplay.length() - edisplay;
            if(currentDisplay.length() > 13) {
                display.setText(currentDisplay.substring(0,13-eNeeded) + currentDisplay.substring(edisplay,currentDisplay.length()));
            } else {
                display.setText(currentDisplay);
            }

        } else if(currentDisplay.length() > 12) {
            display.setText(currentDisplay.substring(0,12) + " ");
        } else {
            display.setText(currentDisplay);
        }


    }

    public void store(View view) {
        stored = currentDisplay;
    }

    public void minus(View view) {
        processOperation(view);
        operation = SUBTRACTION;
    }


    public void recall(View view) {
        if (!stored.equals("")) {
            currentDisplay = stored;
        }
        updateDisplay(view);
    }

    public void multiply(View view) {
        processOperation(view);

        operation = MULTIPLICATION;
    }

    public void pi(View view) {
        currentDisplay = String.valueOf(Math.PI);
        updateDisplay(view);
    }

    public void divide(View view) {
        processOperation(view);
        operation = DIVISION;
    }

    public void tan(View view) {
        checkRadians();
        double display = Double.parseDouble(currentDisplay);
        if(radians) {
            if(alt) {
                currentDisplay = String.valueOf(Math.atan(display));
            } else {
                currentDisplay = String.valueOf(Math.tan(display));
            }

        } else {
            display = (Math.PI/180) * display;
            if(alt) {
                currentDisplay = String.valueOf(Math.atan(display));
            } else {
                currentDisplay = String.valueOf(Math.tan(display));
            }
        }

        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }

    public void cos(View view) {
        double display = Double.parseDouble(currentDisplay);
        checkRadians();
        if(radians) {
            if(alt) {
                currentDisplay = String.valueOf(Math.acos(display));
            } else {
                currentDisplay = String.valueOf(Math.cos(display));
            }
        } else {
            display = (Math.PI/180) * display;
            if(alt) {
                currentDisplay = String.valueOf(Math.acos(display));
            } else {
                currentDisplay = String.valueOf(Math.cos(display));
            }
        }
        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }

    public void sin(View view) {
        double display = Double.parseDouble(currentDisplay);
        checkRadians();
        if(radians) {
            if(alt) {
                currentDisplay = String.valueOf(Math.asin(display));
            } else {
                currentDisplay = String.valueOf(Math.sin(display));
            }
        } else {
            display = (Math.PI/180) * display;
            if(alt) {
                currentDisplay = String.valueOf(Math.asin(display));
            } else {
                currentDisplay = String.valueOf(Math.sin(display));
            }
        }
        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }

    public void square(View view) {
        double display = Double.parseDouble(currentDisplay);

        currentDisplay = String.valueOf(display * display);
        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }


    public void three(View view) {
        addNumber("3");

        updateDisplay(view);
    }

    public void two(View view) {
        addNumber("2");
        updateDisplay(view);
    }

    public void one(View view) {
        addNumber("1");
        updateDisplay(view);
    }

    public void six(View view) {
        addNumber("6");
        updateDisplay(view);
    }

    public void five(View view) {
        addNumber("5");
        updateDisplay(view);
    }

    public void four(View view) {
        addNumber("4");
        updateDisplay(view);
    }
    public void nine(View view) {
        addNumber("9");
        updateDisplay(view);
    }

    public void eight(View view) {
        addNumber("8");
        updateDisplay(view);
    }

    public void seven(View view) {
        addNumber("7");
        updateDisplay(view);
    }
    public void zero(View view) {
        addNumber("0");
        updateDisplay(view);
    }

    public void options(View view) {
        Intent intent = new Intent(this,settings.class);
        startActivity(intent);
    }

    public void exponential(View view) {
        double display = Double.parseDouble(currentDisplay);
        if(alt) {
            currentDisplay = String.valueOf(Math.log(display));
        } else {
            currentDisplay = String.valueOf(Math.exp(display));
        }

        updateDisplay(view);
        result = true;
        operation = NONE;
        prevOperation = NONE;
    }
}