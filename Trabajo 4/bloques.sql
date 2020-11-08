drop table bloque;
CREATE TABLE bloque(
id NUMBER(38) PRIMARY KEY,
hash_bloq Varchar2(4000),
time_bloq varchar2(4000),
size_bloq Varchar2(4000),
miner Varchar2(4000),
extra_data_hex Varchar2(4000),
difficulty Varchar2(4000),
gas_used Varchar2(4000),
gas_limit Varchar2(4000),
logs_bloom Varchar2(4000),
mix_hash Varchar2(4000),
nonce Varchar2(4000),
receipts_root Varchar2(4000),
sha3_uncles Varchar2(4000),
state_root varchar2(4000),
total_difficulty Varchar2(4000),
transactions_root Varchar2(4000),
uncle_count Varchar2(4000),
transaction_count Varchar2(4000),
synthetic_transaction_count Varchar2(4000),
call_count Varchar2(4000),
synthetic_call_count Varchar2(4000),
value_total Varchar2(4000),
value_total_usd Varchar2(4000),
internal_value_total Varchar2(4000),
internal_value_total_usd Varchar2(4000),
generation Varchar2(4000),
generation_usd Varchar2(4000),
uncle_generation Varchar2(4000),
uncle_generation_usd Varchar2(4000),
fee_total Varchar2(4000),
fee_total_usd Varchar2(4000),
reward Varchar2(4000),
reward_usd Varchar2(4000)
);
commit;
