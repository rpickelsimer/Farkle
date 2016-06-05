package project.sk8pickel.farkle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import project.sk8pickel.model.Scorecard;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment implements OnUserInputListener.OnInputEnteredListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton btnAdd;
    private OnFragmentInteractionListener mListener;
    
    private Scorecard scorecard;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        
        // TODO: create scorecard from bundle if available
        scorecard = new Scorecard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        btnAdd = (ImageButton) view.findViewById(R.id.btn_add_player);
        btnAdd.setOnClickListener(new OnUserInputListener(this, OnUserInputListener.NAME_MESSAGE));

        return view;
    }
    
    public void addTableRow() {
        int i = scorecard.getNumPlayers() - 1;      //used as an index for scorecard

        if (getView() != null) {
            // make a table row and add two headings
            TableLayout tl = (TableLayout) getView().findViewById(R.id.main_table);
            TableRow tr = new TableRow(getContext());
            tr.setId(i);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView label_name = new TextView(getContext());
            label_name.setId(i + 10);   // max of ten players
            label_name.setText(scorecard.getName(i));
            //label_name.setPadding(5, 5, 5, 5);
            tr.addView(label_name);// add the column to the table row here

            TextView label_score = new TextView(getContext());
            label_score.setId(i + 20);// define id that must be unique
            label_score.setText(Integer.toString(scorecard.getScore(i))); // set the text for the header
            //label_score.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr.addView(label_score); // add the column to the table row here

            tr.setOnClickListener(new OnUserInputListener(this, OnUserInputListener.POINTS_MESSAGE));
            tr.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //return false; // leads to further processing - onClick()?

                    return true;
                }
            });
            /**tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scorecard.incrementScore(v.getId(), 10);    // 10 is a test value, get value from user with same dialog as name dialog
                    TextView tv = (TextView) v.findViewById(v.getId() + 20);
                    tv.setText(Integer.toString(scorecard.getScore(v.getId())));
                }
            });/**/

            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNameSelected(String name) {
        scorecard.addName(name);
        addTableRow();
    }

    @Override
    public void onPointsAdded(int id, int points) {
        boolean gameOver = scorecard.incrementScore(id, points);
        TextView tv = (TextView) getView().findViewById(id + 20);
        tv.setText(Integer.toString(scorecard.getScore(id)));

        if (gameOver) {
            //TODO: bundle scorecard and swap to leader board fragment
            //display in short period of time
            Toast.makeText(getContext(), "Winner!", Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
