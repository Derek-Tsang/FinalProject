package algonquin.cst2335.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import algonquin.cst2335.finalproject.Entities.TriviaUser;
import algonquin.cst2335.finalproject.R;


/**
 * Adapter class for displaying trivia user scores in a RecyclerView.
 * This adapter binds TriviaUser to the corresponding views in the RecyclerView.
 */
public class TriviaUserAdapter extends RecyclerView.Adapter<TriviaUserAdapter.TriviaScoresViewHolder>{

    /**
     * List of Users
     */
    protected List<TriviaUser> users;

    /**
     * Application context
     */
    private Context context;

    /**
     * Default constructor for the TriviaUserAdapter.
     */
    public TriviaUserAdapter(){}

    /**
     * Constructor for the TriviaUserAdapter.
     * @param context The context of the calling activity.
     * @param users List of TriviaUser items to be displayed.
     */
    public TriviaUserAdapter(Context context,List<TriviaUser> users){
        this.context = context;
        this.users = users;
    }

    /**
     * Creates a new ViewHolder by inflating the layout for the Trivia user scores.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new instance of TriviaScoresViewHolder.
     */
    @NonNull
    @Override
    public TriviaScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trivia_scores_item_layout,parent,false);
        return new TriviaScoresViewHolder(itemView);

    }

    /**
     * Binds the Trivia data to the corresponding views in the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TriviaScoresViewHolder holder, int position) {
        TriviaUser user = users.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.username.setText(user.getUsername());
        holder.userScores.setText(String.valueOf(user.getScores()));

    }

    /**
     * Returns the total number of Trivia Users in the adapter.
     *
     * @return The number of Trivia users in the adapter.
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * ViewHolder class for displaying trivia user scores.
     */
    class TriviaScoresViewHolder extends RecyclerView.ViewHolder{

        /**
         * Represents user id
         */
        TextView id;

        /**
         * Represents username
         */
        TextView username;

        /**
         * Represents user scores
         */
        TextView userScores;

        /**
         * Constructor for the TriviaScoresViewHolder.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public TriviaScoresViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.userID);
            username = itemView.findViewById(R.id.username);
            userScores = itemView.findViewById(R.id.userScores);

        }
    }
}

