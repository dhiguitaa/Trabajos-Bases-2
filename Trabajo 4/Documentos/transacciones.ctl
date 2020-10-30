load data into table transaccion
insert
fields terminated by ";"
(
block_id,
index_trans,
hash_trans,
time_trans,
failed,
type_trans,
sender,
recipient,
call_count,
value_trnas,
value_usd,
internal_value,
internal_value_usd,
fee,
fee_usd,
gas_used,
gas_limit,
gas_price,
input_hex char(4000),
nonce,
v,
r,
s,
x,
y 
)