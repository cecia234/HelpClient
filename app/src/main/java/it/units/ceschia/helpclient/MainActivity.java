package it.units.ceschia.helpclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.units.ceschia.helpclient.databinding.ActivityMainBinding;
import it.units.ceschia.helpclient.entity.HelpRequest;
import it.units.ceschia.helpclient.entity.LoginResult;
import it.units.ceschia.helpclient.viewmodel.ClientViewModel;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private ClientViewModel clientViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        clientViewModel.setFirebaseFirestore(db);
        clientViewModel.setmAuth(mAuth);
        clientViewModel.setFirebaseUser();

        login("admin@admin.it","Adm1nAdm1n");


    }

    private void login(String email,String password){
        clientViewModel.login(email, password).observe(this, (Observer<LoginResult>) result -> {
            if (result.success) {
                Log.i("echo","Login Succeded");
                clientViewModel.fetchRequests().observe(this,(Observer<LoginResult>) res->{
                    navController.navigate(R.id.action_blankFragment_to_helpRequestListFragment);
                });
            } else {
                //showErrorMessage();
                Log.i("echo","Login Failed");
            }
        });
    }


}