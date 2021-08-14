
  CREATE TABLE "DB_1"."USERS" 
   (	"USER_ID" VARCHAR2(200 BYTE), 
	"USER_NAME" VARCHAR2(200 BYTE), 
	"PHONE_NO" VARCHAR2(200 BYTE), 
	"EMAIL_ID" VARCHAR2(200 BYTE), 
	"DOB" DATE,
	CONSTRAINT "USERS_PK" PRIMARY KEY ("USER_ID")
   );
   
Insert into DB_1.USERS (USER_ID,USER_NAME,PHONE_NO,EMAIL_ID,DOB) values ('1','Ricky Ponting','9900887765','ricky.ponting@ca.au',to_date('01-08-21 18:12:49','DD-MM-RR HH24:MI:SS'));
Insert into DB_1.USERS (USER_ID,USER_NAME,PHONE_NO,EMAIL_ID,DOB) values ('2','Brett Lee','6677887766','brett.lee@ca.au',to_date('01-08-21 18:12:56','DD-MM-RR HH24:MI:SS'));
Insert into DB_1.USERS (USER_ID,USER_NAME,PHONE_NO,EMAIL_ID,DOB) values ('3','Steve Waugh','9898787667','steve.waugh@ca.au',to_date('12-08-21 18:39:01','DD-MM-RR HH24:MI:SS'));
Insert into DB_1.USERS (USER_ID,USER_NAME,PHONE_NO,EMAIL_ID,DOB) values ('4','Steve Smith','8987678908','steve.smith@ca.au',to_date('01-08-21 18:39:29','DD-MM-RR HH24:MI:SS'));


