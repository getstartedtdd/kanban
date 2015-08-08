drop table if exists oauth_client_details;  
drop table if exists oauth_access_token;  
drop table if exists oauth_refresh_token;  
drop table if exists oauth_code;  
  
create table oauth_client_details (  
  client_id VARCHAR(50) PRIMARY KEY,  
  resource_ids VARCHAR(256),  
  client_secret VARCHAR(256),  
  scope VARCHAR(256),  
  authorized_grant_types VARCHAR(256),  
  web_server_redirect_uri VARCHAR(256),  
  authorities VARCHAR(256)  
);  
  
create table oauth_access_token (  
  token_id VARCHAR(256),  
  token blob,  
  authentication_id VARCHAR(256),  
  authentication blob,  
  refresh_token VARCHAR(256)  
);  
  
create table oauth_refresh_token (  
  token_id VARCHAR(256),  
  token blob,  
  authentication blob  
);  
  
create table oauth_code (  
  code VARCHAR(256),  
  authentication blob  
);  
  
insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,authorities)   
values('client','sparklr','secret','read,write','authorization_code','ROLE_CLIENT');  