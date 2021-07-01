create table users(
    account varchar(20) not null,
    password varchar(20) not null,
    level int default 0,
    experience int default 0,
    sliver bigint default 0,
    gold int default 0,
    nickname varchar(40) default 'myself',
    avatar varchar(100),
    primary key(account) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into users(account, password) VALUES('player1', 'jijunyu1211');
insert into users(account, password) VALUES('root', 'roottoor');

-- 武将卡
create table generals(
    general_id int UNSIGNED AUTO_INCREMENT,
    category char(4) not null,
    name varchar(10) not null,
    blood int not null,
    gimage varchar(100) not null,
    primary key(general_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 武将技能的的描述，general_skill_name与每个处理类一一处理
create table generalskill(
    general_skill_name varchar(10) not null,
    audio varchar(100),
    general_id int UNSIGNED,
    primary key(general_skill_name),
    foreign key(general_id) references generals(general_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 卡牌及其技能
create table cards(
    id int UNSIGNED AUTO_INCREMENT,
    skill_name varchar(10) not null,
    color varchar(10) not null,
    point int,
    image varchar(100) not null,
    audio varchar(100),
    animiation varchar(10),
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 拥有的武将
create table own_general(
    account varchar(20) not null,
    general_id int UNSIGNED,
    primary key(account, general_id),
    foreign key(account) references users(account),
    foreign key(general_id) references generals(general_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


