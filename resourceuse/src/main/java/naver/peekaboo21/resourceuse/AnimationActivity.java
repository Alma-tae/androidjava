package naver.peekaboo21.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        //사용할 뷰 찾아오기
        Button btnScale = (Button)findViewById(R.id.btnScale);
        btnScale.setOnClickListener(new View.OnClickListener() {
            @Override
            //Delegate 이벤트 처리 모델에서 이벤트 처리 메소드의 첫 번째 매개변수는
            //이벤트가 발생한 객체
            public void onClick(View v) {
                //xml로 디자인한 Animation을 가져옵니다
                Animation anim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.scale);
                //애니메이션을 적용
                v.startAnimation(anim);
            }
        });
    }
}