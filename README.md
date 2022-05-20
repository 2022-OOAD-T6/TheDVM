# DVM 6조

# dvm network 라이브러리 인터페이스
## 1. Client
- `public DVMClient(String host, String msg);`
  - host: 접속하고자 하는 서버의 ip주소
  - msg: 보내고자 하는 **json** string
    - dvm이 만든 Message Object를 json converter를 사용해 json으로 번역한 뒤 인자로 전달 필요
    - 그 후 run() -> 메세지가 전송됨  
  - `DVMClient client = new DVMClient("localhost", jsonMsg);`
## 2. Gson (Json-Message Converter)
### 1. Serializer
```java
public class Serializer {
    // 입력받은 Message 객체를 jsonString으로 변환
    public String message2Json(Message msg);
}
```
### 2. Deserializer
```java
public class Deserializer {
    // 입력받은 jsonString을 Message 객체로 변환
    public Message json2Message(String json);
}
```
## 3. Message
```java
public class Message {
    private int srcId;
    private int dstId;
    private String msgType;
    MsgDescription msgDescription;
    public static class msgDesription {
        private int itemCode;
        private int itemNum;
        private int dvmXCoord, dvmYCoord;
        private int authCode;
    }
}
```
## 2. Server
- 클라 -> 서버에게 메세지를 보내면, 서버는 Statc ArrayList에 Message타입 객체를 저장
- 따라서 가장 마지막 인덱스에서 최신 메세지를 얻을 수 있음
  - 무한 루프를 도는 쓰레드를 만들어서 서버의 ArrayList에 접근해서 계속 최신화
    ```java
    // 서버용 쓰레드 하나 만들어서 사용
    static class Thread1 extends Thread {
        @Override
        public void run() {
            server = new DVMServer(); // ⭐️
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
    }
    ```
- 서버의 static ArrayList에 접근해야함 -> `Server.msgList`
  ```java
  public static 무언가 어떤 우리의 함수() {
    if (server.msgList.size() > 0) {
        /* 메세지 읽어서 잘 사용... */
    } else {
        /* 메세지 리스트 비어있음... */
    }
  }
  ```
## 그 외
- 포트는 8080 고정

