show databases;
CREATE SCHEMA IF NOT EXISTS springboot;
DROP TABLE IF EXISTS user;
create table user
(

    id             BIGINT      NOT NULL auto_increment COMMENT '系统内ID,不用来查询',
    github_id      BIGINT      NOT NULL UNIQUE COMMENT 'github的ID,数字',
    github_node_id VARCHAR(32) NOT NULL UNIQUE COMMENT 'github的nodeId,字符串',
    login          VARCHAR(32) NOT NULL UNIQUE COMMENT '登录名',
    username       VARCHAR(32) NOT NULL UNIQUE COMMENT '用户名',
    company        VARCHAR(32) COMMENT '公司',
    blog           VARCHAR(64) COMMENT '博客地址',
    location       VARCHAR(64) COMMENT '地址',
    email          VARCHAR(64) COMMENT '邮件',
    bio            VARCHAR(128) COMMENT '简介',
    PRIMARY KEY (`id`)
);

-- end
-- -------
-- end