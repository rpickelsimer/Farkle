package project.sk8pickel.farkle;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.UserManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Ryan on 5/31/2016.
 */
public class OnUserInputListener implements View.OnClickListener {

    public final static String NAME_MESSAGE = "Enter player name:";     //move these two to calling class
    public final static String POINTS_MESSAGE = "Enter points:";

    private final String message;
    private OnInputEnteredListener mListener;
    //private DialogInterface.OnClickListener mOnClicklistener;

    /**
     * Constructor assigns listener interface and a prompt message
     * @param mListener - used to return input to calling class
     * @param message - message displayed in prompt
     */
    public OnUserInputListener(OnInputEnteredListener mListener, String message) {
        this.message = message;
        this.mListener = mListener;
    }

    /**
     * OnClick method inflates a dialog to retrieve user input
     * @param v
     */
    @Override
    public void onClick(View v) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(v.getContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                v.getContext());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // references to prompt UI cmoponents
        final TextView tvMessage = (TextView) promptsView.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        if (message.equals(POINTS_MESSAGE))
            userInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        // viewID used to get player and score index selected
        final int viewId = v.getId();

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    String input = userInput.getText().toString();

                                    // get user input and add a row to table
                                    // with player name and an initial score of zero
                                    if (message.equals(NAME_MESSAGE))
                                        mListener.onNameSelected(input);

                                    // add points entered to player score
                                    else if (message.equals(POINTS_MESSAGE))
                                        mListener.onPointsAdded(viewId, Integer.parseInt(input));
                                }
                                catch (NullPointerException ex) {
                                    //TODO: log and toast error messages
                                }
                                catch (NumberFormatException ex) {
                                    //TODO: log and toast error messages
                                }

                                //result.setText(userInput.getText()); //from original example
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public interface OnInputEnteredListener {
        public void onNameSelected(String name);
        public void onPointsAdded(int id, int points);
    }
}
