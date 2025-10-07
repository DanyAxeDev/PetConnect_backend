-- Insert the default profile "User" if it doesn't exist
INSERT INTO profile (nome) 
SELECT 'User' 
WHERE NOT EXISTS (SELECT 1 FROM profile WHERE nome = 'User');

INSERT INTO profile (nome) 
SELECT 'Admin' 
WHERE NOT EXISTS (SELECT 1 FROM profile WHERE nome = 'Admin');
