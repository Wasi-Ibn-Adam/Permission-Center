package com.wasitech.permissioncenter.java.com.wasitech.music.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.assist.command.family.Answers;
import com.wasitech.assist.popups.ThemedPopUps;
import com.wasitech.assist.popups.WaitingPopUp;
import com.wasitech.basics.ItemsLongClickPopUp;
import com.wasitech.basics.Storage;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.adapter.SongAdapter;
import com.wasitech.music.classes.Song;
import com.wasitech.music.classes.Sort;
import com.wasitech.music.pop.SortingPop;
import com.wasitech.music.runnable.AudioSongRunnable;
import com.wasitech.music.runnable.VideoSongRunnable;
import com.wasitech.music.service.SongService;
import com.wasitech.theme.Theme;

import java.util.ArrayList;
import java.util.Collections;

public class MusicListAct extends AssistCompatActivity {
    public static final int REC = 0;
    public static final int AUD = 1;
    public static final int VID = 2;
    public static final int GIVEN = 3;
    public static final int UNKNOWN = 4;

    public static int acType = -1;

    public static ArrayList<Song> list;
    private SongAdapter adapter;

    private FloatingActionButton refresh;
    private RecyclerView recyclerView;

    public MusicListAct() {
        super(R.layout.act_general_list_refresh);
    }

    @Override
    protected String titleBarText() {
        return getAcTitle();
    }

    @Override
    public void setViews() {
        try {
            setBack();
            recyclerView = findViewById(R.id.recyclerView);
            refresh = findViewById(R.id.refresh);
        } catch (Exception e) {
            Issue.print(e, MusicListAct.class.getName());
        }
    }

    @Override
    public void setValues() {
        try {
            recyclerView.setHasFixedSize(true);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (getAcType() == VID) {
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                } else
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            } else {
                if (getAcType() == VID) {
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                } else
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
            switch (getAcType()) {
                case REC: {
                    setAdapter(Storage.getRecList());
                    break;
                }
                case AUD: {
                    setAdapter(ProcessApp.saList);
                    break;
                }
                case VID: {
                    setAdapter(ProcessApp.svList);
                    break;
                }
                case GIVEN: {
                    setAdapter(SongService.list);
                    break;
                }
                case UNKNOWN: {
                    setAdapter(ProcessApp.saList);
                    unknownData();
                    break;
                }
            }
            refresh.setImageResource(R.drawable.refresh);
            setToolbar();
        } catch (Exception e) {
            Issue.print(e, MusicListAct.class.getName());
        }
    }

    @Override
    public void setExtras() {

    }

    @Override
    public void setPermission() {

    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(MusicListAct.this);
        } catch (Exception e) {
            Issue.print(e, MusicListAct.class.getName());
        }
    }

    @Override
    public void setActions() {
        try {
            if (MusicListAct.list == null || MusicListAct.list.size() == 0)
                reLoadSongs();
            refresh.setOnClickListener(v -> reLoadSongs());
        } catch (Exception e) {
            Issue.print(e, MusicListAct.class.getName());
        }
    }

    public static class Intents {
        public static final int SHOW = 0, PLAY = 1;
        public static final String TYPE = "_type";
        public static final String DATA = "_data";
        public static final String ACTION = "_action";

        public static Intent Recording(Context context, int action) {
            MusicListAct.acType = REC;
            return new Intent(context, MusicListAct.class)
                    .putExtra(TYPE, REC)
                    .putExtra(ACTION, action)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        public static Intent Audio(Context context, int action) {
            MusicListAct.acType = AUD;
            return new Intent(context, MusicListAct.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(TYPE, AUD)
                    .putExtra(ACTION, action);
        }

        public static Intent Audio(Context context, int action, String extra) {
            MusicListAct.acType = AUD;
            return new Intent(context, MusicListAct.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(TYPE, AUD)
                    .putExtra(DATA, extra)
                    .putExtra(ACTION, action);
        }

        public static Intent Video(Context context, int action) {
            MusicListAct.acType = VID;
            return new Intent(context, MusicListAct.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(TYPE, VID)
                    .putExtra(ACTION, action);
        }

        public static Intent search(Context context, int action, String extra) {
            MusicListAct.acType = UNKNOWN;
            return new Intent(context, MusicListAct.class)
                    .putExtra(TYPE, UNKNOWN)
                    .putExtra(DATA, extra)
                    .putExtra(ACTION, action)
                    //.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    ;
        }

        public static Intent AudioSelected(Context context) {
            MusicListAct.acType = GIVEN;
            return new Intent(context, MusicListAct.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .putExtra(TYPE, GIVEN);
        }
    }

    private int getAcType() {
        return getIntent().getIntExtra(Intents.TYPE, MusicListAct.acType);
    }

    private int getAcAction() {
        return getIntent().getIntExtra(Intents.ACTION, Intents.SHOW);
    }

    private String getAcData() {
        return getIntent().getStringExtra(Intents.DATA);
    }

    private String getAcTitle() {
        switch (getAcType()) {
            case REC: {
                return "Recording List";
            }
            case AUD: {
                return "Audio List";
            }
            case VID: {
                return "Video List";
            }
            default: {
                return "Music List";
            }
        }
    }

    // edit require
    private void unknownData() {
        try {
            String data = getAcData();
            if (data != null)
                adapter.getFilter().filter(data);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void reLoadSongs() {
        try {
            switch (getAcType()) {
                case REC: {
                    setAdapter(Storage.getRecList());
                    break;
                }
                case AUD: {
                    runOnUiThread(new AudioSongRunnable(getApplicationContext()) {
                        @Override
                        public void onComplete() {
                            setAdapter(ProcessApp.saList);
                        }
                    });
                    break;
                }
                case VID: {
                    runOnUiThread(new VideoSongRunnable(getApplicationContext()) {
                        @Override
                        public void onComplete() {
                            setAdapter(ProcessApp.svList);
                        }
                    });
                    break;
                }
            }
        } catch (Exception e) {
            Issue.print(e, MusicListAct.class.getName());
        }
    }

    private void setAdapter(ArrayList<Song> list) {
        MusicListAct.list = list;
        Collections.sort(MusicListAct.list, Sort.Compare());
        adapter = new SongAdapter(list) {
            @Override
            protected boolean OnLongClickListener(Song s, int p) {
                try {
                    onLongPress(s, p);
                } catch (Exception e) {
                    Issue.print(e, MusicListAct.class.getName());
                }
                return true;
            }

            @Override
            protected void listSize(int l) {
                EmptyLay(l==0);
                switch (l) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        if (getAcAction() == Intents.PLAY) {
                            Song s = adapter.getSongs().get(0);
                            playing(s, 0);
                        }
                        break;
                    }
                    default: {
                        if (getAcAction() == Intents.PLAY)
                            ProcessApp.talk(getApplicationContext(), "Select Song");
                    }
                }
            }

            @Override
            public void onClick(View v) {
                int i = recyclerView.getChildLayoutPosition(v);
                playing(MusicListAct.list.get(i), i);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void onLongPress(Song s, int i) {
        ItemsLongClickPopUp pop = new ItemsLongClickPopUp(MusicListAct.this) {
            @Override
            protected void OnDetail() {
                try {
                    StringBuilder b = new StringBuilder();
                    {
                        b.append("Name: ").append(s.getTitle());
                        b.append("\nAlbum: ").append(s.getAlbum());
                        b.append("\nArtist: ").append(s.getArtist());
                        b.append("\nDate: ").append(s.getDate());
                        b.append("\nPath: ").append(s.getPath());
                        b.append("\nSize: ").append(Format.sizeToMaxGBs(s.getSize()));
                        b.append("\nDuration: ").append(Format.millisToTime(s.getDuration()));
                    }
                    ThemedPopUps.BackgroundPopup(MusicListAct.this, b.toString());
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
                dismiss();
            }

            @Override
            protected void OnAction() {
                playing(s, i);
                dismiss();
            }

            @Override
            protected void OnDelete() {
                try {
                    MediaScannerConnection.scanFile(getApplicationContext(),
                            new String[]{s.getPath()},
                            null,
                            (p, uri) -> {
                                try {
                                    MusicListAct.list.remove(s);
                                    getContentResolver().delete(uri, null, null);
                                    new Handler(Looper.getMainLooper()).post(() -> setAdapter(MusicListAct.list));
                                } catch (Exception e) {
                                    Basics.toasting(MusicListAct.this, Answers.ASSISTANT.NAME_ONLY() + " has No Access to Delete it.");
                                }
                            });
                } catch (Exception e) {
                    Issue.print(e, MusicListAct.class.getName());
                }
                dismiss();
            }

            @Override
            protected void OnShare() {
                try {
                    Basics.Send.share(MusicListAct.this, s.getPath());
                } catch (Exception e) {
                    Issue.print(e, MusicListAct.class.getName());
                }
                dismiss();
            }

            @Override
            protected int setActionImg() {
                return R.drawable.play;
            }
        };
        if (s.isVideo())
            pop.setDetailVisibility(View.GONE);
    }

    private void playing(Song song, int index) {
        if (song.isVideo()) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(song.getPath()), "video/*");
            setResult(RESULT_CANCELED, new Intent());
            startActivity(intent);
        } else {
            sendBroadcast(SongService.BroadCastIntents.start(getApplicationContext(), adapter.getSongs(), index));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicListAct.list = null;
    }

    protected void setToolbar() {
        Toolbar bar = findViewById(R.id.toolbar);
        bar.inflateMenu(R.menu.search_sort_menu);
        bar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        MenuItem item = bar.getMenu().findItem(R.id.search_item);
        SearchView searchView = new SearchView(this);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_item)
            item.expandActionView();
        else {
            new SortingPop(MusicListAct.this) {
                @Override
                protected void UpdateUi() {
                    MusicListAct.this.update(this);
                }
            };
        }
        return super.onOptionsItemSelected(item);
    }

    private void update(SortingPop pop) {
        new WaitingPopUp(MusicListAct.this, "Wait....") {
            @Override
            public void onClose() {

            }

            @Override
            protected void runner() {
                runOnUiThread(() -> {
                    setAdapter(MusicListAct.list);
                    if (SongService.player != null) {
                        int n = SongService.index;
                        Song s = SongService.list.get(n);
                        n = MusicListAct.list.indexOf(s);
                        SongService.index = n;
                        SongService.list = MusicListAct.list;
                    }
                    dismiss();
                    pop.dismiss();
                });
            }
        };
    }
}