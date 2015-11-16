# --- !Ups

create table repo (
  id                            bigint not null,
  name                          varchar(255),
  url                           varchar(255),
  owner_id                      bigint,
  constraint pk_repo primary key (id)
);
create sequence repo_seq;

create table repo_user (
  id                            bigint not null,
  login                         varchar(255),
  constraint pk_repo_user primary key (id)
);
create sequence repo_user_seq;

alter table repo add constraint fk_repo_owner_id foreign key (owner_id) references repo_user (id) on delete restrict on update restrict;
create index ix_repo_owner_id on repo (owner_id);

insert into repo_user (id, login) values (nextval('repo_user_seq'), 'mike');
insert into repo_user (id, login) values (nextval('repo_user_seq'), 'sparky');
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'cracky', 'http://ya.ru', (select id from repo_user where login='mike'));
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'wacky', 'http://yandex.ru', (select id from repo_user where login='mike'));
insert into repo (id, name, url, owner_id) values (nextval('repo_seq'), 'plop', 'http://google.ru', (select id from repo_user where login='sparky'));

# --- !Downs

alter table repo drop constraint if exists fk_repo_owner_id;
drop index if exists ix_repo_owner_id;

drop table if exists repo cascade;
drop sequence if exists repo_seq;

drop table if exists repo_user cascade;
drop sequence if exists repo_user_seq;
