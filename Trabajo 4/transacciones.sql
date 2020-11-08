drop table transaccion;
create table transaccion(
block_id number(38),
index_trans varchar2(4000),
hash_trans varchar2(4000),
time_trans varchar2(4000),
failed varchar2(4000),
type_trans varchar2(4000),
sender varchar2(4000),
recipient varchar2(4000),
call_count varchar2(4000),
value_trnas varchar2(4000),
value_usd varchar2(4000),
internal_value varchar2(4000),
internal_value_usd varchar2(4000),
fee varchar2(4000),
fee_usd varchar2(4000),
gas_used varchar2(4000),
gas_limit varchar2(4000),
gas_price varchar2(4000),
input_hex varchar2(4000),
nonce varchar2(4000),
v varchar2(4000),
r varchar2(4000),
s varchar2(4000),
x number(10),
y number(10)
);
commit;
