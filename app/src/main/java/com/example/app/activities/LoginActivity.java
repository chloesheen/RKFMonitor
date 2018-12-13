package com.example.app.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.POST_LOGIN;
import static com.example.app.util.Constants.REQUEST_LOGIN;
import static com.example.app.util.Constants.REQUEST_STUDENT_LIST;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.Constants.REQUEST_STUDENT_LIST;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.app.R;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.asynctasks.HttpPostRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;
import com.example.app.util.BootingHandler;
import com.example.app.util.Pair;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;

import org.parceler.Parcels;

public class LoginActivity extends AppCompatActivity implements CallbackListener {

    private EditText username;
    private EditText password;
    private CheckBox checkBox;
    private Button submitButton;
    private Button registerButton;
    private SharedPreferences login;

    private CallbackListener mListener;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        username = (EditText) findViewById(R.id.username_edittext);
        password = (EditText) findViewById(R.id.password_edittext);
        checkBox = (CheckBox) findViewById(R.id.remember_me_checkbox);
        submitButton = (Button) findViewById(R.id.submit_button);
        registerButton = (Button) findViewById(R.id.register_button);

        mListener = this;
        mContext = this;

        login = getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        if (login.getBoolean("isChecked", false)) {
            username.setText(login.getString("username", null));
            password.setText(login.getString("password", null));
            checkBox.setChecked(login.getBoolean("isChecked", false));
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = login.edit();
                if (checkBox.isChecked()) {
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    editor.commit();
                } else {
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    editor.commit();
                }

                //String checkUser = "user" + username.getText().toString();
                //String checkPassword = "password" + username.getText().toString();

                HashMap<String, String> logininfo = new HashMap<>();
                logininfo.put("username", username.getText().toString());
                logininfo.put("password", password.getText().toString());
                HttpPostRequests logintask = new HttpPostRequests(logininfo, POST_LOGIN, mListener, mContext);
                logintask.execute(REQUEST_LOGIN);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("testKey", SHARED_PREFS_KEY);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {
        if (success) {
            switch (requestcode) {
                case POST_LOGIN:
                    ArrayList<Pair<String, String>> res = (ArrayList<Pair<String, String>>) object;
                    SharedPreferences.Editor editor = login.edit();
                    editor.putString("authorization", res.get(0).getSecond());
                    editor.putString("isAdministrator", res.get(1).getSecond());
                    editor.putString("isTeacher", res.get(2).getSecond());
                    Log.v("test1", "blahh");
                    editor.putString("isCook", res.get(3).getSecond());
                    editor.commit();
                    Log.v("tet6", "on");
                    BootingHandler.initLauncher(this, login, mListener);
                    /**if (login.getString("isAdministrator", null).equals("true")) {
                        Intent orgIntent = new Intent(LoginActivity.this, OrganizationDashboard.class);
                        startActivity(orgIntent);
                    } else if (login.getString("isTeacher", null).equals("true")){
                        Log.v("test2", "blahblah");
                        HttpGetRequests task = new HttpGetRequests(GET_STUDENTLIST_VIEW, mListener, mContext);
                        task.execute(REQUEST_STUDENT_LIST);
                    } else if (login.getString("isCook", null).equals("true")) {
                        Intent cookIntent = new Intent(LoginActivity.this, FeedingDashboard.class);
                        startActivity(cookIntent);
                    } */
                    break;

                case GET_STUDENTLIST_VIEW:
                    ArrayList<Student> students = (ArrayList<Student>) object;
                    Intent intent = new Intent(LoginActivity.this, ClassViewActivity.class);
                    intent.putExtra("Studentlist", Parcels.wrap(students));
                    startActivity(intent);
                    break;

            }
        }
    }
}

