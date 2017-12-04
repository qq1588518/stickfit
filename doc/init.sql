delete from month_standard;
ALTER TABLE month_standard AUTO_INCREMENT = 1;
insert into exercise_type_po(`name`,`description`,`min`,`unit`,`order`) values 
('跑步','跑步',6,'公里',1),
('走步','走步',10000,'步',2),
('游泳','游泳',1000,'米',3),
('骑车','骑车',12,'公里',4),
('其他','其他运动',45,'分钟',99);

delete from group_po;
ALTER TABLE group_po AUTO_INCREMENT = 1;
insert into group_po(name, owner_id) values('未分组', 0);
insert into group_po(name, owner_id) values('下一马', 3);

delete from month_standard;
ALTER TABLE month_standard AUTO_INCREMENT = 1;
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(1, 201709, 'Count', '15', null);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(1, 201710, 'JogAmountOrMix', '180,100,15,9', null);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(1, 201711, 'JogAmountOrMix', '180,110,15,9', 12);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(1, 201712, 'JogAmountOrMix', '180,110,15,9', 21);

delete from customer_status_po;
ALTER TABLE customer_status_po AUTO_INCREMENT = 1;
insert into customer_status_po(customer_id, group_id, month, status_enum) values(21, 2, 201710, 'LEAVE');
insert into customer_status_po(customer_id, group_id, month, status_enum) values(20, 2, 201711, 'LEAVE');
insert into customer_status_po(customer_id, group_id, month, status_enum) values(16, 2, 201711, 'LEAVE');
insert into customer_status_po(customer_id, group_id, month, status_enum) values(16, 2, 201712, 'LEAVE');
insert into customer_status_po(customer_id, group_id, month, status_enum) values( 5, 2, 201712, 'LEAVE');
