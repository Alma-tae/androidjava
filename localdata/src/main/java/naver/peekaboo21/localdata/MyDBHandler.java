package naver.peekaboo21.localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {

    //생성자 - SQLiteOpenHandler가 Dafault Constructor가 없어서 반드시 생성
    public MyDBHandler(@Nullable Context context) {
        super(context, "item.db", null, 1);
    }

    //DB를 사용하려고 할 때 DB 파일이 없으면 호출되는 메소드
    //테이블을 생성하고 샘플 데이터를 삽입
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table item(id integer primary key," +
                "name text, quantity integer)");
    }

    //DB 버전이 변경된 경우 호출되는 메소드
    //기존 테이블을 제거하고 테이블을 새로 만들기
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //테이블 제거
        db.execSQL("drop table item");
        //테이블 생성
        onCreate(db);
    }
    
    //데이터 삽입하는 메소드
    public void addItem(Item item){
        //ContentValues를 이용한 삽입
        
        //삽입할 객체를 생성
        ContentValues row = new ContentValues();
        row.put("name", item.getName());
        row.put("quantity", item.getQuantity());

        //DB에 접속해서 row 삽입
        SQLiteDatabase db = getWritableDatabase();
        db.insert("item", null, row);
        db.close();
    }

    //제품 이름으로 검색해서 1개의 데이터를 리턴하는 메소드
    public Item findItem(String name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from item where name = "+"\""+
                name + "\"", null);

        Item item = new Item();
        //1개인 경우는 if, 여러 개인 경우는 while
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            //첫번째 컬럼을 가져와서 정수로 변환한 후, id에 삽입
            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setName(cursor.getString(1));
            item.setQuantity(Integer.parseInt(cursor.getString(2)));
            //커서 종료
            cursor.close();
        }else{
            item = null;
        }
        db.close();
        return item;
    }

    //name을 받아서 삭제하는 메소드
    public void deleteItem(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from item where name = ?", new String[]{name});
        db.close();
    }
}
