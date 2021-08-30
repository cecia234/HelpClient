package it.units.ceschia.helpclient.recycler_view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.units.ceschia.helpclient.R;
import it.units.ceschia.helpclient.entity.HelpRequest;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {

    private ArrayList<HelpRequest> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView type;
        private final TextView time;
        private final TextView namesurname;
        private final TextView positionData;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.type =  view.findViewById(R.id.text_view_request_manager_type);
            this.time = view.findViewById(R.id.text_view_request_manager_time);
            this.namesurname =  view.findViewById(R.id.text_view_request_manager_name_surname);
            this.positionData = view.findViewById(R.id.text_view_request_manager_position);
        }

        public TextView getType() {
            return type;
        }

        public TextView getTime() {
            return time;
        }

        public TextView getNamesurname() {
            return namesurname;
        }

        public TextView getPositionData() {
            return positionData;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public RequestListAdapter(ArrayList<HelpRequest> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.request_manager_item_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        HelpRequest helpRequest = localDataSet.get(position);
        String nameSurname = helpRequest.getUser().getName()+" "+ helpRequest.getUser().getSurname();
        String positionString = "lat"+ helpRequest.getLatitude() +"\nlong"+Double.toString(helpRequest.getLongitude())+"\nalt"+Double.toString(helpRequest.getAltitude());
        viewHolder.getNamesurname().setText(nameSurname);
        viewHolder.getPositionData().setText(positionString);
        viewHolder.getTime().setText(helpRequest.getTime().toDate().toString().substring(4,19));
        viewHolder.getType().setText(helpRequest.getType().toString());



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}