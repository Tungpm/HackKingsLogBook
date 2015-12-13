package com.grafixartist.parseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
//    Button mBtnSignup;
//    TextView mUsernameSignup, mPasswordSignup;
//    Profile mFbProfile;
//    ParseUser parseUser;
//    String nameSignup = null, passwordSignup = null;
//
//    public static final List<String> mPermissions = new ArrayList<String>() {{
//        add("public_profile");
//        add("email");
//    }};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login2);
//
//        mBtnSignup = (Button) findViewById(R.id.btn_signup);
//
//        mUsernameSignup = (TextView) findViewById(R.id.username_signup);
//        mPasswordSignup = (TextView) findViewById(R.id.password_signup);
//
//        mFbProfile = Profile.getCurrentProfile();
//
//        //  Use this to test if Parse is working (by sending dummy data)
//        /*ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
//        testObject.get("foo");*/
//
//        //  Use this to output your Facebook Key Hash to Logs
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.grafixartist.parseapp",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//        ParseFacebookUtils.logInWithReadPermissionsInBackground(SignupActivity.this, mPermissions, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException err) {
//
//                if (user == null) {
//                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//                } else if (user.isNew()) {
//                    Log.d("MyApp", "User signed up and logged in through Facebook!");
//                    getUserDetailsFromFB();
//                    saveNewUser();
//                } else {
//                    Log.d("MyApp", "User logged in through Facebook!");
//
//                }
//            }
//        });
//    }
//
//    private void saveNewUser() {
//        parseUser = ParseUser.getCurrentUser();
//        parseUser.setUsername(name);
//
////        Saving profile photo as a ParseFile
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//        byte[] data = stream.toByteArray();
//        String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
//        final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
//
//        parseFile.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                parseUser.put("profileThumb", parseFile);
//
//                //Finally save all the user details
//                parseUser.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        Toast.makeText(SignupActivity.this, "New user:" + name + " Signed up", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
//
//    }
//
//
//    private void getUserDetailsFromFB() {
//
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/me",
//                null,
//                HttpMethod.GET,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//            /* handle the result */
//                        try {
//                            email = response.getJSONObject().getString("email");
//                            mEmailID.setText(email);
//                            name = response.getJSONObject().getString("name");
//                            mUsername.setText(name);
//
//                            saveNewUser();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).executeAsync();
//
//        ProfilePhotoAsync profilePhotoAsync = new ProfilePhotoAsync(mFbProfile);
//        profilePhotoAsync.execute();
//
//    }
//
//    private void getUserDetailsFromParse() {
//        parseUser = ParseUser.getCurrentUser();
//
////Fetch profile photo
//        try {
//            ParseFile parseFile = parseUser.getParseFile("profileThumb");
//            byte[] data = parseFile.getData();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//            mProfileImage.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mEmailID.setText(parseUser.getEmail());
//        mUsername.setText(parseUser.getUsername());
//
//        Toast.makeText(SignupActivity.this, "Welcome back " + mUsername.getText().toString(), Toast.LENGTH_SHORT).show();
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
//        Profile profile;
//        public Bitmap bitmap;
//
//        public ProfilePhotoAsync(Profile profile) {
//            this.profile = profile;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            // Fetching data from URI and storing in bitmap
//            bitmap = DownloadImageBitmap(profile.getProfilePictureUri(200, 200).toString());
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            mProfileImage.setImageBitmap(bitmap);
//        }
//    }
//
//    public static Bitmap DownloadImageBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL aURL = new URL(url);
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            Log.e("IMAGE", "Error getting bitmap", e);
//        }
//        return bm;
//    }
//
//    private void login() {
//        String username = mUsername.getText().toString().trim();
//        String password = mPassword.getText().toString().trim();
//
//        // Validate the log in data
//        boolean validationError = false;
//        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
//        if (username.length() == 0) {
//            validationError = true;
//            validationErrorMessage.append(getString(R.string.error_blank_username));
//        }
//        if (password.length() == 0) {
//            if (validationError) {
//                validationErrorMessage.append(getString(R.string.error_join));
//            }
//            validationError = true;
//            validationErrorMessage.append(getString(R.string.error_blank_password));
//        }
//        validationErrorMessage.append(getString(R.string.error_end));
//
//        // If there is a validation error, display the error
//        if (validationError) {
//            Toast.makeText(SignupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//
//        // Set up a progress dialog
//        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
//        dialog.setMessage(getString(R.string.progress_login));
//        dialog.show();
//        // Call the Parse login method
//        ParseUser.logInInBackground(username, password, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                dialog.dismiss();
//                if (e != null) {
//                    // Show the error message
//                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                } else {
//                    // Start an intent for the dispatch activity
//                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
}
