insert into user(person_id, nick_name, passwd, created_at, role, old, phone_num)
values ('11111', '11', 'tea9152352@', now(), 'ROLE_USER', 12,'01097563400');

insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('고기마켓', 1,'김영수', '고기고기', now(), '서울','서초구','서초 중앙로 14', '010-1111-2222', 3, 6, now(), now(), "9:00", "17:00" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('삼삼이네 분식', 1, '이지은', '삼삼삼삼', now(), '서울','동대문구','보문로 6', '010-2222-3333', 7, 10, now(), now(), "9:00", "17:00" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('해산물마을',1, '박민준', '해산물좋아', now(), '서울','중구','명동4길 25', '010-3333-4444', 40, 12, now(), now(), "9:00", "17:00" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('소나무밥상',1, '최지혜', '소나무가', now(), '서울','광진구', '답심리로 364', '010-4444-5757', 14, 8, now(), now(), "9:00", "17:00" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('쌀국수의 전설',1, '정승우', '쌀국수는베트남', now(), '서울','구로구', '신도림로 51', '010-2483-8487', 67, 223, now(), now(), "9:00", "17:00" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('행복한 파스타집',1, '강수진', '파스타는파스타', now(), '서울' ,'금천구', '독산로 101길', '010-2474-7585', 134, 5, now(), now(), "9:15", "17:10" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('고급스러운 스테이크하우스',1, '윤현우', '스테이크는차갑다', now(), '경기' , '안양시', '동안구 경수대로 436-2', '010-4757-3958', 22, 5, now(), now(), "9:15", "17:10" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('분위기 있는 바', 1,'임서연', '분위기좋음', now(), '서울','중랑구', '상봉중앙로5길 56', '010-2475-5893', 0, 0, now(), now(), "9:15", "17:10" );
insert into article(title, user_id, writer, content, work_time, address1, address2, address3, phone_number, likes, view, reg_date, mod_date, start_time, end_time  )
values ('건강한 샐러드 카페', 1,'한준호', '건강최고', now(), '경기도', '고양시', ' 고양시청로 2', '010-4756-3858', 25, 79, now(), now(), "9:15", "17:10" );

-- 파일 저장
insert into article_file(original_file_name, stored_file_name, article_id)
values ('1-1고기마켓','articleImg/1-1고기마켓.jpg',1);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('1-2고기마켓','articleImg/1-2고기마켓.jpg',1);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('1-3고기마켓','articleImg/1-3고기마켓.jpg',1);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('1-4고기마켓','articleImg/1-4고기마켓.jpg',1);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('1-5고기마켓','articleImg/1-5고기마켓.jpg',1);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('2-1분식','articleImg/2-1분식.jpg',2);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('2-2분식','articleImg/2-2분식.jpg',2);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('2-3분식','articleImg/2-3분식.jpg',2);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('2-4분식','articleImg/2-4분식.jpg',2);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('2-5분식','articleImg/2-5분식.jpg',2);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('3-1해산물','articleImg/3-1해산물.jpg',3);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('3-2해산물','articleImg/3-2해산물.jpg',3);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('3-3해산물','articleImg/3-3해산물.jpg',3);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('3-4해산물','articleImg/3-4해산물.jpg',3);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('3-5해산물','articleImg/3-5해산물.jpg',3);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('4-1소나무밥상','articleImg/4-1소나무밥상.jpg',4);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('4-2소나무밥상','articleImg/4-2소나무밥상.jpg',4);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('4-3소나무밥상','articleImg/4-3소나무밥상.jpg',4);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('4-4소나무밥상','articleImg/4-4소나무밥상.jpg',4);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('4-5소나무밥상','articleImg/4-5소나무밥상.jpg',4);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('5-1쌀국수','articleImg/5-1쌀국수.jpg',5);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('5-2쌀국수','articleImg/5-2쌀국수.jpg',5);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('5-3쌀국수','articleImg/5-3쌀국수.jpg',5);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('5-4쌀국수','articleImg/5-4쌀국수.jpg',5);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('5-5쌀국수','articleImg/5-5쌀국수.jpg',5);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('6-1파스타','articleImg/6-1파스타.jpg',6);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('6-2파스타','articleImg/6-2파스타.jpg',6);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('6-3파스타','articleImg/6-3파스타.jpg',6);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('6-4파스타','articleImg/6-4파스타.jpg',6);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('6-5파스타','articleImg/6-5파스타.jpg',6);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('7-1스테이크','articleImg/7-1스테이크.jpg',7);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('7-2스테이크','articleImg/7-2스테이크.jpg',7);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('7-3스테이크','articleImg/7-3스테이크.jpg',7);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('7-4스테이크','articleImg/7-4스테이크.jpg',7);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('7-5스테이크','articleImg/7-5스테이크.jpg',7);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('8-1분위기 있는 바','articleImg/8-1분위기 있는 바.jpg',8);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('8-2분위기 있는 바','articleImg/8-2분위기 있는 바.jpg',8);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('8-3분위기 있는 바','articleImg/8-3분위기 있는 바.jpg',8);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('8-4분위기 있는 바','articleImg/8-4분위기 있는 바.jpg',8);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('8-5분위기 있는 바','articleImg/8-5분위기 있는 바.jpg',8);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('9-1건강한 샐러드 카페','articleImg/9-1건강한 샐러드 카페.jpg',9);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('9-2건강한 샐러드 카페','articleImg/9-2건강한 샐러드 카페.jpg',9);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('9-3건강한 샐러드 카페','articleImg/9-3건강한 샐러드 카페.jpg',9);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('9-4건강한 샐러드 카페','articleImg/9-4건강한 샐러드 카페.jpg',9);
insert into article_file(original_file_name, stored_file_name, article_id)
values ('9-5건강한 샐러드 카페','articleImg/9-5건강한 샐러드 카페.jpg',9);
