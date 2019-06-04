INSERT INTO `oauth_client_details`(
  `client_id`,
  `resource_ids`,
  `client_secret`,
  `scope`,
  `authorized_grant_types`,
  `web_server_redirect_uri`,
  `authorities`,
  `access_token_validity`,
  `refresh_token_validity`,
  `additional_information`,
  `autoapprove`
  )

  VALUES(
  'client',
  null,
  '{bcrypt}$2a$10$iP9ejueOGXO29.Yio7rqeuW9.yOC4YaV8fJp3eIWbP45eZSHFEwMG',
  'profile,read',
  'authorization_code,implicit,password,client_credentials,refresh_token',
  'http://localhost:8080/callback',
  null,
  3000,
  6000,
  null ,
  'false'
  );
INSERT INTO `oauth_client_details`(
  `client_id`,
  `resource_ids`,
  `client_secret`,
  `scope`,
  `authorized_grant_types`,
  `web_server_redirect_uri`,
  `authorities`,
  `access_token_validity`,
  `refresh_token_validity`,
  `additional_information`,
  `autoapprove`
  )

  VALUES(
  'myApp',
  null,
  '{bcrypt}$2a$10$cd6fXlFCHahiUDSZSlos0uXeVTT8UuHRfM.ZPSAmp0HRbdHgB19D6',
  'write,read',
  'authorization_code,implicit,password,refresh_token',
  'http://localhost:8080/callback',
  null,
  3000,
  6000,
  null ,
  'false'
);