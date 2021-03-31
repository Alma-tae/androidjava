package naver.peekaboo21.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class ItemActivity extends AppCompatActivity {
    EditText itemId, itemName, itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemId = (EditText)findViewById(R.id.itemId);
        itemName = (EditText)findViewById(R.id.itemName);
        itemQuantity = (EditText)findViewById(R.id.itemQuantity);
        //삽입 버튼을 누를 때 수행할 내용
        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB연동 클래스 객체를 생성
                MyDBHandler handler = new MyDBHandler(ItemActivity.this);
                //삽입할 데이터 만들기
                Item item = new Item();
                item.setName(itemName.getText().toString());
                item.setQuantity(Integer.parseInt(itemQuantity.getText().toString()));
                //데이터 삽입
                handler.addItem(item);
                //입력 도구들을 초기화
                itemName.setText("");
                itemQuantity.setText("");
                itemId.setText("");
                //키보드 숨기기
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(itemName.getWindowToken(),0);
                imm.hideSoftInputFromWindow(itemQuantity.getWindowToken(),0);
                imm.hideSoftInputFromWindow(itemId.getWindowToken(),0);
                //메시지 출력
                Snackbar.make(v, "삽입 성공", Snackbar.LENGTH_LONG).show();
            }
        });

        Button btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
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