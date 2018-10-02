package com.example.techdrop.audiorecordnplay;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.googlecode.mp4parser.authoring.Movie;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//  private Button play,stop,record;
  private MediaRecorder mediaRecorder;
  private String outputfile;
  private Toolbar toolbar;
  private TabLayout tabLayout;
  private ViewPager viewPager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    try{
      toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }catch (Exception e){
      //
    }

    viewPager = findViewById(R.id.viewpager);
    setupViewPager(viewPager);

    tabLayout = findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(viewPager);

//    play = findViewById(R.id.play);
//    stop = findViewById(R.id.stop);
//    record = findViewById(R.id.record);
//
//    stop.setEnabled(false);
//    play.setEnabled(false);
//
//    outputfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
//
//    mediaRecorder = new MediaRecorder();
//    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//    mediaRecorder.setOutputFile(outputfile);
//
//    record.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        try {
//          mediaRecorder.prepare();
//          mediaRecorder.start();
//        } catch (IllegalStateException ise) {
//          // make something ...
//        } catch (IOException ioe) {
//          // make something
//        }
//
//        record.setEnabled(false);
//        stop.setEnabled(true);
//        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
//      }
//    });
//
//    stop.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        mediaRecorder.stop();
//        mediaRecorder.release();
//        mediaRecorder = null;
//        record.setEnabled(true);
//        stop.setEnabled(false);
//        play.setEnabled(true);
//        Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
//      }
//    });
//
//
//    play.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//          mediaPlayer.setDataSource(outputfile);
//          mediaPlayer.prepare();
//          mediaPlayer.start();
//          Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//          // make something
//        }
//      }
//    });
  }

  private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFrag(new RecordAnswer(), "Record Answers");
    adapter.addFrag(new ViewRecordings(), "View Recordings");
    viewPager.setAdapter(adapter);
  }

  class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }

  public static void mergeAudio(List<File> filesToMerge) {

    String output = Environment.getExternalStorageDirectory().getAbsolutePath() + "demo_created.mp3";

    while (filesToMerge.size()!=1){

      try {

        String[] Mp3Quesions = new String[]{
          Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp",
          Environment.getExternalStorageDirectory().getAbsolutePath() + "/demo.mp3"
        };

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();

//        for (Movie m : inMovies) {
//          for (Track t : m.getTracks()) {
//            if (t.getHandler().equals("soun")) {
//              audioTracks.add(t);
//            }
//            if (t.getHandler().equals("vide")) {
//              videoTracks.add(t);
//            }
//          }
//        }

        Movie result = new Movie();

        if (!audioTracks.isEmpty()) {
          result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
        }
        if (!videoTracks.isEmpty()) {
          result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
        }

        Container out = new DefaultMp4Builder().build(result);

        FileChannel fc = new RandomAccessFile(output, "rw").getChannel();
        out.writeContainer(fc);
        fc.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
