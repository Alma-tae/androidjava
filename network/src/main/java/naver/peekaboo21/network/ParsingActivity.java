package naver.peekaboo21.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParsingActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            String data = (String)msg.obj;
            TextView result = (TextView)findViewById(R.id.result);
            result.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        Button btnHTML = (Button)findViewById(R.id.btnHTML);
        btnHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드를 생성해서 시작
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            //문자열을 다운로드
                            URL url = new URL("https://comic.naver.com/index.nhn");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);
                            
                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            String headerType = con.getContentType();
                            if(headerType.toUpperCase().indexOf("EUC-KR")>=0){
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            }else{
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line+"\n");
                            }
                            //다운로드 받은 문자열을 확인
                            //Log.e("다운로드 받은 문자열", sb.toString());

                            //HTML 파싱 수행
                            //HTML 파일을 메모리에 펼치기
                            Document doc = Jsoup.parse(sb.toString());
                            //찾고자 하는 선택자를 이용해서 Elements를 찾아오기
                            //a 태그 전부 찾아오기
                            Elements elements = doc.select("a");
                            //파싱한 결과를 저장할 문자열 객체
                            //ListView에 출력할 때는 Lsit를 이용
                            StringBuilder sbResult = new StringBuilder();
                            //하나씩 순회
                            for(Element element : elements){
                                //문자열을 가져오기
                                String contents = element.text();
                                sbResult.append(contents+"\n");
                            }
                            //결과를 핸들러에게 전송해서 출력
                            Message message = new Message();
                            message.obj = sbResult.toString();
                            handler.sendMessage(message);

                            br.close();
                            con.disconnect();

                        }catch(Exception e){
                           Log.e("예외발생-HTML", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

        //XML Parsing 버튼의 클릭 이벤트 작성
        Button btnXML = (Button)findViewById(R.id.btnXML);
        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            //데이터 다운로드
                            //URL url = new URL("https://www.hani.co.kr/rss/culture/");
                            URL url = new URL("https://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=2820066000");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            String headerType = con.getContentType();
                            if(headerType.toUpperCase().indexOf("EUC-KR")>=0){
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            }else{
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line+"\n");
                            }
                            //다운로드 받은 문자열을 확인
                            Log.e("다운로드 받은 문자열", sb.toString());

                            //DOM 파싱을 위해서 Root까지 찾아가기
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            InputStream is = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
                            org.w3c.dom.Document doc = builder.parse(is);
                            org.w3c.dom.Element root = doc.getDocumentElement();

                            //title 태그의 내용을 전부 가져오기
                            //NodeList titles = root.getElementsByTagName("title");
                            //wfKor이라고 지정하면 그것만 가져올 수 있음(날씨 정보에서)
                            NodeList titles = root.getElementsByTagName("wfKor");
                            NodeList hours = root.getElementsByTagName("hour");
                            //결과를 저장할 스트링 객체
                            StringBuilder sbResult = new StringBuilder();
                            //순회
                            for(int i=0; i< titles.getLength(); i++){
                                Node item = hours.item(i);
                                Node contents = item.getFirstChild();
                                String hour = contents.getNodeValue();
                                sbResult.append(hour+"시:");
                                /*//첫 번째 데이터 제외
                                if(i==0){
                                    continue;
                                }*/
                                //하나 가져오기
                                item = titles.item(i);
                                //태그 안의 문자열 가져오기
                                contents = item.getFirstChild();
                                String title = contents.getNodeValue();

                                sbResult.append(title + "\n");
                            }
                            // 핸들러에게 전송해서 출력
                            Message msg = new Message();
                            msg.obj = sbResult.toString();
                            handler.sendMessage(msg);

                            br.close();
                            con.disconnect();

                        }catch(Exception e){
                            Log.e("예외 발생 - XML", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });

        //JSON Parsing 버튼의 클릭 이벤트 작성
        Button btnJSON = (Button)findViewById(R.id.btnJSON);
        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            //데이터 다운로드(문자열)
                            URL url = new URL("http://cyberadam.cafe24.com/movie/list");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            String headerType = con.getContentType();
                            if(headerType.toUpperCase().indexOf("EUC-KR")>=0){
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-KR"));
                            }else{
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line+"\n");
                            }
                            //다운로드 받은 문자열을 확인
                            Log.e("다운로드 받은 문자열", sb.toString());

                            //문자열을 JSON 객체로 변환
                            JSONObject json = new JSONObject(sb.toString());
                            //list 키의 데이터를 배열로 가져오기
                            JSONArray list = json.getJSONArray("list");
                            //결과를 저장할 문자열 생성
                            StringBuilder sbResult = new StringBuilder();
                            //배열 순회
                            for(int i=0; i<list.length(); i++){
                                //배열의 데이터를 객체로 가져오기
                                JSONObject movie = list.getJSONObject(i);
                                //title 속성의 내용 가져오기(추가 가능)
                                String title = movie.getString("title");
                                double rating = movie.getDouble("rating");
                                String genre = movie.getString("genre");
                                sbResult.append(title+"-"+rating+"-"+genre+"\n");
                            }

                            // 핸들러에게 전송해서 출력
                            Message msg = new Message();
                            msg.obj = sbResult.toString();
                            handler.sendMessage(msg);

                            br.close();
                            con.disconnect();

                        }catch(Exception e){
                            Log.e("예외 발생 - JSON", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}