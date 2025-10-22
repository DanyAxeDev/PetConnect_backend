-- Corrigir o tipo da coluna number na tabela pet
-- Converter de VARCHAR para INTEGER usando a cláusula USING
ALTER TABLE pet ALTER COLUMN number TYPE INTEGER USING number::integer;
