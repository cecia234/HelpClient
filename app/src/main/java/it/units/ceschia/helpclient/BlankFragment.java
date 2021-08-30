package it.units.ceschia.helpclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.units.ceschia.helpclient.entity.HelpRequest;
import it.units.ceschia.helpclient.viewmodel.ClientViewModel;

public class BlankFragment extends Fragment {

    ClientViewModel clientViewModel;
    RelativeLayout layout;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.rel_layout);

        //getData(view);


    }

    /*private void getData(View v){
        MutableLiveData<FirebaseUser> firebaseUser =clientViewModel.getFirebaseUser();
        if(firebaseUser==null) return;
        MutableLiveData<FirebaseFirestore> firebaseFirestore=clientViewModel.getFirebaseFirestore();
        CollectionReference collectionReference = firebaseFirestore.getValue().collection("helpRequests");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<HelpRequest> contacts = new ArrayList<HelpRequest>();
                            int count = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.i("echo",document.toString());
                                if(count==1) {
                                    TextView textView = new TextView(v.getContext());
                                    textView.setText(document.toString());
                                    layout.addView(textView);
                                }count++;
                            }
                        } else {
                            Log.d("echo", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }*/
}