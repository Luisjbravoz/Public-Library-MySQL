-- PROJECT: PUBLIC LIBRARY
-- LUIS J. BRAVO ZÚÑIGA.
-- SCRIPT DATA BASE (MySQL).

-- [SYSTEM: DROP DATABASE]
drop database if exists public_library;

-- [SYSTEM: CREATING DATABASE]
 create database public_library;
 use public_library;

-- [SYSTEM: CREATING TABLES] 

-- [SYSTEM: CREATING TABLE USERS]
 create table users(
 username			varchar(25)			not null,	
 password			varchar(16)			not null,
 state_data			varchar(1)			not null
 );
 
-- [SYSTEM: CREATING TABLE BOOK] 
create table Book(
ISBN				varchar(13)		not null,
title				varchar(40)		not null,
author_fname		varchar(20)		not null,
author_lname		varchar(20)		not null,
editorial			varchar(20)		not null,
year_publication	varchar(4)		not null,
idiom				varchar(10)		not null,	
pages				varchar(10)		not null,
id_subgenre			int				not null,
quantity			int				not null,
state_data			varchar(1)		not null
);
    
-- [SYSTEM: CREATING TABLE SUBGENRE] 
create table Subgenre(
id					int				not null	auto_increment,
title				varchar(20)		not null,
unique key (id)
);

-- [SYSTEM: CREATING RESTRICCIONS]

-- [SYSTEM: CREATING PRIMARY KEY OF TABLE USERS] 
alter table Users add constraint PK_users primary key (username);

-- [SYSTEM: CREATING PRIMARY KEY OF TABLE BOOK] 
alter table Book add constraint PK_book primary key (ISBN);

-- [SYSTEM: CREATING PRIMARY KEY OF TABLE SUBGENRE] 
alter table Subgenre add constraint PK_subgenre primary key (id);

-- [SYSTEM: CREATING FOREIGN KEY OF TABLE BOOK]
alter table Book add constraint FK_book_subgenre foreign key (id_subgenre) references Subgenre(id);

-- [SYSTEM: CREATING CHECK CONSTRAINT FOR DATA STATE. THIS ATRIBUTTE ONLY CAN TAKE TWO VALUES: 'A' OR 'I'] 
alter table Users add constraint cdt_state_data_users check(state_data in ('A', 'I'));
alter table Book add constraint cdt_state_data_book check (state_data in ('A', 'I'));
alter table Book add constraint cdt_quantity check (quantity > -1);

-- [SYSTEM: INIT AUTOINCREMENT TABLE SUBGENRE]
alter table Subgenre auto_increment=1;

DELIMITER $$

-- NOTE: FUNCTION FOR CHECK LOGIN.
-- [SYSTEM: CREATING FUNCTION FOR CHECK LOGIN]
drop function if exists f_check_login;$$
create function f_check_login(arg_username varchar(25), arg_password varchar(16))
returns int deterministic
begin 
	declare var_result int;
	select count(username)
    into var_result
    from Users
    where username = upper(arg_username) and password = arg_password and state_data = 'A';
    return var_result;
end; 
$$

-- [SYSTEM: CREATING OTHERS FUNCTIONS TO VALIDATE OPERATIONS]

-- [SYSTEM: CREATING FUNCTION TO KNOW IF THE BOOK EXISTS]
drop function if exists f_exist_book;$$
create function f_exist_book(arg_ISBN varchar(13)) 
returns int deterministic
begin 
	declare var_result int;
	select count(ISBN)
	into var_result
	from Book
	where ISBN = arg_ISBN;
	return var_result;
end;
$$

-- [SYSTEM: CREATING PROCEDURE]
-- FORMAT: p_<OPERATION>_<TABLE>

-- NOTE: PROCEDURE FOR INSERT AND UPDATE USER
-- [SYSTEM: CREATING PROCEDURE FOR INSERT USERS]
drop procedure if exists p_insert_user;$$
create procedure p_insert_user(in arg_username varchar(25), in arg_password varchar(16))
begin
	insert into users(username, password, state_data) values (upper(arg_username), arg_password, 'A');
	commit;
end;$$

-- [SYSTEM: CREATING PROCEDURE FOR UPDATE USERS]
drop procedure if exists p_update_user;$$
create procedure p_update_user(in arg_username varchar(25), in arg_password varchar(16))
begin
	update Users set password = arg_password where username = upper(arg_username) and state_data = 'A';
	commit;
end;
$$
 
 -- NOTE: PROCEDURE FOR INSERT, UPDATE, DELETE, CONSULT AND LIST BOOK
 -- [SYSTEM: CREATING PROCEDURE FOR INSERT BOOK]
drop procedure if exists p_insert_book;$$
create procedure p_insert_book(in arg_ISBN varchar(13), in arg_title varchar(40),
								in arg_author_fname varchar(20), in arg_author_lname varchar(20),
                                in arg_editorial varchar(20), in arg_year_publication varchar(4),
                                in arg_idiom varchar(10), in arg_pages varchar(10),
                                in arg_id_subgenre int, in arg_quantity int)
begin
	if f_exist_book(arg_ISBN) = 0 then
		insert into Book(ISBN, title, author_fname, author_lname, editorial, year_publication, idiom, pages, id_subgenre, quantity, state_data) values (arg_ISBN, arg_title, arg_author_fname, arg_author_lname, arg_editorial, arg_year_publication, arg_idiom, arg_pages, arg_id_subgenre, arg_quantity, 'A');
	else
		update Book set title = arg_title, author_fname = arg_author_fname, author_lname = arg_author_lname, editorial = arg_editorial, year_publication = arg_year_publication, idiom = arg_idiom, pages = arg_pages, id_subgenre = arg_id_subgenre, quantity = arg_quantity, state_data = 'A'
		where ISBN = arg_ISBN;
	end if;
	commit;
end;
$$

 -- [SYSTEM: CREATING PROCEDURE FOR UPDATE BOOK]
drop procedure if exists p_update_book;$$
create procedure p_update_book(in arg_ISBN varchar(13), in arg_title varchar(40),
								in arg_author_fname varchar(20), in arg_author_lname varchar(20),
                                in arg_editorial varchar(20), in arg_year_publication varchar(4),
                                in arg_idiom varchar(10), in arg_pages varchar(10),
                                in arg_id_subgenre int, in arg_quantity int)
begin
	update Book set title = arg_title, author_fname = arg_author_fname, author_lname = arg_author_lname, editorial = arg_editorial, year_publication = arg_year_publication, idiom = arg_idiom, pages = arg_pages, id_subgenre = arg_id_subgenre, quantity = arg_quantity
	where ISBN = arg_ISBN;
	commit;
end;
$$

 -- [SYSTEM: CREATING PROCEDURE FOR DELETE BOOK]
drop procedure if exists p_delete_book;$$
create procedure p_delete_book(in arg_ISBN varchar(13))
begin
	update Book set state_data = 'I' where ISBN = arg_ISBN;
	commit;
end;
$$

-- [SYSTEM: CREATING PROCEDURE FOR CONSULT BOOK]
drop procedure if exists p_consult_book;$$
create procedure p_consult_book(in arg_ISBN varchar(13))
begin
	select ISBN, title, author_fname, author_lname, editorial, year_publication, idiom, pages, id_subgenre, quantity
    from Book
    where ISBN = arg_ISBN;
end;
$$

-- [SYSTEM: CREATING PROCEDURE FOR LIST BOOK]
drop procedure if exists p_list_book;$$
create procedure p_list_book()
begin
	select ISBN, title, author_fname, author_lname, editorial, year_publication, idiom, pages, id_subgenre, quantity
    from Book
    order by author_lname;
end;
$$

-- NOTE: PROCEDURE FOR INSERT AND LIST SUBGENRE
-- [SYSTEM: CREATING PROCEDURE FOR INSERT SUBGENRE]
drop procedure if exists p_insert_subgenre;$$
create procedure p_insert_subgenre(in arg_title varchar(20))
begin
	insert into subgenre(title) values (arg_title);
	commit;
end;
$$

-- [SYSTEM: CREATING PROCEDURE FOR LIST SUBGENRE]
drop procedure if exists p_list_subgenre;$$
create procedure p_list_subgenre()
begin
	select title
    from Subgenre;
end;
$$

-- [SYSTEM: CREATING TRIGGERS]
-- FORMAT: t_<bf/af>_<OPERATION>_<TABLE>

-- [SYSTEM: CREATING TRIGGERS BEFORE INSERT OR UPDATE BOOK] 
drop trigger if exists t_bf_ins_upd_book;$$
create trigger t_bf_ins_upd_book before insert on Book
	for each row
begin
		set new.title := initcap(new.title);
		set new.author_fname := initcap(new.author_fname);
		set new.author_lname := initcap(new.author_lname);
		set new.editorial := initcap(new.editorial);
		set new.idiom := initcap(new.idiom); 
end; 
$$

-- [SYSTEM: CREATING FUNCTION INITCAP] 
drop function if exists initcap;$$
create function initcap(input varchar(255))
returns varchar(255) deterministic	
begin
	declare len int;
	declare i int;
	set len   = char_length(input);
	set input = lower(input);
	set i = 0;
	while (i < len) do
		if (mid(input,i,1) = ' ' or i = 0) then
			if (i < len) then
				set input = concat(
					left(input,i),
					upper(mid(input,i + 1,1)),
					right(input,len - i - 1)
				);
			end if;
		end if;
		set i = i + 1;
	end while;
return input;
end;
$$

 DELIMITER ;
 
-- [SYSTEM: INSERTING DATA] 

-- [SYSTEM: INSERTING USERS] 
call p_insert_user('library@newton.ma.us', 'Admin1234');

-- [SYSTEM: INSERTING SUBGENRE]
call p_insert_subgenre('Novel');
call p_insert_subgenre('Poem');
call p_insert_subgenre('Tale');
call p_insert_subgenre('Biographic');
call p_insert_subgenre('Chronicle');
call p_insert_subgenre('Fable');
call p_insert_subgenre('Essay');
call p_insert_subgenre('Drama');
call p_insert_subgenre('Comedy');
call p_insert_subgenre('Epic');

-- [SYSTEM: INSERTING BOOK] 
call p_insert_book('9788426107824','The Divine Comedy', 'Dante', 'ALIghieri', 'Juventud Editorial', '2009', 'English', '400', 2, 10);
call p_insert_book('9780345533661','Defending Jacob', 'William', 'Landay', 'Bantan Editorial', '2012', 'English', '437', 1, 5);
call p_insert_book('9788420649672','The Cold War. A very short introduction', 'Robert J.', 'McMahon', 'Alianza Editorial', '2003', 'Spanish', '299', 5, 2);
call p_insert_book('9789588940151','Voices from Chernobyl', 'Svetlana', 'Aleksievich', 'Penguin Random House', '2015', 'Spanish', '406', 7, 12);
call p_insert_book('9788491046691','History of the Soviet Union', 'Carlos', 'Taibo', 'Alianza Editorial', '2017', 'Spanish', '463', 5, 10);
call p_insert_book('9788466342711','Jurassic Park', 'Michael', 'Crichton', 'Penguin Random House', '1990', 'Spanish', '448', 1, 10);
call p_insert_book('9788466343756','Jurassic Park: The Lost World', 'Michael', 'Crichton', 'Penguin Random House', '1995', 'Spanish', '393', 1, 5);
call p_insert_book('9786070721311','Inferno', 'Dan', 'Brown', 'Planeta Editorial', '2013', 'English', '551', 1, 7);
call p_insert_book('9788408176107','Digital Fortress', 'Dan', 'Brown', 'Planeta Editorial', '1998', 'English', '527', 1, 7);
call p_insert_book('9788497941488','Homero: The Odyssey and The Iliad', 'Homero', 'Homero', 'EDIMAT', '2015', 'Spanish', '551', 2, 20);
call p_insert_book('9780785825050','The Raven', 'Edgar Allan', 'Poe', 'EDIMAT', '2015', 'English', '30', 2, 0);

-- NOTE: FINAL COMMIT;
commit;

-- [SYSTEM: END OF SCRIPT]