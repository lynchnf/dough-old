insert into ACCT (ID,VERSION,NAME,FID,OFX_ACCT_ID) values (3317,1,'read ira','1111','4094');
insert into ACCT (ID,VERSION,NAME,FID,OFX_ACCT_ID) values (2401,6,'save uju','6465','0620');
insert into ACCT (ID,VERSION,NAME,FID,OFX_ACCT_ID,TYPE,BEGIN_DATE,BEGIN_BALANCE) values (7399,4,'zzz acct balance','6518','5629','CHECKING','2015-05-10',7057.02);

insert into TRAN (ID,VERSION,ACCT_ID,NAME,FITID) values (5144,6,3317,'read tki','2714');
insert into TRAN (ID,VERSION,ACCT_ID,NAME,FITID) values (8909,2,2401,'save jma','6159');
insert into TRAN (ID,VERSION,ACCT_ID,POST_DATE,AMOUNT,FITID) values (4713,1,7399,'2015-05-19',-82.04,'0202');
insert into TRAN (ID,VERSION,ACCT_ID,POST_DATE,AMOUNT,FITID) values (9008,5,7399,'2015-05-27',51.25,'3157');
insert into TRAN (ID,VERSION,ACCT_ID,POST_DATE,AMOUNT,FITID) values (1346,3,7399,'2015-06-09',92.38,'1547');

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

insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,MEMO) values (5376,1,748,'2015-12-31',71.58,'read ice');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,MEMO) values (1897,4,255,'2016-01-01',42.13,'save ehm');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (1171,3,3562,'2016-01-30',98.42,null,null,'filter ssh',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (6730,4,9217,'2015-12-30',33.58,'2015-12-25',82.64,'filter whp',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (2715,3,5165,'2016-03-02',47.36,'2016-03-03',57.59,'filter xgq','2016-03-03',57.59,'confirm uem');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (5735,3,923,'2016-02-02',57.39,'2016-01-26',98.66,'filter iwr','2016-01-26',98.66,'confirm apr');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (9912,4,1860,'2016-01-23',62.05,'2016-01-23',25.88,'filter ufv',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (7655,5,3119,'2016-01-03',43.18,null,null,'filter fsw',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (2300,6,6414,'2016-03-22',57.29,'2016-03-28',67.20,'filter qzc','2016-03-28',67.20,'confirm zic');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (2949,3,6861,'2016-02-15',3.58,null,null,'filter ely',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (6817,4,8818,'2016-03-19',11.87,'2016-03-14',90.76,'filter qwm','2016-03-14',90.76,'confirm duv');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (5878,4,376,'2016-01-27',85.04,'2016-01-22',87.52,'filter wep','2016-01-22',87.52,'confirm rkl');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (5579,2,3562,'2016-01-24',61.28,null,null,'filter nwc',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (6801,3,9217,'2016-02-09',54.24,'2016-02-08',39.54,'filter ytx','2016-02-08',39.54,'confirm eph');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (334,4,5165,'2016-01-29',2.13,'2016-01-25',30.06,'filter drt','2016-01-25',30.06,'confirm fcr');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (4440,4,923,'2016-03-01',9.10,'2016-02-27',60.15,'filter fub',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (1483,5,1860,'2016-01-12',55.99,null,null,'filter cta',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (673,3,3119,'2016-02-01',34.19,'2016-02-02',76.81,'filter iup','2016-02-02',76.81,'confirm pjv');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (5676,5,6414,'2016-02-14',75.43,'2016-02-10',60.74,'filter woj',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (7929,1,6861,'2016-02-05',79.44,'2016-02-05',56.56,'filter sts','2016-02-05',56.56,'confirm ybe');
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (6307,2,8818,'2016-03-04',80.97,null,null,'filter sdn',null,null,null);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT,ACT_DUE_DATE,ACT_AMOUNT,MEMO,PAID_DATE,PAID_AMOUNT,CONFIRM_CODE)values (429,4,376,'2016-01-30',59.87,'2016-01-28',74.70,'filter tnf','2016-01-28',74.70,'confirm bnv');

insert into PAYEE (ID,VERSION,NAME,CRON_EXPRESSION,NBR_EST_TO_CREATE,EST_AMOUNT) values (6962,6,'cron wtp','0 0 0 20 * ?',2,38.59);
insert into PAYABLE (ID,VERSION,PAYEE_ID,EST_DUE_DATE,EST_AMOUNT) values (4577,1,6962,'2016-02-20',38.59);
