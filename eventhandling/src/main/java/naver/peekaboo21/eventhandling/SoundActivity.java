package naver.peekaboo21.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class SoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        //여기에서만 사용하므로 따로 변수 선언은...
        //진동 버튼의 클릭 이벤트 처리
        Button btnVib = (Button)findViewById(R.id.btnVib);
        btnVib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(2000);
            }
        });
        //효과음 버튼의 클릭 이벤트 처리
        Button btnAla = (Button)findViewById(R.id.btnAla);
        btnAla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //효과음 재생
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();
            }
        });
        //
        Button btnSound = (Button)findViewById(R.id.btnSound);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //노래 재생
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.song);
                player.start();
            }
        });
    }
}