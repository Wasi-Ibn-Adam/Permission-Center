package com.wasitech.permissioncenter.java.com.wasitech.register.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentTransaction;

import com.wasitech.assist.R;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.command.family.Intents;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.assist.runnables.UserPicUpdateRunnable;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.basics.fragments.AssistFragment;
import com.wasitech.database.CloudDB;
import com.wasitech.database.LocalDB;
import com.wasitech.register.classes.UserProfile;
import com.wasitech.register.fragment.ProfileFirst;
import com.wasitech.register.fragment.ProfileSecond;
import com.wasitech.register.fragment.ProfileThird;
import com.wasitech.theme.Theme;

public class Profile extends AssistCompatActivity implements AssistFragment.TaskEvents {
    public static int CODE = 0;
    public static byte[] img;
    private WaitingPopUp pop;


    public Profile() {
        super(R.layout.act_r_1_frag);
    }
    @Override
    protected String titleBarText() {
        return "";
    }
    @Override
    public void onComplete() {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (Profile.CODE) {
                default:
                case 0: {
                    transaction.replace(R.id.fragment, ProfileFirst.getInstance(true));
                    break;
                }
                case ProfileFirst.COMPLETE: {
                    transaction.replace(R.id.fragment, ProfileSecond.getInstance(true));
                    break;
                }
                case ProfileSecond.COMPLETE: {
                    transaction.replace(R.id.fragment, ProfileThird.getInstance(true));
                    break;
                }
                case ProfileThird.COMPLETE: {
                    saveUser();
                    Profile.CODE = 0;
                    return;
                }
            }
            transaction.commitNow();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void onAction() {

    }

    @Override
    public void onPrev() {
        onBackPressed();
    }

    private void saveUser() {
        pop = new WaitingPopUp(Profile.this) {
            @Override
            protected void runner() {
                new CloudDB.ProfileCenter(getApplicationContext()).uploadProfile();
                runOnUiThread(new UserPicUpdateRunnable(Profile.img) {
                    @Override
                    public void onSuccess() {
                        new LocalDB(getApplicationContext()).savePic(ProcessApp.getUser().getUid(), Profile.img);
                    }

                    @Override
                    public void onError(Exception e) {
                        NotifyToUser("Unable to Set Profile Pic.");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        CloudDB.Tinggo.addTingUser();
                        dismiss();
                    }
                });
            }

            @Override
            public void onClose() {
                startActivity(Intents.MainActivity(getApplicationContext(), false));
            }
        };

    }

    @Override
    public void onBackPressed() {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (Profile.CODE) {
                default:
                case 0: {
                    Profile.CODE = 0;
                    finish();
                    return;
                }
                case ProfileFirst.COMPLETE: {
                    Profile.CODE = 0;
                    transaction.replace(R.id.fragment, ProfileFirst.getInstance(true));
                    break;
                }
                case ProfileSecond.COMPLETE: {
                    Profile.CODE = ProfileFirst.COMPLETE;
                    transaction.replace(R.id.fragment, ProfileSecond.getInstance(true));
                    break;
                }
                case ProfileThird.COMPLETE: {
                    return;
                }
            }
            transaction.commitNow();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(Profile.this);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try {
            int type = getIntent().getIntExtra("type", Params.TYPE_EMAIL);
            ProcessApp.getPref().edit().putInt(Params.AC_TYPE, type).apply();
            switch (type) {
                case Params.TYPE_FB: {
                    Basics.Log( "fb");
                    break;
                }
                case Params.TYPE_GOOGLE: {
                    Basics.Log("google");
                    break;
                }
                default:
                case Params.TYPE_EMAIL:
                case Params.TYPE_PHONE: {
                    Basics.Log("empty profile");
                    break;
                }
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions() {
        onComplete();
    }

    @Override
    public void setExtras() {

    }

    @Override
    public void setPermission() {

    }

    public static class Params {
        public static final String FIRST_NAME = "user_fname";
        public static final String MIDDLE_NAME = "user_mname";
        public static final String LAST_NAME = "user_lname";
        public static final String GENDER = "user_gender";
        public static final String INTEREST = "user_interest";
        public static final String DOB_D = "user_date";
        public static final String DOB_M = "user_month";
        public static final String DOB_Y = "user_year";
        public static final String HS_NO = "user_hs";
        public static final String ST_NO = "user_st";
        public static final String TOWN = "user_town";
        public static final String CITY = "user_city";
        public static final String STATE = "user_state";
        public static final String COUNTRY = "user_country";
        public static final String RELIGION = "user_religion";
        public static final String AC_TYPE = "user_account_type";

        public static final int MALE = 0;
        public static final int FEMALE = 1;
        public static final int TRANSGENDER = 2;
        public static final int BOTH_MALE_FEMALE = 3;
        public static final int BOTH_MALE_TRANS = 4;
        public static final int BOTH_FEMALE_TRANS = 5;
        public static final int ALL = 6;

        public static final int MUSLIM = 0;
        public static final int CHRISTIAN = 1;
        public static final int HINDU = 2;
        public static final int BUDDHA = 3;
        public static final int OTHER = 4;

        public static final int TYPE_EMAIL = 0;
        public static final int TYPE_GOOGLE = 1;
        public static final int TYPE_PHONE = 2;
        public static final int TYPE_FB = 3;

    }

    public static class User {
        public static boolean isProfileValid() {
            return (!firstName().isEmpty()
                    && !lastName().isEmpty()
                    && !town().isEmpty()
                    && !city().isEmpty()
                    && !state().isEmpty()
                    && !country().isEmpty()
                    && dateOfBirth() > 0
                    && monthOfBirth() > 0
                    && yearOfBirth() > 0);
        }

        public static boolean isProfileComplete() {
            return ProcessApp.getCurUser().getPhotoUrl() != null && isProfileValid();
        }

        public static boolean isUserPresent() {
            return ProcessApp.getCurUser() != null;
        }

        public static void setUser(UserProfile profile) {
            ProcessApp.getPref().edit()
                    .putInt(Params.AC_TYPE, profile.getAccountType())
                    .putInt(Params.GENDER, profile.getGender())
                    .putInt(Params.DOB_D, profile.getD())
                    .putInt(Params.DOB_M, profile.getM())
                    .putInt(Params.DOB_Y, profile.getY())
                    .putInt(Params.INTEREST, profile.getInterestedIn())
                    .putInt(Params.HS_NO, profile.getHouse())
                    .putInt(Params.ST_NO, profile.getStreet())
                    .putInt(Params.RELIGION, profile.getReligion())
                    .putString(Params.FIRST_NAME, profile.getfName())
                    .putString(Params.MIDDLE_NAME, profile.getmName())
                    .putString(Params.LAST_NAME, profile.getlName())
                    .putString(Params.TOWN, profile.getTown())
                    .putString(Params.STATE, profile.getState())
                    .putString(Params.CITY, profile.getCity())
                    .putString(Params.COUNTRY, profile.getCountry())
                    .apply();
        }

        public static UserProfile completeProfile() {
            UserProfile profile = new UserProfile();
            profile.setAccountType(ProcessApp.getPref().getInt(Params.AC_TYPE, Params.TYPE_EMAIL));
            profile.setfName(ProcessApp.getPref().getString(Params.FIRST_NAME, ""));
            profile.setmName(ProcessApp.getPref().getString(Params.MIDDLE_NAME, ""));
            profile.setlName(ProcessApp.getPref().getString(Params.LAST_NAME, ""));
            profile.setGender(ProcessApp.getPref().getInt(Params.GENDER, Params.MALE));
            profile.setD(ProcessApp.getPref().getInt(Params.DOB_D, 0));
            profile.setM(ProcessApp.getPref().getInt(Params.DOB_M, 0));
            profile.setY(ProcessApp.getPref().getInt(Params.DOB_Y, 0));
            profile.setInterestedIn(ProcessApp.getPref().getInt(Params.INTEREST, -1));
            profile.setHouse(ProcessApp.getPref().getInt(Params.HS_NO, 0));
            profile.setStreet(ProcessApp.getPref().getInt(Params.ST_NO, 0));
            profile.setTown(ProcessApp.getPref().getString(Params.TOWN, ""));
            profile.setState(ProcessApp.getPref().getString(Params.STATE, ""));
            profile.setCity(ProcessApp.getPref().getString(Params.CITY, ""));
            profile.setCountry(ProcessApp.getPref().getString(Params.COUNTRY, ""));
            profile.setReligion(ProcessApp.getPref().getInt(Params.RELIGION, Params.OTHER));
            return profile;
        }

        public static int acType() {
            return ProcessApp.getPref().getInt(Params.AC_TYPE, Params.TYPE_EMAIL);
        }

        public static String birthDate() {
            return Format.Max2(monthOfBirth()) + "/" + Format.Max2(dateOfBirth()) + "/" + yearOfBirth();
        }

        public static int dateOfBirth() {
            return ProcessApp.getPref().getInt(Params.DOB_D, 0);
        }

        public static int monthOfBirth() {
            return ProcessApp.getPref().getInt(Params.DOB_M, 0);
        }

        public static int yearOfBirth() {
            return ProcessApp.getPref().getInt(Params.DOB_Y, 0);
        }

        public static String fullName() {
            try{
                String mn = ProcessApp.getPref().getString(Params.MIDDLE_NAME, "");
                if (mn == null || mn.isEmpty())
                    return firstName() + " " + lastName();
                else
                    return firstName() + " " + mn + " " + lastName();
            }
            catch (Exception e){
                Issue.print(e,"");
            }
            return "";
        }

        public static String firstName() {
            return ProcessApp.getPref().getString(Params.FIRST_NAME, "");
        }

        public static String middleName() {
            return ProcessApp.getPref().getString(Params.MIDDLE_NAME, "");
        }

        public static String lastName() {
            return ProcessApp.getPref().getString(Params.LAST_NAME, "");
        }

        public static int gender() {
            return ProcessApp.getPref().getInt(Params.GENDER, Params.MALE);
        }

        public static int interest() {
            return ProcessApp.getPref().getInt(Params.INTEREST, -1);
        }

        public static int religion() {
            return ProcessApp.getPref().getInt(Params.RELIGION, Params.OTHER);
        }

        public static String completeAddress() {
            int h = house();
            int s = street();
            String add = "";
            if (h > 0)
                add += "H " + h;
            if (s > 0)
                add += " S " + s;
            return (add + " " + town() + " " + city() + " " + state() + " " + country()).trim();
        }

        public static int street() {
            return ProcessApp.getPref().getInt(Params.ST_NO, 0);
        }

        public static int house() {
            return ProcessApp.getPref().getInt(Params.HS_NO, 0);
        }

        public static String state() {
            return ProcessApp.getPref().getString(Params.STATE, "");
        }

        public static String city() {
            return ProcessApp.getPref().getString(Params.CITY, "");
        }

        public static String country() {
            return ProcessApp.getPref().getString(Params.COUNTRY, "");
        }

        public static String town() {
            return ProcessApp.getPref().getString(Params.TOWN, "");
        }

        public static TingUser getTingUser() {
            TingUser user = new TingUser();
            user.setName(fullName());
            user.setEmail(ProcessApp.getCurUser().getEmail());
            user.setNumber(ProcessApp.getCurUser().getPhoneNumber());
            user.setUid(ProcessApp.getCurUser().getUid());
            if (ProcessApp.getCurUser().getPhotoUrl() != null)
                user.setImagePath(ProcessApp.getCurUser().getPhotoUrl().toString());
            return user;
        }

        public static void removeUser() {
            ProcessApp.getPref().edit()
                    .remove(Params.AC_TYPE)
                    .remove(Params.GENDER)
                    .remove(Params.DOB_D)
                    .remove(Params.DOB_M)
                    .remove(Params.DOB_Y)
                    .remove(Params.INTEREST)
                    .remove(Params.HS_NO)
                    .remove(Params.ST_NO)
                    .remove(Params.RELIGION)
                    .remove(Params.FIRST_NAME)
                    .remove(Params.MIDDLE_NAME)
                    .remove(Params.LAST_NAME)
                    .remove(Params.TOWN)
                    .remove(Params.STATE)
                    .remove(Params.CITY)
                    .remove(Params.COUNTRY)
                    .apply();
        }

        public static boolean isValidDOB() {
            return dateOfBirth() > 0 && monthOfBirth() > 0 && yearOfBirth() > 0;
        }

        public static void setFbId(String fbId) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pop != null && pop.isShowing()) pop.dismiss();
    }


    public static Intent Open(Context context, int type) {
        return new Intent(context, Profile.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("type", type);
    }
}