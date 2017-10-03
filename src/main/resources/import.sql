insert into inst (id,version,organization,fid) values (1,0,'bank of foo','012');
insert into inst (id,version,organization,fid) values (2,0,'bank of bar','789');
insert into inst (id,version,organization,fid) values (3,0,'bank of baz','456');
insert into inst (id,version,organization,fid) values (4,0,'bank of qux','123');

insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (1,0,4,'1029384001','foo','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (2,0,3,'1029384002','bar','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (3,0,2,'1029384003','foobar','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (4,0,1,'1029384004','baz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (5,0,null,'1029384005','foobaz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (6,0,4,'1029384006','barbaz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (7,0,3,'1029384007','foobarbaz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (8,0,2,'1029384008','qux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (9,0,1,'1029384009','fooqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (10,0,null,'1029384010','barqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (11,0,4,'1029384011','foobarqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (12,0,3,'1029384012','bazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (13,0,2,'1029384013','foobazqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (14,0,1,'1029384014','barbazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (15,0,null,'1029384015','foobarbazqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (101,0,4,'1029384101','foo','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (102,0,3,'1029384102','bar','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (103,0,2,'1029384103','foobar','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (104,0,1,'1029384104','baz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (105,0,null,'1029384105','foobaz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (106,0,4,'1029384106','barbaz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (107,0,3,'1029384107','foobarbaz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (108,0,2,'1029384108','qux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (109,0,1,'1029384109','fooqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (110,0,null,'1029384110','barqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (111,0,4,'1029384111','foobarqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (112,0,3,'1029384112','bazqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (113,0,2,'1029384113','foobazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (114,0,1,'1029384114','barbazqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (115,0,null,'1029384115','foobarbazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (201,0,4,'1029384201','foo','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (202,0,3,'1029384202','bar','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (203,0,2,'1029384203','foobar','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (204,0,1,'1029384204','baz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (205,0,null,'1029384205','foobaz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (206,0,4,'1029384206','barbaz','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (207,0,3,'1029384207','foobarbaz','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (208,0,2,'1029384208','qux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (209,0,1,'1029384209','fooqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (210,0,null,'1029384210','barqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (211,0,4,'1029384211','foobarqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (212,0,3,'1029384212','bazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (213,0,2,'1029384213','foobazqux','123','456','CHECKING','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (214,0,1,'1029384214','barbazqux','123','456','CC','2016-12-31',12345.67);
insert into acct (id,version,inst_id,acct_nbr,name,ofx_bank_id,ofx_acct_id,type,begin_date,begin_balance) values (215,0,null,'1029384215','foobarbazqux','123','456','CHECKING','2016-12-31',12345.67);

insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (1,0,'foo.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (2,0,'bar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (3,0,'foobar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (4,0,'baz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (5,0,'foobaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (6,0,'barbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (7,0,'foobarbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (8,0,'qux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (9,0,'fooqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (10,0,'barqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (11,0,'foobarqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (12,0,'bazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (13,0,'foobazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (14,0,'barbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (15,0,'foobarbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (101,0,'foo.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (102,0,'bar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (103,0,'foobar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (104,0,'baz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (105,0,'foobaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (106,0,'barbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (107,0,'foobarbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (108,0,'qux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (109,0,'fooqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (110,0,'barqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (111,0,'foobarqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (112,0,'bazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (113,0,'foobazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (114,0,'barbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (115,0,'foobarbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (201,0,'foo.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (202,0,'bar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (203,0,'foobar.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (204,0,'baz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (205,0,'foobaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (206,0,'barbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (207,0,'foobarbaz.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (208,0,'qux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (209,0,'fooqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (210,0,'barqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (211,0,'foobarqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (212,0,'bazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (213,0,'foobazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (214,0,'barbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');
insert into ofx_file (id,version,original_filename,content_type,size,upload_timestamp) values (215,0,'foobarbazqux.ofx','application/octet-stream',1234,'2017-01-23 12:34:56.789');