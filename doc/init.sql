insert into exercise_type_po(`name`,`description`,`min`,`unit`,`order`) values 
('跑步','跑步',6,'公里',1),
('走步','走步',10000,'步',2),
('游泳','游泳',1000,'米',3),
('骑车','骑车',12,'公里',4),
('其他','其他运动',45,'分钟',99);


insert into month_standard(group_id, month, standard_enum, standard_param) values(1, 201709, 'Count', '15');
insert into month_standard(group_id, month, standard_enum, standard_param) values(1, 201710, 'JogAmountOrMix', '180,100,15,9');
insert into month_standard(group_id, month, standard_enum, standard_param) values(1, 201711, 'JogAmountOrMix', '180,110,15,9');
insert into month_standard(group_id, month, standard_enum, standard_param) values(1, 201712, 'JogAmountOrMix', '180,110,15,9');
