# Whatareyouupto


### 어려웠던 부분(해결)
1. 달력에 있는 날짜마다 일정을 추가하면 SQLite 데이터베이스에 저장되게 하는 것은 성공했으나, 막상 저장되어 있는 값을, 날짜를 선택할 때마다 불러오게 하는 것은 힘들었다.    
하지만, SQLite를 더 공부하면서 SQL SELECT 메소드에서 SELECT FROM WHERE를 이용하여 내가 선택한 날짜와 저장되어 있는 일정에 있는 날짜가 같으면 불러올 수 있는 것이 가능하였고,  
리사이클러뷰를 이용하여 내가 저장했던 일정의 날과 내가 선택한 날이 같으면 그 일정을 불러올 수 있게 구현에 성공했다.  
![sqlite](https://user-images.githubusercontent.com/86480696/176424817-6da0d160-7751-4891-aad3-3f68f30f6d16.gif){: width="30px", height="100px"}
