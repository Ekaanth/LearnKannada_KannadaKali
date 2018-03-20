package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import app.learnkannada.com.learnkannadakannadakali.DayActivity;
import app.learnkannada.com.learnkannadakannadakali.R;
import utils.FindResource;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> implements Filterable {

    private List<String> values, kanValues, mFilteredList, mFilteredKanList, mKanScripts;
    private Context context;
    private String mCategory;
    private AlertDialog.Builder letterWriter;

    private int lastClickedItem = 0, oldClickedItem = 0;

    /**
     * Provide a suitable constructor (depends on the kind of dataset)
     *
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

    public ListViewAdapter(Context mContext, List<String> myDataset, String category, List<String> kanInput, List<String> kanScripts) {
        context = mContext;
        values = myDataset;
        mFilteredKanList = kanInput;
        mFilteredList = myDataset;
        mCategory = category;
        kanValues = kanInput;
        letterWriter = new AlertDialog.Builder(context);
        mKanScripts = kanScripts;
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
        Log.d("AdapterLogs", "Section: onCreateViewHolder \n Category: " + mCategory);
        switch (mCategory) {
            //Constants.INVIDUAL_DAY_CONTENT will be received  when user clicks on Day 1 to Day 7 in 10 days course
            case Constants.INDIVIDUAL_DAY_CONTENT: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_day, parent, false);
                break;
            }
            case Constants.DAY89_10:{
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_day, parent, false);
                break;
            }

            //Constants.TENDAYSCOURSE will be received when user clicks on 10 days course on Home page
            case Constants.TENDAYSCOURSE: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_course, parent, false);

                break;
            }
            //Constants.ANTONYMS will be received when user clicks on Antonyms section in FLEXI COURSE
            case Constants.ANTONYMS: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_minimal, parent, false);
                break;
            }
            //Constants.FLEXI_WORDS will be received when user clicks on "Words" section inside FLEXI COURSE
            case Constants.FLEXI_WORDS: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_image, parent, false);
                break;
            }
            //Constants.FLEXI_CONVERSATIONS will be received when user clicks on "Conversations" section inside FLEXI COURSE
            case Constants.FLEXI_CONVERSATIONS: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_image, parent, false);
                break;
            }
            case Constants.ALPHABETS: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout, parent, false);
                break;
            }
            //Default row_layout will be inflated.
            default: {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_day, parent, false);
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


        switch (mCategory) {
            //Constants.TENDAYSCOURSE will be received when user clicks on 10 Days Course on home page
            case Constants.TENDAYSCOURSE:
                String mname = name.replaceAll(" ", "") + "_content";
                switch (name) {
                    case "Day 8":
                        holder.size.setText(Constants.NUMBERS);
                        break;
                    case "Day 9":
                        holder.size.setText(Constants.CONV_WITH_FRIEND);
                        break;
                    case "Day 10":
                        holder.size.setText(Constants.CONV_WITH_DRIVER);
                        break;
                    default:
                        int n = context.getResources().getStringArray(context.getResources().getIdentifier(mname, "array", context.getPackageName())).length;
                        String displaySize = n + " words";
                        holder.size.setText(displaySize);
                        break;
                }
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, DayActivity.class);
                        i.putExtra(Constants.POSITION, name);
                        v.getContext().startActivity(i);
                    }
                });
                break;
            //Constants.TENDAYSCOURSE will be received when user clicks on Day 1 to Day 7 inside 10 days course
            case Constants.INDIVIDUAL_DAY_CONTENT:
                holder.textInKannada.setText(mKanScripts.get(position));
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oldClickedItem = lastClickedItem;
                        lastClickedItem = position;
                        notifyItemChanged(oldClickedItem);
                        notifyItemChanged(lastClickedItem);
                        try {
                            playOffline(position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (lastClickedItem == position) {
                    holder.exampleButton.setVisibility(View.VISIBLE);
                    holder.textInKannada.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                } else {
                    holder.exampleButton.setVisibility(View.GONE);
                    holder.textInKannada.setVisibility(View.GONE);
                    holder.divider.setVisibility(View.GONE);
                }
                holder.exampleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayExampleDialog(v, name, kName);
                    }
                });
                break;
            //Constants.DAY89_10 will be received when user clicks on Day 8 to Day 10 inside 10 days course
            case Constants.DAY89_10:
                holder.exampleButton.setVisibility(View.GONE);
                holder.textInKannada.setText(mKanScripts.get(position));
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oldClickedItem = lastClickedItem;
                        lastClickedItem = position;
                        notifyItemChanged(oldClickedItem);
                        notifyItemChanged(lastClickedItem);
                        try {
                            playOffline(position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (lastClickedItem == position) {
                    holder.textInKannada.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                } else {
                    holder.textInKannada.setVisibility(View.GONE);
                    holder.divider.setVisibility(View.GONE);
                }
                break;
            //Constants.FLEXI_WORDS will be received when user clicks on "Words" section in FLEXI COURSE
            case Constants.FLEXI_WORDS:
                //extracting name and finding appropriate image
                String imageName = name.toLowerCase().replaceAll(" ", "");
                holder.imageView1.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
                //setting speaker icon invisible as it is not required for displaying words categories.
                holder.imageView2.setVisibility(View.INVISIBLE);
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, CategoryContentActivity.class);
                        i.putExtra(Constants.FROM, Constants.FLEXI);
                        i.putExtra(Constants.CATEGORY, name.toLowerCase().replaceAll(" ", "_"));
                        v.getContext().startActivity(i);
                    }
                });
                break;

            //Constants.FLEXI_CONVERSATIONS will be received when user clicks on "Conversations" section in FLEXI COURSE
            case Constants.FLEXI_CONVERSATIONS:
                //extracting name and finding appropriate image
                final String conversationName = name.toLowerCase().replaceAll(" ", "").replaceAll("conversationwith", "")
                        .replaceAll("/", "").replaceAll("conversationto", "").replaceAll("-", "");
                holder.imageView1.setImageResource(context.getResources().getIdentifier(conversationName, "drawable", context.getPackageName()));
                //setting speaker icon invisible as it is not required for displaying words categories.
                holder.imageView2.setVisibility(View.INVISIBLE);
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, DayActivity.class);
                        i.putExtra(Constants.POSITION, conversationName);
                        v.getContext().startActivity(i);
                    }
                });
                break;
            //Constants.ANTONYMS will be received when user "Antonyms" section in FLEXI COURSE
            case Constants.ANTONYMS:
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Constants.FROM_ADAPTER);
                        intent.putExtra(Constants.WORDS, name);
                        intent.putExtra(Constants.ANTONYMS, kName);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                    }
                });
                break;

            case Constants.ALPHABETS:
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlphabetsDialog(view, name, position);
                        try {
                            playOffline(position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            //default block for all other categories.
            default:
                holder.exampleButton.setVisibility(View.GONE);
                holder.textInKannada.setText(mKanScripts.get(position));
                holder.textInKan.setText(mFilteredKanList.get(position));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oldClickedItem = lastClickedItem;
                        lastClickedItem = position;
                        notifyItemChanged(oldClickedItem);
                        notifyItemChanged(lastClickedItem);
                        try {
                            playOffline(position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (lastClickedItem == position) {
                    holder.textInKannada.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                } else {
                    holder.textInKannada.setVisibility(View.GONE);
                    holder.divider.setVisibility(View.GONE);
                }
                break;

        }

        /*if (mCategory.equals(Constants.FLEXI_WORDS)) {
            //holder.textInKan.setVisibility(View.INVISIBLE);
            String imageName = name.toLowerCase().replaceAll(" ", "");
            holder.imageView1.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
            holder.imageView2.setVisibility(View.INVISIBLE);

            //setOnClickListener code starts here _for flexi course->Words
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, CategoryContentActivity.class);
                    i.putExtra(Constants.FROM, Constants.FLEXI);
                    i.putExtra(Constants.CATEGORY, name.toLowerCase().replaceAll(" ", "_"));
                    v.getContext().startActivity(i);
                }
            });
        } else*/ /*if (mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
            final String conversationName = name.toLowerCase().replaceAll(" ", "").replaceAll("conversationwith", "")
                    .replaceAll("/", "").replaceAll("conversationto", "").replaceAll("-", "");
            holder.imageView1.setImageResource(context.getResources().getIdentifier(conversationName, "drawable", context.getPackageName()));
            holder.imageView2.setVisibility(View.INVISIBLE);
            //holder.textInEng.setPaddingRelative(20,60,0,0);

            //setOnClickListener code starts here _for flexi course->Conversations
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DayActivity.class);
                    i.putExtra(Constants.POSITION, conversationName);
                    v.getContext().startActivity(i);
                }
            });
        } else*/ /*if (mCategory.equals(Constants.ANTONYMS)) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Constants.FROM_ADAPTER);
                    intent.putExtra(Constants.WORDS, name);
                    intent.putExtra(Constants.ANTONYMS, kName);
                    LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                }
            });
        }*/

       /* if (!mCategory.equals(Constants.TENDAYSCOURSE) && !mCategory.equals(Constants.ANTONYMS)) {
            holder.textInKan.setText(mFilteredKanList.get(position));
        }*/

        //onClickListener code begins
        /*if (!mCategory.equals(Constants.ANTONYMS) &&
                !mCategory.equals(Constants.INDIVIDUAL_DAY_CONTENT) &&
                !mCategory.equals(Constants.TENDAYSCOURSE) &&
                !mCategory.equals(Constants.FLEXI_WORDS) &&
                !mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Clicked on old alpha",Toast.LENGTH_SHORT).show();
                   *//* if (mCategory.equals(Constants.ALPHABETS)) {
                        View view = LayoutInflater.from(context).inflate(R.layout.layout_letterwriting, null);
                        Integer resID = v.getContext().getResources().getIdentifier("ka_" + FindResource.processStringName(name), "drawable", context.getPackageName());
                        Glide.with(v.getContext()).load(resID).into((ImageView) view.findViewById(R.id.lw_iconID));
                        //Toast.makeText(context,"ka_" + processStringName(name) + " " + FindResource.rawResourceAvailable(context,"ka_" + processStringName(name)),Toast.LENGTH_SHORT).show();
                        Button letter = (Button) view.findViewById(R.id.lw_textID);
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
                    }*//*
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/ /*else if (mCategory.equals(Constants.TENDAYSCOURSE)) {
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
        }*/ /*else if (mCategory.equals(Constants.INDIVIDUAL_DAY_CONTENT)) {
            holder.textInKannada.setText(mKanScripts.get(position));
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldClickedItem = lastClickedItem;
                    lastClickedItem = position;
                    notifyItemChanged(oldClickedItem);
                    notifyItemChanged(lastClickedItem);
                    //Toast.makeText(context,name + "_t_" + position, Toast.LENGTH_LONG).show();
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (lastClickedItem == position) {
                holder.exampleButton.setVisibility(View.VISIBLE);
                holder.textInKannada.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.VISIBLE);
            } else {
                holder.exampleButton.setVisibility(View.GONE);
                holder.textInKannada.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
            holder.exampleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*Intent i = new Intent(context, ExampleActivity.class);
                    i.putExtra(Constants.NAME, name);
                    i.putExtra(Constants.KNAME, kName);
                    v.getContext().startActivity(i);*//*
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    AlertDialog dialog = builder.create();
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_example_popup, null);
                    TextView english = (TextView) view.findViewById(R.id.pu_wordInEngID);
                    TextView kannada = (TextView) view.findViewById(R.id.pu_wordInKanID);
                    ImageView speaker = (ImageView) view.findViewById(R.id.pu_speakerID);
                    TextView example = (TextView) view.findViewById(R.id.pu_exampleText);
                    english.setText(name);
                    kannada.setText(kName);
                    String exampleText = name.replaceAll(" ", "_").replaceAll("\\?", "")
                            .replaceAll("\\(", "_").replaceAll("\\)", "") + "_ex";
                    final String spokenWord = name.replaceAll(" ", "_").replaceAll("\\?", "").toLowerCase()
                            .replaceAll("\\(", "_")
                            .replaceAll("\\)", "") + "_ex";
                    example.setText(context.getResources().getIdentifier(exampleText.toLowerCase(), "string", context.getPackageName()));
                    speaker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AudioPlayer.playAudio(context, spokenWord);
                        }
                    });
                    dialog.setView(view);
                    dialog.show();
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            if (AudioPlayer.mediaPlayer != null && AudioPlayer.mediaPlayer.isPlaying())
                                AudioPlayer.stopAudio();
                        }
                    });
                }
            });
        }*/
        //separate condition _for Day 9 and 10 to set exampleButton invisible _for these as these are conversations
        /*else if (mCategory.equals(Constants.DAY89_10)) {
            Log.d("adapterlogs","DAY89_10 Category");
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldClickedItem = lastClickedItem;
                    lastClickedItem = position;
                    notifyItemChanged(lastClickedItem);
                    notifyItemChanged(oldClickedItem);
                    //Toast.makeText(context,name + "__" + position, Toast.LENGTH_LONG).show();
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            if(lastClickedItem==position)
            {
                holder.exampleButton.setVisibility(View.VISIBLE);
                holder.textInKannada.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.exampleButton.setVisibility(View.GONE);
                holder.textInKannada.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
        }*/

    }

    private void showAlphabetsDialog(View v, String name, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_letterwriting, null);
        Integer resID = v.getContext().getResources().getIdentifier("ka_" + FindResource.processStringName(name), "drawable", context.getPackageName());
        Glide.with(v.getContext()).load(resID).into((ImageView) view.findViewById(R.id.lw_iconID));
        Button letter = (Button) view.findViewById(R.id.lw_textID);
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

    private void displayExampleDialog(View v, String name, String kName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        AlertDialog dialog = builder.create();
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_example_popup, null);
        TextView english = (TextView) view.findViewById(R.id.pu_wordInEngID);
        TextView kannada = (TextView) view.findViewById(R.id.pu_wordInKanID);
        ImageView speaker = (ImageView) view.findViewById(R.id.pu_speakerID);
        TextView example = (TextView) view.findViewById(R.id.pu_exampleText);
        english.setText(name);
        kannada.setText(kName);
        String exampleText = name.replaceAll(" ", "_").replaceAll("\\?", "")
                .replaceAll("\\(", "_").replaceAll("\\)", "") + "_ex";
        final String spokenWord = name.replaceAll(" ", "_").replaceAll("\\?", "").toLowerCase()
                .replaceAll("\\(", "_")
                .replaceAll("\\)", "") + "_ex";
        example.setText(context.getResources().getIdentifier(exampleText.toLowerCase(), "string", context.getPackageName()));
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlayer.playAudio(context, spokenWord);
            }
        });
        dialog.setView(view);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (AudioPlayer.mediaPlayer != null && AudioPlayer.mediaPlayer.isPlaying())
                    AudioPlayer.stopAudio();
            }
        });
    }

    private void playOffline(int position) throws IOException {
        String voiceId = null;
        if (mCategory.equals(Constants.ALPHABETS)) {
            voiceId = FindResource.processStringName(mFilteredList.get(position));
        } else {
            voiceId = mFilteredList.get(position).replaceAll(" ", "_").replaceAll("\\?", "")
                    .replaceAll("\\(", "_").replaceAll("\\)", "")
                    .replaceAll(":", "").replaceAll(",", "").replaceAll("\\.", "");
        }
        if (FindResource.rawResourceAvailable(context, voiceId))
            AudioPlayer.playAudio(context, voiceId);
        else {
            Toast.makeText(context, voiceId, Toast.LENGTH_SHORT).show();
            throw new IOException("\n ERROR! Audio file not found _for " + voiceId);
        }
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
                   /* _for (String tempString : values) {
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

        TextView textInEng, textInKan, size, textInKannada;
        ImageView imageView1, imageView2;
        Button exampleButton;
        View layout, divider;

        ViewHolder(View itemView)
        {
            super(itemView);
            layout = itemView;

            switch (mCategory) {
                case Constants.TENDAYSCOURSE:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    size = (TextView) layout.findViewById(R.id.sizeID);
                    break;
                case Constants.INDIVIDUAL_DAY_CONTENT:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    exampleButton = (Button) layout.findViewById(R.id.exampleButtonID);
                    textInKannada = (TextView) layout.findViewById(R.id.textInKannadaId);
                    divider = layout.findViewById(R.id.dividerID);
                    break;
                case Constants.DAY89_10:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    exampleButton = (Button) layout.findViewById(R.id.exampleButtonID);
                    textInKannada = (TextView) layout.findViewById(R.id.textInKannadaId);
                    divider = layout.findViewById(R.id.dividerID);
                    break;
                case Constants.FLEXI_WORDS:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    imageView1 = (ImageView) layout.findViewById(R.id.imageID);
                    imageView2 = (ImageView) layout.findViewById(R.id.speakerID);
                    break;
                case Constants.FLEXI_CONVERSATIONS:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    imageView1 = (ImageView) layout.findViewById(R.id.imageID);
                    imageView2 = (ImageView) layout.findViewById(R.id.speakerID);
                    break;
                case Constants.ANTONYMS:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    break;
                case Constants.ALPHABETS:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    break;
                default:
                    textInEng = (TextView) layout.findViewById(R.id.textID);
                    textInKan = (TextView) layout.findViewById(R.id.textInKanID);
                    exampleButton = (Button) layout.findViewById(R.id.exampleButtonID);
                    textInKannada = (TextView) layout.findViewById(R.id.textInKannadaId);
                    divider = layout.findViewById(R.id.dividerID);
                    break;

            }

            /*if(!mCategory.equals(Constants.ANTONYMS))
                textInKan = (TextView) layout.findViewById(R.id.textInKanID);
            if (mCategory.equals(Constants.TENDAYSCOURSE))
                size = (TextView) layout.findViewById(R.id.sizeID);
            if (mCategory.equals(Constants.INDIVIDUAL_DAY_CONTENT) || mCategory.equals(Constants.DAY89_10))
            {
                exampleButton = (Button) layout.findViewById(R.id.exampleButtonID);
                textInKannada = (TextView) layout.findViewById(R.id.textInKannadaId);
                divider = layout.findViewById(R.id.dividerID);
            }
            if (mCategory.equals(Constants.FLEXI_WORDS) || mCategory.equals(Constants.FLEXI_CONVERSATIONS)) {
                imageView1 = (ImageView) layout.findViewById(R.id.imageID);
                imageView2 = (ImageView) layout.findViewById(R.id.speakerID);
            }*/

        }
    }
}