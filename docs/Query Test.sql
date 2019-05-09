use bookmall;

show tables;

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

-- order
desc orders;
insert into orders
values(null, 
(select concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull(last_insert_id(), 1), 5, 0))), 
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

-- order_book
desc order_book;
insert into order_book values(2, 2, 2);


-- select concat(date_format(now(), '%Y%m%d'), '-', lpad(ifnull((select last_insert_id() + 1 from orders), 1), 5, 0));


select * from book;
select * from category;
select * from member;
select * from cart;