insert into ACCT (ID,VERSION,NAME,FID,OFX_ACCT_ID) values (3317,1,'read ira','1111','4094');
insert into ACCT (ID,VERSION,NAME,FID,OFX_ACCT_ID) values (2401,6,'save uju','6465','0620');

insert into TRAN (ID,VERSION,ACCT_ID,NAME,FITID) values (5144,6,3317,'read tki','2714');
insert into TRAN (ID,VERSION,ACCT_ID,NAME,FITID) values (8909,2,2401,'save jma','6159');

insert into PAYEE (ID,VERSION,NAME) values (748,4,'read dxk');
insert into PAYEE (ID,VERSION,NAME) values (255,4,'save jog');
insert into PAYEE (ID,VERSION,NAME) values (3562,4,'filter ahq');
insert into PAYEE (ID,VERSION,NAME) values (9217,6,'filter tna');
insert into PAYEE (ID,VERSION,NAME) values (5165,1,'filter ddk');
insert into PAYEE (ID,VERSION,NAME) values (923,4,'filter wli');
insert into PAYEE (ID,VERSION,NAME) values (1860,3,'filter agy');
insert into PAYEE (ID,VERSION,NAME) values (3119,3,'filter tqi');
insert into PAYEE (ID,VERSION,NAME) values (6414,6,'filter gsi');
insert into PAYEE (ID,VERSION,NAME) values (6861,6,'filter lvy');
insert into PAYEE (ID,VERSION,NAME) values (8818,2,'filter leo');
insert into PAYEE (ID,VERSION,NAME) values (376,4,'filter bue');

insert into PAYABLE (ID,VERSION,PAYEE_ID,MEMO) values (5376,1,748,'read ice');
insert into PAYABLE (ID,VERSION,PAYEE_ID,MEMO) values (1897,4,255,'save ehm');

insert into PAYEE (ID,VERSION,CRON_EXPRESSION,NBR_EST_TO_CREATE,EST_AMOUNT) values (6962,6,'0 0 0 20 * ?',2,38.59);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT) values (4577,1,6962,'2016-02-20',38.59);
