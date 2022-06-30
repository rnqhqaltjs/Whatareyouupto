# 오늘 뭐해?

## 개요
'오늘 뭐해?' 는 오픈소스인 'Material Calendar View'를 이용한 투두 캘린더로 나의 여러가지 일정들을 정리하고 확인할 수 있는 어플리케이션이다.  

## 기능

### 캘린더(Todo Calendar)
캘린더 날짜마다 일정을 추가, 수정, 삭제할 수 있으며, 날짜마다 내가 추가시켰던 일정들을 확인할 수 있다.  

### 오늘뭐해?(Today List)
이름 그대로 캘린더에서 내가 추가시켰던 일정들 중에 오늘 일정이 무엇인지 한눈에 볼 수 있고, 일정을 완료했으면 직접 체크하여 일정을 완료할 수 있다.  
오늘 일정들 중에서 완료한 일정의 비율을 '진행도'를 이용하여 실시간으로 확인할 수 있다.  

### 타임라인(Done List)
내가 완료했던 일정들을 날짜별로 확인할 수 있다.  

##
### 2022. 06. 08
'오늘 뭐해? 개발 시작, 아이디어 구상

### 2022. 06. 09
'Material Calendar View'를 이용하여 캘린더 생성

### 2022. 06. 10
캘린더 꾸미기  
1. 이미 지난 날짜와 현재 날짜 +5달 이후 날들은 회색으로 표시하고 터치 불가, 그외에는 검은색, 굵게 표시하고 터치 가능  
2. 토요일은 파란색, 일요일은 빨간색, 오늘 날짜는 자홍색으로 표시  
3. 선택한 날짜는 주황색 테두리로 표시  

### 2022. 06. 11
캘린더뷰 밑에 리사이클러뷰 추가  
SQLite를 추가하여 데이터베이스에 담을 날짜, 시간 등 여러가지 데이터 구성

### 2022. 06. 12
캘린더 뷰 선택마다 일정을 추가할수 있는 버튼 생성  
버튼을 누르면 

## 어려웠던 부분(해결)

1. 달력에 있는 날짜마다 일정을 추가하면 SQLite 데이터베이스에 저장되게 하는 것은 성공했으나, 막상 저장되어 있는 값을, 날짜를 선택할 때마다 불러오게 하는 것은 힘들었다.    
하지만, SQLite를 더 공부하면서 SQL SELECT 메소드에서 SELECT FROM WHERE를 이용하여 내가 선택한 날짜와 저장되어 있는 일정에 있는 날짜가 같으면 불러올 수 있는 것이 가능하였고,  
리사이클러뷰를 이용하여 내가 저장했던 일정의 날과 내가 선택한 날이 같으면 그 일정을 불러올 수 있게 구현을 하였다. 

![sqlite](https://user-images.githubusercontent.com/86480696/176426809-8798d19e-8827-4305-a40f-0e71256c40d4.gif)

2. 프로그레스 바를 이용하여 전체 일정과 체크박스가 체크되어있는 일정들을 SQLite 데이터베이스에서 불러와서 나의 오늘 일정들 중에 완료한 일정의 비율을 %로 진행도가 나오게끔 구현하려 했다.  
데이터를 불러와서 진행도를 표시하는 데는 성공했지만, 진행도가 일정 체크 여부에 따라 실시간으로 반영되지는 않았다. (앱을 껐다 키면 반영됨.)  
체크박스는 리사이클러뷰 아이템으로 액티비티 클릭이벤트는 사용할 수 없었다.  
이 문제를 해결하기 위해 Handler를 찾았다. Handler 를 이용하면 UI작업을 꼭 main thread에서 사용하지 않아도 된다.  
이 Handler를 이용하여 타이머처럼 단독으로 프로그레스바를 0.5초마다 새로고침 하게 구현하였고, 이것을 이용하여 데이터가 실시간으로 진행도에 반영되도록 할 수 있었다.  

![refresh](https://user-images.githubusercontent.com/86480696/176429549-56196db4-e203-4737-80a1-78400cafd50c.gif)
