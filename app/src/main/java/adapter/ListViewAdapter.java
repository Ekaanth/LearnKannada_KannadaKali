package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import utils.AudioPlayer;
import app.learnkannada.com.learnkannadakannadakali.CategoryContentActivity;
import app.learnkannada.com.learnkannadakannadakali.AntonymsActivity;
import app.learnkannada.com.learnkannadakannadakali.DayActivity;
import app.learnkannada.com.learnkannadakannadakali.R;
import utils.FindResource;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> implements Filterable {

    private List<String> values, kanValues, mFilteredList, mFilteredKanList;
    private Context context;
    private String mCategory;
    private AlertDialog.Builder letterWriter;

    /**
     * Provide a suitable constructor (depends on the kind of dataset)
     * @param mContext Context to be supplied
     */
    public ListViewAdapter(Context mContext, List<String> myDataset, String category, List<String> kanInput) {
        context = mContext;
        values = myDataset;
        mFilteredKanList = kanInput;
        mFilteredList = myDataset;
        mCategory = category;
        kanValues = kanInput;
        letterWriter = new AlertDialog.Builder(context);
    }

    public void add(int position, String item) {
        mFilteredList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mFilteredList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (mCategory) {
            case Constants.DAYCOURSE: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_day, parent, false);
                break;
            }
            case Constants.HOMECOURSE: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_course, parent, false);

                break;
            }
            case Constants.ANTONYMS:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_minimal,parent,false);
                break;
            }
            default:
                if (mCategory.equals(Constants.FLEXI_WORDS) || mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
                    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                    v = inflater.inflate(R.layout.row_layout_image, parent, false);
                } else {
                    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                    v = inflater.inflate(R.layout.row_layout, parent, false);
                }
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = mFilteredList.get(position);
        final String kName = mFilteredKanList.get(position);
        holder.textInEng.setText(name);

        if (mCategory.equals(Constants.FLEXI_WORDS)) {
            //holder.textInKan.setVisibility(View.INVISIBLE);
            String imageName = name.toLowerCase().replaceAll(" ", "");
            holder.imageView1.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
            holder.imageView2.setVisibility(View.INVISIBLE);

            //setOnClickListener code starts here for flexi course->Words
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, CategoryContentActivity.class);
                    i.putExtra(Constants.FROM, Constants.FLEXI);
                    i.putExtra(Constants.CATEGORY, name.toLowerCase().replaceAll(" ", "_"));
                    v.getContext().startActivity(i);
                }
            });
        }
        else if (mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
            final String conversationName = name.toLowerCase().replaceAll(" ", "").replaceAll("conversationwith", "")
                    .replaceAll("/", "").replaceAll("conversationto", "").replaceAll("-", "");
            holder.imageView1.setImageResource(context.getResources().getIdentifier(conversationName, "drawable", context.getPackageName()));
            holder.imageView2.setVisibility(View.INVISIBLE);
            //holder.textInEng.setPaddingRelative(20,60,0,0);

            //setOnClickListener code starts here for flexi course->Conversations
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DayActivity.class);
                    i.putExtra(Constants.POSITION, conversationName);
                    v.getContext().startActivity(i);
                }
            });
        }

        else if(mCategory.equals(Constants.ANTONYMS))
        {
            //holder.textInKan.setVisibility(View.INVISIBLE);
            //holder.imageView1.setVisibility(View.INVISIBLE);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Constants.FROM_ADAPTER);
                    intent.putExtra(Constants.WORDS,name);
                    intent.putExtra(Constants.ANTONYMS,kName);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }

        if (!mCategory.equals(Constants.HOMECOURSE )&& !mCategory.equals(Constants.ANTONYMS)) {
            holder.textInKan.setText(mFilteredKanList.get(position));
        }

        //onClickListener code begins
        if (!mCategory.equals(Constants.ANTONYMS)&&
                !mCategory.equals(Constants.DAYCOURSE) &&
                !mCategory.equals(Constants.HOMECOURSE) &&
                !mCategory.equals(Constants.FLEXI_WORDS) &&
                !mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCategory.equals(Constants.ALPHABETS))
                    {
                        View view = LayoutInflater.from(context).inflate(R.layout.layout_letterwriting,null);
                        Integer resID = v.getContext().getResources().getIdentifier("ka_"+ audioResourceFinder(name),"drawable",context.getPackageName());
                        Glide.with(v.getContext()).load(resID).into((ImageView) view.findViewById(R.id.lw_iconID));
                        //Toast.makeText(context,"ka_" + audioResourceFinder(name) + " " + FindResource.rawResourceAvailable(context,"ka_" + audioResourceFinder(name)),Toast.LENGTH_SHORT).show();
                        TextView letter = (TextView) view.findViewById(R.id.lw_textID);
                        ImageView speaker = (ImageView) view.findViewById(R.id.lw_speakerID);
                        letter.setText(name);
                        speaker.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    playOffline(position);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        letterWriter.setView(view).create().show();
                    }
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //playOnline(position);
                }
            });
        }
        else if (mCategory.equals(Constants.HOMECOURSE)) {
            switch (name) {
                case "Day 1":
                case "Day 2":
                case "Day 3":
                case "Day 4":
                case "Day 5":
                case "Day 6":
                case "Day 7":
                    String mname = name.replaceAll(" ", "") + "_content";
                    int n = context.getResources().getStringArray(context.getResources().getIdentifier(mname, "array", context.getPackageName())).length;
                    holder.size.setText(n + " words");
                    break;
                case "Day 8":
                    holder.size.setText("Numbers");
                    break;
                case "Day 9":
                    holder.size.setText("Conversation between friends");
                    break;
                case "Day 10":
                    holder.size.setText("Conversation with Cab/Auto Driver");
                    break;
            }

            //holder.size.setText("10" + " words");
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,name,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, DayActivity.class);
                    i.putExtra(Constants.POSITION, name);
                    v.getContext().startActivity(i);
                }
            });
        }
        else if (mCategory.equals(Constants.DAYCOURSE)) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,name + "_t_" + position, Toast.LENGTH_LONG).show();
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.exampleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent i = new Intent(context, ExampleActivity.class);
                    i.putExtra(Constants.NAME, name);
                    i.putExtra(Constants.KNAME, kName);
                    v.getContext().startActivity(i);*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    AlertDialog dialog = builder.create();
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_example_popup,null);
                    TextView english = (TextView) view.findViewById(R.id.pu_wordInEngID);
                    TextView kannada = (TextView) view.findViewById(R.id.pu_wordInKanID);
                    ImageView speaker = (ImageView) view.findViewById(R.id.pu_speakerID);
                    TextView example = (TextView) view.findViewById(R.id.pu_exampleText);
                    english.setText(name);
                    kannada.setText(kName);
                    String exampleText = name.replaceAll(" ", "_").replaceAll("\\?","")
                            .replaceAll("\\(","_").replaceAll("\\)","")+ "_ex";
                    final String spokenWord = name.replaceAll(" ", "_").replaceAll("\\?","").toLowerCase()
                            .replaceAll("\\(","_")
                            .replaceAll("\\)","")+ "_ex";
                    example.setText(context.getResources().getIdentifier(exampleText.toLowerCase(),"string",context.getPackageName()));
                    speaker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AudioPlayer.playAudio(context,spokenWord);
                        }
                    });
                    dialog.setView(view);
                    dialog.show();
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            if(AudioPlayer.mediaPlayer!=null && AudioPlayer.mediaPlayer.isPlaying())
                                AudioPlayer.stopAudio();
                        }
                    });
                }
            });
        }
        //separate condition for Day 9 and 10 to set exampleButton invisible for these as these are conversations
        else if (mCategory.equals(Constants.DAY89_10)) {
            holder.exampleButton.setVisibility(View.INVISIBLE);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,name + "__" + position, Toast.LENGTH_LONG).show();
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void playOffline(int position) throws IOException {
        String voiceId = null;
        if (mCategory.equals(Constants.ALPHABETS)) {
            voiceId = audioResourceFinder(mFilteredList.get(position));
        } else {
            voiceId = mFilteredList.get(position).replaceAll(" ", "_").replaceAll("\\?", "")
                    .replaceAll("\\(", "_").replaceAll("\\)", "")
                    .replaceAll(":", "").replaceAll(",", "").replaceAll("\\.", "");
        }
        AudioPlayer.playAudio(context, voiceId);
    }

    private String audioResourceFinder(String s) {
        if (s.equals("Nya"))
            return ("nya_");
        else if (s.equals("Cha"))
            return ("cha_");
        else if (s.equals("Ta"))
            return ("ta_");
        else if (s.equals("Tha"))
            return ("tha_");
        else if (s.equals("Da"))
            return ("da_");
        else if (s.equals("Na"))
            return ("na_");
        else if (s.equals("Dha"))
            return ("dha_");
        else if (s.equals("La"))
            return ("la_");
        else if (s.equals("Sha"))
            return ("sha_");
        else
            return s;

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    /*private void playOnline(int position) {
        Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();

        storageReference = FirebaseStorage.getInstance().getReference().child("Categories").child(mCategory)
                .child(values.get(position).toLowerCase()+".mp3");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUri = uri;
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(downloadUri.toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Resource not found",Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    mFilteredList = values;
                    mFilteredKanList = kanValues;
                } else {

                    ArrayList<String> engFilteredList = new ArrayList<>();
                    ArrayList<String> kanFilteredList = new ArrayList<>();

                    for (int a = 0; a < values.size(); a++) {
                        String engString = values.get(a);
                        String kanString = kanValues.get(a);
                        if (engString.toLowerCase().contains(charString) || kanString.toLowerCase().contains(charString)) {
                            engFilteredList.add(engString);
                            kanFilteredList.add(kanString);
                        }
                    }
                   /* for (String tempString : values) {
                        if (tempString.toLowerCase().contains(charString))
                            filteredList.add(tempString);
                    }*/
                    mFilteredList = engFilteredList;
                    mFilteredKanList = kanFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textInEng, textInKan, size;
        ImageView imageView1, imageView2;
        Button exampleButton;
        View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            textInEng = (TextView) layout.findViewById(R.id.textID);
            if(!mCategory.equals(Constants.ANTONYMS))
                textInKan = (TextView) layout.findViewById(R.id.textInKanID);
            if (mCategory.equals(Constants.HOMECOURSE))
                size = (TextView) layout.findViewById(R.id.sizeID);
            if (mCategory.equals(Constants.DAYCOURSE))
                exampleButton = (Button) layout.findViewById(R.id.exampleButtonID);
            if (mCategory.equals(Constants.FLEXI_WORDS) || mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
                imageView1 = (ImageView) layout.findViewById(R.id.imageID);
                imageView2 = (ImageView) layout.findViewById(R.id.speakerID);
            }

        }
    }
}