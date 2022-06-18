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
    private String srcId;
    private String dstId;
    private String msgType;
    MsgDescription msgDescription;
    public static class msgDesription {
        private String itemCode;
        private int itemNum;
        private int dvmXCoord, dvmYCoord;
        private String authCode;
    }
}
```
## 4. Server
- 클라 -> 서버에게 메세지를 보내면, 서버는 Statc ObservableList에 Message타입 객체를 저장
- OvservableList 에는 리스너를 달아줄 수 있음
  - 이 리스너가 새로운 메세지를 수신할 때 마다 적절히 핸들링 하도록 구현   
  ```java
  // 서버용 쓰레드가 서버 시작
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
- 리스너는 서버의 static ObservableLis에 접근함 -> `Server.observableList`
  ```java
  // 리스너 구현 예시
  class MyListener implements messageListChangeListener {
  
    @override
    void onChanged() {
      while(change.next()) {
        if (change.wasAdded()) {
            message = DvmServer.observableList.get();
            /* 메세지 읽어서 잘 사용... */
        }     
      }
    }
  
  }
  ```
  
## 그 외
- dvm_network 라이브러리 정책에 의해 포트는 8080으로 고정되어있음

