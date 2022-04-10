SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- begin
-- -------
-- begin

show databases;
create database if not exists springboot DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_unicode_ci;
use springboot;
show tables;
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
    PRIMARY KEY (`id`),
    index (`company`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

-- end
-- -------
-- end

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

show tables;