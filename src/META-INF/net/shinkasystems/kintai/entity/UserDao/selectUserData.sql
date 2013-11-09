select
	USER.ID,
	USER.AUTHORITY_ID,
	USER.USER_NAME,
	USER.PASSWORD,
	USER.DISPLAY_NAME,
	USER.ACTIVATED,
	USER.EXPIRE_DATE,
	USER.ROLE,
	AUTHORITY.DISPLAY_NAME
from
	USER
	inner join USER as AUTHORITY
		on USER.AUTHORITY_ID = AUTHORITY.ID

