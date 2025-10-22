-- Corrigir apenas a constraint da tabela users_profile para adicionar CASCADE DELETE

-- Remover a constraint existente da tabela users_profile (sem CASCADE)
ALTER TABLE users_profile DROP CONSTRAINT IF EXISTS users_profile_user_id_fkey;

-- Recriar a constraint com CASCADE DELETE
ALTER TABLE users_profile 
ADD CONSTRAINT users_profile_user_id_fkey 
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
