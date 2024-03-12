insert into t_user(id, c_username, c_password)
values (1, 'admin', '{noop}admin');

insert into t_user(id, c_username, c_password)
values (2, 'posts', '{noop}posts');

insert into t_user(id, c_username, c_password)
values (3, 'users', '{noop}users');

insert into t_user(id, c_username, c_password)
values (4, 'albums', '{noop}albums');

insert into t_user_authority(id_user, c_authority)
values (1, 'ROLE_ADMIN');

insert into t_user_authority(id_user, c_authority)
values (2, 'ROLE_POSTS');

insert into t_user_authority(id_user, c_authority)
values (3, 'ROLE_USERS');

insert into t_user_authority(id_user, c_authority)
values (4, 'ROLE_ALBUMS');