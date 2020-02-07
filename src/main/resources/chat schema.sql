CREATE DATABASE if not exists JavaProject;
USE JavaProject;
CREATE TABLE User(
	userID INT primary key auto_increment,
    userStatus int not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null,
    password varchar(30) not null,
	phone varchar(15) unique not null
);

CREATE TABLE friend_requests(
	sender_id INT not null,
    receiver_id INT not null,
    status int,
    primary key (sender_id, receiver_id),
    foreign key (sender_id) references Users(userID) ON DELETE CASCADE,
    foreign key (receiver_id) references Users(userID) ON DELETE CASCADE
);

CREATE TABLE chat_rooms(
	chat_room_id int primary key auto_increment
);

CREATE TABLE users_chat_rooms(
	chat_room_id int,
    userID int,
    primary key (chat_room_id, userID),
    foreign key (chat_room_id) references chat_rooms(chat_room_id) ON DELETE CASCADE,
    foreign key (userID) references User(userID) ON DELETE CASCADE
);

CREATE TABLE messages(
	message_id int primary key auto_increment,
	message_type int not null,
    sender_id int not null,
    chat_room_id int not null,
    content varchar(500) not null,
    foreign key (sender_id) references User(userID) ON DELETE CASCADE,
    foreign key (chat_room_id) references chat_rooms(chat_room_id) ON DELETE CASCADE
);

CREATE TABLE notifications(
	notification_id int primary key auto_increment,
    notification_type int not null,
    source_id int,
    receiver_id int not null,
    notification_status int not null,
    foreign key (source_id) references User(userID) ON DELETE CASCADE,
    foreign key (receiver_id) references User(userID) ON DELETE CASCADE
    
);

insert into User (first_name, last_name, userStatus, email, password, phone)
	values ("khaled", "elhossiny", 0, "khaled.elhossiny10@gmail.com", "password", "0122222");
    
insert into User (first_name, last_name, userStatus, email, password, phone)
	values ("abdelrahman", "elhossiny", 0, "abdo.elhossiny10@gmail.com", "password", "01111");
    
insert into friend_requests (sender_id, receiver_id, status) values (1, 2, 1);
    
insert into chat_rooms () values ();

insert into messages (message_type, sender_id, chat_room_id, content)
	values (0, 1, 1, "hello");
