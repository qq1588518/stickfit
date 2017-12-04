delete from group_po;
ALTER TABLE group_po AUTO_INCREMENT = 1;
insert into group_po(name, owner_id) values('未分组', 0);
insert into group_po(name, owner_id) values('下一马', 3);

delete from month_standard;
ALTER TABLE month_standard AUTO_INCREMENT = 1;
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(2, 201709, 'Count', '15', null);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(2, 201710, 'JogAmountOrMix', '180,100,15,9', null);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(2, 201711, 'JogAmountOrMix', '180,110,15,9', 12);
insert into month_standard(group_id, month, standard_enum, standard_param, pioneer_id) values(2, 201712, 'JogAmountOrMix', '180,110,15,9', 21);

update customer_po set group_id=2 where id in(1,3,4,5,7,8,9,10,11,12,13,14,15,16,19,20,21,31);
update exercise_po set group_id=2 where customer_id in(1,3,4,5,7,8,9,10,11,12,13,14,15,16,19,20,21,31);
