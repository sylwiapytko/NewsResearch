create table user_data(
	user_id 		bigint primary key,
	user_name		varchar(50),
	screen_name		varchar(50),
	created_at 		timestamp,
	retrieved_at	timestamp,
	user_url		text,
	linked_url		text,
	description 	text, 
	user_language 	varchar(3),
	user_location 	varchar(255),
	tweets_count 		integer,
	favourites_count 	integer,
	followers_count		integer,
	followings_count	integer,
	listed_count		integer,
	default_image		bool,
	default_profile		bool
);

create table tweet (
	tweet_id 		bigint primary key,
	user_id 		bigint references user_data(user_id),
	created_at 		timestamp,
	retrieved_at 	timestamp,
	url 			text,
	text_full 		text,
	tweet_source	text,
	tweet_language	varchar(3),
	possibly_sensitive bool,
	retweet_count	integer,
	like_count		integer,
	reply_count		integer,
	quote_count		integer,
	retweet_of_tweet bigint,
	reply_of_tweet	bigint,
	quote_of_tweet	bigint	
);

create table tweet_url (
	tweet_url_id 	integer primary key,
	tweet_id 		bigint references tweet(tweet_id),
	url_short		varchar(255),
	url_expanded	text
);
create table tweet_retweeters (
	tweet_retweeters_id 	integer primary key,
	tweet_id 		bigint references tweet(tweet_id),
	user_id			bigint references user_data(user_id)
);