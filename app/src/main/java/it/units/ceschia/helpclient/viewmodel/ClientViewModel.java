package it.units.ceschia.helpclient.viewmodel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.units.ceschia.helpclient.entity.GeneralResult;
import it.units.ceschia.helpclient.entity.HelpRequest;
import it.units.ceschia.helpclient.entity.HelpRequestSet;
import it.units.ceschia.helpclient.entity.LoginResult;
import it.units.ceschia.helpclient.recycler_view.adapter.RequestListAdapter;

public class ClientViewModel extends ViewModel {

    private MutableLiveData<HelpRequestSet> helpRequestSetMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<FirebaseAuth> mAuth = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();
    private MutableLiveData<FirebaseFirestore> firebaseFirestore = new MutableLiveData<>();

    public ClientViewModel() {
    }

    public ClientViewModel(MutableLiveData<FirebaseAuth> mAuth, MutableLiveData<FirebaseUser> firebaseUser, MutableLiveData<FirebaseFirestore> firebaseFirestore) {
        this.mAuth = mAuth;
        this.firebaseUser = firebaseUser;
        this.firebaseFirestore = firebaseFirestore;
    }

    public MutableLiveData<HelpRequestSet> getHelpRequestSetMutableLiveData() {
        return helpRequestSetMutableLiveData;
    }

    public void setHelpRequestSetMutableLiveData(HelpRequestSet helpRequestSetMutableLiveData) {
        this.helpRequestSetMutableLiveData.setValue(helpRequestSetMutableLiveData);
    }

    public MutableLiveData<FirebaseAuth> getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth.setValue(mAuth);
    }

    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser() {
        this.firebaseUser.setValue(mAuth.getValue().getCurrentUser());
    }

    public MutableLiveData<FirebaseFirestore> getFirebaseFirestore() {
        return firebaseFirestore;
    }

    public void setFirebaseFirestore(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore.setValue(firebaseFirestore);
    }

    public MutableLiveData<LoginResult> login(String email, String password) {
        FirebaseAuth auth = mAuth.getValue();
        MutableLiveData<LoginResult> loginResultMutableLiveData = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            setFirebaseUser();
                            loginResultMutableLiveData.setValue(new LoginResult(true));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            loginResultMutableLiveData.setValue(new LoginResult(false));
                        }
                    }
                });
        return loginResultMutableLiveData;
    }

    public MutableLiveData<GeneralResult> fetchRequests() {
        MutableLiveData<GeneralResult> fetchResult = new MutableLiveData<>();
        if (firebaseUser.getValue() == null) {
            fetchResult.setValue(new GeneralResult(false));
            return fetchResult;
        }
        CollectionReference collectionReference = firebaseFirestore.getValue().collection("helpRequests");

        Source source = Source.DEFAULT;

        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "fail retrieve");
                            fetchResult.setValue(new GeneralResult(false));
                            return;
                        }

                        ArrayList<HelpRequest> helpRequests = new ArrayList<HelpRequest>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HelpRequest helpRequest = document.toObject(HelpRequest.class);
                            helpRequests.add(helpRequest);
                            Log.i("echo", document.getId());
                        }
                        Log.i("echo", helpRequests.size() + " " + helpRequests.get(1));
                        setHelpRequestSetMutableLiveData(new HelpRequestSet(helpRequests));
                        fetchResult.setValue(new GeneralResult(true));

                    }

                });
        return fetchResult;
    }

    public void fetchRequestsContinue(final RequestListAdapter mAdapter, final RecyclerView mRecyclerView) {
        if (firebaseUser.getValue() == null) {
            return;
        }
        CollectionReference collectionReference = firebaseFirestore.getValue().collection("helpRequests");
        ArrayList<HelpRequest> helpRequests = new ArrayList<HelpRequest>();
        Source source = Source.DEFAULT;
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (QueryDocumentSnapshot document : value) {
                    HelpRequest helpRequest = document.toObject(HelpRequest.class);
                    helpRequests.add(helpRequest);

                }
                Collections.sort(helpRequests, new Comparator<HelpRequest>() {
                    @Override
                    public int compare(HelpRequest first, HelpRequest second) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return Integer.compare(second.getTime().compareTo(first.getTime()), 0);
                    }
                });


                mAdapter.setLocalDataSet(helpRequests);
                mAdapter.notifyDataSetChanged();

                mRecyclerView.setAdapter(mAdapter);

            }
        });
    }


}
