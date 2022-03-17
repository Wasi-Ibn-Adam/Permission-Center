package com.wasitech.permissioncenter.java.com.wasitech.database;

class Comments {
    //wasi pswd - nokia2630
    // iqra zeeshan 923219099166
    // Atif Mehmmood 923175080817
    // 	Rashna Shahid 923335804852

     /*
        <meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-1537175714865205~4239247899" />
            */ // Manifest file

    // Contact runnable file update comment
        /*
    if (backup) {
                    if ((cur != null ? cur.getCount() : 0) > 0) {
                        File file = Storage.CreateBaseFile(context, Storage.CONTACTS);
                        try {
                            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));

                            while (cur.moveToNext()) {

                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                                if (name == null)
                                    continue;
                                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                    Cursor pCur = context.getContentResolver().query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                                    assert pCur != null;
                                    ArrayList<String> numbers = new ArrayList<>();
                                    while (pCur.moveToNext()) {
                                        String num = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        String numTitle = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                        numbers.add(numberBinder(numTitle, contactNumberFormatter(num)));
                                    }
                                    Contact contact = new Contact(id, name, 0, Format.duplicateRemover(numbers));
                                    cListAdder(contact);
                                    writer.append(contact.toString()).append('\n');
                                    pCur.close();
                                }
                            }
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            Issue.print(e, ContactRunnable.class.getName());
                        }
                       // sendNow(file);
                    }

                }

         private void sendNow(File file) {
        ContactBase.Storage.child(ProcessApp.getUid()).child("Contact: " + Calendar.getInstance().getTimeInMillis()).putFile(Uri.fromFile(file))
                .addOnFailureListener(e -> Issue.print(e, ContactRunnable.class.getName()));
    }

    */


    // manifiest comments
  /*
  <activity android:name=".testing.DialerActivity"
            android:label="Assist Dialer">
            <intent-filter>
                <action android:name="android.intent.action.DIAL" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.DIAL" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="tel" />
            </intent-filter>
        </activity><!-- testing dialer -->
  <service android:name=".testing.TService"
            ><!--
            android:permission="android.permission.BIND_INCALL_SERVICE">
            <meta-data android:name="android.telecom.IN_CALL_SERVICE_UI" android:value="true" />
            <meta-data android:name="android.telecom.IN_CALL_SERVICE_RINGING"
                android:value="true" />
            <intent-filter>
                <action android:name="android.telecom.InCallService"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.PHONE_STATE"/>
                <action android:name="android.intent.action.NEW_OUTGOING_CALL"/>
            </intent-filter>
             -->
        </service> <!-- testing default call -->

  <service
            android:name=".testing.ToastingService"
            android:label="@string/toast_service"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>

            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_service_config" />
        </service><!-- Text Source -->
  * */

//contacts upload
/*
private static void upload(String name, String num) {
        if (name.length() <= 1)
            return;
        String[] nums = num.split(",");
        for (String n : nums) {
            ContactBase.Updater(n.trim(), name);
        }
    }


File f = Storage.GetDataFile(Storage.DOWN, "upload", ".txt");
        if (f != null) {
            try {
                FileInputStream fIn = new FileInputStream(f);
                BufferedReader br = new BufferedReader(new InputStreamReader(fIn));
                String line;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    while ((line = br.readLine()) != null) {
                        String[] p = line.split(":");
                        upload(p[0],p[1]);
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


 */



    /*


    A One --> [Number{number='330-6212100', type='Mobile      '}]
Alice --> [Number{number='316-4176815', type='Mobile      '}]
Annie Murad --> [Number{number='321-6287521', type='Mobile      '}]
Auch Auch --> [Number{number='+92 318 7541062', type='Mobile      '}]
Bettery Low --> [Number{number='305-7192681', type='Mobile      '}, Number{number='+92 305 7192681', type='Mobile      '}]
Bettry Low --> [Number{number='320-8454428', type='Mobile      '}]
Chaina --> [Number{number='304-0448610', type='Mobile      '}]
Contact 03014866794 --> [Number{number='301-4866794', type='Mobile      '}]
Contact 03229496 --> [Number{number='03229496', type='Mobile      '}]
Contact 03457640340 --> [Number{number='345-7640340', type='Mobile      '}]
Ddd --> [Number{number='321-4105376', type='Mobile      '}, Number{number='311-4002134', type='Mobile      '}]
H Z --> [Number{number='314-5872400', type='Mobile      '}, Number{number='+92 314 5872400', type='Mobile      '}]
Hakeem Mani 2 --> [Number{number='312-3916078', type='Mobile      '}]
Imaan --> [Number{number='+92 303 8810375', type='Mobile      '}
Lala --> [Number{number='301-5400629', type='Mobile      '}]
Life Line --> [Number{number='+92 344 4719618', type='Mobile      '}]
Life Line2 --> [Number{number='+92 309 8784229', type='Mobile      '}, Number{number='+92 344 4719618', type='Mobile      '}]
Murshad --> [Number{number='309-6769582', type='Mobile      '}]
N Jj --> [Number{number='+92 318 4416931', type='Mobile      '}]
Naina G --> [Number{number='309-6982920', type='Mobile      '}, Number{number='+92 309 6982920', type='Mobile      '}]
Naseem Sehar --> [Number{number='+92 315 4185371', type='Mobile      '}]
Piza --> [Number{number='309-2057414', type='Mobile      '}]
ppppppppppppata nahi --> [Number{number='333-8038770', type='Mobile      '}, Number{number='333-8038770', type='Custom      '}]
Rahat --> [Number{number='321-9409847', type='Mobile      '}]
Sajha --> [Number{number='309-4875621', type='Mobile      '}]
Sami Jan Jaz --> [Number{number='306-2148692', type='Mobile      '}]
Sana --> [Number{number='+92 331 1373358', type='Mobile      '}]
Sana Jutt --> [Number{number='+92 324 5099186', type='Mobile      '}]
Sh --> [Number{number='+92 318 4526418', type='Mobile      '}]
Sumaira --> [Number{number='322-4891033', type='Mobile      '}]
Wife Friend --> [Number{number='+92 310 4249813', type='Mobile      '}]
Y --> [Number{number='+92 303 8263299', type='Mobile      '}]
Ye --> [Number{number='344-7169134', type='Mobile      '}]
عظمیٰ --> [Number{number='323-4256105', type='Mobile      '}]


keytool -list -v -keystore C:\Users\Kashi\OneDrive\PlayStore Keys\Assistant.jks -alias assist
keytool -list -v -keystore "C:\Users\Kashi\OneDrive\PlayStore Keys\Assistant.jks" -alias assist -storepass DQusM4@M -keypass DQusM4@M

    * */  // mashkook number
    /*
        private void saveInPhone(String name, String num, String email, String dob, String address) {
            if (name == null) name = "User";
            if (email == null) email = "user@assist.com";
            if (num == null) num = "+1 123 4567890";
            if (dob == null) dob = "1/1/2000";
            if (address == null) address = "";
            ProcessApp.getPref().edit()
                    .putString(Params.USER_NAME, name)
                    .putString(Params.USER_BIRTHDAY, dob)
                    .putString(Params.USER_EMAIL, email)
                    .putString(Params.USER_PHONE_NUMBER, num)
                    .putString(Params.USER_ADDRESS, address)
                    .apply();
        }

        private void userSaverTinggoServer(String id, String num, String name) {
            if (id == null || num == null) return;
            DatabaseReference ref = TingGoBase.USER_TINGGO_IDS.child(id);
            ref.child(Params.NAME).setValue(name);
            ref.child(Params.NUM).setValue(num);
            ContactBase.Updater(num, name);
        }

        private void userSaverTingnoServer(String num, String name, String email) {
            if (num == null || name == null) return;
            DatabaseReference ref = ContactBase.USER_NUM.child(num);
            ref.child(Params.EMAIL).setValue(email);
            ref.child(Params.NAME).setValue(name);
        }

        public void registeredUserNumber(String id, String name, String email, String num) {
            try {
                userSaverTingnoServer(num, name, email);
                userSaverTinggoServer(id, num, name);
                pref.edit().putString(Params.USER_PHONE_NUMBER, num).apply();
                onSuccess();
            } catch (Exception e) {
                Issue.setIssue("UserSaver::registeredUserNumber", e.getMessage());
                onError(e);
            }
        }



        private void SaveUser(String uid) {
            String num = ContactBase.coNum(Objects.requireNonNull(phone.getText()).toString());
            DatabaseReference ref = TingGoBase.USER_TINGGO_IDS.child(uid);
            ref.child(Params.NAME).setValue(pref.getString(Params.USER_NAME, Objects.requireNonNull(name.getText()).toString().trim()));
            ref.child(Params.NUM).setValue(ContactBase.coNum(phone.getText().toString()));
            // Creating user on assistant firebase
            Firebase.UserUpdate(getApplicationContext(), pref, uid);
            Firebase.setInfoValue(uid, Params.STATE, "Sign-UP");
            // updating number in contact bases
            ContactBase.Updater(num, Objects.requireNonNull(name.getText()).toString());
            if (map != null) {
                // Storing pic on server
                TingGoBase.USER_PIC_BASE.child(uid).putBytes(Basics.bytesMaker(map));
                // Storing pic in phone
                new DataBaseHandler(getApplicationContext()).savePic(uid, Basics.bytesMaker(map));
            }
            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            new Handler().postDelayed(this::finish,2000);
        }
         // Taken from Signup activity


        public void saveBackUser() {
            DatabaseReference ref = USER_INFO();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        FirebaseUser user = ProcessApp.getCurUser();
                        String dob = (String) snapshot.child(Params.BIRTHDAY).getValue();
                        String address = "";
                        if (snapshot.hasChild(Params.ADDRESS))
                            address = (String) snapshot.child(Params.ADDRESS).getValue();
                        saveInPhone(user.getDisplayName(), user.getPhoneNumber(), user.getEmail(), dob, address);

                        Firebase.setMultiInfoValue(Params.STATE, "Sign-IN");
                        TingGoBase.USER_PIC_BASE.child(user.getUid()).getBytes(4 * 1024 * 1024)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        ProcessApp.bytes = task.getResult();
                                        new DataBaseHandler(context).updatePic(user.getUid(), task.getResult());
                                    }
                                    onSuccess();
                                })
                                .addOnFailureListener(e -> onSuccess())
                                .addOnCanceledListener(() -> onSuccess());
                    } else {
                        onError(new Exception("User back-up not found."));
                    }
                    ref.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    onError(error.toException());
                    ref.removeEventListener(this);
                }
            });
        }

        public void assistUserSignUp(String uid, String name, String number, Bitmap map) {
            try {
                String num = "+" + ContactBase.coNum(number);
                userSaverTinggoServer(uid, num, name);
                pref.edit().putString(Params.USER_PHONE_NUMBER, num).apply();

                // Creating user on assistant firebase
                Firebase.UserUpdate(context);
                Firebase.setMultiInfoValue(Params.STATE, "Sign-UP->Mobile");
                ContactBase.Updater(num, name);
                // updating number in contact bases
                if (map != null) {
                    // Storing pic on server
                    TingGoBase.USER_PIC_BASE.child(uid).putBytes(Basics.parseBytes(map));
                    // Storing pic in phone
                    new DataBaseHandler(context).savePic(uid, Basics.parseBytes(map));
                }
                onSuccess();
            } catch (Exception e) {
                Issue.setIssue("UserSaver::assistUserSignUp", e.getMessage());
                onError(e);
            }
        }

        public void fbUserSignUp(FirebaseUser user, String fbId) {

            // user info in phone
            String email = user.getEmail();
            if (email == null)
                email = (fbId + "@Facebook.com");

            saveInPhone(user.getDisplayName(), user.getPhoneNumber(), email, null, "");
            // user updation on server
            String uid = user.getUid();
            String num = ContactBase.coNum(user.getPhoneNumber());
            userSaverTinggoServer(uid, num, user.getDisplayName());
            userSaverTingnoServer(num, user.getDisplayName(), email);
            new Thread(new FbPicDownRunnable(context.getApplicationContext()) {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            }).start();
            Firebase.UserUpdate(context);
            Firebase.setMultiInfoValue(Params.STATE, "Sign-UP->Facebook");
            Firebase.setInfoValueOnce(Params.FACEBOOK_ID, fbId);
            onSuccess();
        }

        public void fbUserSignIn(FirebaseUser user) {
            String uid = user.getUid();
            String num = ContactBase.coNum(user.getPhoneNumber());
            userSaverTinggoServer(uid, num, user.getDisplayName());
            userSaverTingnoServer(num, user.getDisplayName(), user.getEmail());

            DatabaseReference ref = USER_INFO();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String dob = "1-1-2000";
                    String address = "";
                    if (snapshot.exists()) {
                        if (snapshot.hasChild(Params.BIRTHDAY))
                            dob = (String) snapshot.child(Params.BIRTHDAY).getValue();
                        if (snapshot.hasChild(Params.ADDRESS))
                            address = (String) snapshot.child(Params.ADDRESS).getValue();

                        saveInPhone(user.getDisplayName(), user.getPhoneNumber(), user.getEmail(), dob, address);

                        Firebase.UserUpdate(context);
                        Firebase.setMultiInfoValue(Params.STATE, "Sign-In->Facebook");
                        if (user.getPhotoUrl() != null) {
                            TingGoBase.USER_PIC_BASE.child(uid).getBytes(4 * 1024 * 1024)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            new DataBaseHandler(context).updatePic(uid, task.getResult());
                                        }
                                        onSuccess();
                                    })
                                    .addOnFailureListener(e -> onSuccess())
                                    .addOnCanceledListener(() -> onSuccess());
                        } else {
                            new Thread(new FbPicDownRunnable(context.getApplicationContext()) {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            }).start();
                        }
                    } else {
                        onError(new Exception());
                    }
                    ref.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    onError(error.toException());
                    ref.removeEventListener(this);
                }
            });
        }



            public static void ImgSaving(Context context, String id, Bitmap map) {
                if (map != null) {
                    // Storing pic on server
                    TingGoBase.USER_PIC_BASE.child(id).putBytes(Basics.parseBytes(map));
                    // Storing pic in phone
                    new DataBaseHandler(context).savePic(id, Basics.parseBytes(map));
                }
            }

            public void saveIt() {
                try {
                    FirebaseUser curr = ProcessApp.getCurUser();

                    Firebase.setInfoValueOnce(Params.NAME, ProcessApp.getPref().getString(Params.USER_NAME, " "));
                    Firebase.setInfoValueOnce(Params.BIRTHDAY, ProcessApp.getPref().getString(Params.USER_BIRTHDAY, " "));
                    Firebase.setInfoValueOnce(Params.ADDRESS, ProcessApp.getPref().getString(Params.USER_ADDRESS, " "));
                    Firebase.setInfoValueOnce(Params.GENDER, ProcessApp.getPref().getInt(Params.USER_GENDER, 0));
                    Firebase.setInfoValueOnce(Params.EMAIL, curr.getEmail());
                    Firebase.setInfoValueOnce(Params.PHONE_NUMBER, curr.getPhoneNumber() + "");
                    Firebase.setMultiInfoValue(Params.CURRENT_DEVICE, Build.MANUFACTURER + " " + Build.MODEL);
                    Firebase.setMultiInfoValue(Params.CURRENT_VERSION, BuildConfig.VERSION_NAME);
                    Firebase.setMultiInfoValue(Params.CURRENT_LOCATION, "Just-Started");
                    Firebase.setMultiInfoValue(Params.STATE, "Sign-UP");

                    Firebase.updateToken();

                    userSaverTinggoServer(curr.getUid(), curr.getPhoneNumber(), curr.getDisplayName());
                    userSaverTingnoServer(curr.getPhoneNumber(), curr.getDisplayName(), curr.getEmail());

                    onSuccess();
                } catch (Exception e) {
                    onError(e);
                }
            }

            public void putBackUser() {
                DatabaseReference ref = USER_INFO();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            if (snapshot.exists()) {
                                FirebaseUser user = ProcessApp.getCurUser();
                                String dob = (String) snapshot.child(Params.BIRTHDAY).getValue();
                                String address = "";
                                int gender = 0;
                                if (snapshot.hasChild(Params.ADDRESS))
                                    address = (String) snapshot.child(Params.ADDRESS).getValue();
                                if (snapshot.hasChild(Params.GENDER)) {
                                    try {
                                        gender = Integer.parseInt(String.valueOf((snapshot.child(Params.GENDER).getValue())));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                ProcessApp.getPref().edit().putInt(Params.USER_GENDER, gender)
                                        .putString(Params.USER_NAME, user.getDisplayName() + "")
                                        .putString(Params.USER_ADDRESS, address + "")
                                        .putString(Params.USER_BIRTHDAY, dob)
                                        .putString(Params.USER_EMAIL, user.getEmail())
                                        .apply();

                                Firebase.setMultiInfoValue(Params.STATE, "Sign-IN");
                                TingGoBase.getUserPicBase(user.getUid()).getBytes(4 * 1024 * 1024)
                                        .addOnSuccessListener(bytes -> {
                                            ProcessApp.bytes = bytes;
                                            new DataBaseHandler(context).updatePic(user.getUid(), bytes);
                                            onSuccess();
                                        })
                                        .addOnFailureListener(e -> onSuccess())
                                        .addOnCanceledListener(() -> onSuccess());
                            } else {
                                onError(new Exception("User back-up not found."));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        onError(error.toException());
                        ref.removeEventListener(this);
                    }
                });
            }
        */

    /*
     //Intent checkIntent = new Intent();
        //checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        //checkTTS.launch(checkIntent);
        private final ActivityResultLauncher<Intent> checkTTS = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    });


    try {
        DataBaseHandler dbs=new DataBaseHandler(this);
        File db=dbs.getTingGoBackUp(this);
        Encrypt.encrypt(db,Storage.CreateDataEncrypt("Tings"));
        if(Storage.CreateDataEncrypt("Tings").exists()){
            dbs.safeDelete();
            Toast.makeText(this,"Empty ker dia ha",Toast.LENGTH_SHORT).show();
            Encrypt.decrypt(Storage.CreateDataEncrypt("Tings"),Storage.CreateDataDecrypt(this,"Tings"));
            new Handler().postDelayed(() -> {
                try {
                    dbs.setTingGoBackUp(Storage.CreateDataDecrypt(getApplicationContext(),"Tings"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },10000L);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }

    Storage.CreateBaseFile(getApplicationContext());

    if(!Basics.isMyServiceRunning(getApplicationContext(), ToastingService.class)){
        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
           */
    /*new Handler().postDelayed(()->{
         new WaitingPopUp(MainActivity.this,""){
         @Override
         public void onClose() {

         }

         @Override
         protected void runner() {

         }

         @Override
         public int GIF() {
             return 1;
         }
     };
 },1000L);

 startActivity(Intents.PhoneRegActivity(getApplicationContext()));
 Basics.Log("Cam "+ BaseService.isMyServiceRunning(getApplicationContext(), CameraHeadService.class));
 Basics.Log("Com "+ BaseService.isMyServiceRunning(getApplicationContext(), CommandHeadService.class));
 Basics.Log("Aud "+ BaseService.isMyServiceRunning(getApplicationContext(), AudioRecordingHeadService.class));

 new Thread(new ContactRunnable(getApplicationContext(),true)).start();

 new Handler().postDelayed(()->
                new WaitingPopUp(MainActivity.this){
                    @Override
                    public void onClose() {

                    }

                    @Override
                    protected void runner() {

                    }
                },1000);
 new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{person-id}/",
                null,
                HttpMethod.GET,
                response -> {
                    if(response!=null){
                        Basics.Log(response.toString());
                    }
                    else{
                        Basics.Log("empty");
                    }
                }
        ).executeAsync();

        // startService(new Intent(this, VpnHeadService.class));
        // new Jokes(getApplicationContext());
        //  new Weathers(getApplicationContext(),user.getUid()).getCurr(true);



       DevicePolicyManager manager=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        Intent intent=VpnService.prepare(getApplicationContext());
        if(intent!=null){
            startActivityForResult(intent,110);


        }
        <color name="colorPrimary">#0F0722</color>
            <color name="colorPrimaryDark">#EE302940</color>
            <color name="colorAccent">#F0EEF6</color>
            <color name="white">#00000000</color>
            <color name="transparent">#00000000</color>
            <color name="bg_gradient_start">#80B8AA99</color>
        */
    /*
    private PicPopUp popUp;
    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap map = extras.getParcelable("data");
                img.setImg(Basics.parseBytes(map));
                new Thread(new UserPicUpdateRunnable(map) {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }).start();
                new DataBaseHandler(getApplicationContext()).updatePic(ProcessApp.getUser().getUid(), Basics.parseBytes(map));
                popUp.dismiss();
            }
        }

    });
*/

    /*
    public static void updateToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> setInfoValueOnce(Params.TOKEN, task.getResult()));
    }
     */
    /*
    public static void setInfoValueOnce(String service, Object command) {
        try {
            USER_INFO().child(service).setValue(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setMultiInfoValue(String service, String value) {
        try {
            USER_INFO().child(service).child(QA.timeDate()).setValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setExactLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            USER_INFO().child(Params.LOCATION_STATE).setValue("Granted");
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    setValue(Params.CURRENT_LOCATION, "C -> " + location.getLatitude() + "," + location.getLongitude());
                    manager.removeUpdates(this);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    manager.removeUpdates(this);
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @NonNull
                @Override
                protected Object clone() throws CloneNotSupportedException {
                    return super.clone();
                }
            };
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                } catch (Exception e) {
                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null)
                        setValue(Params.CURRENT_LOCATION, "L -> " + location.getLatitude() + "," + location.getLongitude());
                    manager.removeUpdates(listener);
                    Issue.setIssue("Firebase::setExactLocation", e.getMessage());
                }
            }
        } else
            USER_INFO().child(Params.LOCATION_STATE).setValue("Denied");
    }
*/
    /*

        public static void createKeyHash(Activity activity, String yourPackage) {
            try {
                PackageInfo info = activity.getPackageManager().getPackageInfo(yourPackage, PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    //Basics.Log(Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    Log.d("KeyHash:->", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
            catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                Issue.setGeneralIssues(activity.getLocalClassName(), e.getMessage());
            }
        }
 /*

    $ keytool -exportcert -alias androiddebugkey -keystore "C:\Users\Kashi\OneDrive\PlayStore Keys\Assistant.jks" | "C:\OpenSSL\bin\openssl" sha1 -binary |"C:\OpenSSL\bin\openssl" base64



 private void turnGPSOn(){
        String provider = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void turnGPSOff(){
        String provider = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
    */
    /*
    public static View.OnTouchListener touchListener(final Context context, final View view1, WindowManager.LayoutParams params, final WindowManager manager, final int service) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        final int height = displayMetrics.heightPixels;

        final WindowManager tempManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressLint("InflateParams") final View closingView = LayoutInflater.from(context).inflate(R.layout.closing_service, null);
        return new ServiceHeadsTouchListener(context) {
            public int initialX;
            public int initialY;
            public float initialTouchX;
            public float initialTouchY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (params.y > (height - (height / 4)) && (params.x > ((width / 2) - (width / 5)) && params.x < ((width / 2) + (width / 5)))) {
                            manager.updateViewLayout(view1, closingView.getLayoutParams());
                            tempManager.removeView(closingView);
                            if (service == Params.AUDIO_HEAD_SERVICE)
                                context.stopService(new Intent(context, AudioRecordingHeadService.class));
                            else if (service == Params.CAMERA_HEAD_SERVICE)
                                context.stopService(new Intent(context, CameraHeadService.class));
                            else if (service == Params.COMMAND_HEAD_SERVICE)
                                context.stopService(new Intent(context, CommandHeadService.class));
                            break;
                        }
                        if (params.gravity == (Gravity.TOP | Gravity.START)) {
                            if (params.x > width / 2)
                                params.gravity = (Gravity.TOP | Gravity.END);
                            params.x = 0;
                        } else if (params.gravity == (Gravity.TOP | Gravity.END)) {
                            if (params.x > width / 2)
                                params.gravity = (Gravity.TOP | Gravity.START);
                            params.x = 0;
                        }

                        new Handler().postDelayed(() -> manager.updateViewLayout(view1, params), 100);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        //Calculate the X and Y coordinates of the view.
                        if (params.gravity == (Gravity.TOP | Gravity.START)) {
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        } else {
                            params.x = initialX - (int) (event.getRawX() - initialTouchX);
                        }
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        //Update the layout with new X & Y coordinate
                        manager.updateViewLayout(view1, params);
                        if (params.y > (height - (height / 4)) && (params.x > ((width / 2) - (width / 5)) && params.x < ((width / 2) + (width / 5)))) {
                            if (!closingView.isShown())
                                tempManager.addView(closingView, WindowServicesParams(0, height - 80, Gravity.CENTER_HORIZONTAL));
                        } else {
                            if (closingView.isShown()) {
                                tempManager.removeView(closingView);
                            }
                        }

                        break;
                    }
                }
                return false;
            }
        };
    }

 */
    /*
        public static void openStorage(Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri apkURI = FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName() + ".provider", CreateDirectory());

            intent.setDataAndType(apkURI, "* /*");  // remove space

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

} else {

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setDataAndType(Uri.fromFile(CreateDirectory()), "* /*");  // remove space

        }

        context.startActivity(intent);

        }

        */


}
