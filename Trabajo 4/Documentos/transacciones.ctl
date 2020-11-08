load data into table transaccion
insert
fields terminated by ";"
(
block_id,
index_trans char(4000),
hash_trans char(4000),
time_trans char(4000),
failed char(4000),
type_trans char(4000),
sender char(4000),
recipient char(4000),
call_count char(4000),
value_trnas char(4000),
value_usd char(4000),
internal_value char(4000),
internal_value_usd char(4000),
fee char(4000),
fee_usd char(4000),
gas_used char(4000),
gas_limit char(4000),
gas_price char(4000),
input_hex char(4000),
nonce char(4000),
v char(4000),
r char(4000),
s char(4000),
x,
y 
)
