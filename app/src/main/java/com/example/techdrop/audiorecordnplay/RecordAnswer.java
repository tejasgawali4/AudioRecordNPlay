package com.example.techdrop.audiorecordnplay;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.renderscript.Allocation;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.AUDIO_SERVICE;
import static java.lang.Thread.sleep;

public class RecordAnswer extends Fragment {


  private ImageView imageButton;
  private TextView textView;
  private MediaRecorder mediaRecorder;
  private String outputfile;
  AudioManager audioManager;
  MediaPlayer mediaPlayer;
  int no;
  String path;

  public RecordAnswer() {
    // Required empty public constructor
  }

  @SuppressLint("NewApi")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_record_answer, null);
    imageButton = view.findViewById(R.id.imageView);
    textView = view.findViewById(R.id.textView);
    audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);

    imageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (textView.getText().equals("Click button to start recording")){
              textView.setText("Recording...");
              outputfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording_"+no+".mp3";
              if(checkPermission()) {
                MediaRecorderReady();
                try {
                  mediaRecorder.prepare();
                  mediaRecorder.start();
                } catch (IllegalStateException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
                Toast.makeText(getContext(), "Recording started", Toast.LENGTH_LONG).show();
              } else {
                requestPermission();
              }
              imageButton.setImageResource(R.mipmap.ic_launcher);
              try {
                ListenAnswer();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }

        }else {
              textView.setText("Click button to start recording");
              Toast.makeText(getContext(),"Click button to start recording",Toast.LENGTH_SHORT).show();
              imageButton.setImageResource(R.mipmap.ic_launcher_mic);
              try{
                mediaRecorder.stop();
              }catch (Exception e){//

              }
          }
      }
    });

    return view;
  }

  private void ListenAnswer() throws InterruptedException {

    SetQuestion(1);
    if (mediaPlayer.isPlaying()){
      Thread.sleep(3000);
      SetQuestion(2);
    }else{
      SetQuestion(2);
      Thread.sleep(3000);
    }
    if (mediaPlayer.isPlaying()){
      Thread.sleep(3000);
      SetQuestion(3);
    }else{
      SetQuestion(3);
      Thread.sleep(3000);
    }

    if (mediaPlayer.isPlaying()){
      Thread.sleep(3000);
      SetQuestion(4);
    }else{
      SetQuestion(4);
      Thread.sleep(3000);
    }

    if (mediaPlayer.isPlaying()){
      Thread.sleep(3000);
      SetQuestion(5);
    }else{
      SetQuestion(5);
      Thread.sleep(3000);
    }

    if (mediaPlayer.isPlaying()){
      Thread.sleep(3000);
      SetQuestion(6);
    }else{
      SetQuestion(6);
      Thread.sleep(3000);
    }

    Thread.sleep(3000);
    mediaRecorder.stop();
    Toast.makeText(getContext(),"Recorded Successfully..",Toast.LENGTH_SHORT).show();

    //
//    new CountDownTimer(3000, 1000) {
//      public void onTick(long millisUntilFinished) {
//      }
//
//      public void onFinish() {
//        Toast.makeText(getContext(), "Recording Completed",Toast.LENGTH_LONG).show();
//        mediaRecorder.stop();
//      }
//    }.start();

  }

  public Boolean SetQuestion(int x){
      final int t = x++;
      int resID=getResources().getIdentifier("voice00"+t, "raw", getActivity().getPackageName());
      mediaPlayer = new MediaPlayer();
      mediaPlayer.setVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
      mediaPlayer=MediaPlayer.create(getContext(),resID);
      mediaPlayer.start();
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
          mediaPlayer.reset();
          final int resID=getResources().getIdentifier("beep", "raw", getActivity().getPackageName());
          mediaPlayer=MediaPlayer.create(getContext(),resID);
          mediaPlayer.start();
          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              mediaPlayer.reset();
            }
          });
        }
      });
    return true;
  }


  public void MediaRecorderReady(){
    mediaRecorder=new MediaRecorder();
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    mediaRecorder.setOutputFile(outputfile);
  }

  private void requestPermission() {
    ActivityCompat.requestPermissions(getActivity(), new
      String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 1);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
    String permissions[], int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length> 0) {
          boolean StoragePermission = grantResults[0] ==
            PackageManager.PERMISSION_GRANTED;
          boolean RecordPermission = grantResults[1] ==
            PackageManager.PERMISSION_GRANTED;

          if (StoragePermission && RecordPermission) {
            Toast.makeText(getContext(), "Permission Granted",
              Toast.LENGTH_LONG).show();
          } else {
            Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_LONG).show();
          }
        }
        break;
    }
  }

  public boolean checkPermission() {
    int result = ContextCompat.checkSelfPermission(getContext(),
      WRITE_EXTERNAL_STORAGE);
    int result1 = ContextCompat.checkSelfPermission(getContext(),
      RECORD_AUDIO);
    return result == PackageManager.PERMISSION_GRANTED &&
      result1 == PackageManager.PERMISSION_GRANTED;
  }
}
