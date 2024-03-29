package com.example.a15puzzletolga;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import android.support.v7.app.AppCompatActivity;

import static com.example.a15puzzletolga.Game.isWin;
import static com.example.a15puzzletolga.Game.show;

public class MainActivity extends AppCompatActivity {
    private Button exit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TimeView ab = findViewById(R.id.timeView1);
        final TextView ab2 = findViewById(R.id.textView1);
        final Score ab3 = findViewById(R.id.ScoreS);
        SquareView[][] sv = new SquareView[4][4];

        sv[0][0] = findViewById(R.id.square0);
        sv[0][1] = findViewById(R.id.square1);
        sv[0][2] = findViewById(R.id.square2);
        sv[0][3] = findViewById(R.id.square3);

        sv[1][0] = findViewById(R.id.square4);
        sv[1][1] = findViewById(R.id.square5);
        sv[1][2] = findViewById(R.id.square6);
        sv[1][3] = findViewById(R.id.square7);

        sv[2][0] = findViewById(R.id.square8);
        sv[2][1] = findViewById(R.id.square9);
        sv[2][2] = findViewById(R.id.square10);
        sv[2][3] = findViewById(R.id.square11);

        sv[3][0] = findViewById(R.id.square12);
        sv[3][1] = findViewById(R.id.square13);
        sv[3][2] = findViewById(R.id.square14);
        sv[3][3] = findViewById(R.id.square15);

        Game.setGame(sv);

        try {
            if(getIntent().getExtras().getInt("keyGame") == 1) {
                isWin();
                Intent İntent=new Intent(MainActivity.this,WinScreen.class);
                startActivity(İntent);
            } else show();
        } catch (Exception e) {
            show();
        }

        @SuppressLint("HandlerLeak") final Handler myHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1998) {
                    ab.refresh();
                    ab2.setText("Moves:" + Config.count);
                    ab3.scorecalculate();
                }
                super.handleMessage(msg);
            }
        };
        Thread t = new Thread() {
            public void run() {
                while (true) {
                    if (Config.start) {
                        Message msg = new Message();
                        msg.what = 1998;
                        myHandler.sendMessage(msg);
                    }

                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public void scrambleOnClick(View view) {
        Game.scramble();
    }

    /*public int scorecalculate() {
        long time = System.currentTimeMillis() - Config.startTime;
        int count= Config.count;
        int calculate= (int) (10000-((time/1000)*count));
        return calculate;
    }*/
}