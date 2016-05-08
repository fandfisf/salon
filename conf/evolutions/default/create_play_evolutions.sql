-- CREDIT : https://github.com/debiki/debiki-dao-rdb/blob/master/scripts/create-play_evolutions-table.sql
CREATE TABLE if not exists play_evolutions (
    id integer NOT NULL,
    hash varchar2(255) NOT NULL,
    applied_at timestamp  NOT NULL,
    apply_script varchar2(65535),
    revert_script varchar2(65535),
    state varchar2(255),
    last_problem varchar2(65535)
);
