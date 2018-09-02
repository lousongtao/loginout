#update the table structure
ALTER TABLE `zheng`.`upms_user`
CHANGE COLUMN `username` `loginname` VARCHAR(40) NOT NULL COMMENT '帐号' ,
CHANGE COLUMN `ctime` `create_time` BIGINT(20) NULL DEFAULT NULL COMMENT '创建时间' ,
ADD COLUMN `work_type` TINYINT(4) NULL DEFAULT 0 COMMENT 'work type(0=full time; 1=parttime; 2=Casual)' AFTER `type`,
ADD COLUMN `birth` BIGINT(10) NULL AFTER `work_type`,
ADD COLUMN `start_workday` BIGINT(10) NULL AFTER `birth`,
ADD COLUMN `visa_type` VARCHAR(20) NULL AFTER `start_workday`,
ADD COLUMN `visa_expire` BIGINT(10) NULL AFTER `visa_type`;

CREATE TABLE `zheng`.`mc_branch` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `branch_name` VARCHAR(45) NOT NULL,
  `gps_x` FLOAT NULL,
  `gps_y` FLOAT NULL,
  `user_id` INT(10) NOT NULL,
  PRIMARY KEY (`id`))
COMMENT = 'the different shops for one customer';

UPDATE `zheng`.`upms_system` SET `title`='Permission System' WHERE `system_id`='1';
UPDATE `zheng`.`upms_system` SET `title`='User System' WHERE `system_id`='4';

delete from `zheng`.`upms_permission` where id > 0;
INSERT INTO `zheng`.`upms_permission` VALUES (1,1,0,'System Management',1,'','','zmdi zmdi-accounts-list',1,1,1),(2,1,1,'System',2,'upms:system:read','/manage/system/index','',1,2,2),(3,1,1,'Organization',2,'upms:organization:read','/manage/organization/index','',1,3,3),(4,1,0,'Member',1,'','','zmdi zmdi-accounts',1,4,4),(5,1,4,'Member',2,'upms:role:read','/manage/role/index','',1,6,6),(6,1,4,'User',2,'upms:user:read','/manage/user/index','',1,5,5),(7,1,0,'Permission',1,'','','zmdi zmdi-lock-outline',1,7,7),(12,1,0,'Others',1,'','','zmdi zmdi-more',1,12,12),(14,1,12,'Session',2,'upms:session:read','/manage/session/index','',1,14,14),(15,1,12,'Log',2,'upms:log:read','/manage/log/index','',1,15,15),(24,1,2,'Add',3,'upms:system:create','/manage/system/create','zmdi zmdi-plus',1,24,24),(25,1,2,'Update',3,'upms:system:update','/manage/system/update','zmdi zmdi-edit',1,25,25),(26,1,2,'Delete',3,'upms:system:delete','/manage/system/delete','zmdi zmdi-close',1,26,26),(27,1,3,'Add',3,'upms:organization:create','/manage/organization/create','zmdi zmdi-plus',1,27,27),(28,1,3,'Update',3,'upms:organization:update','/manage/organization/update','zmdi zmdi-edit',1,28,28),(29,1,3,'Delete',3,'upms:organization:delete','/manage/organization/delete','zmdi zmdi-close',1,29,29),(30,1,6,'Add',3,'upms:user:create','/manage/user/create','zmdi zmdi-plus',1,30,30),(31,1,6,'Update',3,'upms:user:update','/manage/user/update','zmdi zmdi-edit',1,31,31),(32,1,6,'Delete',3,'upms:user:delete','/manage/user/delete','zmdi zmdi-close',1,32,32),(33,1,5,'Add',3,'upms:role:create','/manage/role/create','zmdi zmdi-plus',1,33,33),(34,1,5,'Update',3,'upms:role:update','/manage/role/update','zmdi zmdi-edit',1,34,34),(35,1,5,'Delete',3,'upms:role:delete','/manage/role/delete','zmdi zmdi-close',1,35,35),(36,1,39,'Add',3,'upms:permission:create','/manage/permission/create','zmdi zmdi-plus',1,36,36),(37,1,39,'Update',3,'upms:permission:update','/manage/permission/update','zmdi zmdi-edit',1,37,37),(38,1,39,'Delete',3,'upms:permission:delete','/manage/permission/delete','zmdi zmdi-close',1,38,38),(39,1,7,'Permission',2,'upms:permission:read','/manage/permission/index',NULL,1,39,39),(46,1,5,'Role Permission',3,'upms:role:permission','/manage/role/permission','zmdi zmdi-key',1,1488091928257,1488091928257),(48,1,6,'Organization',3,'upms:user:organization','/manage/user/organization','zmdi zmdi-accounts-list',1,1488120011165,1488120011165),(50,1,6,'Role',3,'upms:user:role','/manage/user/role','zmdi zmdi-accounts',1,1488120554175,1488120554175),(51,1,6,'Permission',3,'upms:user:permission','/manage/user/permission','zmdi zmdi-key',1,1488092013302,1488092013302),(53,1,14,'Force Logout',3,'upms:session:forceout','/manage/session/forceout','zmdi zmdi-run',1,1488379514715,1488379514715),(57,1,15,'Delete',3,'upms:log:delete','/manage/log/delete','zmdi zmdi-close',1,1489503867909,1489503867909),(86,4,0,'Staff',1,'','','zmdi zmdi-accounts',1,1530595340779,1530595340779),(87,4,86,'Staff',2,'ucenter:staff:read','/manage/staff/index','zmdi zmdi-widgets',1,1530595492374,1530595492374),(88,4,87,'Add',3,'ucenter:staff:write','/manage/staff/create','zmdi zmdi-plus',1,1530595659153,1530595659153),(89,4,87,'Delete',3,'ucenter:staff:delete','/manage/staff/delete','zmdi zmdi-close',1,1530595804860,1530595804860),(90,4,87,'Update',3,'ucenter:staff:write','/manage/staff/update','zmdi zmdi-edit',1,1530595839982,1530595839982),(91,4,86,'Group',2,'ucenter:group:read','/manage/group/index','zmdi zmdi-widgets',1,1530696583298,1530696583298),(92,4,91,'Add',3,'ucenter:group:create','/manage/group/create','zmdi zmdi-plus',1,1530696675946,1530696675946),(93,4,91,'Delete',3,'ucenter:group:delete','/manage/group/delete','zmdi zmdi-close',1,1530697058963,1530697058963),(94,4,91,'Update',3,'ucenter:group:update','/manage/group/update','zmdi zmdi-edit',1,1530697134567,1530697134567),(95,4,0,'Schedule',1,'','','zmdi zmdi-calendar',1,1531205108710,1531205108710),(96,4,95,'Workforce',2,'ucenter:shift:read','/manage/shift/index','zmdi zmdi-widgets',1,1531205308436,1531205308436),(98,4,95,'Schedule',2,'ucenter:scheduling:read','/manage/scheduling/index','zmdi zmdi-widgets',1,1531205520896,1531205520896),(99,4,0,'Checking',1,'','','zmdi zmdi-check-all',1,1531205694321,1531205694321),(100,4,96,'Add',3,'ucenter:shift:create','/manage/shfit/create','zmdi zmdi-plus',1,1531211104719,1531211104719),(101,4,96,'Update',3,'ucenter:shift:update','/manage/shfit/update','zmdi zmdi-edit',1,1531211164796,1531211164796),(102,4,96,'Delete',3,'ucenter:shift:delete','/manage/shift/delete','zmdi zmdi-close',1,1531211204357,1531211204357),(103,4,98,'Schedule',3,'ucenter:scheduling:write','/manage/scheduling/update','zmdi zmdi-edit',1,1531211447584,1531211447584),(104,4,99,'SignIn',2,'ucenter:sign:read','/manage/sign/index','zmdi zmdi-widgets',1,1531278844615,1531278844615),(105,4,87,'Group',3,'ucenter:staff:group','/manage/staff/group','zmdi zmdi-accounts-list',1,1531818590902,1531818590902),(106,4,104,'Signin Stat',3,'ucenter:sign:write','/manage/sign/signIn','zmdi zmdi-widgets',1,1533282578515,1533282578515),(107,4,99,'My Schedule',2,'ucenter:staff:schedule','/manage/staff/schedule','zmdi zmdi-widgets',1,1533288099073,1533288099073),(108,4,99,'AttendanceManagement',2,'ucenter:attendance:read','/manage/attendance/index','zmdi zmdi-widgets',1,1534391246163,1534391246163),(109,4,108,'Update Login Record',3,'ucenter:attendance:write','/manage/attendance/update','zmdi zmdi-edit',1,1534391419740,1534391419740),(110,4,0,'Branch Management',1,'','','zmdi zmdi-widgets',1,1535092089152,1535092089152),(111,4,110,'Branch',2,'ucenter:branch:read','/manage/branch/index','zmdi zmdi-widgets',1,1535092260863,1535092260863),(112,4,111,'Add',3,'ucenter:branch:add','/manage/branch/create','zmdi zmdi-widgets',1,1535092308449,1535092308449),(113,4,111,'Update',3,'ucenter:branch:update','/manage/branch/update','zmdi zmdi-widgets',1,1535092486484,1535092486484),(114,4,111,'Delete',3,'ucenter:branch:delete','/manage/branch/delete','zmdi zmdi-widgets',1,1535092523898,1535092523898);

ALTER TABLE `zheng`.`mc_user_sign`
CHANGE COLUMN `extend1` `sign_gpsx` FLOAT NULL DEFAULT NULL COMMENT 'GPS X' ,
CHANGE COLUMN `extend2` `sign_gpsy` FLOAT NULL DEFAULT NULL COMMENT 'GPS Y' ,
CHANGE COLUMN `extend3` `sign_via` VARCHAR(30) NULL DEFAULT NULL COMMENT 'sign tool, 0=browser; 1=phone app;' ;

alter table `zheng`.`mc_schedule_plan`
add column `branch_id` INT(11) NOT NULL after `update_time`;
