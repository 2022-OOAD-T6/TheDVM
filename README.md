# OOAD Project - Distributed vending machine

- 건국대학교 2022-1학기 객체지향개발방법론 수업의 팀프로젝트입니다.
- 주어진 [PFR](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/PFR.pdf)을 바탕으로 OOPT(Object Oriented Process with Traceability) 방법론을 적용하여 분산 자판기를 개발했습니다.
- `분산 자판기`란, 현 자판기에 재고가 없더라도 네트워크 통신을 통해 재고가 있는 다른 자판기를 안내하고, 선결제 서비스 등을 제공하는 자판기입니다.

## 개발 과정 - OOPT stage

### [1. stage 1000 Plan](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/OOPT_stage_1000_plan.pdf)

- 주어진  [PFR](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/PFR.pdf)을 보고, 요구사항(FR, NFR)과 business usecase를 도출해냅니다. 그리고 draft system architecture를 작성합니다.

### [2. stage 2030 Analyze](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/OOPT_stage_2030_analyze.pdf)

- 전 stage에서 도출했던 business usecase를 통해 essential usecase로 구체화합니다. 이 때 usecase로부터 간단한 domain model을 도출해봅니다.
- 각 usecase 별로 actor와 system만의 interaction을 나타낸 system sequence diagram을 작성합니다. system operation이 나타나게 됩니다.
- 각 usecase 별로 필요한 sytem test case를 작성합니다.

### [3. stage 2040 Design](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/OOPT_stage_2040_design.pdf)

- essential usecase로부터 real usecase를 설계합니다. 이 과정에서 실제 UI의 흐름을 같이 작성해봅니다.
- 이제 actor와 system만의 interaction만 보이는 SSD가 아닌 시스템 내부 object 간의 interaction까지 나타낸 sequence diagram을 usecase마다 작성합니다.
- sequence diagram을 그리면서, 내부 object들이 이제 도출되게 됩니다. 따라서 그 과정에서 도출된 class, 각 attribute, method로 class diagram이 완성됩니다. 

### 4. stage 2050 Construct & 2060 Testing [1st Cycle](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/OOPT_1st_implementation.pdf), [2nd Cycle](https://github.com/2022-OOAD-T6/TheDVM/tree/master/docs/OOPT_2nd_implementation_FINAL.pdf)

- 전 stage에서 정의한 class diagram으로 생성된 skeleton 코드를 sequence diagram을 보면서 object 간의 interaction을 채우면서 구현을 합니다.
- 구현 과정에서 unit test, system test를 진행합니다.
- 2번의 cycle을 진행합니다. 두번째 cycle에서는 desgin pattern(observer, singleton)을 적용하고, clean code를 위해 정적 분석 도구(Spotbugs)를 활용하여 코드를 개선합니다.

 ## 실행

### 환경 설정

- `src/main/resources/properties`폴더 내의 파일을 통해 기본 설정을 합니다. 
- 실행 시에 설정 사항이 잘 반영됐는지 출력문을 확인할 수 있습니다. 
- 설정 파일에 오류가 있을 경우 기본 설정으로 적용됩니다.

1. 네트워크 설정 (`network.properties`)

   - 본 시스템은 분산 자판기입니다. 따라서 네트워크 ip 설정이 필요합니다. 

     ```properties
     current.id=Team6
     current.x=130
     current.y=30
     other.id= Team0, Team1, Team2, Team3, \
       Team4, Team5
     other.ip= 192.168.64.60, 192.168.64.60, 192.168.64.60, \
       192.168.64.60, 192.168.64.60, 192.168.64.60
     ```

   - `current.id`: 현재 자판기의 id. PFR에 따라 'Team숫자'의 형식으로 작성해야 합니다.
   - `current,x`, `current,y`: 현 자판기의 x, y좌표
   - `other.id`, `other.ip`: 다른 자판기의 id와 ip를 작성합니다. 이 때 같은 순서대로 대응되므로 주의해서 작성합니다.

2. 현 자판기 음료 재고 설정 (`stock.properties`)

   - 현 자판기(해당 설정으로 실행되는 프로세스의 자판기)에서 판매 중인 7가지 음료의 종류와 재고를 표시합니다.
   
   - 이 때, PFR 상으로 7개를 판매하는 것이 원칙이므로 7개를 모두 작성해야 합니다. 음료의 종류 역시 PFR 상의 음료 코드를 따릅니다.
   
     ```properties
     01=100
     02=0
     05=0
     06=0
     18=0
     19=500
     20=500
     ```
   

### 빌드 후 실행

- gradle로 빌드할 수 있습니다.

```shell
./gradlew clean build
java -jar build/libs/OOAD-DVM-T6-1.0-SNAPSHOT.jar 
```

## Reference

- [2022 객체지향개발방법론 수업 홈페이지](http://dslab.konkuk.ac.kr/Class/2022/22SMA/22SMA.htm)
- [2022 객체지향개방방법론 팀프로젝트 제출물](http://dslab.konkuk.ac.kr/Class/2022/22SMA/Team_Organization_A.htm)

