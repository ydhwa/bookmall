desc book;
desc cart;
desc category;
desc member;
desc orders;
desc order_book;

-- category
insert into category values(null, '문학');
select no, name from category order by no asc;
select * from category;

-- book
desc book;
insert into book values(null, '청산도', 5000, 1);
select * from book;

select title, price, category_no from book order by no asc;

-- member
desc member;
insert into member values(null, '양동화', '010-0123-4567', 'ydhwa_18@naver.com', password('1234'));
select LAST_INSERT_ID();
select name, phone, email, passwd from member order by no asc;


-- cart
desc cart;
insert into cart(book_no, member_no, amount) values(2, 1, 2);
select b.title, c.amount, (b.price * c.amount)
from cart c, book b, member m
where c.book_no = b.no
    and c.member_no = m.no
    and m.no = 1
    and c.order = true;
update cart
set order_ok = true
where member_no = 1;

-- order
desc orders;
insert into orders
values(null, 
 concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull((select last_insert_id() from orders), 1), 5, 0)), 
10000, '경기도 가천시', null, 1);
-- 멤버의 주문내역
select o.no, o.order_no, m.name, m.email, o.price, o.receive_address
from orders o, member m
where o.member_no = m.no
	and m.no = 1;
-- 한 주문의 책들
select b.no, b.title, ob.amount
from orders o, order_book ob, book b
where o.no = ob.order_no
	and b.no = ob.book_no
    and o.no = 2;
select * from orders;
-- order_book
desc order_book;
insert into order_book values(2, 2, 2);


-- select concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull((select last_insert_id() + 1 from orders), 1), 5, 0));


select * from book;
select * from category;
select * from member;
select * from cart;
select * from orders;
select * from order_book;

/* 시나리오*/
insert into category values(null, '문학');

insert into book values(null, '청산도', 5000, 1);
insert into book values(null, '찰리와 초콜릿 공장', 7000, 1);
insert into book values(null, '메밀꽃 필 무렵', 3000, 1);

insert into member values(null, '양동화', '010-0123-4567', 'ydhwa_18@naver.com', password('1234'));
-- 양동화가 청산도를 2권 카트에 넣음
insert into cart(book_no, member_no, amount) values(1, 1, 2);
-- 양동화가 찰리와초콜릿공장을 1권 카트에 넣음
insert into cart(book_no, member_no, amount) values(2, 1, 1);

-- 양동화의 주문 목록을 생성함
insert into orders(order_no, receive_address, member_no)
values(concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull(last_insert_id(), 1), 5, 0)), '경기도 가천시', 1);
select * from orders;

-- 양동화는 카트에 담겨 있는 상품들 중 '찰리와초콜릿공장'을 구매결정함
update cart set order_ok = true
where book_no = 1
and member_no = 1;
select * from cart;

-- 구매결정한 찰리와초콜릿공장은 주문목록에 넣음
insert into order_book
	select book_no, (select last_insert_id() from orders), amount
    from cart
    where order_ok = true
    and member_no = 1;
select * from order_book;
    
-- 양동화는 별도로 다시 주문하기를 시도함
insert into orders(order_no, receive_address, member_no)
values(concat(date_format(now(), '%Y%m%d'), '-', lpad((select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders'), 5, 0)), '경기도 남양주시', 1);
-- 청산도 두권을 구입함
desc order_book;
insert into order_book values(1, last_insert_id(), 2);

-- 주문 목록에서 최종 결제 금액 업데이트 시킴
update orders
set price = (select sum(b.price * ob.amount) from order_book ob, book b where ob.order_no = 1 and b.no = ob.book_no group by ob.order_no)
where no = 1;
select * from orders;
select * from order_book;

-- 조회들
select title, price from book order by no asc;

select no, name from category order by no asc;

select name, phone, email, passwd from member order by no asc;

select b.title, c.amount, (b.price * c.amount)
from cart c, book b, member m
where c.book_no = b.no
and c.member_no = m.no
and m.no = 1
order by b.no asc;

select o.no, o.order_no, m.name, m.email, o.price, o.receive_address
from orders o, member m
where o.member_no = m.no
and m.no = 1
order by o.order_no asc;

select b.no, b.title, ob.amount
from orders o, order_book ob, book b
where o.no = ob.order_no
and b.no = ob.book_no
and o.no = 1
order by b.no asc;

-- auto_increment 값 가져오기(last_insert_id는 안먹힘)
show table status like 'orders';
select `auto_increment`
from information_schema.tables
where table_schema = 'bookmall'
and table_name = 'orders';
(select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders');

desc order_book;
desc cart;
insert into order_book(book_no, order_no, amount)
	select book_no, (select `auto_increment` - 1 from information_schema.tables where table_schema = 'bookmall' and table_name = 'orders'), amount
	from cart
	where order_ok = true
		and member_no = 1;

-- 주문 번호 등록 위한 카운팅        
select count(*) from orders o where (select substr(order_no, 1, 8) from orders where no = o.no) = date_format(now(), '%Y%m%d');

select * from orders;
(select count(*) from orders o where (select substr(order_no, 1, 8) from orders where no = o.no) = date_format(now(), '%Y%m%d'));
select lpad((select count(*) from orders o where (select substr(order_no, 1, 8) from orders where no = o.no) = date_format(now(), '%Y%m%d')), 5, 0);

					insert into orders(order_no, receive_address, member_no)
					values(concat(date_format(now(), '%Y%m%d'), '-', lpad((select count(*) from orders o where (select substr(order_no, 1, 8) from orders oo where oo.no = o.no) = date_format(now(), '%Y%m%d')), 5, 0)), '강원도', 1);
