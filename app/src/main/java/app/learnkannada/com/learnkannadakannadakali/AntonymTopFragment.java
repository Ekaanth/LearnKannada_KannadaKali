package app.learnkannada.com.learnkannadakannadakali;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import utils.AudioPlayer;

/**
 * Created by raggitha on 06-Feb-18.
 */

public class AntonymTopFragment extends Fragment{

    private TextView engWord, kanWord, engAntonym,kanAntonym;
    private ImageView leftSpeaker, rightSpeaker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_antonym_top,container,false);
        engWord = (TextView)view.findViewById(R.id.antonym_eng_one);
        kanWord = (TextView)view.findViewById(R.id.antonym_kan_one);
        engAntonym = (TextView)view.findViewById(R.id.antonym_eng_two);
        kanAntonym = (TextView)view.findViewById(R.id.antonym_kan_two);
        leftSpeaker = (ImageView) view.findViewById(R.id.leftSpeakerID);
        rightSpeaker = (ImageView) view.findViewById(R.id.rightSpeakerID);

        engWord.setVisibility(View.INVISIBLE);
        kanWord.setVisibility(View.INVISIBLE);
        engAntonym.setVisibility(View.INVISIBLE);
        kanAntonym.setVisibility(View.INVISIBLE);
        return view;

    }

    public void updateValues(final String wordEng, final String antonymEng, String wordKan, String antonymKan){
        engWord.setText(wordEng);
        kanWord.setText(wordKan);
        engAntonym.setText(antonymEng);
        kanAntonym.setText(antonymKan);
        engWord.setVisibility(View.VISIBLE);
        kanWord.setVisibility(View.VISIBLE);
        engAntonym.setVisibility(View.VISIBLE);
        kanAntonym.setVisibility(View.VISIBLE);

        leftSpeaker.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}
