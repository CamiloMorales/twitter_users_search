# --- !Ups

INSERT INTO speaker(id, name, email, bio, picture_url, twitter_id)
values
(1, 
 'Name 1',
 'name_1@name_1.com',
 'Bio 1',
 'https://secure.gravatar.com/name_1',
 'twitter_name_1'
 );

INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id)
values
('Title 1', 
 'Proposal 1',
  0,
  true,
 'keyword_1_1, keyword_1_2',
  1
 );
 
INSERT INTO speaker(id, name, email, bio, picture_url, twitter_id)
values
(2, 
 'Name 2',
 'name_2@name_2.com',
 'Bio 2',
 'https://secure.gravatar.com/name_2',
 'twitter_name_2'
 );

INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id)
values
('Title 2', 
 'Proposal 2',
  1,
  true,
 'keyword_2_1, keyword_2_2',
  2
 );
 
INSERT INTO speaker(id, name, email, bio, picture_url, twitter_id)
values
(3, 
 'Name 3',
 'name_3@name_3.com',
 'Bio 3',
 'https://secure.gravatar.com/name_3',
 'twitter_name_3'
 );

INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id)
values
('Title 3', 
 'Proposal 3',
  1,
  true,
 'keyword_3_1, keyword_3_2',
  3
 );
 
INSERT INTO speaker(id, name, email, bio, picture_url, twitter_id)
values
(4, 
 'Name 4',
 'name_4@name_4.com',
 'Bio 4',
 'https://secure.gravatar.com/name_4',
 'twitter_name_4'
 );
 
INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id)
values
('Title 4', 
 'Proposal 4',
  0,
  true,
 'keyword_4_1, keyword_4_2',
  4
 );

INSERT INTO speaker(id, name, email, bio, picture_url, twitter_id)
values
(5, 
 'Name 5',
 'name_5@name_5.com',
 'Bio 5',
 'https://secure.gravatar.com/name_5',
 'twitter_name_5'
 );

INSERT INTO proposal(title, proposal, type, is_approved, keywords, speaker_id)
values
('Title 5', 
 'Proposal 5',
  1,
  true,
 'keyword_5_1, keyword_5_2',
  5
 );