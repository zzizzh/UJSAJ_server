
﻿# UJSAJ_server

< 프로젝트 주요 과제 – 서버/DB>

■ DB에서는 최근 시간, 위치, 카테고리, 콘텐츠 타입에 해당하는 Posts를 return 할 수 있어야 한다. 

■ 서버에서는 클라이언트에서 받은 명령어(ex:#login)를 보고 클라이언트에서 데이터를 받아서 DB에 저장하거나, DB에서 데이터를 가져와서 클라이언트로 보내줘야 한다.

■ 서버에서 클라이언트의 명령은 #과 $로 구분한다. #은 명령어의 시작, $은 명령어의 토큰 구분자이다.

■ 클라이언트 – 서버 연결은 일대다 통신으로 쓰레드를 사용해서 여러 클라이언트에서 들어온 요청을 하나의 서버에서 모두 처리해 주어야 한다.


< 그 외 세부 사항>

■ 타임라인 목록 가져오는 기준
● 시간 : 최근 시간부터 10개 밑으로 스크롤 내릴 때 마다 10개씩 더 받아온다.
● 위치 : 현재 선택한 관광 정보에 해당하는 최신 Posts를 가져옴. 부족할 때는 비슷한 카테고리나 같은 콘텐츠타입 ID의 최신 Posts를 가져옴.

■ 내가 좋아요 한 목록 가져오는 기준
● 시간 : 최근 시간부터 10개. 밑으로 스크롤 내릴 때 마다 10개씩 더 받아온다.

■ 로그인 후 클라이언트에서는 타임라인 목록을 요청(Posts 10개)

■ 해당 Location에 게시글이 없을 경우에 같은 카테고리(소->중->대 순서)나 같은 콘텐츠타입의 ID를 갖고있는 Location의 게시글을 보내줘야 한다.

< 서버-클라이언트 간 명령 명세 >
■ 로그인(login)
● 클라이언트->서버 : (string) #login$id$pass / 
● 서버->클라이언트 : (string) #err or User

■ 회원가입(register)
● 클라이언트->서버 : (string) #register$id$password
● 서버->클라이언트 :#fin / #err

■ 타임라인 포스트 요청(시간, 위치) (morePosts)
● 클라이언트->서버 : (string) #morePosts
● 서버->클라이언트 : ArrayList<Posts>

■ 타임라인 포스트 새로고침(시간, 위치) (refresh)
● 클라이언트->서버 : (string) #refresh
● 서버->클라이언트 : ArrayList<Posts>

■ 내가 게시한 포스트 새로고침
● 클라이언트->서버 : (string) #myPosts
● 서버->클라이언트 : ArrayList<Posts>

■ 내가 게시한 포스트 요청
● 클라이언트->서버 : (string) #moreMyPosts
● 서버->클라이언트 : ArrayList<Posts>
  
■ 내가 좋아요 한 포스트 요청(처음) (myLike)
● 클라이언트->서버 : (string) #userIndex
● 서버->클라이언트 : ArrayList<Posts>

■ 내가 좋아요 한 포스트 요청(추가) (moreLike)
● 클라이언트->서버 : (string) #moreLike
● 서버->클라이언트 : ArrayList<Posts>

■ 포스트 쓰기(포스트 저장) (Post)
● 클라이언트->서버 : Posts
서버->클라이언트   : #fin / #err

■ 내가 쓴 포스트 삭제 (delete)
● 클라이언트->서버 : (string) #delete$(int)index
● 서버->클라이언트 : #fin / #err

■ 포스트 좋아요 (like)
● 클라이언트->서버 : (string) #like$(int)index
● 서버->클라이언트 : #fin / #err

■ 포스트 좋아요 취소 (disLike)
● 클라이언트->서버 : (string) #dislike$(int)index
● 서버->클라이언트 : #fin / #err

■ 유저정보업데이트 (updateUser)
● 클라이언트->서버 : (string) #updateUser
● 서버->클라이언트 : User
< 데이터 클래스 >
< 서버/DB 함수 >

DBManager
- getUserIndex() return userIndex
- getPostsIndex() return postsIndex
- refreshTimeLine() return ArrayList<Posts>
- getMorePosts() return ArrayList<Posts>
- insertUser(User user)
- getPWByID(String id) return String(PW)
- getUserByID(String id) return User
- insertPosts(Posts posts)
- deletePosts(int index)
- getPostsByIndex(int index) return Posts
- getPostsByLocation(Location location) return ArrayList<Posts>
- getPostsByOption(int option, int value) return ArrayList<Posts>
0 : 소분류, 1 : 중분류, 2 : 대분류, 3 : 콘텐츠아이디

ServerConsole
- login(String msg) 
- register(String msg) 
- refresh()
- morePosts()
- myLike(String msg)
- moreLike(String msg)
- posts(Posts p)
- like(String msg)
- dislike(String msg)
- updateUser()
- sendUser(User u)
- sendPostsList(PostsList p)
- sendString(String msg)

< 안드로이드 함수 >
