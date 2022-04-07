DROP ALL OBJECTS DELETE FILES;
use `PUBLIC`;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`   int(10) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(64)    NOT NULL,
    `password`       varchar(128)       NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details`
(
    `id`  int(11) NOT NULL AUTO_INCREMENT ,
    `client_id`  varchar(32)  NOT NULL ,
    `resource_ids`  varchar(256)  NULL DEFAULT NULL ,
    `client_secret`  varchar(256)  NULL DEFAULT NULL ,
    `scope`  varchar(256)  NULL DEFAULT NULL ,
    `authorized_grant_types`  varchar(256)  NULL DEFAULT NULL ,
    `web_server_redirect_uri`  varchar(256)  NULL DEFAULT NULL ,
    `authorities`  varchar(256)  NULL DEFAULT NULL ,
    `access_token_validity`  int(11) NULL DEFAULT NULL ,
    `refresh_token_validity`  int(11) NULL DEFAULT NULL ,
    `additional_information`  varchar(4096)  NULL DEFAULT NULL ,
    `autoapprove`  varchar(256)  NULL DEFAULT NULL ,
    PRIMARY KEY (`id`)
);