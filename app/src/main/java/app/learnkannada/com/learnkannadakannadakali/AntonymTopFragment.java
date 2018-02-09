package app.learnkannada.com.learnkannadakannadakali;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import utils.AudioPlayer;
import utils.FindResource;

/**
 * Created by raggitha on 06-Feb-18.
 */

public class AntonymTopFragment extends Fragment{

    private TextView engWord, kanWord, engAntonym,kanAntonym;
    private CardView pCard, nCard;
    private ImageView happy, sad;
    private boolean clicked = false;

    private static String brown="#795548",
            grey = "#9e9e9e",
            lime = "#cddc39",
            pink = "#ff4081",
            red = "#e53935",
            purple = "#7b1fa2",
            orange = "#EF6C00",
            teal = "#00bfa5",
            blue = "#1e88ef";
    private static String[] colors = {pink,purple,red,lime,grey,brown,orange,teal,blue};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_antonym_top,container,false);
        engWord = (TextView)view.findViewById(R.id.antonym_eng_one);
        kanWord = (TextView)view.findViewById(R.id.antonym_kan_one);
        engAntonym = (TextView)view.findViewById(R.id.antonym_eng_two);
        kanAntonym = (TextView)view.findViewById(R.id.antonym_kan_two);
        happy = (ImageView) view.findViewById(R.id.happyID);
        sad = (ImageView) view.findViewById(R.id.sadID);

        pCard = (CardView) view.findViewById(R.id.fa_pCardID);
        nCard = (CardView) view.findViewById(R.id.fa_nCardID);

        pCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Positive",Toast.LENGTH_SHORT).show();
                String engVoice = engWord.getText().toString().replaceAll(" ","_").replaceAll("\\?","").trim();
                if(FindResource.rawResourceAvailable(view.getContext(),engVoice))
                    AudioPlayer.playAudio(view.getContext(),engVoice);
            }
        });

        nCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Negative",Toast.LENGTH_SHORT).show();
                String engVoice = engAntonym.getText().toString().replaceAll(" ","_").replaceAll("\\?","").trim();
                if(FindResource.rawResourceAvailable(view.getContext(),engVoice))
                    AudioPlayer.playAudio(view.getContext(),engVoice);
            }
        });

        engWord.setVisibility(View.INVISIBLE);
        kanWord.setVisibility(View.INVISIBLE);
        engAntonym.setVisibility(View.INVISIBLE);
        kanAntonym.setVisibility(View.INVISIBLE);
        return view;

    }

    public void updateValues(final String wordEng, final String antonymEng, String wordKan, String antonymKan){
        setColors();
        if(!clicked)
        {
            happy.setVisibility(View.INVISIBLE);
            sad.setVisibility(View.INVISIBLE);
        }
        engWord.setText(wordEng);
        kanWord.setText(wordKan);
        engAntonym.setText(antonymEng);
        kanAntonym.setText(antonymKan);
        engWord.setVisibility(View.VISIBLE);
        kanWord.setVisibility(View.VISIBLE);
        engAntonym.setVisibility(View.VISIBLE);
        kanAntonym.setVisibility(View.VISIBLE);
        clicked = true;
        /*leftSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(!engWord.getText().equals(null))
                    AudioPlayer.playAudio(getView().getContext(),engWord.getText().toString().toLowerCase()
                            .replaceAll(" ", "_")
                            .replaceAll("\\?","")
                            .replaceAll("\\(","_")
                            .replaceAll("\\)",""));
            }
        });

        rightSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(!engAntonym.getText().equals(null))
                    AudioPlayer.playAudio(getView().getContext(),engAntonym.getText().toString().toLowerCase()
                            .replaceAll(" ", "_")
                            .replaceAll("\\?","")
                            .replaceAll("\\(","_")
                            .replaceAll("\\)",""));
            }
        });*/
    }

    public void setColors()
    {
        final Random random = new Random();
        int i = random.nextInt(colors.length);
        pCard.setCardBackgroundColor(Color.parseColor(colors[i]));
        i=random.nextInt(colors.length);
        nCard.setCardBackgroundColor(Color.parseColor(colors[i]));

    }
}
