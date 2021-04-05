package naver.peekaboo21.webserverparameter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ItemPostActivity extends AppCompatActivity {

    //입력을 위한 뷰
    EditText itemnameinput, priceinput, descriptioninput;

    //출력을 위한 뷰
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_post);

        itemnameinput = (EditText)findViewById(R.id.itemnameinput);
        priceinput = (EditText)findViewById(R.id.priceinput);
        descriptioninput = (EditText)findViewById(R.id.descriptioninput);

        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}