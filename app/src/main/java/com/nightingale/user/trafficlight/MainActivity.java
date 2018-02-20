package com.nightingale.user.trafficlight;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int STATUS_NONE = 0; // start position
    final int STATUS_FIRST = 1; // only green
    final int STATUS_SECOND = 2; // green plus yellow
    final int STATUS_THIRD = 3; // only red
    final int STATUS_FOURTH = 4; // red plus yellow

    Handler h;

    Boolean flag = false;

    TextView tvStatus;
    ProgressBar pbConnect;
    Button btnOnOff;
    Button btnTop;
    Button btnMid;
    Button btnBot;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOnOff = (Button) findViewById(R.id.btnOnOff);
        btnTop = (Button) findViewById(R.id.btnTop);
        btnMid = (Button) findViewById(R.id.btnMid);
        btnBot = (Button) findViewById(R.id.btnBot);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnOnOff.setEnabled(true);
                        btnTop.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnMid.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnBot.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;

                    case STATUS_FIRST:
                        btnTop.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnMid.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnBot.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case STATUS_SECOND:
                        btnTop.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnMid.setBackgroundColor(getResources().getColor(R.color.yellow));
                        btnBot.setBackgroundColor(getResources().getColor(R.color.green));
                        break;

                    case STATUS_THIRD:
                        btnTop.setBackgroundColor(getResources().getColor(R.color.red));
                        btnMid.setBackgroundColor(getResources().getColor(R.color.gray));
                        btnBot.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;

                    case STATUS_FOURTH:
                        btnTop.setBackgroundColor(getResources().getColor(R.color.red));
                        btnMid.setBackgroundColor(getResources().getColor(R.color.yellow));
                        btnBot.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;

                }
            };
        };
        h.sendEmptyMessage(STATUS_NONE);

        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnOnOff.getTag().toString().equals("On")){
                    btnOnOff.setText("Off");
                    btnOnOff.setTag("Off");
                    flag = true;

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                do {
                                    if(!flag){
                                        break;
                                    }
                                    h.sendEmptyMessage(STATUS_FIRST);
                                    TimeUnit.SECONDS.sleep(7);

                                    if(!flag){
                                        break;
                                    }
                                    h.sendEmptyMessage(STATUS_SECOND);
                                    TimeUnit.SECONDS.sleep(3);

                                    if(!flag){
                                        break;
                                    }
                                    h.sendEmptyMessage(STATUS_THIRD);
                                    TimeUnit.SECONDS.sleep(7);

                                    if(!flag){
                                        break;
                                    }
                                    h.sendEmptyMessage(STATUS_FOURTH);
                                    TimeUnit.SECONDS.sleep(3);
                                } while (flag);

                                h.sendEmptyMessage(STATUS_NONE);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();

                } else {
                    btnOnOff.setText("On");
                    btnOnOff.setTag("On");
                    btnOnOff.setEnabled(false);
                    flag = false;
                }
            }
        });
    }
}
