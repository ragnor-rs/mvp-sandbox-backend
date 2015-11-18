# --- !Ups

create table repo_like (
  user_id                       bigint not null,
  repo_id                       bigint not null
);

create table repo (
  id                            bigint not null,
  name                          varchar(255) not null,
  url                           varchar(255),
  owner_id                      bigint not null,
  like_count                    bigint default 0,
  revision                      bigint default 0,
  constraint pk_repo primary key (id)
);
create sequence repo_seq;

create table repo_user (
  id                            bigint not null,
  login                         varchar(255) not null,
  name                          varchar(255),
  revision                      bigint default 0,
  constraint pk_repo_user primary key (id)
);
create sequence repo_user_seq;

alter table repo_like add constraint fk_repo_like_user_id foreign key (user_id) references repo_user (id) on delete restrict on update restrict;
create index ix_repo_like_user_id on repo_like (user_id);

alter table repo_like add constraint fk_repo_like_repo_id foreign key (repo_id) references repo (id) on delete restrict on update restrict;
create index ix_repo_like_repo_id on repo_like (repo_id);

alter table repo add constraint fk_repo_owner_id foreign key (owner_id) references repo_user (id) on delete restrict on update restrict;
create index ix_repo_owner_id on repo (owner_id);

insert into repo_user (id, login, name) values (nextval('repo_user_seq'), 'mike', 'Michael');
insert into repo_user (id, login, name) values (nextval('repo_user_seq'), 'sparky', 'Davide');
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'cracky', 'http://ya.ru', (select id from repo_user where login='mike'));
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'wacky', 'http://yandex.ru', (select id from repo_user where login='mike'));
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'plop', 'http://google.ru', (select id from repo_user where login='sparky'));
insert into repo_like (repo_id, user_id) values ((select id from repo_user where login='mike'), (select id from repo where name='cracky'));

# --- !Downs

alter table repo_like drop constraint if exists fk_repo_like_user_id;
drop index if exists ix_repo_like_user_id;

alter table repo_like drop constraint if exists fk_repo_like_repo_id;
drop index if exists ix_repo_like_repo_id;

alter table repo drop constraint if exists fk_repo_owner_id;
drop index if exists ix_repo_owner_id;

drop table if exists repo_like cascade;

drop table if exists repo cascade;
drop sequence if exists repo_seq;

drop table if exists repo_user cascade;
drop sequence if exists repo_user_seq;
