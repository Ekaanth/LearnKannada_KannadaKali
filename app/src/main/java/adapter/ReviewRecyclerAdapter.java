package adapter;

/**
 * Created by raggitha on 05-Apr-18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.learnkannada.com.learnkannadakannadakali.QuizActivity;
import app.learnkannada.com.learnkannadakannadakali.R;
import app.learnkannada.com.learnkannadakannadakali.model.Question;

/**
 * Created by raggitha on 01-Apr-18.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.mViewHolder>{
    private ArrayList<String> mListOfAnswers;
    private List<Question> mListOfQuestions;

    public ReviewRecyclerAdapter(ArrayList<String> listOfAnswers)
    {
        mListOfQuestions= QuizActivity.listOfQuestions;
        mListOfAnswers=listOfAnswers;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_review,parent,false);
        return (new mViewHolder(v));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {

        int solutionIndex = mListOfQuestions.get(position).getSolution() - 1;
        holder.question.setText(mListOfQuestions.get(position).getQuestion());
        holder.correctAnswer.setText(mListOfQuestions.get(position).getOptions()[solutionIndex]);
        holder.chosenAnswer.setText(mListOfAnswers.get(position));
        if(holder.correctAnswer.getText().toString().equals(holder.chosenAnswer.getText().toString()))
            holder.imageView.setImageResource(R.drawable.correct);
        else
            holder.imageView.setImageResource(R.drawable.wrong_icon);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mListOfQuestions.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder
    {
        TextView question, chosenAnswer, correctAnswer;
        ImageView imageView;
        public mViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.reviewQuestionID);
            chosenAnswer = (TextView) itemView.findViewById(R.id.reviewChosenAnswer);
            correctAnswer= (TextView) itemView.findViewById(R.id.reviewCorrectAnswer);
            imageView = (ImageView) itemView.findViewById(R.id.reviewResultImage);
        }
    }


}
